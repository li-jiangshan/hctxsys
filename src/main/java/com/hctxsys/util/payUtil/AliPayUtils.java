package com.hctxsys.util.payUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hctxsys.config.payConfig.AlipayConfig;

//支付宝支付工具类
public class AliPayUtils {
	
	/**
    * app支付接口  alipay.trade.app.pay
    * @param outTradeNo:商户订单号, totalAmount:总金额, 
    * @return
    */
	public static String tradeWapPay(String outTradeNo, String totalAmount, String goodsName) {
		
		try {
			
			System.out.println("调用支付宝支付接口-------");
			
	        //实例化客户端  
			//设置请求
	        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,  //网关(gateway)
	        																			   AlipayConfig.APP_ID,  //应用id(app_id)
	        																			   AlipayConfig.APP_PRIVATE_KEY,  //应用私钥(private_key)
	        																			   AlipayConfig.FORMAT,  //返回参数格式
	        																			   AlipayConfig.CHARSET, //编码格式(charset)
	        																			   AlipayConfig.ALIPAY_PUBLIC_KEY, //支付宝公钥(alipay_public_key)
	        																			   AlipayConfig.SIGN_TYPE); //签名类型(sign_type)
	        
	        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay 
	        //SDK已经封装掉了公共参数，这里只需要传入业务参数。
	        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest(); 
	        //以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。  
	        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();  
	        model.setSubject(goodsName);                  //商品名称
	        model.setOutTradeNo(outTradeNo);          //商户订单号(自动生成)
	        model.setTimeoutExpress("30m");     //交易超时时间
	        model.setTotalAmount(totalAmount);         //支付金额
	        model.setProductCode("QUICK_MSECURITY_PAY");         //销售产品码
	        ali_request.setBizModel(model);  
	        ali_request.setNotifyUrl(AlipayConfig.NOTIFY_URL); //回调地址
	        
	        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(ali_request);  
	        
        	String orderStr = response.getBody();  
	        
	        return orderStr;
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
    * app支付接口回调
    * @param body:阿里返回信息, 
    * @return
    */
	public static Boolean tradeWapPayDotify(String body, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  	}
		    //乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = false;
		try {
			flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
    * app交易查询接口alipay.trade.query
    * @param outTradeNo:商户订单号
    * @return
    */
	public static AlipayTradeQueryResponse tradeQuery(String outTradeNo) {
		
		try {
	        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,  //网关(gateway)
	        																			   AlipayConfig.APP_ID,  //应用id(app_id)
	        																			   AlipayConfig.APP_PRIVATE_KEY,  //应用私钥(private_key)
	        																			   AlipayConfig.FORMAT,  //返回参数格式
	        																			   AlipayConfig.CHARSET, //编码格式(charset)
	        																			   AlipayConfig.ALIPAY_PUBLIC_KEY, //支付宝公钥(alipay_public_key)
	        																			   AlipayConfig.SIGN_TYPE); //签名类型(sign_type)
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
			AlipayTradeQueryModel model = new AlipayTradeQueryModel();  
			model.setOutTradeNo(outTradeNo);
			request.setBizModel(model);
			//通过alipayClient调用API，获得对应的response类
			AlipayTradeQueryResponse response = alipayClient.execute(request);
	        return response;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
    * app交易退款接口alipay.trade.refund
    * @param outTradeNo:商户订单号, outRequestNo,退款单号, refundAmount 退款金额
    * @return
    */
	public static AlipayTradeRefundResponse tradeRefund(String outTradeNo, String outRequestNo, String refundAmount) {
		
		try {
	        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,  //网关(gateway)
	        																			   AlipayConfig.APP_ID,  //应用id(app_id)
	        																			   AlipayConfig.APP_PRIVATE_KEY,  //应用私钥(private_key)
	        																			   AlipayConfig.FORMAT,  //返回参数格式
	        																			   AlipayConfig.CHARSET, //编码格式(charset)
	        																			   AlipayConfig.ALIPAY_PUBLIC_KEY, //支付宝公钥(alipay_public_key)
	        																			   AlipayConfig.SIGN_TYPE); //签名类型(sign_type)
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类
			AlipayTradeRefundModel model = new AlipayTradeRefundModel();  
			model.setOutTradeNo(outTradeNo);
			model.setOutRequestNo(outRequestNo);
			model.setRefundAmount(refundAmount);
			request.setBizModel(model);
			//通过alipayClient调用API，获得对应的response类
			AlipayTradeRefundResponse response = alipayClient.execute(request);
	        return response;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
    * app交易退款查询接口alipay.trade.fastpay.refund.query
    * @param outTradeNo:商户订单号, outRequestNo:退款单号
    * @return
    */
	public static AlipayTradeFastpayRefundQueryResponse tradeRefundQuery(String outTradeNo, String outRequestNo) {
		
		try {
	        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,  //网关(gateway)
	        																			   AlipayConfig.APP_ID,  //应用id(app_id)
	        																			   AlipayConfig.APP_PRIVATE_KEY,  //应用私钥(private_key)
	        																			   AlipayConfig.FORMAT,  //返回参数格式
	        																			   AlipayConfig.CHARSET, //编码格式(charset)
	        																			   AlipayConfig.ALIPAY_PUBLIC_KEY, //支付宝公钥(alipay_public_key)
	        																			   AlipayConfig.SIGN_TYPE); //签名类型(sign_type)
	        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();//创建API对应的request类
	        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();  
			model.setOutTradeNo(outTradeNo);
			model.setOutRequestNo(outRequestNo);
			request.setBizModel(model);
			//通过alipayClient调用API，获得对应的response类
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
	        return response;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return null;
	}
}
