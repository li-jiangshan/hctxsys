package com.hctxsys.controller.adminmall;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskGoodAttributeEntity;
import com.hctxsys.entity.YskGoodModelEntity;
import com.hctxsys.repository.YskGoodModelRepository;
import com.hctxsys.service.YskGoodAttributeSerivceImpl;
import com.hctxsys.service.YskGoodModelSerivceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Controller
@RequestMapping("/Adminmall/Goodmodel")
public class YskGoodModelController {
	
	@Autowired
	private YskGoodModelRepository yskGoodModelRepository;
	
	@Autowired
	private YskGoodModelSerivceImpl yskGoodModelSerivceImpl;
	
	@Autowired
	private YskGoodAttributeSerivceImpl yskGoodAttributeSerivceImpl;
	/**
	 * 添加商品时的list
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/all/{id}")
	public @ResponseBody List<?> getMoList(@PathVariable String id) {
		List<?> list = yskGoodModelSerivceImpl.getMoList(Integer.valueOf(id));
		return list;
	}
	
	/**
	 * 模型列表
	 * @return
	 */
	@GetMapping(path = "/index")
	public @ResponseBody ModelAndView getModelList(TableResult result) {
		TableResult tableResult = yskGoodModelSerivceImpl.getModelListPage(result);
		ModelAndView modelAndView = new ModelAndView("adminmall/goodmodel/index");
		modelAndView.addObject("tableResult", tableResult);
		return modelAndView;
	}
	
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	@GetMapping(path = "/keyword")
	public @ResponseBody ModelAndView findByKeyword(@RequestParam String keyword) {
		List<YskGoodModelEntity> list = yskGoodModelSerivceImpl.findKeyword(keyword);
		ModelAndView modelAndView = new ModelAndView("adminmall/goodmodel/index");
		modelAndView.addObject("model", list);
		return modelAndView;
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping("/add")
	public String page() {
		return "adminmall/goodmodel/edit"; //返回页面
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id",required = false,defaultValue = "0") String id) {
		YskGoodModelEntity model = yskGoodModelSerivceImpl.findById(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/goodmodel/edit");
		modelAndView.addObject("model",model);
		return modelAndView; //返回页面
	}
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/update",method=RequestMethod.POST)
	@ResponseBody
	public Result updateModel(YskGoodModelEntity model,HttpSession httpSession) {
		//System.err.println(model);
		if(model.getModelName()==null||"".equals(model.getModelName())) {
			return new Result(0, "模型名称不能为空", "/Adminmall/Goodmodel/index", "");
		}
		if (model.getId()==0) {
			int id = yskGoodModelRepository.findMaxId().get(0).getId()+1;
			model.setId(id);
		}
		model.setSellerId(0);
		YskGoodModelEntity entity = yskGoodModelSerivceImpl.updateModel(model);
		if (entity!=null) {
			return new Result(1, "保存成功", "/Adminmall/Goodmodel/index", "");
		}
		return new Result(0, "保存失败", "/Adminmall/Goodmodel/index", "");
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@RequestMapping(path = "/deletemodel",method=RequestMethod.GET)
	@ResponseBody
	public Result deleteCat(@RequestParam String id) {
		List<YskGoodAttributeEntity> list = yskGoodAttributeSerivceImpl.getAttributeList(Integer.valueOf(id));
		if (!list.isEmpty()) {
			return new Result(0, "不可删除，该模型下有规格", "/Adminmall/Goodmodel/index", "");
		}
		int i = yskGoodModelSerivceImpl.deleteModel(Integer.valueOf(id));
		if (i==1) {
			return new Result(1, "删除成功，不可恢复", "/Adminmall/Goodmodel/index", "");
		}
		return new Result(0, "删除失败", "/Adminmall/Goodmodel/index", "");
	}
}
