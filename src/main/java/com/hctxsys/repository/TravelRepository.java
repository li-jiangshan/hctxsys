package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskTravelEntity;

public interface TravelRepository extends JpaRepository<YskTravelEntity,Integer>,JpaSpecificationExecutor<YskTravelEntity> {
}
