package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskYwtPubKeyEntity;

public interface YsKYwtPubKeyRepository extends JpaRepository<YskYwtPubKeyEntity, Integer>,JpaSpecificationExecutor<YskYwtPubKeyEntity>{

}
