package com.hctxsys.repository;

import com.hctxsys.entity.YskKucunIntegralDetailEntity;
import com.hctxsys.entity.YskMoneyDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoneyDetailRepository extends JpaRepository<YskMoneyDetailEntity,Integer>,JpaSpecificationExecutor<YskMoneyDetailEntity> {
    @Query("select u,m from YskMoneyDetailEntity m left join YskUserEntity  u on u.userid=m.uid")
    Page<YskMoneyDetailEntity> getRecharge(Pageable pageable);
    Page<YskMoneyDetailEntity> findTop500ByUidOrderByIdDesc(Integer id,Pageable pageable);
    
	List<YskMoneyDetailEntity> findAllByCreateTimeBetweenAndUidAndFromTypeAndType(Integer oldTime, Integer newTime,Integer uid, byte fromType, String type);
}
