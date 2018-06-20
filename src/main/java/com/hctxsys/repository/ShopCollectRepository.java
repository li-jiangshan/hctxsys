package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskShopCollectEntity;

public interface ShopCollectRepository extends JpaRepository<YskShopCollectEntity, Integer>,JpaSpecificationExecutor<YskShopCollectEntity> {
	
	public List<YskShopCollectEntity> findByUid(Integer uid);
	
	public void deleteByUidAndSellerId(Integer uid,Integer sellerId);
	
	public YskShopCollectEntity findByUidAndSellerId(Integer uid,Integer sellerId);
	
}
