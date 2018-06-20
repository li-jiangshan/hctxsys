package com.hctxsys.controller.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.entity.YskMessageReadEntity;
import com.hctxsys.service.api.ApiMessageServiceImpl;

import net.sf.json.JSONArray;

/**
 * @ClassName:ApiMessageListController
 * @Author:li
 * @CreateDate:2018年4月23日
 */
@Controller
@RequestMapping(value = "/home/message")
public class ApiMessageListController {
	
	@Autowired
	private ApiMessageServiceImpl apiMessageService;
	
	int uid;

	/**
	 * 获取用户消息列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	public String getMessageList(@PathVariable Integer uid, Model model) {
		
		this.uid = uid;
		
		// 根据用户获取消息列表
		List<YskMessageEntity> messageList = apiMessageService.findMessageList(this.uid);
		
		// 根据用户获取为读取消息
		YskMessageReadEntity messageRead = apiMessageService.findMessageRead(uid);
		
		String[] mid_arr = null;
		if (messageRead != null) {
			mid_arr = messageRead.getMessage_id().split(",");
		}
		model.addAttribute("msgList", messageList);
		model.addAttribute("mid_arr", JSONArray.fromObject(mid_arr));
		return "home/message/index";
	}
	
	/**
	 * 根据id获取消息详细
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/id/{id}", method = RequestMethod.GET)
	public String getMessageDetail(@PathVariable Integer id, Model model) {

		// 根据用户获取为读取消息
		YskMessageReadEntity messageRead = apiMessageService.findMessageRead(this.uid);

		// 根据id获取消息详细
		YskMessageEntity message = apiMessageService.findMessageById(id);
		if (message.getUid() == 0) {
			String messageReaded;
			Object mid = message.getId();
			if (messageRead != null) {
				if (messageRead.getMessage_id() != null) {
					String[] arr = messageRead.getMessage_id().split(",");
					List<String> list = Arrays.asList(arr);
					if (!list.contains(mid.toString())) {
						messageReaded = messageRead.getMessage_id() + message.getId();
						messageRead.setMessage_id(messageReaded);
						apiMessageService.modifyMessageById(messageRead);
					}
				} 
			} else {
				YskMessageReadEntity messageRead2 = new YskMessageReadEntity();
				messageReaded = message.getId() + ",";
				messageRead2.setMessage_id(messageReaded);
				messageRead2.setUid(this.uid);
				apiMessageService.modifyMessageById(messageRead2);
			}
		} else {
			message.setStatus((byte) 1);
			apiMessageService.modifyMessageById(message);
		}
		model.addAttribute("info", message);
		return "home/message/detail";
	}
}
