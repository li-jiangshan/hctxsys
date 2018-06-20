package com.hctxsys.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodImgEntity;

public interface YskGoodImgRepository extends JpaRepository<YskGoodImgEntity, Integer>,JpaSpecificationExecutor<YskGoodImgEntity> {
	
	public YskGoodImgEntity findById(int id);
	
	public List<YskGoodImgEntity> findByGoodId(int goodId);
	
	@Query("select gi from YskGoodImgEntity gi order by id desc")
	public List<YskGoodImgEntity> findMaxId();
	
}
