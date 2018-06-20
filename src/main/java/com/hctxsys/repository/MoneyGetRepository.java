package com.hctxsys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskMoneyGetEntity;

public interface MoneyGetRepository extends JpaRepository<YskMoneyGetEntity, Integer>,JpaSpecificationExecutor<YskMoneyGetEntity>{
	
	public YskMoneyGetEntity findByIdAndStatusEquals(Integer id,Byte status);
	
	//通过用户信息查询提现记录列表
	public Page<YskMoneyGetEntity> findByUidOrderByCreateTimeDesc(Integer uid,Pageable pageable);
	
}
