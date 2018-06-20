package com.hctxsys.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskUserEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends JpaRepository<YskUserEntity, Integer>,JpaSpecificationExecutor<YskUserEntity> {

	List<YskUserEntity> findAllByPidIsGreaterThan(Integer id);

	List<YskUserEntity> findByAccount(String account);
	
	List<YskUserEntity> findByMobile(String mobile);
	
	List<YskUserEntity> findByGgid(Integer ggid);
	
	List<YskUserEntity> findByGid(Integer gid);
	
	List<YskUserEntity> findByPid(Integer pid);
	
	YskUserEntity findByUserid(Integer userid);
	/**
	 * kipin:2018-04-26 add 
	 * 手机号登录
	 */
	public abstract YskUserEntity findByMobileAndStatusGreaterThan(String moblie, Byte status);
	
	/**
	 * kipin:2018-04-26 add 
	 * 用户名登录
	 */
	public abstract YskUserEntity findByAccountAndStatusGreaterThan(String Account, Byte status);
	
	/**
	 * kipin:2018-04-26 add 
	 * 更新某用户在表中的session
	 */
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value="update ysk_user set session_id=?1 where userid=?2", nativeQuery=true)
	public abstract void updateSession(String session_id, long userid);
	
	List<YskUserEntity> findByUseridAndLevelNotAndStatus(Integer userid,byte level,byte status);
	
	List<YskUserEntity> findByPidAndLevel(Integer pid, byte level); //我邀请的一级用户
	List<YskUserEntity> findByGidAndLevel(Integer gid, byte level); //我邀请的二级用户
}
