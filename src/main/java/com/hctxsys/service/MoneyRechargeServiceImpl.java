package com.hctxsys.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.entity.YskMoneyRechargeEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.repository.MoneyRechargeRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.Result;

@Service("moneyRechargeSerivce")
public class MoneyRechargeServiceImpl {
	@Autowired
	private MoneyRechargeRepository moneyRechargeRepository;
	@Autowired
	private UserWealthRepository userWealthRepository;
	@Autowired
	private MoneyDetailRepository moneyDetailRepository;
	
	/**搜索栏功能
	 * @param status 状态
	 * @param page 页面
	 * @param size 每页显示几个
	 * @param date_start 开始日期
	 * @param date_end 结束日期
	 * @param querytype 搜索类型
	 * @param keyword 搜索关键字
	 * @return
	 */
	public Page<YskMoneyRechargeEntity> findByStatusEquals(byte status,int page,int size,String date_start,String date_end,String querytype,String keyword){
		Sort sort = new Sort(Direction.DESC,"id");
		PageRequest pageable=PageRequest.of(page, size, sort);
		Page<YskMoneyRechargeEntity> findAll = moneyRechargeRepository.findAll(new Specification<YskMoneyRechargeEntity>() {
			
			@Override
			public Predicate toPredicate(Root<YskMoneyRechargeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<>();//条件集合
				predicates.add(cb.equal(root.get("status"), status));
				predicates.add(cb.equal(root.get("type"), "underline"));
				if(null!=date_start) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_start))));
				}
				if(null!=date_end) {
					predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(date_end))+ 60 * 60 * 24));
				}
				if(null!=querytype&&null!=keyword) {
					//predicates.add(cb.equal(root.get(querytype), keyword));
					predicates.add(cb.like(root.get(querytype), "%"+keyword+"%"));
				}
				Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
		return findAll;
	}
	
	//提交审核并进行数据修改
	/**
	 * @param reply 回复的信息
	 * @param status 状态
	 * @param id 用户id
	 * @return
	 */
	@Transactional
	public Result updateMoneyRecharge(String reply,Byte status,Integer id,Integer adminid) {
		YskMoneyRechargeEntity moneyRecharge = moneyRechargeRepository.findByIdAndStatusEquals(id, (byte)0);
		if(moneyRecharge==null) {
			return new Result(500, "操作失败");
		}
		moneyRecharge.setStatus(status);
		moneyRecharge.setAdminId(adminid);
		//有回复信息
		if(reply.length()!=0) {
			moneyRecharge.setReply(reply);
		}
		YskMoneyRechargeEntity recharge = moneyRechargeRepository.saveAndFlush(moneyRecharge);
		//通过
		if(status==1) {
			int uid = moneyRecharge.getUid();
			BigDecimal total = moneyRecharge.getMoney();
			Optional<YskUserWealthEntity> findById = userWealthRepository.findById(uid);
			YskUserWealthEntity userWealth = findById.get();
			userWealth.setMoney(total.add(userWealth.getMoney()));
			userWealth.setTotalMoney(total.add(userWealth.getTotalMoney()));
			YskUserWealthEntity wealth = userWealthRepository.saveAndFlush(userWealth);
			if(null!=wealth) {
				YskMoneyDetailEntity moneyDetail = new YskMoneyDetailEntity();
				moneyDetail.setOrderNo("");
				moneyDetail.setContent("线下充值"+total);
				moneyDetail.setFromType((byte)1);
				moneyDetail.setType("underline");
				moneyDetail.setTypeName("线下充值");
				moneyDetail.setUid(uid);
				moneyDetail.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
				moneyDetail.setStatus((byte)1);
				moneyDetail.setMoney(total);
				moneyDetail.setMoneyRecord(wealth.getMoney());
				moneyDetailRepository.save(moneyDetail);
			}
		}
		
		if(null!=recharge) {
			return new Result(1, "操作成功");
		}else {
			return new Result(500, "操作失败");
		}
		
	}
	
	
	public String setHref(String root,byte status,Integer page,String date_start,String date_end,String querytype,String keyword) {
		StringBuffer sb = new StringBuffer(root);
		if(status==0) {
			sb.append("index?");
		}
		if(status!=0) {
			sb.append("index/type/"+status+"?");
		}
		if(null!=date_start) {
			sb.append("date_start="+date_start+"&");
		}
		if(null!=date_end) {
			sb.append("date_end="+date_end+"&");
		}
		if(null!=querytype) {
			sb.append("querytype="+querytype+"&");
		}
		if(null!=keyword) {
			sb.append("keyword="+keyword+"&");
		}
		if(null!=page) {
			sb.append("status="+status+"&page="+page);
		}
		return sb.toString();
	}
}
