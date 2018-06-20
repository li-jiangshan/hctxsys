package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiAgreementServiceImpl;
import com.hctxsys.service.api.ApiAgricultureServiceImpl;
import com.hctxsys.service.api.ApiAroundServiceImpl;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/agriculture")
public class ApiAgricultureController {
    @Autowired
    private ApiAgricultureServiceImpl agricultureService;
    @RequestMapping("getList")
    public JsonResult getList(@RequestBody PageVo pageVo) {
        return agricultureService.getList(pageVo);
    }
    @PostMapping("getDetail")
    public JsonResult getDetail(@RequestBody HashMap<String,Integer> map) {
        Integer id = map.get("id");
        if(id==null) return new JsonResult(0,"id不能为空");
        return agricultureService.getDetail(id);
    }
}
