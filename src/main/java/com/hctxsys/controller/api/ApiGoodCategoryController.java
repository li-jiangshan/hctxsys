package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiGoodCategoryServiceImpl;
import com.hctxsys.vo.api.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/good")
public class ApiGoodCategoryController {
	
    @Autowired
    private ApiGoodCategoryServiceImpl goodCategoryService;

    /**
     *
     *查询商品列表
     * @return
     */
    @PostMapping("/goodCategory")
    @ResponseBody
    public JsonResult getGoodCategory() {
//        return goodCategoryService.getCategory(); //查询所有分类
        return goodCategoryService.getCategoryHasGood(); //查询有商品的分类
    }
}
