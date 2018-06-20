package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskReturnIntegralEntity;

public interface ReturnIntegralRepository extends JpaRepository<YskReturnIntegralEntity, Integer>,JpaSpecificationExecutor<YskReturnIntegralEntity> {
	
}
