package com.hctxsys.service.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.YsKUserRepository;

@Service("apiShareUrlService")
public class ApiShareUrlServiceImpl {

	@Autowired
	private YsKUserRepository userRepository;
	
	public Optional<YskUserEntity> findUserInfo(int uid) {
		return userRepository.findById(uid);
	}
}
