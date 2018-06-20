package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskIntegralDetailEntity;

import java.util.List;

public interface IntegralDetailRepository extends JpaRepository<YskIntegralDetailEntity, Integer>,JpaSpecificationExecutor<YskIntegralDetailEntity> {
	
	List<YskIntegralDetailEntity> findByFromTypeAndCreateTimeBetween(byte fromType, Integer oldTime, Integer newTime);
	
	@Query("select k from YskIntegralDetailEntity k where k.uid=?1 order by createTime desc")
	public List<YskIntegralDetailEntity> findByUid(int uid);
}
