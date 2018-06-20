package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodPriceEntity;

public interface YskGoodPriceRepository extends JpaRepository<YskGoodPriceEntity, Integer>,JpaSpecificationExecutor<YskGoodPriceEntity> {
	
	@Query("select gp from YskGoodPriceEntity gp where gp.goodId=?1 order by goodArrOrder desc")
	public List<YskGoodPriceEntity> findByPriceId(int id);
	
	@Query("select gp from YskGoodPriceEntity gp where gp.goodId=?1 and gp.goodAttrValue=?2")
	public YskGoodPriceEntity getPriceAndStore(int goodId,String goodAttrValue);
	
	@Modifying
	@Transactional
	@Query("delete from YskGoodPriceEntity gp where gp.goodId=?1")
	public void deleteByGoodId(int goodId);
	
	@Query("select gp from YskGoodPriceEntity gp order by id desc")
	public List<YskGoodPriceEntity> findMaxId();
}
