package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskAmountMoneyEntity;


public interface AmountMoneyRepository extends JpaRepository<YskAmountMoneyEntity,Integer>,JpaSpecificationExecutor<YskAmountMoneyEntity> {
    
}
