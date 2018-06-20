package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskSellerApplyEntity;

public interface SellerApplyRepository extends JpaRepository<YskSellerApplyEntity, Integer>,JpaSpecificationExecutor<YskSellerApplyEntity>{
	
	public YskSellerApplyEntity findByIdAndStatus(Integer id,Byte status);
	
	public List<YskSellerApplyEntity> findByUidOrderByIdDesc(int uid);
	
}
