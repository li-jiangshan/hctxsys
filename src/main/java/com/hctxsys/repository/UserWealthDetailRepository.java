package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskUserWealthDetailEntity;

public interface UserWealthDetailRepository extends JpaRepository<YskUserWealthDetailEntity, Integer>,JpaSpecificationExecutor<YskUserWealthDetailEntity> {
	
}
