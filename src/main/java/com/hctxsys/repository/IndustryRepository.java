package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskIndustryEntity;

public interface IndustryRepository extends JpaRepository<YskIndustryEntity, Integer>,JpaSpecificationExecutor<YskIndustryEntity> {
	
	@Query("select i from YskIndustryEntity i where i.pid = ?1 order by sortOrder desc")
	public List<YskIndustryEntity> findByPid(int pid);
	
	public YskIndustryEntity findById(int id);
	
	@Query("select i from YskIndustryEntity i where i.isShow=1 and i.pid=?1")
	public List<YskIndustryEntity> findByPidAndIsShow(int pid);
	
	@Query("select i from YskIndustryEntity i order by sortOrder desc")
	public List<YskIndustryEntity> findAll();
	
	@Query("select i from YskIndustryEntity i order by id desc")
	public List<YskIndustryEntity> findMaxId();
	
}
