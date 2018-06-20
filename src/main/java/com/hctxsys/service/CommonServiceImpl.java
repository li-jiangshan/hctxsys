package com.hctxsys.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskAmountMoneyEntity;
import com.hctxsys.entity.YskIntegralDetailEntity;
import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.entity.YskReturnIntegralEntity;
import com.hctxsys.entity.YskSystemCostEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.entity.YskUserWealthDetailEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.AmountMoneyRepository;
import com.hctxsys.repository.IntegralDetailRepository;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.repository.ReturnIntegralRepository;
import com.hctxsys.repository.SystemCostRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.repository.UserWealthDetailRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;

@Service("CommonService")
public class CommonServiceImpl {
	@Autowired
	private UserWealthDetailRepository userWealthDetailRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private SystemCostRepository systemCostRepository;
	@Autowired
	private MoneyDetailRepository moneyDetailRepository;
	@Autowired
	private IntegralDetailRepository integralDetailRepository;
	@Autowired
	private ReturnIntegralRepository returnintegralrepository;
	@Autowired
	private AmountMoneyRepository amountmoneyrepository;
	@Autowired
	private UserRepository userrepository;
	@PersistenceContext
	public EntityManager em;

	/**
	 * 每日定时将用户积分转到积分明细表
	 * 
	 * @return
	 */
	@Transactional
	public Result cunUserWealthDetail() {

		Session session = (Session) em.getDelegate();
		session.setFlushMode(FlushMode.MANUAL);

		List<YskUserWealthEntity> userWealthList = userWealthRepository.findAll();
		YskUserWealthDetailEntity yskuserwealthdetailentity = new YskUserWealthDetailEntity();
		for (YskUserWealthEntity yskuserwealthentity : userWealthList) {
			// 时间戳
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();

			String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
			// int userId = yskuserwealthentity.getUid();
			// yskuserwealthdetailentity.setUserid(userId);
			// yskuserwealthdetailentity.setIntegral(yskuserwealthentity.getIntegral());
			BeanUtils.copyProperties(yskuserwealthentity, yskuserwealthdetailentity);
			yskuserwealthdetailentity.setCreateTime(Integer.valueOf(time));
			// userWealthDetailRepository.save(yskuserwealthdetailentity);
			session.save(yskuserwealthdetailentity);
			session.flush();
			session.clear();
		}
		return new Result();
	}

	/**
	 * 每日定时计算返现(加权)
	 * 
	 * @return
	 */
	@Transactional
	public String Reappearance() {
		// 获取前天零点
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Long qiantian = cal.getTimeInMillis() / 1000;
		// 获取前天23:59:59
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -2);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		Long yesterday = c.getTimeInMillis() / 1000;

		// 根据前天零点到昨天零点的条件查询平台费用表所有符合的数据
		List<YskSystemCostEntity> systemCostList = systemCostRepository
				.findAll(new Specification<YskSystemCostEntity>() {
					private static final long serialVersionUID = -5293116040876858751L;

					@Override
					public Predicate toPredicate(Root<YskSystemCostEntity> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						List<Predicate> predicates = new ArrayList<>();// 条件集合
						predicates.add(
								cb.between(root.get("createTime"), Long.valueOf(qiantian), Long.valueOf(yesterday)));
						Predicate[] pre = new Predicate[predicates.size()];
						return query.where(predicates.toArray(pre)).getRestriction();
					}
				});

