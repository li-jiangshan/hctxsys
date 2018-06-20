package com.hctxsys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskMoneyRechargeEntity;

public interface MoneyRechargeRepository extends JpaRepository<YskMoneyRechargeEntity, Integer>,JpaSpecificationExecutor<YskMoneyRechargeEntity> {
	
	public YskMoneyRechargeEntity findByIdAndStatusEquals(Integer id,Byte status);
	//通过用户信息查询充值记录列表
	public Page<YskMoneyRechargeEntity> findByUidOrderByCreateTimeDesc(Integer uid,Pageable pageable);

	
	@Query(value = "SELECT * FROM ysk_money_recharge WHERE uid = ?1 AND order_no = ?2",nativeQuery=true)
	public YskMoneyRechargeEntity findByUidAndOrderNo(int userId,String orderNo);
	
	public YskMoneyRechargeEntity findByOrderNoAndUidAndStatus(String orderNo,int uid,byte status);
	
	public YskMoneyRechargeEntity findByOrderNo(String orderNo);
}
