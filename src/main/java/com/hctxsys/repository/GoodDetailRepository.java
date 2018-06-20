package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodImgEntity;

public interface GoodDetailRepository extends JpaRepository<YskGoodImgEntity, Integer>, JpaSpecificationExecutor<YskGoodImgEntity> {
	
	@Query(value = "SELECT * FROM ysk_good_img WHERE good_id=?1", nativeQuery = true)
	public List<YskGoodImgEntity> findGoodImgList(int good_id);
}
