package com.hctxsys.controller.examine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskBuyKucunIntegralApplyEntity;
import com.hctxsys.service.BuyKucunIntegralApplyServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Controller
@RequestMapping("Admin/IntegralAudit")
public class IntegralAuditController {
	
	@Autowired
	private BuyKucunIntegralApplyServiceImpl buyKucunIntegralApplyServiceImpl;
	
	/**
	 * @param result
	 * @return
	 */
	@RequestMapping(value = {"/index","/index/status/{status}"},method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView getList(TableResult result,@PathVariable(required=false) String status) {
		TableResult tableResult = null;
		if(status==null) {
			tableResult = buyKucunIntegralApplyServiceImpl.findList(result,(byte)0);
		}else {
			tableResult = buyKucunIntegralApplyServiceImpl.findList(result, Byte.valueOf(status));
		} 
		ModelAndView modelAndView = new ModelAndView("admin/integralaudit/index");
		modelAndView.addObject("tableResult", tableResult);
		modelAndView.addObject("status",status);
		return modelAndView;
	}
	
	
	/**
	 * 通过
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pass",method=RequestMethod.GET)
	@ResponseBody
	public Result passAudit(@RequestParam String id) {
		YskBuyKucunIntegralApplyEntity entity = buyKucunIntegralApplyServiceImpl.pass(Integer.valueOf(id));
		if(1==entity.getStatus()) {
			return new Result(1, "操作成功", "/Admin/IntegralAudit/index", "");
		}
		return new Result(0, "操作失败", "/Admin/IntegralAudit/index", "");
	}
	
	
	/**
	 * 不通过
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/notpass",method=RequestMethod.GET)
	@ResponseBody
	public Result notpassAudit(@RequestParam String id) {
		YskBuyKucunIntegralApplyEntity entity = buyKucunIntegralApplyServiceImpl.notpass(Integer.valueOf(id));
		if(2==entity.getStatus()) {
			return new Result(1, "操作成功", "/Admin/IntegralAudit/index", "");
		}
		return new Result(0, "操作失败", "/Admin/IntegralAudit/index", "");
	}
}
