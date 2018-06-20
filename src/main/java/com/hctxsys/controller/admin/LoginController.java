package com.hctxsys.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.AdminLoginServiceImpl;
import com.hctxsys.util.Result;

/**
 * Created by kipin on 2018-04-19
 */
@Controller
public class LoginController {
	@Autowired
	private AdminLoginServiceImpl adminLogin;

	/**
	 * 首页
	 * @return view
	 */
	@RequestMapping(value = "/")
	public String login(@RequestHeader ("host") String hostName) {
		if(hostName.equals("www.huacaitx.com")) {
			return "home/login/hctxhome";
		} else {
			return "login";
		}
	}
	
	/**
	 * 登录验证
	 * 
	 * @param username 输入的用户名
	 * @param password 输入的密码
	 * @param identity 登录用户身份 2018-04-26 add 用于合并之前的小系统
	 * @param request session使用参数
	 * @return response
	 */
	@RequestMapping(value = "/dologin")
	public @ResponseBody Result doLoign(@RequestParam String username, @RequestParam String password,  HttpServletRequest request) {
		Result result = adminLogin.checkLogin(username, password, request);
		return result;
	}
}
