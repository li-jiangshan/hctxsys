package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskTurntableLvEntity;

public interface TurntableLvRepository extends JpaRepository<YskTurntableLvEntity, Integer>,JpaSpecificationExecutor<YskTurntableLvEntity> {
	
}
