package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskSystemCostEntity;

public interface SystemCostRepository extends JpaRepository<YskSystemCostEntity, Integer>,JpaSpecificationExecutor<YskSystemCostEntity> {
	
}
