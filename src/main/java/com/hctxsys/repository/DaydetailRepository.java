package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskDaydetailEntity;

public interface DaydetailRepository extends JpaRepository<YskDaydetailEntity,Integer>,JpaSpecificationExecutor<YskDaydetailEntity> {
	@Query(value = "SELECT * FROM ysk_daydetail d WHERE d.d_uid = ?1 AND d.d_addtime BETWEEN ?2 AND ?3",nativeQuery=true)
	public List<YskDaydetailEntity> findByDUidAndDAddtimeBetween(int dUid, String searchTimeStart, String searchTimeEnd);
}
