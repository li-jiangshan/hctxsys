package com.hctxsys.service;

import com.hctxsys.entity.*;
import com.hctxsys.repository.*;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.Result;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("MoneyGetService")
public class MoneyGetServiceImpl {
	@Autowired
	private MoneyGetRepository moneyGetRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private MoneyDetailRepository moneyDetailRepository;
	@Autowired
	private HuabaoDetailRepository huabaoDetailRepository;
	@Autowired
	private MessageRepository messageRepository;

    public List<YskMoneyGetEntity> getAll(Integer status, String date_start,
                                          String date_end, String querytype, String keyword) {

        return moneyGetRepository.findAll(new Specification<YskMoneyGetEntity>() {
            @Override
            public Predicate toPredicate(Root<YskMoneyGetEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();// 条件集合
                predicates.add(cb.equal(root.get("type"), 1));
                if (status == 1) {
                    predicates.add(cb.or(cb.equal(root.get("status"), 1), (cb.equal(root.get("status"), 3))));
                } else {
                    predicates.add(cb.equal(root.get("status"), status));
                }
                if (!StringUtils.isNullOrEmpty(date_start)) {
                    predicates.add(
                            cb.greaterThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_start))));
                    // System.out.println(Long.valueOf(DateUtils.getTime(date_start)));
                }
                if (!StringUtils.isNullOrEmpty(date_end)) {
                    predicates
                            .add(cb.lessThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_end)) + 60 * 60 * 24));
                }
                if (!StringUtils.isNullOrEmpty(querytype) && !StringUtils.isNullOrEmpty(keyword)) {
                    // predicates.add(cb.equal(root.get(querytype), keyword));
                    predicates.add(cb.like(root.get(querytype), "%" + keyword + "%"));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        });
    }
	/**
	 * 搜索栏功能
	 * 
	 * @param status
	 *            状态
	 * @param page
	 *            页码
	 * @param size
	 *            每页几个数据
	 * @param feetime
	 *            到账时间
	 * @param type
	 *            提现类型
	 * @param date_start
	 *            开始日期
	 * @param date_end
	 *            结束日期
	 * @param querytype
	 *            搜索类型
	 * @param keyword
	 *            搜索关键字
	 * @return
	 */
	@Transactional
	// 按条件查找提现数据
	public Page<YskMoneyGetEntity> findByStatusEquals(byte status, int page, int size, String feetime, String type,
			String date_start, String date_end, String querytype, String keyword) {
		Sort sort = new Sort(Direction.DESC, "id");
		PageRequest pageable = PageRequest.of(page, size, sort);
		/* List<MoneyGet> list = repository.findByStatusEquals(status, pageable); */
		Page<YskMoneyGetEntity> list = moneyGetRepository.findAll(new Specification<YskMoneyGetEntity>() {

			@Override
			public Predicate toPredicate(Root<YskMoneyGetEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if (status == 1) {
					predicates.add(cb.or(cb.equal(root.get("status"), 1),(cb.equal(root.get("status"), 3))));
				} else {
					predicates.add(cb.equal(root.get("status"), status));
				}
				if (null != feetime) {
					predicates.add(cb.equal(root.get("feeTime").as(String.class), feetime));
				}
				if (null != type) {
					predicates.add(cb.equal(root.get("type").as(String.class), type));
				}
				if (null != date_start) {
					predicates.add(
							cb.greaterThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_start))));
					// System.out.println(Long.valueOf(DateUtils.getTime(date_start)));
				}
				if (null != date_end) {
					predicates
							.add(cb.lessThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_end))+60 * 60 * 24));
				}
				if (null != querytype && null != keyword) {
					// predicates.add(cb.equal(root.get(querytype), keyword));
					predicates.add(cb.like(root.get(querytype), "%" + keyword + "%"));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return list;
	}

	// 提交审核并进行数据修改
	/**
	 * @param reply
	 *            回复的信息
	 * @param status
	 *            状态
	 * @param id
	 *            用户id
	 * @return
	 */
	@Transactional
	public Result updateMoneyGet(String reply, Byte status, Integer id, Integer adminid) {
		YskMoneyGetEntity moneyGet = moneyGetRepository.findByIdAndStatusEquals(id, (byte) 0);
		if (null == moneyGet) {
			return new Result(500, "操作失败");
		}
		moneyGet.setStatus(status);
		moneyGet.setAdminId(adminid);
		// 有填写回复信息
		if (reply.length() != 0) {
			moneyGet.setReply(reply);
		}
		YskMoneyGetEntity get = moneyGetRepository.saveAndFlush(moneyGet);
		// 不通过审核
		if (status == 2) {
			int uid = moneyGet.getUid();
			BigDecimal total = moneyGet.getMoney();
			// 是现金提现
			if (moneyGet.getType() == 1) {
				Optional<YskUserWealthEntity> findById = userWealthRepository.findById(uid);
				YskUserWealthEntity userWealth = findById.get();
				userWealth.setMoney(total.add(userWealth.getMoney()));
				YskUserWealthEntity wealth = userWealthRepository.saveAndFlush(userWealth);
				if (null != wealth) {
					YskMoneyDetailEntity moneyDetail = new YskMoneyDetailEntity();
					moneyDetail.setOrderNo("");
					moneyDetail.setContent("提现审核不通过，退回" + total);
					moneyDetail.setFromType((byte) 1);
					moneyDetail.setType("moneyback");
					moneyDetail.setTypeName("提现失败");
					moneyDetail.setUid(uid);
					moneyDetail.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
					moneyDetail.setStatus((byte) 1);
					moneyDetail.setMoney(total);
					moneyDetail.setMoneyRecord(wealth.getMoney());
					moneyDetailRepository.saveAndFlush(moneyDetail);
				}
			} else {
				// 华宝提现
				Optional<YskUserWealthEntity> findById = userWealthRepository.findById(uid);
				YskUserWealthEntity userWealth = findById.get();
				userWealth.setHuabao(total.add(userWealth.getHuabao()));
				YskUserWealthEntity wealth = userWealthRepository.saveAndFlush(userWealth);
				if (null != wealth) {
					YskHuabaoDetailEntity huabaoDetail = new YskHuabaoDetailEntity();
					huabaoDetail.setContent("提现审核不通过，退回" + total);
					huabaoDetail.setFromType((byte) 1);
					huabaoDetail.setType("moneyback");
					huabaoDetail.setTypeName("提现失败");
					huabaoDetail.setUid(uid);
					huabaoDetail.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
					huabaoDetail.setStatus((byte) 1);
					huabaoDetail.setMoney(total);
					huabaoDetail.setMoneyRecord(wealth.getMoney());
					huabaoDetailRepository.saveAndFlush(huabaoDetail);
				}
			}
			// 填写了回复信息
			if (null != reply) {
				YskMessageEntity message = new YskMessageEntity();
				message.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
				message.setStatus((byte) 0);
				message.setSend(1);
				message.setUid(uid);
				message.setContent(reply);
				message.setType(1);
				message.setTitle("提现审核");
				messageRepository.saveAndFlush(message);
			}
		}

		if (null != get) {
			return new Result(1, "操作成功");
		} else {
			return new Result(500, "操作失败");
		}

	}

	// index/status/1.html?feetime=1&type=2&date_start=2018-04-12&date_end=2018-04-04&querytype=mobile&keyword=ertr
	// index.html?feetime=1&type=2&date_start=2018-03-27&date_end=2018-04-03&querytype=mobile&keyword=dsada
	public String setHref(String root, byte status, Integer page, String feetime, String type, String date_start,
			String date_end, String querytype, String keyword) {
		StringBuffer sb = new StringBuffer(root);
		if (status == 0) {
			sb.append("index?");
		}
		if (status != 0) {
			sb.append("index/status/" + status + "?");
		}
		if (null != feetime) {
			sb.append("feetime=" + feetime + "&");
		}
		if (null != type) {
			sb.append("type=" + type + "&");
		}
		if (null != date_start) {
			sb.append("date_start=" + date_start + "&");
		}
		if (null != date_end) {
			sb.append("date_end=" + date_end + "&");
		}
		if (null != querytype) {
			sb.append("querytype=" + querytype + "&");
		}
		if (null != keyword) {
			sb.append("keyword=" + keyword + "&");
		}
		if (null != page) {
			sb.append("status=" + status + "&page=" + page);
		}
		return sb.toString();
	}

	// 新增修改状态

	/**
	 * 点击确认到账将状态修改为3
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional
	public Result updateStatus(byte status, Integer id, Integer uid) {

        YskMoneyGetEntity yskmoneygetentity = new YskMoneyGetEntity();
		yskmoneygetentity = moneyGetRepository.findById(id).get();
		yskmoneygetentity.setStatus(status);
		YskMoneyGetEntity moneyGetEntity = moneyGetRepository.saveAndFlush(yskmoneygetentity);

//		YskUserWealthEntity yskuserwealthentity = new YskUserWealthEntity();
//		yskuserwealthentity = userWealthRepository.findByUid(uid);
//		BigDecimal oldMoney = yskuserwealthentity.getMoney();
//		yskuserwealthentity.setMoney(oldMoney.subtract(yskmoneygetentity.getMoney()));
//		userWealthRepository.saveAndFlush(yskuserwealthentity);

//    	YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
//    	moneyDetailEntity.setContent("提现" + yskmoneygetentity.getMoney());
//    	moneyDetailEntity.setFromType((byte) 2); //1-转入 2-转出
//    	moneyDetailEntity.setType("getmoney"); //明细类型 
//    	moneyDetailEntity.setTypeName("提现");
//    	moneyDetailEntity.setUid(uid);
//    	moneyDetailEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date())); //创建时间
//    	moneyDetailEntity.setStatus((byte) 1); //0-未支付 1-已支付 2-不通过
//    	moneyDetailEntity.setMoney(yskmoneygetentity.getMoney()); //金额
//    	moneyDetailEntity.setMoneyRecord(oldMoney.subtract(yskmoneygetentity.getMoney()));
//    	moneyDetailEntity.setOrderNo("");
//    	moneyDetailRepository.save(moneyDetailEntity);
		
		
		if (null != moneyGetEntity) {
			return new Result(1, "操作成功", "/Admin/Money/index", "");
		} else {
			return new Result(0, "操作失败", "/Admin/Money/index", "");
		}
	}
}
