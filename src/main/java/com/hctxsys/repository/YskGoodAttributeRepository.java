package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodAttributeEntity;

public interface YskGoodAttributeRepository extends JpaRepository<YskGoodAttributeEntity, Integer>,JpaSpecificationExecutor<YskGoodAttributeEntity> {
	
	@Query("select ga from YskGoodAttributeEntity ga where ga.modelId=?1 order by attrOrder desc")
	public Page<YskGoodAttributeEntity> findByModelIdpage(int id,Pageable pageable);
	
	@Query("select ga from YskGoodAttributeEntity ga where ga.modelId=?1 order by attrOrder desc")
	public List<YskGoodAttributeEntity> findByModelId(int id);
	
	public YskGoodAttributeEntity findById(int id) ;
	
	@Query("select ga from YskGoodAttributeEntity ga order by id desc")
	public List<YskGoodAttributeEntity> findMaxId();
	
}
