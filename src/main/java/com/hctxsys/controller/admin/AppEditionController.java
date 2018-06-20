package com.hctxsys.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskAppEditionEntity;
import com.hctxsys.service.AppEditionServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.vo.AppVo;

@Controller
public class AppEditionController {

	@Autowired
	private AppEditionServiceImpl appEditionService;

	/**
	 * app管理初始化查询
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/Admin/App/index")
	public String appindex(Model model) {
		YskAppEditionEntity yskappeditionentity = appEditionService.findApp();

		model.addAttribute("yskappeditionentity", yskappeditionentity);

		return "admin/app/index";
	}

	/**
	 * 点击确定修改App版本信息
	 * 
	 * @param appvo
	 * @return
	 */
	@RequestMapping(path = "/Admin/App/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateApp(AppVo appvo) {
		Result result = null;
		if (appvo.getId() != 0) {
			result = appEditionService.updateApp(appvo);
		} else {
			result = appEditionService.saveApp(appvo);
		}
		return result;
	}
}
