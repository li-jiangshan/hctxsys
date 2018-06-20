package com.hctxsys.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hctxsys.service.IndexPageServiceImpl;

/**
 * Created by kipin on 2018-04-23
 */
@Controller
@RequestMapping(value = "admin")
public class IndexController {
	@Autowired
	private IndexPageServiceImpl indexPage;

	@RequestMapping(value = "index/index")
	public String index(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("total", indexPage.getTotal());
		model.addAttribute("date_total", indexPage.getDateTotal());
		String systemCostSum = indexPage.systemCostSum();
		model.addAttribute("systemCostSum", systemCostSum);
		String userWealthDetailSum = indexPage.userWealthDetailSum();
		model.addAttribute("userWealthDetailSum", userWealthDetailSum);
		return "admin/index/index";
	}
	
	/**
	 * 退出登录	kipin:2018-04-26 add
	 * 清除登录时保存的session，不要清除全部的session。
	 * 	因为CSS|JS有保存在session里面。
	 */
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		//管理员 uid & username & nickname & flag & islogin
		request.getSession().removeAttribute("uid");
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("nickname");
		request.getSession().removeAttribute("flag");
		request.getSession().removeAttribute("islogin");
		
		//商家 sellerId & account & mobile & username & in_time & flag
		request.getSession().removeAttribute("sellerId");
		request.getSession().removeAttribute("account");
		request.getSession().removeAttribute("mobile");
		request.getSession().removeAttribute("in_time");
		request.getSession().removeAttribute("flag");
		return "redirect:/";
	}
}
