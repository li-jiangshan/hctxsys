package com.hctxsys.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskAmountMoneyEntity;
import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskReturnIntegralEntity;
import com.hctxsys.entity.YskSystemCostEntity;
import com.hctxsys.entity.YskTurntableLvEntity;
import com.hctxsys.repository.AmountMoneyRepository;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.ReturnIntegralRepository;
import com.hctxsys.repository.TurntableLvRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.vo.ConfigVo;

@Service("ConfigService")
public class ConfigServiceImpl {
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private TurntableLvRepository turntableLvRepository;
	@Autowired
	private ReturnIntegralRepository returnIntegralRepository;
	@Autowired 
	private AmountMoneyRepository amountMoneyRepository;

	/**
	 * 首页查询系统配置信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskConfigEntity> selectbase() {
		List<YskConfigEntity> list = configRepository.findAll(new Specification<YskConfigEntity>() {

			private static final long serialVersionUID = -8883165541901452057L;

			@Override
			public Predicate toPredicate(Root<YskConfigEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("block"), 1));
				predicates.add(cb.equal(root.get("status"), 1));
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		});

		return list;
	}

	/**
	 * 系统设置点击确认
	 * 
	 * @param configVo
	 * @return
	 */
	@Transactional
	public Result baseUpdate(ConfigVo configVo) {

		Integer[] configId = configVo.getConfigId();

		String[] nameShuzu = configVo.getNameShuzu();

		for (int i = 0; i < configId.length; i++) {

			YskConfigEntity yskconfigentity = configRepository.findById(configId[i]).get();

			yskconfigentity.setValue(nameShuzu[i]);

			yskconfigentity = configRepository.saveAndFlush(yskconfigentity);
		}

		return new Result(1, "操作成功", "/Admin/Config/group", "");
	}

	/**
	 * 首页查询基本设置信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskConfigEntity> selectsystem() {
		List<YskConfigEntity> list = configRepository.findAll(new Specification<YskConfigEntity>() {

			private static final long serialVersionUID = -8072324342092792119L;

			@Override
			public Predicate toPredicate(Root<YskConfigEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("block"), 2));
				predicates.add(cb.equal(root.get("status"), 1));
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		});

		return list;
	}

	/**
	 * 基本设置点击确认
	 * 
	 * @param configVo
	 * @return
	 */
	@Transactional
	public Result systemUpdate(ConfigVo configVo) {

		Integer[] configId = configVo.getConfigId();

		String[] nameShuzu = configVo.getNameShuzu();

		for (int i = 0; i < configId.length; i++) {

			YskConfigEntity yskconfigentity = configRepository.findById(configId[i]).get();

			yskconfigentity.setValue(nameShuzu[i]);

			yskconfigentity = configRepository.saveAndFlush(yskconfigentity);
		}

		return new Result(1, "操作成功", "/Admin/Config/system", "");
	}

	/**
	 * 首页查询分销设置信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskConfigEntity> selectfee() {
		List<YskConfigEntity> list = configRepository.findAll(new Specification<YskConfigEntity>() {

			private static final long serialVersionUID = 4387803211965046775L;

			@Override
			public Predicate toPredicate(Root<YskConfigEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("block"), 4));
				predicates.add(cb.equal(root.get("status"), 1));
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		});

		return list;
	}

	/**
	 * 分销设置点击确认
	 * 
	 * @param configVo
	 * @return
	 */
	@Transactional
	public Result feeUpdate(ConfigVo configVo) {

		Integer[] configId = configVo.getConfigId();

		String[] nameShuzu = configVo.getNameShuzu();

		for (int i = 0; i < configId.length; i++) {

			YskConfigEntity yskconfigentity = configRepository.findById(configId[i]).get();

			yskconfigentity.setValue(nameShuzu[i]);

			yskconfigentity = configRepository.saveAndFlush(yskconfigentity);
		}

		return new Result(1, "操作成功", "/Admin/Config/fee", "");
	}

