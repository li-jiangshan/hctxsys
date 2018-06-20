package com.hctxsys.repository;

import com.hctxsys.entity.YskOrderPayEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderPayRepository extends JpaRepository<YskOrderPayEntity,Integer>,JpaSpecificationExecutor<YskOrderPayEntity> {
	@Query(value = "SELECT * FROM ysk_order_pay WHERE user_id = ?1 AND order_no = ?2",nativeQuery=true)
	public YskOrderPayEntity findByUidAndOrderNo(int userId,String orderNo);
	
	public YskOrderPayEntity findByOrderNoAndUserIdAndOrderStatus(String orderNo,int userId,byte orderStatus);
	
	public YskOrderPayEntity findByOrderNo(String orderNo);

	public YskOrderPayEntity findByOrderIdListLike(String orderNo);

}
