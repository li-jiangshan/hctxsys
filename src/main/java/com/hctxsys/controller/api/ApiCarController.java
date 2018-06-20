package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiCarServiceImpl;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/car")
public class ApiCarController {
    @Autowired
    private ApiCarServiceImpl carService;

    /**
     * 获取汽车列表
     * @param pageVo
     * @return
     */
    @PostMapping("getList")
    public JsonResult carIndex(@RequestBody PageVo pageVo) {
        return carService.getList(pageVo);
    }
    @PostMapping("getDetail")
    public JsonResult getDetail(@RequestBody HashMap<String,Integer> map) {
        Integer id = map.get("id");
        if(id==null) return new JsonResult(0,"id不能为空");
        return carService.getDetail(id);
    }
}
