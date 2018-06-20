package com.hctxsys.controller.api;

import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.service.api.ApiOrderServiceImpl;
import com.hctxsys.vo.api.ApiOrderVo;
import com.hctxsys.vo.api.ApiPayOrderVo;
import com.hctxsys.vo.api.JsonResult;
import com.hctxsys.vo.api.OrderPageVo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("api/order")
public class ApiOrderController {
	
    @Autowired
    private ApiOrderServiceImpl orderService;

    /**
     * 获取用户订单信息
     * @param vo 订单信息实体
     * @param
     * @return
     */
    @PostMapping("/getOrders")
    @ResponseBody
    public JsonResult getOrders(@RequestBody OrderPageVo vo) {
        if(vo.getUserId()==0) {
            JsonResult jsonResult = new JsonResult();
            jsonResult.setMessage("用户ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        return orderService.getOrders(vo);
    }
    
    //获取支付订单
    @PostMapping("/getPayOrders")
    @ResponseBody
    public JsonResult getPayOrders(@RequestBody ApiPayOrderVo vo) {
        if(StringUtils.isNullOrEmpty(vo.getOrderNo())) {
            JsonResult jsonResult = new JsonResult();
            jsonResult.setMessage("订单ID不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        return orderService.getPayOrders(vo);
    }
    
    //取消订单
    @PostMapping("/cancelOrder")
    @ResponseBody
    public JsonResult cancelOrder(@RequestBody ApiOrderVo orderVo) {

        if(StringUtils.isNullOrEmpty(orderVo.getOrderNo())) {
            JsonResult jsonResult = new JsonResult();
            jsonResult.setMessage("订单编号不能为空");
            jsonResult.setCode(0);
            return jsonResult;
        }
        return orderService.cancelOrder(orderVo);
    }
    
    //添加订单
    @PostMapping("/addOrder")
    @ResponseBody
    public JsonResult addOrder(@RequestBody ApiOrderVo orderVo) {
        return orderService.addOrder(orderVo);
    }

    /**
     * 删除订单
     *
     * @param order
     * @return
     */
    @PostMapping("delOrder")
    @ResponseBody
    public JsonResult delOrder(@RequestBody YskOrderEntity order) {
        return orderService.delOrder(order.getOrderNo());
    }
	// 确认收货
   	@RequestMapping(value = "/confirmReceipt", method = {RequestMethod.POST})
    @ResponseBody
   	public JsonResult confirmReceipt(@RequestBody ApiOrderVo ApiOrderVo) {
   		YskOrderEntity orderEntity = orderService.getOrderEntity(ApiOrderVo.getOrderNo());
   		if(orderEntity.getUserId() != ApiOrderVo.getUserId()) {
   			JsonResult result = new JsonResult();
   			result.setCode(0);
   			result.setMessage("用户与订单不匹配");
   		}
   		
   		return orderService.confirmReceipt(orderEntity);
   	}
   	
   	// 定时确认收货
   	@Scheduled(cron = "0 0 3 * * ?")
   	public void autoConfirmReceipt() {
   		List<YskOrderEntity> orderEntityList = orderService.getAutoConfirmOrder();
   		if(orderEntityList.size() > 0) {
   			for(YskOrderEntity orderEntity : orderEntityList) {
   	   			orderService.confirmReceipt(orderEntity);
   	   		}
   		}
   	}
}
