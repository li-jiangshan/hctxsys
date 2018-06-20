package com.hctxsys.repository;

import com.hctxsys.entity.YskOrderRejectedEntity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRejectedRepository extends JpaRepository<YskOrderRejectedEntity,Integer>,JpaSpecificationExecutor<YskOrderRejectedEntity> {
	
	public YskOrderRejectedEntity findByOrderDetailIdAndOrderStatusNot(Integer orderDetailId, Integer orderStatus);
	
	public YskOrderRejectedEntity findByRejectedNo(String rejectedNo);
	
	public List<YskOrderRejectedEntity> findAllByRejectedNo(String rejectedNo);
	
	YskOrderRejectedEntity findByRejectedId(Integer rejectId);

	public YskOrderRejectedEntity findByOrderDetailId(Integer orderDetailId);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "UPDATE ysk_order_rejected SET order_status = 2, order_status_reason = ?2 WHERE rejected_id = ?1", nativeQuery=true)
	int updateRejectOrderStatus(int rejectedId, String orderStatusReason);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "UPDATE ysk_order_rejected SET order_status = ?2 WHERE rejected_id = ?1", nativeQuery=true)
	int updateOrderStatus(int rejectedId, int orderStatus);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "UPDATE ysk_order_rejected SET order_status = 1, rejected_name = ?2, rejected_phone = ?3, receiving_address = ?4 WHERE rejected_id = ?1", nativeQuery=true)
	int updateOrderName(int rejectId, String rejectedName, String rejectedPhone, String receivingAddress);

}
