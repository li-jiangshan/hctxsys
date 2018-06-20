package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskGoodCollectEntity;

public interface GoodCollectRepository extends JpaRepository<YskGoodCollectEntity, Integer>,JpaSpecificationExecutor<YskGoodCollectEntity>  {
	public List<YskGoodCollectEntity> findByUid(Integer uid);
	public void deleteByUidAndGoodId(Integer uid,Integer goodId);
	public YskGoodCollectEntity findByGoodIdAndUid(Integer goodid,Integer uid);
}
