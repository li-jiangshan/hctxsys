package com.hctxsys.controller.adminmall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.entity.YskTravelEntity;
import com.hctxsys.entity.YskTravelTypeEntity;
import com.hctxsys.service.TravelServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.TravelVo;

@Controller
@RequestMapping("Adminmall/Module")
public class TravelController {
	@Autowired
	private TravelServiceImpl travelService;

	/**
	 * 显示旅游模块页面查询列表
	 * 
	 * @param tableResult
	 * @return
	 */
	@GetMapping("travelIndex")
	public ModelAndView index(TableResult tableResult) {
		ModelAndView modelAndView = new ModelAndView("/adminmall/module/travelIndex");
		TableResult result = travelService.index(tableResult);
		List<YskTravelTypeEntity> allType = travelService.getAllType();
		modelAndView.addObject("typeList", allType);
		modelAndView.addObject("tableResult", result);
		return modelAndView;
	}

	/**
	 * 跳转到旅游信息发布页 查询旅游信息列表
	 * 
	 * @return
	 */
	@GetMapping("addTravel")
	public ModelAndView addTravel() {
		ModelAndView modelAndView = new ModelAndView("adminmall/module/travelAdd");
		List<YskTravelTypeEntity> allType = travelService.getAllType();
		modelAndView.addObject("typeList", allType);
		return modelAndView;
	}

	/**
	 * 旅游信息插入的逻辑操作
	 * 
	 * @param travelvo
	 * @return
	 */
	@PostMapping("travelInsert")
	@ResponseBody
	public Result travelInsert(@RequestBody TravelVo travelvo) {
		return travelService.travelInsert(travelvo);
	}

	/**
	 * 根据id查询旅游信息跳转编辑页面
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("travelEdit/{id}")
	public ModelAndView ModuleEdit(@PathVariable Integer id) {
		YskTravelEntity ysktravelentity = travelService.findById(id);
		List<YskTravelTypeEntity> allType = travelService.getAllType();

		ModelAndView modelAndView = new ModelAndView("adminmall/module/travelAdd");
		modelAndView.addObject("travel", ysktravelentity);
		modelAndView.addObject("typeList", allType);
		return modelAndView;
	}

	/**
	 * 通过旅游id查图片list
	 * 
	 * @param moduleId
	 * @return
	 */
	@GetMapping(path = "/imgByModuleId/{moduleId}")
	@ResponseBody
	public List<YskModuleImgEntity> getImgByModuleId(@PathVariable Integer moduleId) {
		List<YskModuleImgEntity> list = travelService.getImg(moduleId);
		return list;
	}

	/**
	 * 删除图片
	 * 
	 * @return
	 */
	@GetMapping("/delImg/{id}/{moduleId}")
	@ResponseBody
	public List<YskModuleImgEntity> deleteImg(@PathVariable Integer moduleId,@PathVariable Integer id) {
		travelService.deleteImg(id);
		List<YskModuleImgEntity> list = travelService.getImg(moduleId);
		return list;
	}

	/**
	 * 修改旅游信息
	 * 
	 * @param travelvo
	 * @return
	 */
	@PostMapping("travelUpdate")
	@ResponseBody
	public Result travelUpdate(@RequestBody TravelVo travelvo) {
		return travelService.travelUpdate(travelvo);
	}

	/**
	 * 删除旅游信息
	 * 
	 * @param moduleId
	 * @return
	 */
	@RequestMapping("/traveldelete/moduleId/{moduleId}")
	@ResponseBody
	public Result travelDelete(@PathVariable Integer moduleId) {
		return travelService.deleteById(moduleId);
	}
}
