package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskUserBankEntity;

public interface UserBankRepository extends JpaRepository<YskUserBankEntity,Integer>,JpaSpecificationExecutor<YskUserBankEntity>{
	
	public List<YskUserBankEntity> findByUid(Integer uid);
	
	public YskUserBankEntity findByUidAndIsDefault(Integer uid, byte isDefault);
	
	public YskUserBankEntity findByUidAndBankNo(Integer uid, String bankNo);
}
