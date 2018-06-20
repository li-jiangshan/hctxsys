package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskHuabaoDetailEntity;

public interface HuabaoDetailRepository extends JpaRepository<YskHuabaoDetailEntity, Integer>,JpaSpecificationExecutor<YskHuabaoDetailEntity>{
	
}