	/**
	 * 首页查询转盘信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskTurntableLvEntity> selectturntable() {
		List<YskTurntableLvEntity> list = turntableLvRepository.findAll();
		return list;
	}

	/**
	 * 转盘配置点击确认
	 * 
	 * @param configVo
	 * @return
	 */
	@Transactional
	public Result turntableUpdate(ConfigVo configVo) {

		YskTurntableLvEntity yskturntablelventity = turntableLvRepository.findById(configVo.getId()).get();

		yskturntablelventity.setOne(configVo.getOne());

		yskturntablelventity.setTwo(configVo.getTwo());

		yskturntablelventity.setThree(configVo.getThree());

		yskturntablelventity.setFour(configVo.getFour());

		yskturntablelventity = turntableLvRepository.saveAndFlush(yskturntablelventity);

		return new Result(1, "操作成功", "/Admin/Config/turntable", "");
	}
	
	/**
	 * 首页查询网站开关信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskConfigEntity> selectsiteclose() {
		List<YskConfigEntity> list = configRepository.findAll(new Specification<YskConfigEntity>() {

			private static final long serialVersionUID = -4782405655431788419L;

			@Override
			public Predicate toPredicate(Root<YskConfigEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("block"), 3));
				predicates.add(cb.equal(root.get("status"), 1));
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		});

		return list;
	}
	
	/**
	 * 转盘配置点击确认
	 * 
	 * @param configVo
	 * @return
	 */
	@Transactional
	public Result sitecloseUpdate(ConfigVo configVo) {

		Integer[] configId = configVo.getConfigId();

		String[] nameShuzu = configVo.getNameShuzu();

		for (int i = 0; i < configId.length; i++) {

			YskConfigEntity yskconfigentity = configRepository.findById(configId[i]).get();

			yskconfigentity.setValue(nameShuzu[i]);
			
			yskconfigentity.setTip(configVo.getTip());

			yskconfigentity = configRepository.saveAndFlush(yskconfigentity);
		}

		return new Result(1, "操作成功", "/Admin/Config/siteclose", "");
	}
	
	/**
	 * 首页查询返还积分信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskReturnIntegralEntity> selectReturnIntegral() {
		List<YskReturnIntegralEntity> list = returnIntegralRepository.findAll();
		return list;
	}
	
	/**
	 * 首页查询返现金额信息
	 * 
	 * @return
	 */
	@Transactional
	public List<YskAmountMoneyEntity> selectAmountMoney() {

		// 获取昨天24点
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Long yesterday=cal.getTimeInMillis()/1000;
		//System.out.println(yesterday+"-------------");
				
		// 获取今天24点
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		Long today=c.getTimeInMillis()/1000;
		//System.out.println(today+"================");
		
		Sort sort = new Sort(Direction.DESC, "createTime");
		
		List<YskAmountMoneyEntity> list = amountMoneyRepository
				.findAll(new Specification<YskAmountMoneyEntity>() {
					private static final long serialVersionUID = -5293116040876858751L;
					@Override
					public Predicate toPredicate(Root<YskAmountMoneyEntity> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						List<Predicate> predicates = new ArrayList<>();// 条件集合
						predicates.add(cb.between(root.get("createTime"), Long.valueOf(yesterday),Long.valueOf(today)));
						Predicate[] pre = new Predicate[predicates.size()];
						return query.where(predicates.toArray(pre)).getRestriction();
					}
				},sort);
		return list;
	}
	
	/**
	 * 返还积分点击确认
	 * 
	 * @param configVo
	 * @return
	 */
	@Transactional
	public Result returnIntegralUpdate(YskReturnIntegralEntity entity,String amountMoney) {
		YskReturnIntegralEntity returnIntegralEntity = returnIntegralRepository.findById(entity.getId()).get();
		returnIntegralEntity.setToSeller(entity.getToSeller());
		returnIntegralEntity.setToCustomer(entity.getToCustomer());
		returnIntegralEntity.setRuncost(entity.getRuncost());
		returnIntegralEntity.setReturnIntegral(entity.getReturnIntegral());
		returnIntegralEntity.setStockIntegral(entity.getStockIntegral());
		returnIntegralEntity.setIntegralMoneyFee(entity.getIntegralMoneyFee());
		returnIntegralEntity = returnIntegralRepository.saveAndFlush(returnIntegralEntity);
		
		YskAmountMoneyEntity amountentity = new YskAmountMoneyEntity();
		amountentity.setAmountMoney(BigDecimal.valueOf(Double.valueOf(amountMoney)));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
		amountentity.setCreateTime(Integer.valueOf(time));
		
		amountMoneyRepository.saveAndFlush(amountentity);

		return new Result(1, "操作成功", "/Admin/Config/integral", "");
	}
	
}
