package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiTravelServiceImpl;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/travel")
public class ApiTravelController {
    @Autowired
    private ApiTravelServiceImpl travelService;
    @RequestMapping("getList")
    public JsonResult getList(@RequestBody PageVo pageVo) {
        return travelService.getList(pageVo);
    }
    @PostMapping("getDetail")
    public JsonResult getDetail(@RequestBody HashMap<String,Integer> map) {
        Integer id = map.get("id");
        if(id==null) return new JsonResult(0,"id不能为空");
        return travelService.getDetail(id);
    }
}
