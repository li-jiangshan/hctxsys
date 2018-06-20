package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodCategoryEntity;

public interface YskGoodCategoryRepository extends JpaRepository<YskGoodCategoryEntity, Integer>,JpaSpecificationExecutor<YskGoodCategoryEntity> {
	
	@Query("select gc from YskGoodCategoryEntity gc where gc.pid=?1 order by sortOrder desc")
	public List<YskGoodCategoryEntity> findByPid(short pid);
	
	public YskGoodCategoryEntity findById(short id);
	
	@Query("select gc from YskGoodCategoryEntity gc where gc.isShow=1 and gc.pid=?1")
	public List<YskGoodCategoryEntity> findByPidAndIsShow(short pid);
	
	@Query("select gc from YskGoodCategoryEntity gc order by sortOrder desc")
	public List<YskGoodCategoryEntity> findAll();

	public void deleteById(short id);
	
	@Query("select gc from YskGoodCategoryEntity gc order by id desc")
	public List<YskGoodCategoryEntity> findMaxId();
	
}
