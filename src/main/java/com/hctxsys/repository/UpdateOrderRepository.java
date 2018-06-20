package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskUpdateOrderEntity;

public interface UpdateOrderRepository extends JpaRepository<YskUpdateOrderEntity,Integer>,JpaSpecificationExecutor<YskUpdateOrderEntity> {
	@Query(value = "SELECT * FROM ysk_update_order WHERE uid = ?1 AND order_no = ?2",nativeQuery=true)
	public YskUpdateOrderEntity findByUidAndOrderNo(int userId,String orderNo);
	
	public YskUpdateOrderEntity findByOrderNoAndUidAndStatus(String orderNo,int uid,byte status);
	
	public YskUpdateOrderEntity findByOrderNo(String orderNo);
	
	public List<YskUpdateOrderEntity> findByUidAndUserLevel(int uid, int userLevel);
}
