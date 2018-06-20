package com.hctxsys.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hctxsys.entity.YskArticleEntity;
import com.hctxsys.service.api.ApiUserHelpServiceImpl;

/**
 * @ClassName:ApiUserHelpController
 * @Author:li
 * @CreateDate:2018年4月23日
 */
@Controller
@RequestMapping(value = "/mall/user")
public class ApiUserHelpController {
	
	@Autowired
	private ApiUserHelpServiceImpl apiUserHelpService;

	/**
	 * 获取新手上路
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userhelp", method = RequestMethod.GET)
	public String getAgreement(Model model) {
		List<YskArticleEntity> userHelpInfo = apiUserHelpService.findUserHelp();
		model.addAttribute("userHelpList", userHelpInfo);
		return "/mall/user/userhelp";
	}
	
	/**
	 * 根据id获取新手上路详细
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/helpdetail/id/{id}", method = RequestMethod.GET)
	public String getUserHelpDetail(@PathVariable Integer id, Model model) {
		
		Optional<YskArticleEntity> userHelpDetail = apiUserHelpService.findUserHelpDetail(id);
		model.addAttribute("info", userHelpDetail.get());
		return "/mall/user/helpdetail";
	}
}
