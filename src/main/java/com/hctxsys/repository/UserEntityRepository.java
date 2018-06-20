package com.hctxsys.repository;

import com.hctxsys.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.beans.Transient;
import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity,Integer>,JpaSpecificationExecutor {
    @Query("select new UserEntity(u.userid,u.account) from UserEntity u")
    List<UserEntity> selectAll();
}
