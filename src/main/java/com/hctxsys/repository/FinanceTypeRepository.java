package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskFinanceEntity;
import com.hctxsys.entity.YskFinanceTypeEntity;

public interface FinanceTypeRepository extends JpaRepository<YskFinanceTypeEntity,Integer>,JpaSpecificationExecutor<YskFinanceTypeEntity> {

}
