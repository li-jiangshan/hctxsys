package com.hctxsys.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName:ApiUserRightController
 * @Author:li
 * @CreateDate:2018年4月18日
 */
@Controller
@RequestMapping(value = "/home/userupdate")
public class ApiUserRightController {

	/**
	 * 获取用户权益
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userright", method = RequestMethod.GET)
	public String getAgreement(Model model) {
		return "/home/userupdate/userright";
	}
}