		Sort sort = new Sort(Direction.DESC, "createTime");
		// 根据前天零点到昨天零点的条件查询发放钱数表所有符合的数据
		List<YskAmountMoneyEntity> amountmoneyList = amountmoneyrepository
				.findAll(new Specification<YskAmountMoneyEntity>() {
					private static final long serialVersionUID = -5293116040876858751L;

					@Override
					public Predicate toPredicate(Root<YskAmountMoneyEntity> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						List<Predicate> predicates = new ArrayList<>();// 条件集合
						predicates.add(
								cb.between(root.get("createTime"), Long.valueOf(qiantian), Long.valueOf(yesterday)));
						
						Predicate[] pre = new Predicate[predicates.size()];
						return query.where(predicates.toArray(pre)).getRestriction();
					}
				},sort);
		// 遍历查询到的平台费用表的集合，循环相加返现额度
		BigDecimal systemCostSum = new BigDecimal(0);
		for (YskSystemCostEntity ysksystemcostentity : systemCostList) {
			systemCostSum = systemCostSum.add(ysksystemcostentity.getReturnAmount());
		}
		// 判断发放钱数表是否有符合数据
		if (amountmoneyList.size() > 0) {
			// 遍历发放钱数表集合	按照时间倒序排列，取第一次循环的数据既为时间最晚的那条，循环第一条之后，for循环break。
			for (YskAmountMoneyEntity yskamountmoneyentity : amountmoneyList) {
				// 将平台费用和发放钱数相加
				systemCostSum = systemCostSum.add(yskamountmoneyentity.getAmountMoney());
				break;
			}
		}

