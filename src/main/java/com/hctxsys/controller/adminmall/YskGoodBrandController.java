package com.hctxsys.controller.adminmall;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskGoodBrandEntity;
import com.hctxsys.service.YskGoodBrandSerivceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;



@Controller
@RequestMapping("/Adminmall/Goodbrand")
public class YskGoodBrandController {
	
	@Autowired
	private YskGoodBrandSerivceImpl yskGoodBrandSerivceImpl;
	
	
	@GetMapping(path = "/all/{id}")
	public @ResponseBody List<?> getCatList(@PathVariable String id) {
		List<?> list = yskGoodBrandSerivceImpl.getBrList(Integer.valueOf(id));
		return list;
	}
	
	/**
	 * 品牌列表
	 * @return
	 */
	@GetMapping(path = "/index")
	public @ResponseBody ModelAndView getModelList(TableResult result) {
		TableResult tableResult = null;
		if(result.getTypeText()==null||"".equals(result.getTypeText())) {
			tableResult = yskGoodBrandSerivceImpl.findByOrderPage(result);
		}else {
			tableResult = yskGoodBrandSerivceImpl.findKeyword(result);
		}
		ModelAndView modelAndView = new ModelAndView("adminmall/goodbrand/index");
		modelAndView.addObject("tableResult", tableResult);
		return modelAndView;
	}
	
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	@GetMapping(path = "/keyword")
	public @ResponseBody ModelAndView findByKeyword(TableResult result) {
		TableResult tableResult = yskGoodBrandSerivceImpl.findKeyword(result);
		ModelAndView modelAndView = new ModelAndView("adminmall/goodbrand/index");
		modelAndView.addObject("tableResult", tableResult);
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping(path="/add")
	public String page() {
		return "adminmall/goodbrand/edit"; //返回页面
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id",required = false,defaultValue = "0") String id) {
		YskGoodBrandEntity brand = yskGoodBrandSerivceImpl.findById(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/goodbrand/edit");
		modelAndView.addObject("brand",brand);
		return modelAndView;
	}
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/update",method=RequestMethod.POST)
	@ResponseBody
	public Result updateBrand(YskGoodBrandEntity brand) {
		if(brand.getBrandName()==null||"".equals(brand.getBrandName())) {
			return new Result(0, "品牌名称不能为空", "/Adminmall/Goodbrand/index", "");
		}
		if(brand.getBrandOrder()==null||"".equals(brand.getBrandOrder())) {
			brand.setBrandOrder(0);
		}
		brand.setStatus((byte) 1);
		brand.setSellerId(0);
		YskGoodBrandEntity entity = yskGoodBrandSerivceImpl.updateBrand(brand);
		if (entity!=null) {
			return new Result(1, "保存成功", "/Adminmall/Goodbrand/index", "");
		}
		return new Result(0, "保存失败", "/Adminmall/Goodbrand/index", "");
	}
	
	/**
	 * 启用，禁用
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/setStatus",method=RequestMethod.GET)
	@ResponseBody
	public Result setStatus(@RequestParam String id) {
		YskGoodBrandEntity st = yskGoodBrandSerivceImpl.findById(Integer.valueOf(id));
		if(1==st.getStatus()) {
			st.setStatus((byte) 0);
		}else {
			st.setStatus((byte) 1);
		}
		YskGoodBrandEntity entity = yskGoodBrandSerivceImpl.updateBrand(st);
		if(1==entity.getStatus()) {
			Result result = new Result();
			result.setStatus(1);
			result.setInfo("启用成功");
			return result;
			//return new Result(1, "启用成功", "/Adminmall/Goodbrand/index", "");
		}else if(0==entity.getStatus()) {
			Result result = new Result();
			result.setStatus(1);
			result.setInfo("禁用成功");
			return result;
			//return new Result(1, "启用成功", "/Adminmall/Goodbrand/index", "");
		}else{
			Result result = new Result();
			result.setStatus(0);
			result.setInfo("操作失败");
			return result;
			//return new Result(1, "禁用成功", "/Adminmall/Goodbrand/index", "");
		}
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@RequestMapping(path = "/deletebrand",method=RequestMethod.GET)
	@ResponseBody
	public Result deleteBrand(@RequestParam String id) {
		int i = yskGoodBrandSerivceImpl.deleteBrand(Integer.valueOf(id));
		if (i==1) {
			Result result = new Result();
			result.setStatus(1);
			result.setInfo("删除成功，不可恢复");
			return result;
			//return new Result(1, "删除成功，不可恢复", "/Adminmall/Goodbrand/index", "");
		}
		Result result = new Result();
		result.setStatus(0);
		result.setInfo("删除失败");
		return result;
		//return new Result(0, "删除失败", "/Adminmall/Goodbrand/index", "");
	}
}
