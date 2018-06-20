package com.hctxsys.repository;

import com.hctxsys.entity.YskBuyKucunIntegralApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BuyKucunIntegralApplyRepository extends JpaRepository<YskBuyKucunIntegralApplyEntity,Integer>,JpaSpecificationExecutor<YskBuyKucunIntegralApplyEntity> {
    
}
