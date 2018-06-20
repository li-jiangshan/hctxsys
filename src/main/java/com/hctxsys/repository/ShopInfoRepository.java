package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskShopInfoEntity;

public interface ShopInfoRepository extends JpaRepository<YskShopInfoEntity, Integer>,JpaSpecificationExecutor<YskShopInfoEntity> {
	public YskShopInfoEntity findByUid(Integer uid);
	@Query(value = "SELECT * FROM ysk_shop_info WHERE uid=?1", nativeQuery = true)
	public YskShopInfoEntity findShopInfo(int seller_id);
	
	public YskShopInfoEntity findByisSelfShop(Integer isSelf);
}
