package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskUserEntity;

/**
 * Created by kipin on 2018-04-24
 */
public interface IndexRepository extends JpaRepository<YskUserEntity, Integer>{
	/**
	 * 查询ysk_user表里的数据总数，也就是用户总数
	 */
	public long count();
	
	/**
	 * 根据reg_date字段为条件，查询当天新增用户总数
	 * @param currentDate “20180424”的格式
	 */
	@Query(value="SELECT count(*) FROM ysk_user WHERE FROM_UNIXTIME(REG_DATE,'%Y%m%d') = ?1", nativeQuery = true)
	public long dateCount(String currentDate);
}
