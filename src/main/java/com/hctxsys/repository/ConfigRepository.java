package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskConfigEntity;

public interface ConfigRepository extends JpaRepository<YskConfigEntity, Integer>,JpaSpecificationExecutor<YskConfigEntity> {
	
	public List<YskConfigEntity> findByIdIn(List<Integer> ids);  //通过用户id查询
}
