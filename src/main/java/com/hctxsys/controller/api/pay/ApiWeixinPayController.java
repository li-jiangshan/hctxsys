package com.hctxsys.controller.api.pay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.api.pay.ApiPayServiceImpl;
import com.hctxsys.util.payUtil.WxPayUtils;
import com.hctxsys.vo.api.ApiPayVo;
import com.hctxsys.vo.api.JsonResult;

//支付controller
@Controller
@RequestMapping("/api/weixin")
public class ApiWeixinPayController {

	@Autowired
	private ApiPayServiceImpl apiPayServiceImpl;

	//微信支付接口（统一下单）
	@RequestMapping(value = "/wxPay", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult wxPay(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) {
	    
        JsonResult returnVo = new JsonResult();
        // 返回客户端作为调用微信客户端使用
        Map<String, String> resp = WxPayUtils.unifiedOrder(payVo.getOrderNo(), payVo.getAmount(),"商品描述", request);
        
        returnVo.setData(resp);
        
		return  returnVo;
	}
	
	//微信支付接口(微信统一下单回调)
	@RequestMapping(value = "/wxPayNotify", produces="text/html;charset=utf-8")
    @ResponseBody
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//微信回调验签
		Map<String, String> resp  = WxPayUtils.unifiedOrderDotify(request, response);
        
         if("true".equals(resp.get("verify_result"))){//验证成功 处理自己的业务

        	 System.out.println("微信支付回调验签成功----------");
             String outTradeNo = resp.get("out_trade_no");//商户订单号
             String tradeStatus = resp.get("result_code"); // 交易状态

        	 if (tradeStatus.equals("SUCCESS")) {
             	System.out.println("微信支付回调业务成功----------");
//	             查询支付结果
	             ApiPayVo searchVo = apiPayServiceImpl.getOrderStatus(outTradeNo);
	             //支付成功 返回结果给前台
	             if ("1".equals(searchVo.getStatus())) {
	                 return this.returnXML("SUCCESS");
	             }
	        	//更新保存成功接口
	            apiPayServiceImpl.paySuccess(outTradeNo);
        	 }
         } else{//验证失败
        	 System.out.println("微信支付回调失败----------");
             return this.returnXML("FAIL");
         }

      	System.out.println("微信支付回调回调返回数据----------" + resp);
         
         return this.returnXML("SUCCESS");
	}
	
	//微信支付查询订单接口
	@RequestMapping(value = "/wxQueryOrder", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult wxQueryOrder(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonResult returnVo = new JsonResult();
		//查询微信订单信息
        Map<String, String> returnMap = WxPayUtils.orderQuery(payVo.getOrderNo());
        
        String wxTradeStatus = returnMap.get("trade_state"); // 交易状态
        if (wxTradeStatus.equals("SUCCESS")) {
        	System.out.println("调用成功"); 
        } else {
        	System.out.println("调用失败"); 
        }
    	
        returnVo.setData(returnMap);

    	return returnVo;
	}
	
	//微信支付退款接口
	@RequestMapping(value = "/wxRefund", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult wxRefund(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) {
	    
        JsonResult returnVo = new JsonResult();
        // 返回客户端作为调用微信客户端使用
        Map<String, String> resp = WxPayUtils.refund(payVo.getOrderNo(), payVo.getRefundOrderNo(),payVo.getAmount(),payVo.getRefundAmount());
        
        returnVo.setData(resp);
        
        if(resp.get("result_code").equals("SUCCESS")) {
        	System.out.println("调用成功");
		} else {
        	System.out.println("调用失败");
		}
        
		return  returnVo;
	}
	
	//微信支付查询退款订单接口
	@RequestMapping(value = "/wxRefundQuery", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult wxRefundQuery(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonResult returnVo = new JsonResult();
		//查询微信订单信息
        Map<String, String> returnMap = WxPayUtils.refundQuery(payVo.getRefundOrderNo());
    	
        if(returnMap.get("result_code").equals("SUCCESS")) {
        	System.out.println("调用成功");
		} else {
        	System.out.println("调用失败");
		}
        
        returnVo.setData(returnMap);

    	return returnVo;
	}
	
	//获取微信沙箱密钥
	@RequestMapping(value = "/getsignkey", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult getsignkey(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonResult returnVo = new JsonResult();
		//查询微信订单信息
        Map<String, String> returnMap = WxPayUtils.getsignkey();
    	
        returnVo.setData(returnMap);
        
    	return returnVo;
	}
	
	//微信回调返回参数
	private String returnXML(String return_code) {
        return "<xml><return_code><![CDATA["
                + return_code
                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

	
	
}
