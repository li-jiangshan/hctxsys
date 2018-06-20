package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskBankNameEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface BankNameRepository extends JpaRepository<YskBankNameEntity, Integer>,JpaSpecificationExecutor<YskBankNameEntity> {
	public YskBankNameEntity findByBankName(String bankName);


}
