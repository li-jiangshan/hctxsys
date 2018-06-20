package com.hctxsys.repository;

import com.hctxsys.entity.YskGoodPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GoodPriceRepository extends JpaRepository<YskGoodPriceEntity, Integer>,JpaSpecificationExecutor<YskGoodPriceEntity> {
    List<YskGoodPriceEntity> findAllByGoodId(Integer goodId);
	YskGoodPriceEntity findByGoodIdAndGoodAttrValue(int goodId ,String goodAttrValue);
	
	public List<YskGoodPriceEntity> findByGoodId(int goodId);
}
