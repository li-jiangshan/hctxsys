package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskSchoolPeopleEntity;

public interface SchoolPeopleRepository extends JpaRepository<YskSchoolPeopleEntity,Integer>,JpaSpecificationExecutor<YskSchoolPeopleEntity> {

}
