package com.hctxsys.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskAmountMoneyEntity;
import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskReturnIntegralEntity;
import com.hctxsys.entity.YskTurntableLvEntity;
import com.hctxsys.service.ConfigServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.vo.ConfigVo;

@Controller
public class ConfigController {

	@Autowired
	private ConfigServiceImpl configService;

	/**
	 * 首页-系统配置
	 *
	 * @return
	 */
	@RequestMapping("/Admin/Config/group")
	public String baseindex(Model model) {
		List<YskConfigEntity> list = configService.selectbase();

		Map<String, Object> maplist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getValue());
			maplist.putAll(hm);
		}

		Map<String, Object> idlist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getId());
			idlist.putAll(hm);
		}

		model.addAttribute("keysMap", maplist);
		model.addAttribute("idsMap", idlist);

		return "admin/config/base"; // 返回页面
	}

	/**
	 * 首页系统配置点击确定
	 *
	 * @return
	 */
	@RequestMapping(path = "/Admin/Config/baseupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result baseupdate(ConfigVo configVo) {

		Result result = configService.baseUpdate(configVo);

		return result;
	}

	/**
	 * 首页-基本设置
	 *
	 * @return
	 */
	@RequestMapping("/Admin/Config/system")
	public String systemindex(Model model) {
		List<YskConfigEntity> list = configService.selectsystem();

		Map<String, Object> maplist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getValue());
			maplist.putAll(hm);
		}

		Map<String, Object> idlist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getId());
			idlist.putAll(hm);
		}

		model.addAttribute("keysMap", maplist);
		model.addAttribute("idsMap", idlist);

		return "admin/config/system"; // 返回页面
	}
	
	/**
	 * 首页基本设置点击确定
	 *
	 * @return
	 */
	@RequestMapping(path = "/Admin/Config/systemupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result systemupdate(ConfigVo configVo) {

		Result result = configService.systemUpdate(configVo);

		return result;
	}
	
	/**
	 * 首页-分销设置
	 *
	 * @return
	 */
	@RequestMapping("/Admin/Config/fee")
	public String feeindex(Model model) {
		List<YskConfigEntity> list = configService.selectfee();

		Map<String, Object> maplist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getValue());
			maplist.putAll(hm);
		}

		Map<String, Object> idlist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getId());
			idlist.putAll(hm);
		}

		model.addAttribute("keysMap", maplist);
		model.addAttribute("idsMap", idlist);

		return "admin/config/fee"; // 返回页面
	}
	
	/**
	 * 首页分销设置点击确定
	 *
	 * @return
	 */
	@RequestMapping(path = "/Admin/Config/feeupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result feeupdate(ConfigVo configVo) {

		Result result = configService.feeUpdate(configVo);

		return result;
	}
	
	/**
	 * 首页-转盘设置
	 *
	 * @return
	 */
	@RequestMapping("/Admin/Config/turntable")
	public String turntableindex(Model model) {
		List<YskTurntableLvEntity> list = configService.selectturntable();

		model.addAttribute("keysMap", list);

		return "admin/config/turntable"; // 返回页面
	}
	
	/**
	 * 首页转盘配置点击确定
	 *
	 * @return
	 */
	@RequestMapping(path = "/Admin/Config/turntableupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result turntableupdate(ConfigVo configVo) {

		Result result = configService.turntableUpdate(configVo);

		return result;
	}
	
	/**
	 * 首页-网站开关
	 *
	 * @return
	 */
	@RequestMapping("/Admin/Config/siteclose")
	public String sitecloseindex(Model model) {
		List<YskConfigEntity> list = configService.selectsiteclose();

		Map<String, Object> maplist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getValue());
			maplist.putAll(hm);
		}

		Map<String, Object> idlist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getId());
			idlist.putAll(hm);
		}
		
		Map<String, Object> tiplist = new HashMap<String, Object>();
		for (YskConfigEntity configentity : list) {
			Map<String, Object> hm = new HashMap<String, Object>();
			hm.put(configentity.getName(), configentity.getTip());
			tiplist.putAll(hm);
		}

		model.addAttribute("keysMap", maplist);
		model.addAttribute("idsMap", idlist);
		model.addAttribute("tipMap", tiplist);

		return "admin/config/siteclose"; // 返回页面
	}
	
	/**
	 * 首页分销设置点击确定
	 *
	 * @return
	 */
	@RequestMapping(path = "/Admin/Config/sitecloseupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result sitecloseupdate(ConfigVo configVo) {

		Result result = configService.sitecloseUpdate(configVo);

		return result;
	}

	
	/**
	 * 首页-返还积分
	 *
	 * @return
	 */
	@RequestMapping("/Admin/Config/integral")
	public String integralindex(Model model) {
		List<YskReturnIntegralEntity> list = configService.selectReturnIntegral();
		List<YskAmountMoneyEntity> amountMoneylist = configService.selectAmountMoney();
		
		model.addAttribute("keysMap", list);
		if(amountMoneylist.size()==0) {
			model.addAttribute("amountMoneyId", 0);
			model.addAttribute("amountMoney", 0);
		}else {
			model.addAttribute("amountMoneyId", amountMoneylist.get(0).getId());
			model.addAttribute("amountMoney", amountMoneylist.get(0).getAmountMoney());
		}
		

		return "admin/config/integral"; // 返回页面
	}
	
	/**
	 * 首页返还积分点击确定
	 *
	 * @return
	 */
	@RequestMapping(path = "/Admin/Config/integralupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result integralupdate(YskReturnIntegralEntity entity,String amountMoney) {

		Result result = configService.returnIntegralUpdate(entity,amountMoney);

		return result;
	}
}
