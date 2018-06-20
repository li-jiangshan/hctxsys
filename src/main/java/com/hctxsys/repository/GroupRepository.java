package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskGroupEntity;

/**
 * Created by kipin on 2018-04-20
 */
public interface GroupRepository extends JpaRepository<YskGroupEntity, Integer>,JpaSpecificationExecutor<YskGroupEntity>{
	public YskGroupEntity findById(int id);
}
