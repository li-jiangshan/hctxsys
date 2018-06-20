package com.hctxsys.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.hctxsys.service.CommonServiceImpl;

@Controller
public class CommonController {

	@Autowired
	private CommonServiceImpl commonService;

	/**
	 * 每日定时将用户积分转到积分明细表
	 * 
	 * @return
	 */
	@Scheduled(cron = "0 5 0 * * ?") // 从左往右依次是 秒 分 时
	public String insertUserWealthDetail() {
		commonService.cunUserWealthDetail();
		return null;
	}

	/**
	 * 每日加权平均
	 * 
	 * @return
	 */
	@Scheduled(cron = "30 5 0 * * ?") // 从左往右依次是 秒 分 时
	public String Reappearance() {
		commonService.Reappearance();
		return null;
	}
//	/**
//	 * 每日加权平均
//	 * 
//	 * @return
//	 */
//	@Scheduled(cron = "0 15 17 * * ?") // 从左往右依次是 秒 分 时
//	public String test() {
//		commonService.test();
//		return null;
//	}
}
