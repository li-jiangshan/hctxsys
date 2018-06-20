package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskAgricultureEntity;

public interface AgricultureRepository extends JpaRepository<YskAgricultureEntity,Integer>,JpaSpecificationExecutor<YskAgricultureEntity> {
}
