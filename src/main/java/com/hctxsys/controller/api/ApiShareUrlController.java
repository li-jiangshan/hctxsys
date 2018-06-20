package com.hctxsys.controller.api;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.service.api.ApiShareUrlServiceImpl;
import com.hctxsys.util.CreateQrCode;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * @ClassName:ApiShareUrlController
 * @Author:li
 * @CreateDate:2018年4月23日
 */
@Controller
@RequestMapping(value = "/home/login")
public class ApiShareUrlController {

	@Autowired
	private ApiShareUrlServiceImpl apiShareUrlService;

	/**
	 * 获取分享二维码
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/usercode/uid/{uid}", method = RequestMethod.GET)
	public String getQrCode(@PathVariable Integer uid, Model model) {
		Optional<YskUserEntity> user = apiShareUrlService.findUserInfo(uid);
		// TODO判断终端 url需要区分
//		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = requestAttributes.getRequest();
//		HttpServletResponse response = requestAttributes.getResponse();
//		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//		Browser browser = userAgent.getBrowser();
//		System.out.println(browser);
//		OperatingSystem os = userAgent.getOperatingSystem();
//		System.out.println(os);
//		if (!os.toString().toLowerCase().contains("android")) {
//			response.setHeader("Cache-Control", "no-store");
//			response.setHeader("Pragrma", "no-cache");
//			response.setDateHeader("Expires", 0);
//		}
		// TODO 发布域名
		String url = "http://113.235.113.113:80/home/login/selectreg/pid/" + uid;
		CreateQrCode codePath = new CreateQrCode();
		String codeUrl = codePath.createQrCode(uid.toString(), url);
		model.addAttribute("account", user.get().getAccount());
		model.addAttribute("code_path", "uploads/usercode/" + codeUrl);
		model.addAttribute("url", url);
		return "/home/login/usercode";
	}
	
	/**
	 * 选择注册
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectreg/pid/{pid}", method = RequestMethod.GET)
	public String selectreg(@PathVariable Integer pid, Model model) {
		model.addAttribute("pid", pid);
		return "/home/login/selectreg";
	}
	
	/**
	 * 个人注册
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register/pid/{pid}", method = RequestMethod.GET)
	public String register(@PathVariable Integer pid, Model model) {
		Optional<YskUserEntity> user = apiShareUrlService.findUserInfo(pid);
		model.addAttribute("mobile", user.get().getMobile());
		return "/home/login/register";
	}
	/**
	 * 企业注册
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/companyregister/pid/{pid}", method = RequestMethod.GET)
	public String companyRegister(@PathVariable Integer pid, Model model) {
		Optional<YskUserEntity> user = apiShareUrlService.findUserInfo(pid);
		model.addAttribute("mobile", user.get().getMobile());
		return "/home/login/companyregister";
	}
	/**
	 * app下载
	 * @return
	 */
	@RequestMapping(value = "/appdownload", method = RequestMethod.GET)
	public String appDownload() {
		return "/home/login/appdownload";
	}
	
	/**
	 * app下载帮助
	 * @return
	 */
	@RequestMapping(value = "/appdownload/help", method = RequestMethod.GET)
	public String appDownloadHelp() {
		return "/home/login/appdownloadhelp";
	}
	
	@RequestMapping(value = "/hctxhome", method = RequestMethod.GET)
	public String hctxHome() {
		return "/home/login/hctxhome";
	}
}
