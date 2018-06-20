package com.hctxsys.repository;

import com.hctxsys.entity.YskUserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<YskUserAddressEntity,Integer>,JpaSpecificationExecutor<YskUserAddressEntity> {
    List<YskUserAddressEntity> findAllByUserId(Integer userID);
    YskUserAddressEntity findByUserIdAndIsDefault(Integer userID,Byte isDefault);
}
