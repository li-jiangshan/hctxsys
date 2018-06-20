package com.hctxsys.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskMenuEntity;
import com.hctxsys.service.MenuServiceImpl;

@Controller
public class MenuController {
	@Autowired
	private MenuServiceImpl menuService;

	/**
	 * 查询菜单
	 * 
	 * @param menuid
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/menu")
	@ResponseBody
	public Map<String, List<YskMenuEntity>> index(HttpServletRequest request, HttpSession session) {
		List<YskMenuEntity> toplist = menuService.getTopMenu(request, session);
		Integer flag = (Integer) request.getSession().getAttribute("flag");
		if (flag == null) {
			request.getSession().setAttribute("flag", 0);
		}
		List<YskMenuEntity> firstmenulist = menuService.getFirstMenu(request, session);
		List<YskMenuEntity> secondmenulist = menuService.getSecondMenu(request, session);
		List<YskMenuEntity> urllist = menuService.getUrl(request, session);

		Map<String, List<YskMenuEntity>> menuMapList = new HashMap<String, List<YskMenuEntity>>();
		menuMapList.put("toplist", toplist);
		menuMapList.put("firstmenulist", firstmenulist);
		menuMapList.put("secondmenulist", secondmenulist);
		menuMapList.put("urllist", urllist);

		return menuMapList;
	}

	@RequestMapping("/canshu")
	@ResponseBody
	public String canshu(@RequestParam(value = "menuid", required = false) Integer menuid,
			HttpServletRequest request, HttpSession session) {
		Integer flag = (Integer) request.getSession().getAttribute("flag");
		if (flag == null) {
			request.getSession().setAttribute("flag", 0);
		}
		if (menuid == null) {
		} else if (menuid == 0) {
			request.getSession().setAttribute("flag", 0);
		} else if (menuid == 1) {
			request.getSession().setAttribute("flag", 1);
		} else {
			request.getSession().setAttribute("flag", 2);
		}

		return null;
	}
}
