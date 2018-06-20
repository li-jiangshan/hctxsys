package com.hctxsys.controller.api.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hctxsys.config.payConfig.BankPayConfig;
import com.hctxsys.service.api.pay.ApiBankSearchSingleServiceImpl;
import com.hctxsys.service.api.pay.ApiBankSetPayReqDataSuccessServiceImpl;
import com.hctxsys.service.api.pay.ApiPayServiceImpl;
import com.hctxsys.util.payUtil.AliPayUtils;
import com.hctxsys.util.payUtil.BankPayUtils;
import com.hctxsys.util.payUtil.WxPayUtils;
import com.hctxsys.vo.api.ApiPayOrderSearchVo;
import com.hctxsys.vo.api.ApiPayVo;
import com.hctxsys.vo.api.JsonResult;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;

//支付controller
@Controller
@RequestMapping("/api/pay")
public class ApiPayController {
	
	@Autowired
	private ApiPayServiceImpl apiPayServiceImpl;
	@Autowired
	private ApiBankSetPayReqDataSuccessServiceImpl apiBankSetPayReqDataSuccessServiceImpl;
	@Autowired
	private ApiBankSearchSingleServiceImpl apiBankSearchSingleService;
	
	//在线支付接口
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult pay(@RequestBody ApiPayVo jsonRequestData, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		if (jsonRequestData.getPayType() == null) {
			jsonResult.setCode(0);
			jsonResult.setMessage("请选择支付类型");
			return jsonResult;
		}
		if (StringUtils.isNullOrEmpty(jsonRequestData.getOrderNo())) {
			jsonResult.setCode(0);
			jsonResult.setMessage("订单号不能为空");
			return jsonResult;
		}		
		if (jsonRequestData.getAmount() == null) {
			jsonResult.setCode(0);
			jsonResult.setMessage("金额不能为空");
			return jsonResult;
		}		
		if (StringUtils.isNullOrEmpty(jsonRequestData.getSafePwd())) {
			jsonResult.setCode(0);
			jsonResult.setMessage("请输入安全密码");
			return jsonResult;
		}
		if (jsonRequestData.getUserID() == null) {
			jsonResult.setCode(0);
			jsonResult.setMessage("用户id不能为空");
			return jsonResult;
		}
		// 安全码check
		if (!apiPayServiceImpl.isValidSafePwd(jsonRequestData.getUserID(), jsonRequestData.getSafePwd())) {
			jsonResult.setCode(0);
			jsonResult.setMessage("安全密码错误或未设置");
			return jsonResult;
		}
        //查询支付结果
        ApiPayVo searchVo = apiPayServiceImpl.getOrderStatus(jsonRequestData.getOrderNo());
        
        //支付成功 返回结果给前台
        if ("1".equals(searchVo.getStatus())) {
			jsonResult.setCode(0);
			jsonResult.setMessage("该订单已完成支付");
			return jsonResult;
        }
		if(!apiPayServiceImpl.setPayType(jsonRequestData.getOrderNo(), 
				jsonRequestData.getPayType(), 
				jsonRequestData.getUserID())) {
			
			jsonResult.setCode(0);
			jsonResult.setMessage("获取订单信息失败，检查用户与订单是否匹配");
			return jsonResult;
		}
		
		switch (jsonRequestData.getPayType()) {
			// 余额
			case 1:
				jsonResult = apiPayServiceImpl.payOrderByBalance(jsonRequestData);
				if (jsonResult.getCode() == 0) {
		  			return jsonResult;
				}
		        //查询支付结果
		        ApiPayVo searchConfirmVo = apiPayServiceImpl.getOrderStatus(jsonRequestData.getOrderNo());
		        jsonResult.setData(searchConfirmVo);
				return jsonResult;
			// 微信
			case 2:
		        // 返回客户端作为调用微信客户端使用
		        Map<String, String> wxResp = WxPayUtils.unifiedOrder(jsonRequestData.getOrderNo(), 
		        		jsonRequestData.getAmount(),searchVo.getGoodsName(),request);
		        jsonResult.setData(wxResp);
				return jsonResult;
			// 支付宝
			case 3:
		        // 返回客户端作为调用支付宝客户端用
		        String orderStr = AliPayUtils.tradeWapPay(jsonRequestData.getOrderNo(), String.valueOf(jsonRequestData.getAmount()),searchVo.getGoodsName());
		        Map<String, String> aliResp = new HashMap<String, String>();
		        aliResp.put("sign", orderStr); 
		        jsonResult.setData(aliResp);
		        return jsonResult;
			// 一网通
			case 4:
				// 获取请求信息
				JSONObject bankReqData = apiBankSetPayReqDataSuccessServiceImpl.setPayReqData(jsonRequestData);
				
				if(bankReqData != null) {
					Map<String, String> ywtResp = new HashMap<String, String>();
					ywtResp.put("reqData", BankPayUtils.getBankReqDate(bankReqData));
					jsonResult.setData(ywtResp);
					return jsonResult;
				}
		}
		jsonResult.setCode(0);
		jsonResult.setMessage("支付类型错误");
		return jsonResult;
		
	}
	
