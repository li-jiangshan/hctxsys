package com.hctxsys.controller.api;

import com.hctxsys.service.HouseServiceImpl;
import com.hctxsys.service.api.ApiHouseServiceImpl;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/house")
public class ApiHouseController {
    @Autowired
    private ApiHouseServiceImpl houseService;
    @RequestMapping("getList")
    public JsonResult getList(@RequestBody PageVo pageVo) {
        return houseService.getList(pageVo);
    }
    @PostMapping("getDetail")
    public JsonResult getDetail(@RequestBody HashMap<String,Integer> map) {
        Integer id = map.get("id");
        if(id==null) return new JsonResult(0,"id不能为空");
        return houseService.getDetail(id);
    }
}
