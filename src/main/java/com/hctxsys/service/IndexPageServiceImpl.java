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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskSystemCostEntity;
import com.hctxsys.entity.YskUserWealthDetailEntity;
import com.hctxsys.repository.IndexRepository;
import com.hctxsys.repository.SystemCostRepository;
import com.hctxsys.repository.UserWealthDetailRepository;

/**
 * Created by kipin on 2018-04-24
 */
@Service("indexPage")
public class IndexPageServiceImpl {
	@Autowired
	private IndexRepository indexRepository;
	@Autowired
	private UserWealthDetailRepository userWealthDetailRepository;
	@Autowired
	private SystemCostRepository systemCostRepository;

	/**
	 * 获取会员总数
	 * 
	 * @return 会员总数
	 */
	public long getTotal() {
		return indexRepository.count();
	}

	/**
	 * 获取今日新增会员 给dateCount方法传入“20180424”形式的时间字符串
	 * 
	 * @return 今日新增会员总数
	 */
	public long getDateTotal() {
		// System.out.println("hahahahaha:"+new SimpleDateFormat("yyyyMMdd").format(new
		// Date()));//debug
		return indexRepository.dateCount(new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}

	public String systemCostSum() {
		// 获取前天
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

		// 根据等于前天的条件查询平台费用表所有符合的数据
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
		// 遍历查询到的平台费用表的集合，循环相加返现额度
		BigDecimal systemCostSum = new BigDecimal(0);
		for (YskSystemCostEntity ysksystemcostentity : systemCostList) {
			systemCostSum = systemCostSum.add(ysksystemcostentity.getReturnAmount());
		}
		return systemCostSum.toString();
	}

	public String userWealthDetailSum() {
		// 获取前天
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
		// 根据等于前天的条件查询用户积分明细表所有符合的数据
		List<YskUserWealthDetailEntity> userWealthDetailList = userWealthDetailRepository
				.findAll(new Specification<YskUserWealthDetailEntity>() {
					private static final long serialVersionUID = 2720021466301138171L;

					@Override
					public Predicate toPredicate(Root<YskUserWealthDetailEntity> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						List<Predicate> predicates = new ArrayList<>();// 条件集合
						predicates.add(
								cb.between(root.get("createTime"), Long.valueOf(qiantian), Long.valueOf(yesterday)));
						Predicate[] pre = new Predicate[predicates.size()];
						return query.where(predicates.toArray(pre)).getRestriction();
					}
				});
		BigDecimal userWealthDetailSum = new BigDecimal(0);
		// 判断如果用户积分明细表没有符合数据，即分母为0，则不做操作
		if (userWealthDetailList.size() != 0) {
			// 遍历查询到的用户积分明细表的集合，循环相加积分
			for (YskUserWealthDetailEntity yskuserwealthdetailentity : userWealthDetailList) {
				userWealthDetailSum = userWealthDetailSum.add(yskuserwealthdetailentity.getIntegral());
			}
			// 积分除以100得到钱数
			BigDecimal faciend = new BigDecimal(Double.toString(100));
			userWealthDetailSum = userWealthDetailSum.divide(faciend, 2, BigDecimal.ROUND_HALF_UP);
		}
		return userWealthDetailSum.toString();
	}
}
