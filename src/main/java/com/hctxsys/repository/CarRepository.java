package com.hctxsys.repository;

import com.hctxsys.entity.YskCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<YskCarEntity,Integer>,JpaSpecificationExecutor<YskCarEntity> {
}
