package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.api.ApiUserSignServiceImpl;
import com.hctxsys.vo.api.JsonResult;

/**
 * @ClassName:ApiUserSignController
 * @Author:wu
 * @CreateDate:2018年5月11日
 */
@Controller
@RequestMapping(value = "/home/usersign")
public class ApiUserSignController {

	@Autowired
	private ApiUserSignServiceImpl apiUserSignService;

	/**
	 * 获取用户签到数据
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uid/{uid}", method = RequestMethod.GET)
	public String getQdData(@PathVariable Integer uid, Model model) {
		apiUserSignService.getQdData(uid, model);
		return "/home/usersign/usersign";
	}
	
	@RequestMapping(value = "/signAdd/uid/{uid}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult signAdd(@PathVariable Integer uid) {
		return apiUserSignService.signadd(uid);
	}
}
