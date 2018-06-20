package com.hctxsys.repository;

import com.hctxsys.entity.YskGoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GoodCategoryRepository extends JpaRepository<YskGoodCategoryEntity, Short>,JpaSpecificationExecutor<YskGoodCategoryEntity> {
	
    List<YskGoodCategoryEntity> findAllByPid(Short pid);
    
    List<YskGoodCategoryEntity> findByPidPathLike(String pidPath);

    List<YskGoodCategoryEntity> findAllByLevel(Byte level);
    
}
