package com.hctxsys.controller.api;

import com.hctxsys.service.api.ApiOrderRejectedServiceImpl;
import com.hctxsys.util.CheckUtils;
import com.hctxsys.vo.api.ApiOrderRejectedVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//退货流程
@Controller
@RequestMapping("api/orderRejected")
public class ApiOrderRejectedController {
	
    @Autowired
    private ApiOrderRejectedServiceImpl apiOrderRejectedService;

    //再次退货
    @PostMapping("secondOrderRejected")
    @ResponseBody
    public JsonResult SecondOrderRejected(@RequestBody ApiOrderRejectedVo vo) {
        JsonResult jsonResult = new JsonResult();
        if (vo.getRejectedId() == null) {
            jsonResult.setMessage("退货订单ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if (StringUtils.isNullOrEmpty(vo.getRejectedReason())) {
            jsonResult.setMessage("退货原因不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if (StringUtils.isNullOrEmpty(vo.getRejectedImg())) {
            jsonResult.setMessage("请上传退货凭证");
            jsonResult.setCode(0);
            return jsonResult;
        }

        return apiOrderRejectedService.SecondOrderRejected(vo);
    }
    //生成退货申请订单
    @PostMapping("/addOrderRejected")
    @ResponseBody
    public JsonResult addOrderRejected(@RequestBody ApiOrderRejectedVo vo) {

        JsonResult jsonResult = new JsonResult();
        if(vo.getUserId() == null) {
            jsonResult.setMessage("用户ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(vo.getOrderDetailId() == null) {
            jsonResult.setMessage("订单详细ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(StringUtils.isNullOrEmpty(vo.getRejectedReason())) {
            jsonResult.setMessage("退货原因不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(StringUtils.isNullOrEmpty(vo.getRejectedImg())) {
            jsonResult.setMessage("请上传退货凭证");
            jsonResult.setCode(0);
            return jsonResult;
        }
    	
        return apiOrderRejectedService.addOrderRejected(vo);
    }
    
    //添加退货物流信息
    @PostMapping("/updateOrderRejected")
    @ResponseBody
    public JsonResult updateOrderRejected(@RequestBody ApiOrderRejectedVo vo) {

        JsonResult jsonResult = new JsonResult();
        if(vo.getUserId() == null) {
            jsonResult.setMessage("用户ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(vo.getRejectedId() == null) {
            jsonResult.setMessage("退款订单ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(StringUtils.isNullOrEmpty(vo.getLogisticsNo())) {
            jsonResult.setMessage("请输入物流单号");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(StringUtils.isNullOrEmpty(vo.getLogisticsMobile())) {
            jsonResult.setMessage("请输入联系电话");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(StringUtils.isNullOrEmpty(vo.getLogisticsImg())) {
            jsonResult.setMessage("请上传图片");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(!CheckUtils.isCheckMobile(vo.getLogisticsMobile())) {
            jsonResult.setMessage("电话格式不正确");
            jsonResult.setCode(0);
            return jsonResult;
        }
    	
        return apiOrderRejectedService.updateOrderRejected(vo);
    }
    
    //退货订单查询
    @PostMapping("/searchOrderRejected")
    @ResponseBody
    public JsonResult searchOrderRejected(@RequestBody ApiOrderRejectedVo vo) {
        JsonResult jsonResult = new JsonResult();
        if(vo.getUserId() == null) {
            jsonResult.setMessage("用户ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(vo.getOrderDetailId() == null) {
            jsonResult.setMessage("订单详细ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        return apiOrderRejectedService.searchOrderRejected(vo);
    }
    
    //撤销退货申请
    @PostMapping("/cancelOrderRejected")
    @ResponseBody
    public JsonResult cancelOrderRejected(@RequestBody ApiOrderRejectedVo vo) {
        JsonResult jsonResult = new JsonResult();
        if(vo.getUserId() == null) {
            jsonResult.setMessage("用户ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        if(vo.getRejectedId() == null) {
            jsonResult.setMessage("退款订单ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        return apiOrderRejectedService.cancelOrderRejected(vo);
    }
}