	//查询支付结果
	@RequestMapping(value = "/paySearch", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult paySearch(@RequestBody ApiPayVo payVo, HttpServletRequest request, HttpServletResponse response) {

   	 	System.out.println("paySearch调用支付之后需要查询----------");
		
        JsonResult returnVo = new JsonResult();
        
		if (StringUtils.isNullOrEmpty(payVo.getOrderNo())) {
			returnVo.setCode(0);
			returnVo.setMessage("订单号不能为空");
			return returnVo;
		}		
		if (payVo.getPayType() == null) {
			returnVo.setCode(0);
			returnVo.setMessage("支付类型不能为空");
			return returnVo;
		}
        
        //查询支付结果
        ApiPayVo searchVo = apiPayServiceImpl.getOrderStatus(payVo.getOrderNo());
        
        //支付成功 返回结果给前台
        if ("1".equals(searchVo.getStatus())) {
        	returnVo.setData(searchVo);
        	return returnVo;
        } 
        
        switch (payVo.getPayType()) {
			// 余额
			case 1:
				return null;
			// 微信
			case 2:
				//查询微信订单信息
		        Map<String, String> returnMap = WxPayUtils.orderQuery(payVo.getOrderNo());
		        //SUCCESS—支付成功、REFUND—转入退款、NOTPAY—未支付、CLOSED—已关闭、
		        //REVOKED—已撤销（刷卡支付）、USERPAYING--用户支付中、PAYERROR--支付失败(其他原因，如银行返回失败_
		        String wxTradeStatus = returnMap.get("trade_state"); // 交易状态
		        if (wxTradeStatus.equals("SUCCESS")) {
		        	//更新保存成功接口
		            Boolean aliPayFlag = apiPayServiceImpl.paySuccess(payVo.getOrderNo());
		            if (aliPayFlag) {
		            	//再次查询接口给前台返回数据
		                ApiPayVo searchConfirmVo = apiPayServiceImpl.getOrderStatus(payVo.getOrderNo());
		            	returnVo.setData(searchConfirmVo);
		            	return returnVo;
		            }
		        }
            	returnVo.setData(returnMap);
            	return returnVo;
			// 支付宝
			case 3:
		        // 调用支付宝 交易查询接口
		        AlipayTradeQueryResponse aliResponse = AliPayUtils.tradeQuery(payVo.getOrderNo());
		        //获取支付结果 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
		        String aliTradeStatus = aliResponse.getTradeStatus();
		        if ("TRADE_SUCCESS".equals(aliTradeStatus)) { //如果查询已支付成功
		        	//更新保存成功接口
		            Boolean aliPayFlag = apiPayServiceImpl.paySuccess(payVo.getOrderNo());
		            if (aliPayFlag) {
		            	//再次查询接口给前台返回数据
		                ApiPayVo searchConfirmVo = apiPayServiceImpl.getOrderStatus(payVo.getOrderNo());
		            	returnVo.setData(searchConfirmVo);
		            	return returnVo;
		            }
		        }
            	returnVo.setData(aliResponse);
				return returnVo;
			// 一网通
			case 4:
				Date payDate = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				String jsonRequestData = apiBankSearchSingleService.setSearchReqData(payVo.getOrderNo(),dateFormat.format(payDate).toString());
				JSONObject resultData = apiBankSearchSingleService.apiBankSearchSingle(jsonRequestData,
						BankPayConfig.BANK_SEARCH_SINGLE_URL, BankPayConfig.CHARSET);
				if (resultData.get("rspCode").toString().equals("SUC0000") && resultData.get("orderStatus").toString().equals("0")) {
					//更新保存成功接口
		            Boolean updateFlag = apiPayServiceImpl.paySuccess(payVo.getOrderNo());
		            if (updateFlag) {
		            	ApiPayVo searchConfirmVo = apiPayServiceImpl.getOrderStatus(payVo.getOrderNo());
		            	returnVo.setData(searchConfirmVo);
		            }
		        	return returnVo;
				}
				return returnVo;
        }
        
		return  returnVo;
	}	
	
	// 订单第三方查询
	@RequestMapping(value = "/orderSearch", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult paySearch(@RequestBody ApiPayOrderSearchVo orderSearchVo) {
		JsonResult returnVo = new JsonResult();
		switch (orderSearchVo.getPayType()) {
			// 余额
			case 1:
				return null;
			// 微信
			case 2:
				return null;
			// 支付宝
			case 3:
				return null;
			// 一网通
			case 4:
				String jsonRequestData = apiBankSearchSingleService.setSearchReqData(orderSearchVo.getOrderNo(),orderSearchVo.getPayTime());
				JSONObject resultData = apiBankSearchSingleService.apiBankSearchSingle(jsonRequestData,
						BankPayConfig.BANK_SEARCH_SINGLE_URL, BankPayConfig.CHARSET);
				if (resultData.get("rspCode").toString().equals("SUC0000") && resultData.get("orderStatus").toString().equals("0")) {
					returnVo.setCode(0);
					returnVo.setMessage("一网通查询成功");
					JSONObject returnData = new JSONObject();
					returnData.put("orderNo", resultData.get("orderNo"));
					returnData.put("date", resultData.get("date"));
					returnData.put("bankSerialNo", resultData.get("bankSerialNo"));
					returnData.put("orderAmount", resultData.get("orderAmount"));
					returnData.put("settleAmount", resultData.get("settleAmount"));
		            returnVo.setData(returnData);
		        	return returnVo;
				} else {
					returnVo.setCode(1);
					returnVo.setMessage(resultData.get("rspMsg").toString());
					return returnVo;
				}
		}
		returnVo.setCode(1);
		returnVo.setMessage("查询失败");
		return returnVo;
	}
	
}
