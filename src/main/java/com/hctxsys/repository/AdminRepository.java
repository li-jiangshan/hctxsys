package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hctxsys.entity.YskAdminEntity;

/**
 * Created by kipin on 2018-04-19
 */
public interface AdminRepository extends JpaRepository<YskAdminEntity, Integer>,JpaSpecificationExecutor<YskAdminEntity>{
	public YskAdminEntity findByUsernameAndStatus(String username,Byte status);
}
