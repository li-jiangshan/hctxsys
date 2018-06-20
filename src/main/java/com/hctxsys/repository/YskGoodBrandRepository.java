package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodBrandEntity;

public interface YskGoodBrandRepository extends JpaRepository<YskGoodBrandEntity, Integer>,JpaSpecificationExecutor<YskGoodBrandEntity> {
	
	@Query("select gb from YskGoodBrandEntity gb where gb.status=1 order by brandOrder desc")
	public List<YskGoodBrandEntity> findByStatus();
	
	@Query("select gb from YskGoodBrandEntity gb order by brandOrder desc")
	public List<YskGoodBrandEntity> findByOrder();
	
	@Query("select gb from YskGoodBrandEntity gb order by brandOrder desc")
	public Page<YskGoodBrandEntity> findByOrderPage(Pageable pageable);
	
	@Query("select gb from YskGoodBrandEntity gb where gb.brandName like %?1% order by brandOrder desc")
	public Page<YskGoodBrandEntity> findByName(String brandName,Pageable pageable);
	
	public YskGoodBrandEntity findById(int id);
}
