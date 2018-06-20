package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskBannerEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface BannerRepository extends JpaRepository<YskBannerEntity, Integer>,JpaSpecificationExecutor<YskBannerEntity> {

	public List<YskBannerEntity> findBybType(String bType);
	
	@Query("select b from YskBannerEntity b order by b.bOrder desc")
	public Page<YskBannerEntity> findAll(Pageable pageable);
	
	@Query("select b from YskBannerEntity b where b.bType = ?1 and b.bName like %?2% order by b.bOrder desc")
	public Page<YskBannerEntity> findAllkeyT(String bType, String bName,Pageable pageable);
	
	@Query("select b from YskBannerEntity b where b.bName like %?1% order by b.bOrder desc")
	public Page<YskBannerEntity> findAllkey(String bName,Pageable pageable);
	
	@Query("select b from YskBannerEntity b where b.bannerId = ?1")
	public YskBannerEntity findById(int bannerId);
}

