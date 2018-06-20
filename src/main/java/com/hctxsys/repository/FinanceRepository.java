package com.hctxsys.repository;

import com.hctxsys.entity.YskFinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FinanceRepository extends JpaRepository<YskFinanceEntity,Integer>,JpaSpecificationExecutor<YskFinanceEntity> {
}
