package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskSchoolDetailsEntity;

public interface SchoolDetailRepository extends JpaRepository<YskSchoolDetailsEntity,Integer>,JpaSpecificationExecutor<YskSchoolDetailsEntity> {

}
