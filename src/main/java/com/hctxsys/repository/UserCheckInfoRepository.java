package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskUserCheckinfoEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserCheckInfoRepository extends JpaRepository<YskUserCheckinfoEntity, Integer>,JpaSpecificationExecutor<YskUserCheckinfoEntity> {
	
	/**
	 * 查询企业用户 kipin:2018-04-26 add
	 * @param is_check_company
	 * @param uid
	 * @return
	 */
	@Query(value="select count(*) from ysk_user_checkinfo where is_check_company=?1 and uid=?2", nativeQuery=true)
	public abstract byte getCompanyCount(byte is_check_company, long uid);
	
	/**
	 * 查询个人用户 kipin:2018-04-26 add
	 * @param is_check_user
	 * @param uid
	 * @return
	 */
	@Query(value="select count(*) from ysk_user_checkinfo where is_check_user=?1 and uid=?2", nativeQuery=true)
	public abstract byte getUserCount(byte is_check_user, long uid);
}
