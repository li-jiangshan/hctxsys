package com.hctxsys.repository;

import com.hctxsys.entity.YskModuleImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleImgRepository extends JpaRepository<YskModuleImgEntity,Integer> {
    int deleteByModuleIdAndModuleType(Integer moduleId,Integer ModuleType);
    List<YskModuleImgEntity> findAllByModuleIdAndModuleType(Integer moduleId,Integer ModuleType);
}
