package com.hctxsys.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.api.ApiNewsServiceImpl;
import com.hctxsys.vo.api.ApiGetNewsVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/news")
public class ApiNewsController {
	@Autowired
	private ApiNewsServiceImpl apiNewsServiceImpl;
	
	@RequestMapping(value="/getNews",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult getNews(@RequestBody ApiGetNewsVo vo) {
		JsonResult result = apiNewsServiceImpl.getNews(vo.getType(), vo.getPage(), vo.getPageSize());
		return result;
	}
}
