package com.hctxsys.controller.adminmall;

import com.hctxsys.entity.YskCarEntity;
import com.hctxsys.entity.YskCarTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.service.CarServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.CarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("Adminmall/Module")
public class CarController {
    @Autowired
    private CarServiceImpl carService;

    /**
     * 显示汽车模块页面
     * @param tableResult
     * @return
     */
    @GetMapping("carIndex")
    public ModelAndView index(TableResult tableResult) {
        ModelAndView modelAndView = new ModelAndView("/adminmall/module/carIndex");
        TableResult result = carService.index(tableResult);
        List<YskCarTypeEntity> allType = carService.getAllType();
        modelAndView.addObject("typeList",allType);
        modelAndView.addObject("tableResult",result);
        return modelAndView;
    }

    /**
     * 跳转到汽车信息发布页
     * @return
     */
    @GetMapping("addCar")
    public ModelAndView addCar() {
        ModelAndView modelAndView = new ModelAndView("adminmall/module/carAdd");
        List<YskCarTypeEntity> allType = carService.getAllType();
        modelAndView.addObject("typeList",allType);
        return modelAndView;
    }

    /**
     * 汽车信息插入的逻辑操作
     * @return
     */
    @PostMapping("carInsert")
    @ResponseBody
    public Result carInsert(@RequestBody CarVo carVo) {
        return carService.carInsert(carVo);
    }
    @GetMapping("carEdit/{id}")
    public ModelAndView carEdit(@PathVariable Integer id) {
        YskCarEntity car = carService.findById(id);
        List<YskCarTypeEntity> allType = carService.getAllType();
        ModelAndView modelAndView = new ModelAndView("adminmall/module/carAdd");
        modelAndView.addObject("car",car);
        modelAndView.addObject("typeList",allType);
        return modelAndView;
    }
    @PostMapping("carUpdate")
    @ResponseBody
    public Result carUpdate(@RequestBody CarVo carVo) {
        return carService.carUpdate(carVo);
    }
    @PostMapping("carDelete/{id}")
    @ResponseBody
    public Result carDelete(@PathVariable Integer id) {
        return carService.deleteById(id);
    }
    @GetMapping(path = "/imgByModuleId/car/{moduleId}")
    @ResponseBody
    public List<YskModuleImgEntity> getImgByModuleId(@PathVariable Integer moduleId) {
        List<YskModuleImgEntity> list = carService.getImg(moduleId);
        return list;
    }

    /**
     * 图片删除
     * @param moduleId
     * @param id
     * @return
     */
    @GetMapping("delCarImg/{id}/{moduleId}")
    @ResponseBody
    public  List<YskModuleImgEntity> delImg(@PathVariable Integer moduleId,@PathVariable Integer id) {
        carService.deleteImg(id);
        List<YskModuleImgEntity> list = carService.getImg(moduleId);
        return list;
    }
}
