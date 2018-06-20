package com.hctxsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskMessageReadEntity;

public interface MessageReadRepository extends JpaRepository<YskMessageReadEntity, Integer> {
	
	@Query(value = "SELECT * FROM ysk_message_read WHERE uid = ?1", nativeQuery = true)
	public YskMessageReadEntity findMessageRead(int uid);
	
}
