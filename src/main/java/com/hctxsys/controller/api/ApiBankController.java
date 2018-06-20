package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskUserBankEntity;
import com.hctxsys.service.api.ApiBankServiceImpl;
import com.hctxsys.vo.api.ApiAddBankVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/bank")
public class ApiBankController {
	@Autowired
	private ApiBankServiceImpl apiBankServiceImpl;
	
	/**添加银行卡
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult addBank(@RequestBody ApiAddBankVo vo) {
		JsonResult result = apiBankServiceImpl.addBank(vo);
		return result;
	}
	
	/**查找我的银行卡
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/myBank",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult myBank(@RequestBody YskUserBankEntity vo) {
		JsonResult result = apiBankServiceImpl.findBank(vo.getUid());
		return result;
	}
}
