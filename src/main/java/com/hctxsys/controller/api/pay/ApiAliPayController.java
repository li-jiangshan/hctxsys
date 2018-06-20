package com.hctxsys.controller.api.pay;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hctxsys.service.api.pay.ApiPayServiceImpl;
import com.hctxsys.util.payUtil.AliPayUtils;
import com.hctxsys.vo.api.ApiPayVo;
import com.hctxsys.vo.api.JsonResult;

//支付controller
@Controller
@RequestMapping("/api/ali")
public class ApiAliPayController {

	@Autowired
	private ApiPayServiceImpl apiPayServiceImpl;
	
	//ali支付接口
	@RequestMapping(value = "/aLiPay", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult aLiPay(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) {
	    
        JsonResult returnVo = new JsonResult();
        // 返回客户端作为调用支付宝客户端用
        String orderStr = AliPayUtils.tradeWapPay(payVo.getOrderNo(), String.valueOf(payVo.getAmount()),"商品描述");
        
        returnVo.setData(orderStr);
        
		return  returnVo;
	}	
	
	//ali回调
	@RequestMapping(value = "/aLiPayNotify", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public String aLiPayNotify(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//支付宝回调验签
        Boolean verify_result = AliPayUtils.tradeWapPayDotify(body, request, response);
        
         if(verify_result){//验证成功 处理自己的业务

        	 System.out.println("支付宝回调验签成功----------");
             String outTradeNo = request.getParameter("out_trade_no");//商户订单号
             String tradeStatus = request.getParameter("trade_status"); // 交易状态

        	 if (tradeStatus.equals("TRADE_SUCCESS")) {
             	System.out.println("支付宝回调业务成功----------");
	             //查询支付结果
	             ApiPayVo searchVo = apiPayServiceImpl.getOrderStatus(outTradeNo);
	             //支付成功 返回结果给前台
	             if ("1".equals(searchVo.getStatus())) {
	                 return "success";
	             }
	        	//更新保存成功接口
	            apiPayServiceImpl.paySuccess(outTradeNo);
        	 }
         } else{//验证失败
        	 System.out.println("支付宝回调失败----------");
        	 return "fail";
         }
         return "success";
	}
	
	//ali支付订单查询
	@RequestMapping(value = "/aLiSearch", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult aLiSearch(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) {
	    
        JsonResult returnVo = new JsonResult();
        
        // 返回客户端作为调用支付宝客户端用
        AlipayTradeQueryResponse aliResponse = AliPayUtils.tradeQuery(payVo.getOrderNo());
        
        //获取支付结果 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
//        String tradeStatus = aliResponse.getTradeStatus();
//        System.out.println(tradeStatus);
//        String trade_no = aliResponse.getTradeNo();  //支付宝交易号
//        String  out_trade_no= aliResponse.getOutTradeNo(); //商家订单号
//        BigDecimal totalAmount = new BigDecimal(aliResponse.getTotalAmount()); //交易的订单金额
//        Date payDate = aliResponse.getSendPayDate(); //本次交易打款给卖家的时间
        returnVo.setData(aliResponse);
		return  returnVo;
	}	
	
	
	//阿里支付退款接口
	@RequestMapping(value = "/aLiRefund", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult aLiRefund(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) {
	    
        JsonResult returnVo = new JsonResult();

        // 返回客户端作为调用支付宝客户端用
        AlipayTradeRefundResponse aliResponse = AliPayUtils.tradeRefund(payVo.getOrderNo(), payVo.getRefundOrderNo(), String.valueOf(payVo.getRefundAmount()));
        
        if (aliResponse.isSuccess()) { 
        	System.out.println("调用成功"); 
    	} else { 
    		System.out.println("调用失败"); 
    	} 

        returnVo.setData(aliResponse);
		return  returnVo;
	}
	
	//阿里支付查询退款订单接口
	@RequestMapping(value = "/aLiRefundQuery", method = {RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JsonResult aliRefundQuery(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonResult returnVo = new JsonResult();
		//查询查询退款订单信息
        AlipayTradeFastpayRefundQueryResponse aliResponse = AliPayUtils.tradeRefundQuery(payVo.getOrderNo(), payVo.getRefundOrderNo());
        
        //REFUND_PROCESSING 退款处理中；REFUND_SUCCESS 退款处理成功；REFUND_FAIL 退款失败;
        String tradeStatus = aliResponse.getRefundStatus();
        
        if (aliResponse.isSuccess()) { 
        	System.out.println("调用成功"); 
        	if ("REFUND_SUCCESS".equals(tradeStatus)) {
            	System.out.println("退款成功"); 
        	}
    	} else { 
    		System.out.println("调用失败"); 
    	} 
        returnVo.setData(aliResponse);

    	return returnVo;
	}
	
}
