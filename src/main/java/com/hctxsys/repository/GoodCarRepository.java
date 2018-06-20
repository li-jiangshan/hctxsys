package com.hctxsys.repository;

import com.hctxsys.entity.YskGoodCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GoodCarRepository extends JpaRepository<YskGoodCarEntity, Integer>,JpaSpecificationExecutor<YskGoodCarEntity> {
	
    public List<YskGoodCarEntity> findByUid(int uid);
    public YskGoodCarEntity findByUidAndGoodId(Integer uid,Integer goodid);
    public YskGoodCarEntity findByUidAndPriceId(Integer uid,Integer priceid);

	public void deleteByUidAndGoodId(Integer uid,Integer goodId);
    
	public void deleteByUidAndGoodIdAndPriceId(Integer uid,Integer goodId, Integer priceId);
	
}
