package com.hctxsys.repository;

import com.hctxsys.entity.YskUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface YsKUserRepository extends JpaRepository<YskUserEntity, Integer>,JpaSpecificationExecutor<YskUserEntity>{
    Long countByMobileEquals(String mobile);
    public YskUserEntity findByMobile(String mobile);

    List<YskUserEntity> findAllByUsernameLike(String username);
}
