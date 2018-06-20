package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskAgricultureTypeEntity;

public interface AgricultureTypeRepository extends JpaRepository<YskAgricultureTypeEntity,Integer>,JpaSpecificationExecutor<YskAgricultureTypeEntity> {

}
