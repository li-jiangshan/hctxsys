package com.hctxsys.vo.api;

import java.util.ArrayList;
import java.util.List;

import com.hctxsys.entity.YskMessageEntity;

public class ApiMessageVo {
	public List<YskMessageEntity> messageList = new ArrayList<YskMessageEntity>();
	public String[] readmessageid;
	
	public List<YskMessageEntity> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<YskMessageEntity> messageList) {
		this.messageList = messageList;
	}
	public String[] getReadmessageid() {
		return readmessageid;
	}
	public void setReadmessageid(String[] readmessageid) {
		this.readmessageid = readmessageid;
	}
	
}
