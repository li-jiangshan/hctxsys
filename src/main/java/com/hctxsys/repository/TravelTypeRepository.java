package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskTravelTypeEntity;

public interface TravelTypeRepository extends JpaRepository<YskTravelTypeEntity,Integer>,JpaSpecificationExecutor<YskTravelTypeEntity> {
}
