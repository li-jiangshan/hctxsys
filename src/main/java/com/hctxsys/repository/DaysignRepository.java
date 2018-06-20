package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskDaysignEntity;

public interface DaysignRepository extends JpaRepository<YskDaysignEntity,Integer>,JpaSpecificationExecutor<YskDaysignEntity> {
	public YskDaysignEntity findByUidAndYearAndMoth(Integer uid, String year, String moth);
}
