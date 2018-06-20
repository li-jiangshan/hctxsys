package com.hctxsys.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.service.api.ApiAroundServiceImpl;

@Controller
@RequestMapping(value = "/home/around")
public class ApiAroundController {

	@Autowired
	private ApiAroundServiceImpl apiAroundService;
	
	@RequestMapping(value = "/detail/{uid}", method = RequestMethod.GET)
	public String getAround(@PathVariable Integer uid, Model model) {
		
		YskShopInfoEntity around = apiAroundService.findAround(uid);
		List<YskGoodCommentEntity> goodCommentListAll = apiAroundService.getGoodComment(uid);
		returnUserName(goodCommentListAll);
		model.addAttribute("info", around);
		model.addAttribute("list", goodCommentListAll);
		return "home/around/detail";
	}
	
	/**
	 * 用户名处理
	 * @param list
	 * @return 处理后用户名
	 */
	public List<YskGoodCommentEntity> returnUserName(List<YskGoodCommentEntity> list) {
		for (int i = 0; i < list.size(); i++) {
			String username = list.get(i).getUsername();
			if (username != null && username.length() != 0) {
				username = username.substring(0, 1) + "***";
			} else {
				username = "匿名";
			}
			list.get(i).setUsername(username);
		}
		return list;
	}
}
