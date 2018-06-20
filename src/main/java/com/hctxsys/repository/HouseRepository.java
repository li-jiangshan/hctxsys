package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskHouseEntity;

public interface HouseRepository extends JpaRepository<YskHouseEntity, Integer>,JpaSpecificationExecutor<YskHouseEntity>{
	
}
