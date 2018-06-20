package com.hctxsys.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.entity.YskMessageReadEntity;
import com.hctxsys.service.api.ApiMessageServiceImpl;
import com.hctxsys.vo.api.ApiMessageVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/massage")
public class ApiMessageController {
	@Autowired
	private ApiMessageServiceImpl apiMessageServiceImpl;
	
	@RequestMapping(value="/getMassage",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult getMessageList(@RequestBody YskMessageEntity vo) {
		JsonResult result = new JsonResult();
		ApiMessageVo apiMessageVo = new ApiMessageVo();
		if(vo.getUid()<1) {
			result.setCode(0);
			result.setMessage("用户id不能小于1");
			return result;
		}
		List<YskMessageEntity> list = apiMessageServiceImpl.findMessageList(vo.getUid());
		if(list.size()==0) {
			result.setCode(1);
			result.setMessage("暂无消息");
			return result;
		}
		apiMessageVo.setMessageList(list);
		YskMessageReadEntity read = apiMessageServiceImpl.findMessageRead(vo.getUid());
		if(null!=read) {
			String message_id = read.getMessage_id();
			String[] split = message_id.split(",");
			apiMessageVo.setReadmessageid(split);
		}
		result.setCode(1);
		result.setMessage("查询消息列表成功");
		result.setData(apiMessageVo);
		return result;
	}
}
