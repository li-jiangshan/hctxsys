package com.hctxsys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskUserAdviceEntity;

public interface UseradviceRepository extends JpaRepository<YskUserAdviceEntity, Integer>,JpaSpecificationExecutor<YskUserAdviceEntity>{
	public Optional<YskUserAdviceEntity> findById(Integer id);
	
	public YskUserAdviceEntity findByUserid(Integer userid);
}
