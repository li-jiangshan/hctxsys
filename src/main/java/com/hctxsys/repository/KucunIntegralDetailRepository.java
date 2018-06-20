package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.entity.YskKucunIntegralDetailEntity;

public interface KucunIntegralDetailRepository extends JpaRepository<YskKucunIntegralDetailEntity, Integer>,JpaSpecificationExecutor<YskKucunIntegralDetailEntity>{
	@Query("select k from YskKucunIntegralDetailEntity k where k.uid=?1 order by createTime desc")
	public List<YskKucunIntegralDetailEntity> findByUid(int uid);
}
