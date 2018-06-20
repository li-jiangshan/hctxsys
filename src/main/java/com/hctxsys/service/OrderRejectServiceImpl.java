package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderRejectedEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.OrderDetailRepository;
import com.hctxsys.repository.OrderRejectedRepository;
import com.hctxsys.repository.OrderRepository;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.util.TableResult;

@Service
public class OrderRejectServiceImpl {
	
	@Autowired
	private OrderRejectedRepository orderRejectedRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public TableResult getRejectList(TableResult result, Integer sellerId) {
		PageRequest pageable = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskOrderRejectedEntity> findAll = orderRejectedRepository.findAll(new Specification<YskOrderRejectedEntity>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<YskOrderRejectedEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				if (!StringUtils.isEmpty(result.getBeginDate())) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(result.getBeginDate()))));
				}
				if (!StringUtils.isEmpty(result.getEndDate())) {
					predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), Long.valueOf(MyUtils.getTime(result.getEndDate()))));
				}
				if (result.getLevel() != null && result.getLevel() < 7) {
					predicates.add(cb.equal(root.get("orderStatus"), result.getLevel()));
				}
				if (!StringUtils.isEmpty(result.getKeyword())) {
					predicates.add(cb.equal(root.get("orderDetailId"), result.getKeyword()));
				}
				predicates.add(cb.equal(root.get("yskOrderDetailEntity").get("sellerId"), sellerId));
				query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				query.orderBy(cb.desc(root.get("rejectedId")));
				return query.getRestriction();
			}
		}, pageable);
		
		result.setRows(findAll.getContent());
		result.setTotal(findAll.getTotalElements());
		result.setPageCount(Long.valueOf(findAll.getTotalPages()));
		
		return result;
	}
	
	public YskOrderRejectedEntity getRejectDetail(int rejectId) {
		return orderRejectedRepository.findByRejectedId(rejectId);
	}
	
	public YskUserEntity getUser(int userId) {
		return userRepository.findByUserid(userId);
	}
	
	public Optional<YskOrderDetailEntity> getOrderDetail(int orderDetailId) {
		return orderDetailRepository.findById(orderDetailId);
	}
	
	public YskOrderEntity getOrderByid(int orderId) {
		return orderRepository.findByOrderId(orderId);
	}
	
	public int updateRejectOrderStatus(int rejectId, String orderStatusReason) {
		return orderRejectedRepository.updateRejectOrderStatus(rejectId, orderStatusReason);
	}
	
	public int updateOrderStatus(int rejectId, int orderStatus) {
		return orderRejectedRepository.updateOrderStatus(rejectId, orderStatus);
	}
	
	public int updateOrderName(int rejectId, String rejectedName, String rejectedPhone, String receivingAddress) {
		return orderRejectedRepository.updateOrderName(rejectId, rejectedName, rejectedPhone, receivingAddress);
	}
}
