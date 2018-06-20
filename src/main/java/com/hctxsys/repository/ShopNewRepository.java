package com.hctxsys.repository;

import com.hctxsys.entity.YskShopnewEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopNewRepository extends JpaRepository<YskShopnewEntity,Integer>,JpaSpecificationExecutor<YskShopnewEntity> {
    List<YskShopnewEntity> findTop50ByIdIsNotNullOrderByIdDesc();
    
    @Query("select sn from YskShopnewEntity sn where sn.id = ?1 or sn.title like %?2%")
    public Page<YskShopnewEntity> findBykeyword(int id,String title,Pageable pageable);
    
    public YskShopnewEntity findById(int id);
}
