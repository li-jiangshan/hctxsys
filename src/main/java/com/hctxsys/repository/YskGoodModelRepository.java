package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodModelEntity;

public interface YskGoodModelRepository extends JpaRepository<YskGoodModelEntity, Integer>,JpaSpecificationExecutor<YskGoodModelEntity> {
	
	@Query("select gm from YskGoodModelEntity gm where gm.modelName like %?1%")
	public List<YskGoodModelEntity> findByModelName(String modelName);
	
	public YskGoodModelEntity findById(int id);
	
	@Query("select gm from YskGoodModelEntity gm order by id desc")
	public List<YskGoodModelEntity> findMaxId();
}
