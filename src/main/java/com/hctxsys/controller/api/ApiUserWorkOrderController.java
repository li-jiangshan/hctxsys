package com.hctxsys.controller.api;

import com.hctxsys.entity.YskUpdateUserinfoEntity;
import com.hctxsys.service.api.ApiWorkOrderServiceImpl;
import com.hctxsys.vo.api.ApiWorkOrderVo;
import com.hctxsys.vo.api.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/workOrder")
public class ApiUserWorkOrderController {
    @Autowired
    private ApiWorkOrderServiceImpl workOrderService;

    /**
     * 工单提交
     * @param workOrderVo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody ApiWorkOrderVo workOrderVo) {
        return workOrderService.update(workOrderVo);
    }

    /**
     * 工单查询
     * @param info
     * @return
     */
    @PostMapping("select")
    public JsonResult select(@RequestBody YskUpdateUserinfoEntity info) {
        return workOrderService.select(info);
    }
    @PostMapping("delete")
    public JsonResult delete(@RequestBody YskUpdateUserinfoEntity info) {
        return workOrderService.delete(info);
    }
}
