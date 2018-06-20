package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiFinanceServiceImpl;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/finance")
public class ApiFinanceController {
    @Autowired
    private ApiFinanceServiceImpl financeService;
    @PostMapping("getList")
    public JsonResult carIndex(@RequestBody PageVo pageVo) {
        return financeService.getList(pageVo);
    }
    @PostMapping("getDetail")
    public JsonResult getDetail(@RequestBody HashMap<String,Integer> map) {
        Integer id = map.get("id");
        if(id==null) return new JsonResult(0,"id不能为空");
        return financeService.getDetail(id);
    }
}
