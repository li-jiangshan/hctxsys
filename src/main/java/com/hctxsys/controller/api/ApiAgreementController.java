package com.hctxsys.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hctxsys.entity.YskArticleEntity;
import com.hctxsys.service.api.ApiAgreementServiceImpl;

import net.sf.json.JSONArray;

/**
 * @ClassName:ApiAgreementController
 * @Author:li
 * @CreateDate:2018年4月18日
 */
@Controller
@RequestMapping(value = "/home")
public class ApiAgreementController {

	@Autowired
	private ApiAgreementServiceImpl apiAgreementService;

	/**
	 * 获取用户注册协议
	 * @param model
	 * @return agreement。html
	 */
	@RequestMapping(value = "/login/agreement", method = RequestMethod.GET)
	public String getAgreement(Model model) {
		Optional<YskArticleEntity> agreementInfo = apiAgreementService.getAboutus(3);
		model.addAttribute("uss", agreementInfo.get());
		return "/home/login/agreement";
	}
	
	
	/**
	 * 关于我们
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/usercenter/aboutus", method = RequestMethod.GET)
	public String getAboutus(Model model) {
		Optional<YskArticleEntity> aboutus = apiAgreementService.getAboutus(4);
		
		model.addAttribute("uss", aboutus.get());
		return "/home/usercenter/aboutus";
	}
}
