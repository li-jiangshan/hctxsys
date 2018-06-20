package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskGoodImgEntity;

public interface GoodImageRepository extends JpaRepository<YskGoodImgEntity, Integer>,JpaSpecificationExecutor<YskGoodImgEntity> {
	
}
