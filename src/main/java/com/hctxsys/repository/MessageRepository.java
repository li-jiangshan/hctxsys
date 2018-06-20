package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskMessageEntity;

public interface MessageRepository extends JpaRepository<YskMessageEntity, Integer>, JpaSpecificationExecutor<YskMessageEntity> {

	@Query(value = "SELECT * FROM ysk_message WHERE uid = 0 or uid = ?1 ORDER BY id DESC LIMIT 50", nativeQuery = true)
	public List<YskMessageEntity> findMessageList(int uid);
	
	@Query(value = "SELECT * FROM ysk_message WHERE id = ?1", nativeQuery = true)
	public YskMessageEntity findMessageById(int id);
}
