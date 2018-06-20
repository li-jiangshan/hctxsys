package com.hctxsys.repository;

import com.hctxsys.entity.YskUserWealthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserWealthRepository extends JpaRepository<YskUserWealthEntity, Integer>,JpaSpecificationExecutor<YskUserWealthEntity> {
	YskUserWealthEntity findByUid(Integer uid);
}
