package com.hctxsys.util.payUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hctxsys.config.payConfig.MyWXPayConfig;
import com.hctxsys.config.weixinPay.WXPay;
import com.hctxsys.config.weixinPay.WXPayConstants;
import com.hctxsys.config.weixinPay.WXPayUtil;

//微信支付工具类
public class WxPayUtils {
	
	/**
    * 微信支付统一下单  https://api.mch.weixin.qq.com/pay/unifiedorder   
    * @param outTradeNo:商户订单号, totalFee:总金额, 
    * @return
    */
	public static Map<String, String> unifiedOrder(String outTradeNo,  BigDecimal totalFee, String goodsName,  HttpServletRequest request) {
		
		try {

			System.out.println("微信统一下单开始---------------");
			String spbillCreateIp = "";
			//获取访问ip
			if (request.getHeader("x-forwarded-for") == null) {  
				spbillCreateIp = request.getRemoteAddr();
		    } else {
		    	spbillCreateIp = request.getHeader("x-forwarded-for");
		    }
			
			MyWXPayConfig config = new MyWXPayConfig();
			//沙箱环境
//			WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
			//正式环境
			WXPay wxpay = new WXPay(config);
			
			Map<String, String> data = new HashMap<String, String>();
			totalFee = totalFee.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN);
			data.put("body", goodsName); //商品描述
			data.put("out_trade_no", outTradeNo); //商户订单号
			data.put("fee_type", "CNY"); //货币
			data.put("total_fee", String.valueOf(totalFee));
			data.put("spbill_create_ip",  spbillCreateIp); //用户端实际ip
			data.put("notify_url", "http://www.lnhuacai.com/api/weixin/wxPayNotify"); //回调地址
			data.put("trade_type", "APP");  // 此处指定为app支付
		
			//调用微信支付统一下单接口
			Map<String, String> resp = wxpay.unifiedOrder(data);

			System.out.println("微信统一下单返回参数---------------" + resp);
			
			if ("FAIL".equals(resp.get("return_code"))) {
				return resp;
			}
			
			//统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
			//参与签名的字段名为appid，partnerid，prepayid，noncestr，timestamp，package。注意：package的值格式为Sign=WXPay
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("appid", config.getAppID()); //appid、
			returnMap.put("partnerid", config.getMchID()); //商户号
			returnMap.put("prepayid", resp.get("prepay_id"));  //统一下单返回数据 预支付交易会话标识prepay_id
			returnMap.put("noncestr", WXPayUtil.generateNonceStr()); //随机字符串
			returnMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000)); //时间戳
			returnMap.put("package", "Sign=WXPay");

			System.out.println("微信统一下单参与签名字段---------------" + returnMap);
			// 二次签名
			String sign = WXPayUtil.generateSignature(returnMap,config.getKey(),WXPayConstants.SignType.MD5); //传给app的签名
			returnMap.put("sign", sign);
			returnMap.put("packageValue", "Sign=WXPay");
			
			System.out.println("微信统一下单给客户端参数---------------" + returnMap);
			
			return returnMap;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
    * app支付接口回调
    * @param body:微信返回信息, 
    * @return
    */
	public static Map<String, String> unifiedOrderDotify(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> notifyMap = new HashMap<String, String>();
		notifyMap.put("verify_result", "false"); 
		
		try {

	        InputStream inputStream = request.getInputStream();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	        final StringBuffer stringBuffer = new StringBuffer();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuffer.append(line).append("\n");
	        }
	        String resultXml = stringBuffer.toString();
	        if (stringBuffer!=null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (inputStream!=null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

			MyWXPayConfig config = new MyWXPayConfig();
			//沙箱环境
//			WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
			//正式环境
			WXPay wxpay = new WXPay(config);

			notifyMap  = WXPayUtil.xmlToMap(resultXml);  // 转换成map
	        
	        if(wxpay.isPayResultNotifySignatureValid(notifyMap)) {
	        	notifyMap.put("verify_result", "true"); 
	        } else {
	        	notifyMap.put("verify_result", "false"); 
	        }

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return notifyMap;
	}
	
	/**
    * 微信退款 需要请求证书 https://api.mch.weixin.qq.com/secapi/pay/refund
    * @param totalFee:订单金额, 
    * 				 refundFee:退款金额 , 
    * 				 outTradeNo:商户订单号, , 
    * 				 outRefundNo:商户退款单号, 
    * @return
    */
	public static Map<String, String> refund(String outTradeNo,  String outRefundNo, BigDecimal totalFee, BigDecimal refundFee) {
		try {
	        MyWXPayConfig config = new MyWXPayConfig();
			//沙箱环境
//			WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
			//正式环境
			WXPay wxpay = new WXPay(config);
	        Map<String, String> data = new HashMap<String, String>();

			totalFee = totalFee.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN);
			refundFee = refundFee.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_DOWN);
	        
//	        data.put("transaction_id",transactionId); //微信订单号  二选一
	        data.put("out_trade_no",outTradeNo); //商户订单号
	        data.put("out_refund_no",outRefundNo); //商户退款单号
			data.put("total_fee", String.valueOf(totalFee)); //订单金额 
			data.put("refund_fee", String.valueOf(refundFee)); //退款金额
			
            Map<String, String> resp = wxpay.refund(data);
            System.out.println(resp);
        	return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
    * 微信查询订单 https://api.mch.weixin.qq.com/pay/orderquery
    * @param transactionId:微信订单号, 
    * 				 outTradeNo:商户订单号, 
    * @return
    */
	public static Map<String, String> orderQuery(String outTradeNo) {
		try {
	        MyWXPayConfig config = new MyWXPayConfig();
			//沙箱环境
//			WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
			//正式环境
			WXPay wxpay = new WXPay(config);
	        Map<String, String> data = new HashMap<String, String>();
//	        data.put("transaction_id",transactionId); //微信订单号  二选一
	        data.put("out_trade_no",outTradeNo); //商户订单号
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println("微信查询返回数据：" + resp);
        	return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
    * 微信查询退款 https://api.mch.weixin.qq.com/pay/refundquery
    * @param transactionId:微信订单号, 
    * 				 outTradeNo:商户订单号, 
    * @return
    */
	public static Map<String, String> refundQuery(String outRefundNo) {
		try {
	        MyWXPayConfig config = new MyWXPayConfig();
			//沙箱环境
//			WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
			//正式环境
			WXPay wxpay = new WXPay(config);
	        Map<String, String> data = new HashMap<String, String>();
//		        data.put("transaction_id",transactionId); //微信订单号  二选一
	        data.put("out_refund_no",outRefundNo); //商户退款单号
            Map<String, String> resp = wxpay.refundQuery(data);
            System.out.println(resp);
        	return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	//获取沙箱密钥
	public static Map<String, String> getsignkey() {
		try {

	        MyWXPayConfig config = new MyWXPayConfig();
			//沙箱环境
			WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);
	        Map<String, String> data = new HashMap<String, String>();
	        
	        String nonceStr = WXPayUtil.generateNonceStr();
	        data.put("mch_id","1503414621"); //商户号
	        data.put("nonce_str",nonceStr); //随机字符串
			// 二次签名
			String sign = WXPayUtil.generateSignature(data,"VmE99atyd8VnTcobWFqoxMwzCx68sMlc"); //传给app的签名
	        data.put("sign",sign); //随机字符串

            Map<String, String> resp = wxpay.getsignkey(data,8000,10000);
            System.out.println(resp);
            
        	return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
}
