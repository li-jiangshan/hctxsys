package com.hctxsys.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.entity.YskMessageReadEntity;
import com.hctxsys.repository.MessageReadRepository;
import com.hctxsys.repository.MessageRepository;

@Service("apiMessageListService")
public class ApiMessageServiceImpl {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private MessageReadRepository messageReadRepository;
	
	public List<YskMessageEntity> findMessageList(int uid) {
		return messageRepository.findMessageList(uid);
	}
	
	public YskMessageReadEntity findMessageRead(int uid) {
		return messageReadRepository.findMessageRead(uid);
	}
	
	public YskMessageEntity findMessageById(int id) {
		return messageRepository.findMessageById(id);
	}
	
	public YskMessageEntity modifyMessageById(YskMessageEntity message) {
		return messageRepository.saveAndFlush(message);
	}
	
	public YskMessageReadEntity modifyMessageById(YskMessageReadEntity message) {
		return messageReadRepository.saveAndFlush(message);
	}
}
