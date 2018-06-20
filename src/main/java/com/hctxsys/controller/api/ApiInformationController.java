package com.hctxsys.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.service.api.ApiInformationServiceImpl;

/**
 * @ClassName:ApiInformationController
 * @Author:li
 * @CreateDate:2018年4月25日
 */
@Controller
@RequestMapping(value = "/home")
public class ApiInformationController {

	@Autowired
	private ApiInformationServiceImpl apiInformationService;
	
	/**
	 * 首页咨询中心
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index/index", method = RequestMethod.GET)
	public String getInformation(Model model) {
		// 取新闻栏
		List<YskNewsTitleEntity> newsTitleList = apiInformationService.findNewsTitleList(346);
		List<YskNewsTitleEntity> helpCenter = apiInformationService.findNewsTitleList(6);
		model.addAttribute("menu_son", newsTitleList);
		model.addAttribute("help_son", helpCenter);
		// 取栏目下的新闻
		List<YskNewsEntity> newsList = apiInformationService.findNewsList(5);
		model.addAttribute("newtoplist", newsList);
		return "/home/index/information";
	}
	
	/**
	 * 新闻中心
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/news/newscenter/new_ids/{id}", method = RequestMethod.GET)
	public String getNewsCenter(@PathVariable Integer id, Model model) {
		// 取新闻栏
		List<YskNewsTitleEntity> newsTitleList = apiInformationService.findNewsTitleList(346);
		List<YskNewsTitleEntity> helpCenter = apiInformationService.findNewsTitleList(6);
		model.addAttribute("menu_son", newsTitleList);
		model.addAttribute("help_son", helpCenter);
		model.addAttribute("pageId", id);
		// 取新闻
		if (id != 0) {
			List<YskNewsEntity> newsList = apiInformationService.findNewsListNotLimit(id);
			model.addAttribute("newtoplist", newsList);
		}
		return "/home/news/newscenter";
	}
	
	/**
	 * 新闻详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/news/newsdetail/news_id/{id}", method = RequestMethod.GET)
	public String getNewsDetail(@PathVariable Integer id, Model model) {
		if (id != 0) {
			YskNewsEntity newsDetail = apiInformationService.findNewsDetail(id);
			model.addAttribute("news_detail", newsDetail);
			newsDetail.setTimes(newsDetail.getTimes() + 1);
			apiInformationService.setTimes(newsDetail);
		}
		return "/home/news/newsdetail";
	}
	
	/**
	 * 帮助中心
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/usercenter/helpcenter/new_ids/{id}", method = RequestMethod.GET)
	public String getUserCenter(@PathVariable Integer id, Model model) {
		
		Optional<YskConfigEntity> configEntity = apiInformationService.findById(9);
		model.addAttribute("serverTel", configEntity.get().getValue());
		
		List<YskNewsTitleEntity> helpCenter = apiInformationService.findNewsTitleList(6);
		model.addAttribute("help_son", helpCenter);
		model.addAttribute("pageId", id);
		if (id != 0) {
			List<YskNewsEntity> newsDetail = apiInformationService.findNewsListNotLimit(id);
			model.addAttribute("helplist", newsDetail);
		}
		
		return "/home/usercenter/helpcenter";
	}
}
