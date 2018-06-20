package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskGoodCarEntity;
import com.hctxsys.service.api.ApiGoodCarServiceImpl;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/goodCar")
public class ApiGoodCarController {
	@Autowired
	private ApiGoodCarServiceImpl apiGoodCarServiceImpl;
	
	@RequestMapping(value="/saveCar",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveGoodCar(@RequestBody YskGoodCarEntity goodCarEntity) {
		JsonResult result = apiGoodCarServiceImpl.saveGoodCar(goodCarEntity);
		return result;
	}
}
