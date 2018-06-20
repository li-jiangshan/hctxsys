package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskAppEditionEntity;


public interface AppEditionRepository extends JpaRepository<YskAppEditionEntity, Integer>,JpaSpecificationExecutor<YskAppEditionEntity>{
	
}
