package com.hctxsys.controller.adminmall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.hctxsys.entity.YskHealthyEntity;
import com.hctxsys.entity.YskHealthyTypeEntity;
import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.service.HealthyServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.CarVo;
import com.hctxsys.vo.HealthyVo;

@Controller
@RequestMapping("Adminmall/Module")
public class HealthyController {
	@Autowired
	HealthyServiceImpl healthyServiceImpl;
	
	/**
	 * 大健康列表
	 * @param result
	 * @return
	 */
	@GetMapping("Healthy")
	public ModelAndView getHealthyList(TableResult result) {
		TableResult tableResult = healthyServiceImpl.indexTable(result);
		List<YskHealthyTypeEntity> allType = healthyServiceImpl.getAllType();
		ModelAndView view = new ModelAndView("/adminmall/module/healthyIndex");
		view.addObject("typeList",allType);
		view.addObject("tableResult", tableResult);
		return view;
		
	}
	
	
	/**
     * 跳转到大健康信息发布页
     * @return
     */
    @GetMapping("addHealthy")
    public ModelAndView addHealthy() {
        ModelAndView modelAndView = new ModelAndView("adminmall/module/healthyAdd");
        List<YskHealthyTypeEntity> allType = healthyServiceImpl.getAllType();
        modelAndView.addObject("typeList",allType);
        return modelAndView;
    }
	
    
    /**
     * 大健康信息插入的逻辑操作
     * @return
     */
    @PostMapping("healthyInsert")
    @ResponseBody
    public Result healthyInsert(@RequestBody HealthyVo healthyVo) {
        return healthyServiceImpl.healthyInsert(healthyVo);
    }
    
    @GetMapping("healthyEdit/{id}")
    public ModelAndView healthyEdit(@PathVariable Integer id) {
        YskHealthyEntity healthy = healthyServiceImpl.findById(id);
        List<YskHealthyTypeEntity> allType = healthyServiceImpl.getAllType();
        ModelAndView modelAndView = new ModelAndView("adminmall/module/healthyAdd");
        modelAndView.addObject("healthy",healthy);
        modelAndView.addObject("typeList",allType);
        return modelAndView;
    }
    
    @PostMapping("healthyUpdate")
    @ResponseBody
    public Result healthyUpdate(@RequestBody HealthyVo healthyVo) {
        return healthyServiceImpl.healthyUpdate(healthyVo);
    }
    
    @PostMapping("healthyDelete/{id}")
    @ResponseBody
    public Result healthyDelete(@PathVariable Integer id) {
        return healthyServiceImpl.deleteById(id);
    }
    
    @GetMapping(path = "/imgByModuleId/healthy/{ModuleId}")
    @ResponseBody
    public List<YskModuleImgEntity> getImgByModuleId(@PathVariable Integer ModuleId) {
        List<YskModuleImgEntity> list = healthyServiceImpl.getImg(ModuleId);
        return list;
    }
    
    /**
     * 图片删除
     * @param moduleId
     * @param id
     * @return
     */
    @GetMapping("/delHealthyImg/{id}/{moduleId}")
    @ResponseBody
    public  List<YskModuleImgEntity> delImg(@PathVariable Integer moduleId,@PathVariable Integer id) {
    	healthyServiceImpl.deleteImg(id);
        List<YskModuleImgEntity> list = healthyServiceImpl.getImg(moduleId);
        return list;
    }
}