		// 判断如果分子也就是前天的平台总收入为0 则不做任何操作
		if (systemCostSum.compareTo(BigDecimal.ZERO) == 0) {
		} else {
			// 根据前天零点到昨天零点的条件查询用户积分明细表所有符合的数据
			List<YskUserWealthDetailEntity> userWealthDetailList = userWealthDetailRepository
					.findAll(new Specification<YskUserWealthDetailEntity>() {
						private static final long serialVersionUID = 2720021466301138171L;

						@Override
						public Predicate toPredicate(Root<YskUserWealthDetailEntity> root, CriteriaQuery<?> query,
								CriteriaBuilder cb) {
							List<Predicate> predicates = new ArrayList<>();// 条件集合
							predicates.add(cb.between(root.get("createTime"), Long.valueOf(qiantian),
									Long.valueOf(yesterday)));
							Predicate[] pre = new Predicate[predicates.size()];
							return query.where(predicates.toArray(pre)).getRestriction();
						}
					});
			// 判断如果用户积分明细表没有符合数据，即分母为0，则不做操作
			if (userWealthDetailList.size() != 0) {
				// 遍历查询到的用户积分明细表的集合，循环相加积分
				BigDecimal userWealthDetailSum = new BigDecimal(0);
				for (YskUserWealthDetailEntity yskuserwealthdetailentity : userWealthDetailList) {
					userWealthDetailSum = userWealthDetailSum.add(yskuserwealthdetailentity.getIntegral());
				}
				// 积分除以100得到钱数
				BigDecimal faciend = new BigDecimal(Double.toString(100));
				userWealthDetailSum = userWealthDetailSum.divide(faciend, 2, BigDecimal.ROUND_HALF_UP);

				// 返现总和 除以 所有消费者钱数的和 得到比例
				BigDecimal ratio = new BigDecimal(0);
				ratio = systemCostSum.divide(userWealthDetailSum, 10, BigDecimal.ROUND_HALF_UP);

				// 判断如果得到比例如果比1大 则不做任何操作
				if (ratio.compareTo(BigDecimal.ONE) == 1 || ratio.compareTo(BigDecimal.ONE) == 0) {
				} else {

					// 提现钱数
					BigDecimal tixianqianshu = new BigDecimal(0);
					// 积分对应钱数
					BigDecimal jifenDuiyingqianshu = new BigDecimal(0);
					// 遍历查询到的用户积分明细表的集合
					for (YskUserWealthDetailEntity userWealthDetailEntity : userWealthDetailList) {
						// 判断积分是否为0
						if (userWealthDetailEntity.getIntegral().compareTo(BigDecimal.ZERO) == 0) {
						} else {
							// 将该用户的积分转为钱数
							jifenDuiyingqianshu = (userWealthDetailEntity.getIntegral()).divide(faciend, 2,
									BigDecimal.ROUND_HALF_UP);
							// 将该用户的 积分对应钱数 乘以 比例
							tixianqianshu = jifenDuiyingqianshu.multiply(ratio);

							// 根据uid主键查询这条数据
							YskUserWealthEntity userWealthEntity = userWealthRepository
									.findByUid(userWealthDetailEntity.getUid());
							
							YskUserEntity yskuserentity = userrepository.findById(userWealthDetailEntity.getUid()).get();

							// 判断如果当日的用户积分为零或者比应该扣除的积分少，则不做任何操作	如果该用户状态为0则不操作
							if (userWealthEntity.getIntegral().compareTo(BigDecimal.ZERO) == 0 || userWealthEntity
									.getIntegral().compareTo(tixianqianshu.multiply(new BigDecimal(100))) == -1 || yskuserentity.getStatus() == 0) {
							} else {

								// 查询ysk_return_integral表的所有数据
								List<YskReturnIntegralEntity> returnIntegralEntityList = returnintegralrepository
										.findAll();
								// 初始化实际提现钱数
								BigDecimal shijitixianqianshu = new BigDecimal(0);
								// 遍历集合获取积分转现金手续费百分比
								for (YskReturnIntegralEntity yskreturnintegralentity : returnIntegralEntityList) {
									// 计算实际提现钱数(实际提现钱数=提现钱数—(提现钱数*积分转现金手续费百分比))
									shijitixianqianshu = tixianqianshu.subtract(
											tixianqianshu.multiply((yskreturnintegralentity.getIntegralMoneyFee())
													.divide(faciend, 2, BigDecimal.ROUND_HALF_UP)));
								}
								// 钱数加上实际提现钱数
								userWealthEntity.setMoney(userWealthEntity.getMoney().add(shijitixianqianshu));
								// 积分减去对应积分
								userWealthEntity.setIntegral(userWealthEntity.getIntegral()
										.subtract(tixianqianshu.multiply(new BigDecimal(100))));

								// 获取当前日期
								Date day = new Date();
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								// 将String转为Timestamp类型
								Timestamp ts = new Timestamp(System.currentTimeMillis());
								ts = Timestamp.valueOf(df.format(day));
								// 为更新日期(时分秒)赋值
								userWealthEntity.setUptimeing(ts);
								// 获取当前日期
								Date dayUptime = new Date();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								// 为更新日期赋值
								userWealthEntity.setUptime(sdf.format(dayUptime));
								// 更新ysk_user_wealth表
								userWealthRepository.saveAndFlush(userWealthEntity);

								// 创建余额明细实体
								YskMoneyDetailEntity yskmoneydetailentity = new YskMoneyDetailEntity();
								// 给余额明细实体赋值
								yskmoneydetailentity.setOrderNo("");
								yskmoneydetailentity.setMoney(new BigDecimal(String.format("%.2f", shijitixianqianshu)));
								yskmoneydetailentity.setUid(userWealthEntity.getUid());
								yskmoneydetailentity.setType("integralToMoney");
								yskmoneydetailentity.setTypeName("积分转余额");
								yskmoneydetailentity.setStatus((byte) 1);
								// 时间戳
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
								Date date = new Date();
								String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
								yskmoneydetailentity.setCreateTime(Integer.valueOf(time));
								yskmoneydetailentity.setFromType((byte) 1);
								yskmoneydetailentity.setContent("积分转余额余额增加"
										+ String.format("%.2f", shijitixianqianshu));
								yskmoneydetailentity.setMoneyRecord(userWealthEntity.getMoney());
								// 向余额明细表插入数据
								moneyDetailRepository.saveAndFlush(yskmoneydetailentity);

								// 创建积分明细实体
								YskIntegralDetailEntity yskintegraldetailentity = new YskIntegralDetailEntity();
								// 给积分明细实体赋值
								yskintegraldetailentity.setMoney(new BigDecimal(String.format("%.2f", tixianqianshu.multiply(new BigDecimal(100)))));
								yskintegraldetailentity.setUid(userWealthEntity.getUid());
								yskintegraldetailentity.setType("integralToMoney");
								yskintegraldetailentity.setTypeName("积分转余额");
								yskintegraldetailentity.setStatus((byte) 1);
								yskintegraldetailentity.setCreateTime(Integer.valueOf(time));
								yskintegraldetailentity.setFromType((byte) 2);
								yskintegraldetailentity
										.setContent("积分转余额积分扣除" + String.format("%.2f", tixianqianshu.multiply(new BigDecimal(100))));
								yskintegraldetailentity.setMoneyRecord(userWealthEntity.getIntegral());
								// 向积分明细表插入数据
								integralDetailRepository.saveAndFlush(yskintegraldetailentity);
							}
						}
					}
				}
			}
			/*
			 * // 查询ysk_user_wealth表 List<YskUserWealthEntity> alluserwealthlist =
			 * userWealthRepository.findAll(); // 遍历集合 for(YskUserWealthEntity
			 * yskuserwealthentity : alluserwealthlist) { // 提现钱数 BigDecimal tixianqianshu =
			 * new BigDecimal(0); // 积分对应钱数 BigDecimal jifenDuiyingqianshu = new
			 * BigDecimal(0); // 判断积分是否为0
			 * if(yskuserwealthentity.getIntegral().compareTo(BigDecimal.ZERO)==0) { }else {
			 * // 将该用户的积分转为钱数 jifenDuiyingqianshu =
			 * (yskuserwealthentity.getIntegral()).divide(faciend,2,BigDecimal.ROUND_HALF_UP
			 * ); // 将该用户的 积分对应钱数 乘以 比例 tixianqianshu = jifenDuiyingqianshu.multiply(ratio);
			 * 
			 * // 根据uid主键查询这条数据 YskUserWealthEntity userWealthEntity =
			 * userWealthRepository.findByUid(yskuserwealthentity.getUid()); //
			 * 查询ysk_return_integral表的所有数据 List<YskReturnIntegralEntity>
			 * returnIntegralEntityList = returnintegralrepository.findAll(); // 初始化实际提现钱数
			 * BigDecimal shijitixianqianshu = new BigDecimal(0); // 遍历集合获取积分转现金手续费百分比
			 * for(YskReturnIntegralEntity yskreturnintegralentity :
			 * returnIntegralEntityList) { // 计算实际提现钱数(实际提现钱数=提现钱数—(提现钱数*积分转现金手续费百分比))
			 * shijitixianqianshu =
			 * tixianqianshu.subtract(tixianqianshu.multiply(yskreturnintegralentity.
			 * getIntegralMoneyFee())); // 钱数加上实际提现钱数
			 * userWealthEntity.setMoney(yskuserwealthentity.getMoney().add(
			 * shijitixianqianshu)); } // 积分减去对应积分
			 * userWealthEntity.setIntegral(yskuserwealthentity.getIntegral().subtract(
			 * tixianqianshu.multiply(new BigDecimal(100)))); // 获取当前日期 Date day = new
			 * Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //
			 * 将String转为Timestamp类型 Timestamp ts = new
			 * Timestamp(System.currentTimeMillis()); ts =
			 * Timestamp.valueOf(df.format(day)); // 为更新日期(时分秒)赋值
			 * userWealthEntity.setUptimeing(ts); // 获取当前日期 Date dayUptime=new Date();
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 为更新日期赋值
			 * userWealthEntity.setUptime(sdf.format(dayUptime)); // 更新ysk_user_wealth表
			 * userWealthRepository.saveAndFlush(userWealthEntity);
			 * 
			 * /////////////////////////////////////////////////////////////////////////////
			 * ///////////////////////////////////////
			 * 
			 * // 创建余额明细实体 YskMoneyDetailEntity yskmoneydetailentity = new
			 * YskMoneyDetailEntity(); // 给余额明细实体赋值 yskmoneydetailentity.setOrderNo("");
			 * yskmoneydetailentity.setMoney(shijitixianqianshu);
			 * yskmoneydetailentity.setUid(yskuserwealthentity.getUid());
			 * yskmoneydetailentity.setType("integralToMoney");
			 * yskmoneydetailentity.setTypeName("积分转余额");
			 * yskmoneydetailentity.setStatus((byte) 1); // 时间戳 SimpleDateFormat dateFormat
			 * = new SimpleDateFormat("yyyyMMddHHmmss"); Date date = new Date(); String time
			 * = DateUtils.getTime(dateFormat.format(date), dateFormat);
			 * yskmoneydetailentity.setCreateTime(Integer.valueOf(time));
			 * yskmoneydetailentity.setFromType((byte) 1);
			 * yskmoneydetailentity.setContent("积分转余额余额增加"+shijitixianqianshu);
			 * yskmoneydetailentity.setMoneyRecord(yskuserwealthentity.getMoney().add(
			 * shijitixianqianshu)); // 向余额明细表插入数据
			 * moneyDetailRepository.save(yskmoneydetailentity);
			 * 
			 * /////////////////////////////////////////////////////////////////////////////
			 * ///////////////////////////////////////
			 * 
			 * // 创建积分明细实体 YskIntegralDetailEntity yskintegraldetailentity = new
			 * YskIntegralDetailEntity(); // 给积分明细实体赋值
			 * yskintegraldetailentity.setMoney(shijitixianqianshu.multiply(new
			 * BigDecimal(100)));
			 * yskintegraldetailentity.setUid(yskuserwealthentity.getUid());
			 * yskintegraldetailentity.setType("integralToMoney");
			 * yskintegraldetailentity.setTypeName("积分转余额");
			 * yskintegraldetailentity.setStatus((byte) 1);
			 * yskintegraldetailentity.setCreateTime(Integer.valueOf(time));
			 * yskintegraldetailentity.setFromType((byte) 2);
			 * yskintegraldetailentity.setContent("积分转余额积分扣除"+shijitixianqianshu.multiply(
			 * new BigDecimal(100)));
			 * yskintegraldetailentity.setMoneyRecord(yskuserwealthentity.getIntegral().
			 * subtract(tixianqianshu.multiply(new BigDecimal(100)))); // 向积分明细表插入数据
			 * integralDetailRepository.save(yskintegraldetailentity); } }
			 */
		}
		return null;
	}

	// /**
	// * 每日定时计算返现(加权)
	// *
	// * @return
	// */
	// @Transactional
	// public String test() {
	//
	// // 获取前天零点
	// Calendar cal = Calendar.getInstance();
	// cal.add(Calendar.DATE, -3);
	// cal.set(Calendar.HOUR_OF_DAY, 0);
	// cal.set(Calendar.MINUTE, 0);
	// cal.set(Calendar.SECOND, 0);
	// cal.set(Calendar.MILLISECOND, 0);
	// Long qiantian = cal.getTimeInMillis() / 1000;
	//
	// // 获取昨天零点
	// Calendar c = Calendar.getInstance();
	// c.add(Calendar.DATE, -2);
	// c.set(Calendar.HOUR_OF_DAY, 0);
	// c.set(Calendar.MINUTE, 0);
	// c.set(Calendar.SECOND, 0);
	// c.set(Calendar.MILLISECOND, 0);
	// Long yesterday = c.getTimeInMillis() / 1000;
	//
	// // 根据前天零点到昨天零点的条件查询平台费用表所有符合的数据
	// List<YskSystemCostEntity> systemCostList = systemCostRepository
	// .findAll(new Specification<YskSystemCostEntity>() {
	// private static final long serialVersionUID = -5293116040876858751L;
	//
	// @Override
	// public Predicate toPredicate(Root<YskSystemCostEntity> root, CriteriaQuery<?>
	// query,
	// CriteriaBuilder cb) {
	// List<Predicate> predicates = new ArrayList<>();// 条件集合
	// predicates.add(
	// cb.between(root.get("createTime"), Long.valueOf(qiantian),
	// Long.valueOf(yesterday)));
	// Predicate[] pre = new Predicate[predicates.size()];
	// return query.where(predicates.toArray(pre)).getRestriction();
	// }
	// });
	//
	// // 根据前天零点到昨天零点的条件查询发放钱数表所有符合的数据
	// List<YskAmountMoneyEntity> amountmoneyList = amountmoneyrepository
	// .findAll(new Specification<YskAmountMoneyEntity>() {
	// private static final long serialVersionUID = -5293116040876858751L;
	//
	// @Override
	// public Predicate toPredicate(Root<YskAmountMoneyEntity> root,
	// CriteriaQuery<?> query,
	// CriteriaBuilder cb) {
	// List<Predicate> predicates = new ArrayList<>();// 条件集合
	// predicates.add(
	// cb.between(root.get("createTime"), Long.valueOf(qiantian),
	// Long.valueOf(yesterday)));
	// Predicate[] pre = new Predicate[predicates.size()];
	// return query.where(predicates.toArray(pre)).getRestriction();
	// }
	// });
	// // 遍历查询到的平台费用表的集合，循环相加返现额度
	// BigDecimal systemCostSum = new BigDecimal(0);
	// for (YskSystemCostEntity ysksystemcostentity : systemCostList) {
	// systemCostSum = systemCostSum.add(ysksystemcostentity.getReturnAmount());
	// }
	// // 判断发放钱数表是否有符合数据
	// if (amountmoneyList.size() > 0) {
	// // 遍历发放钱数表集合(实际应该就一条数据)
	// for (YskAmountMoneyEntity yskamountmoneyentity : amountmoneyList) {
	// // 将平台费用和发放钱数相加
	// systemCostSum = systemCostSum.add(yskamountmoneyentity.getAmountMoney());
	// }
	// }
	//
	// if (systemCostSum.compareTo(BigDecimal.ZERO) == 0) {
	// } else {
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// // 根据前天零点到昨天零点的条件查询用户积分明细表所有符合的数据
	// List<YskUserWealthDetailEntity> userWealthDetailList =
	// userWealthDetailRepository
	// .findAll(new Specification<YskUserWealthDetailEntity>() {
	// private static final long serialVersionUID = 2720021466301138171L;
	//
	// @Override
	// public Predicate toPredicate(Root<YskUserWealthDetailEntity> root,
	// CriteriaQuery<?> query,
	// CriteriaBuilder cb) {
	// List<Predicate> predicates = new ArrayList<>();// 条件集合
	// predicates.add(cb.between(root.get("createTime"), Long.valueOf(qiantian),
	// Long.valueOf(yesterday)));
	// Predicate[] pre = new Predicate[predicates.size()];
	// return query.where(predicates.toArray(pre)).getRestriction();
	// }
	// });
	// // 判断如果用户积分明细表没有符合数据，即分母为0，则不做操作
	// if (userWealthDetailList.size() != 0) {
	// // 遍历查询到的用户积分明细表的集合，循环相加积分
	// BigDecimal userWealthDetailSum = new BigDecimal(0);
	// for (YskUserWealthDetailEntity yskuserwealthdetailentity :
	// userWealthDetailList) {
	// userWealthDetailSum =
	// userWealthDetailSum.add(yskuserwealthdetailentity.getIntegral());
	// }
	// // 积分除以100得到钱数
	// BigDecimal faciend = new BigDecimal(Double.toString(100));
	// userWealthDetailSum = userWealthDetailSum.divide(faciend, 2,
	// BigDecimal.ROUND_HALF_UP);
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// // 返现总和 除以 所有消费者钱数的和 得到比例
	// BigDecimal ratio = new BigDecimal(0);
	// ratio = systemCostSum.divide(userWealthDetailSum, 10,
	// BigDecimal.ROUND_HALF_UP);
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// // 提现钱数
	// BigDecimal tixianqianshu = new BigDecimal(0);
	// // 积分对应钱数
	// BigDecimal jifenDuiyingqianshu = new BigDecimal(0);
	// // 遍历查询到的用户积分明细表的集合
	// for (YskUserWealthDetailEntity userWealthDetailEntity : userWealthDetailList)
	// {
	// // 判断积分是否为0
	// if (userWealthDetailEntity.getIntegral().compareTo(BigDecimal.ZERO) == 0) {
	// } else {
	// // 将该用户的积分转为钱数
	// jifenDuiyingqianshu = (userWealthDetailEntity.getIntegral()).divide(faciend,
	// 2,
	// BigDecimal.ROUND_HALF_UP);
	// // 将该用户的 积分对应钱数 乘以 比例
	// tixianqianshu = jifenDuiyingqianshu.multiply(ratio);
	//
	// // 根据uid主键查询这条数据
	// // 获取前天零点
	// Calendar a = Calendar.getInstance();
	// a.add(Calendar.DATE, -1);
	// a.set(Calendar.HOUR_OF_DAY, 0);
	// a.set(Calendar.MINUTE, 0);
	// a.set(Calendar.SECOND, 0);
	// a.set(Calendar.MILLISECOND, 0);
	// Long zuotianlingdian = a.getTimeInMillis() / 1000;
	//
	// // 获取昨天零点
	// Calendar b = Calendar.getInstance();
	// b.add(Calendar.DATE, 0);
	// b.set(Calendar.HOUR_OF_DAY, 0);
	// b.set(Calendar.MINUTE, 0);
	// b.set(Calendar.SECOND, 0);
	// b.set(Calendar.MILLISECOND, 0);
	// Long jintianlingdian = b.getTimeInMillis() / 1000;
	// List<YskUserWealthDetailEntity> YskUserWealthDetailEntityList =
	// userWealthDetailRepository
	// .findAll(new Specification<YskUserWealthDetailEntity>() {
	// private static final long serialVersionUID = -9130022624811203839L;
	//
	// @Override
	// public Predicate toPredicate(Root<YskUserWealthDetailEntity> root,
	// CriteriaQuery<?> query, CriteriaBuilder cb) {
	// List<Predicate> predicates = new ArrayList<>();// 条件集合
	// predicates.add(cb.equal(root.get("uid"), userWealthDetailEntity.getUid()));
	// predicates.add(cb.between(root.get("createTime"),
	// Long.valueOf(zuotianlingdian),
	// Long.valueOf(jintianlingdian)));
	// Predicate[] pre = new Predicate[predicates.size()];
	// return query.where(predicates.toArray(pre)).getRestriction();
	// }
	// });
	// for (YskUserWealthDetailEntity YskUserWealthDetailEntity :
	// YskUserWealthDetailEntityList) {
	// if (YskUserWealthDetailEntity.getIntegral().compareTo(BigDecimal.ZERO) == 0
	// || YskUserWealthDetailEntity.getIntegral()
	// .compareTo(tixianqianshu.multiply(new BigDecimal(100))) == -1) {
	// } else {
	// // 创建积分明细实体
	// YskIntegralDetailEntity yskintegraldetailentity = new
	// YskIntegralDetailEntity();
	// // 给积分明细实体赋值
	// yskintegraldetailentity.setMoney(tixianqianshu.multiply(new
	// BigDecimal(100)));
	// yskintegraldetailentity.setUid(YskUserWealthDetailEntity.getUid());
	// yskintegraldetailentity.setType("integralToMoney");
	// yskintegraldetailentity.setTypeName("积分转余额");
	// yskintegraldetailentity.setStatus((byte) 1);
	// yskintegraldetailentity.setCreateTime(1527869160);
	// yskintegraldetailentity.setFromType((byte) 2);
	// yskintegraldetailentity
	// .setContent("积分转余额积分扣除" + (tixianqianshu.multiply(new BigDecimal(100)))
	// .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	// yskintegraldetailentity.setMoneyRecord(YskUserWealthDetailEntity.getIntegral()
	// .subtract(tixianqianshu.multiply(new BigDecimal(100))));
	// // 向积分明细表插入数据
	// integralDetailRepository.saveAndFlush(yskintegraldetailentity);
	// }
	// }
	// }
	// }
	// }
	// }
	// return null;
	// }
}
