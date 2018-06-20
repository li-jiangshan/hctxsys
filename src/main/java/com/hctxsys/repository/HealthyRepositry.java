package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskHealthyEntity;
import com.hctxsys.entity.YskNewsTitleEntity;

public interface HealthyRepositry extends JpaRepository<YskHealthyEntity, Integer>,JpaSpecificationExecutor<YskHealthyEntity>{
	public YskHealthyEntity findById(int id);
}
