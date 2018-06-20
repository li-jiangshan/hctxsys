package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskHouseTypeEntity;

public interface HouseTypeRepository extends JpaRepository<YskHouseTypeEntity, Integer>,JpaSpecificationExecutor<YskHouseTypeEntity>{
	
}
