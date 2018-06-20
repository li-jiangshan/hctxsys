package com.hctxsys.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.service.MessageServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.vo.MessageVo;

@Controller
public class MessageController {
	@Autowired
	private MessageServiceImpl messageServiceImpl;
	
	@RequestMapping(value="/Admin/Message/index")
	public String findMessage(Model model,@RequestParam(defaultValue="0")Integer page,String keyword) throws Exception {
		Page<YskMessageEntity> findMessage = messageServiceImpl.findMessage(page, 10,keyword);
		List<MessageVo> messageList = new ArrayList<>();
		for (YskMessageEntity message : findMessage.getContent()) {
			MessageVo messageVo = new MessageVo();
			BeanUtils.copyProperties(message, messageVo);
			messageVo.setMobile(messageServiceImpl.findMobile(message.getUid()));
			messageList.add(messageVo);
		}
		int rows =(int)findMessage.getTotalElements();
		if(rows!=0) {
			model.addAttribute("rows", rows);//总记录数
		}
		model.addAttribute("messageList", messageList);
		model.addAttribute("pageCount", findMessage.getTotalPages());//总页数
		model.addAttribute("page",page);//当前页
		model.addAttribute("keyword", keyword);
		return "/admin/message/index";
	}
	
	/**删除消息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/message/delete/ids/{id}")
	@ResponseBody
	public Result deleteMessage(@PathVariable Integer id) {
		Result result = messageServiceImpl.deleteMessage(id);
		return result;
	}
	
	/**查看详细
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/message/add/ids/{id}")
	public String getMessage(Model model,@PathVariable Integer id) {
		MessageVo message = messageServiceImpl.getMessage(id);
		model.addAttribute("message", message);
		return "/admin/message/edit";
	}
	
	/**
	 * @param id
	 * @param type
	 * @param mobile
	 * @param title
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/admin/message/save")
	@ResponseBody
	public Result saveMessage(Integer id,Integer type,String mobile,String title,String content) {
		Result result = messageServiceImpl.saveMessage(id, type, mobile, title, content);
		return result;
	}
	
	@RequestMapping(value="/admin/message/add")
	public String add() {
		return "/admin/message/add";
	}
}
