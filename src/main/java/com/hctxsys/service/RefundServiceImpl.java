package com.hctxsys.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hctxsys.config.payConfig.BankPayConfig;
import com.hctxsys.entity.YskMoneyDetailEntity;
import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskOrderPayEntity;
import com.hctxsys.entity.YskOrderRejectedEntity;
import com.hctxsys.entity.YskUserWealthEntity;
import com.hctxsys.entity.YskYwtPubKeyEntity;
import com.hctxsys.repository.MoneyDetailRepository;
import com.hctxsys.repository.OrderDetailRepository;
import com.hctxsys.repository.OrderPayRepository;
import com.hctxsys.repository.OrderRejectedRepository;
import com.hctxsys.repository.OrderRepository;
import com.hctxsys.repository.UserWealthRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.payUtil.AliPayUtils;
import com.hctxsys.util.payUtil.BankPayUtils;
import com.hctxsys.util.payUtil.WxPayUtils;
import com.hctxsys.vo.api.JsonResult;

import net.sf.json.JSONObject;

@Service("RefundService")
public class RefundServiceImpl {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	OrderRejectedRepository orderRejectedRepository;
	@Autowired
	UserWealthRepository userWealthRepository;
	@Autowired
	MoneyDetailRepository moneyDetailRepository;
	@Autowired
	BankRefundServiceImpl bankDoRefundServiceImpl;
	
	public HashMap<String,String> getRefundData(String rejectedNo) {
		HashMap<String,String> refundData = new HashMap<String,String>();
		YskOrderRejectedEntity orderRejectedEntity = orderRejectedRepository.findByRejectedNo(rejectedNo);
		refundData.put("orderPayNo", orderRejectedEntity.getPayOrderNo());
		refundData.put("payType", orderRejectedEntity.getPayOrderCode());
		refundData.put("rejectedNo", rejectedNo);
		refundData.put("amount", orderRejectedEntity.getRejectedPrice().toString());
		refundData.put("payPrice", orderRejectedEntity.getPayOrderPrice().toString());
		refundData.put("userId", orderRejectedEntity.getUserId().toString());
		return refundData;
	}
	
	@Transactional
	public JsonResult setRefund(HashMap<String,String> refundData) {
		JsonResult result = new JsonResult();
		Date rejectedDate = new Date();
		SimpleDateFormat rejectedDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Integer rejectedTime = Integer.valueOf(DateUtils.getTime(rejectedDateFormat.format(rejectedDate),rejectedDateFormat));
		
		try {
			YskOrderRejectedEntity orderRejectedEntity = orderRejectedRepository.findByRejectedNo(refundData.get("rejectedNo"));
			orderRejectedEntity.setRejectedTime(rejectedTime);
			orderRejectedEntity.setOrderStatus(4);
			orderRejectedRepository.saveAndFlush(orderRejectedEntity);
			YskOrderDetailEntity orderDetailEntity = orderDetailRepository.findById(orderRejectedEntity.getOrderDetailId()).get();
			orderDetailEntity.setIsSend((byte)3);
			orderDetailRepository.saveAndFlush(orderDetailEntity);
			
			// 原路退款
			switch(refundData.get("payType")) {
				// 余额
				case "1":
					if(this.refundBalance(refundData)) {
						result.setCode(1);
						result.setMessage("退款成功");
						return result;
					} else {
						throw new Exception();
					}
					
				// 微信
				case "2":
					Map<String, String> resp = WxPayUtils.refund(refundData.get("orderPayNo"), refundData.get("rejectedNo"), new BigDecimal(refundData.get("payPrice")),new BigDecimal(refundData.get("amount")));
					
					if(resp.get("result_code").equals("SUCCESS")) {
						result.setCode(1);
						result.setMessage("退款成功");
						return result;
					} else {
						throw new Exception();
					}
				// 支付宝
				case "3":
					AlipayTradeRefundResponse aliResponse = AliPayUtils.tradeRefund(refundData.get("orderPayNo"), refundData.get("rejectedNo"), refundData.get("amount").toString());
			        
			        if (aliResponse.isSuccess()) { 
			        	result.setCode(1);
						result.setMessage("退款成功");
						return result;
			    	} else { 
			    		throw new Exception();
			    	}
				// 一网通
				case "4":
					String jsonParam = bankDoRefundServiceImpl.setDoRefundReqData(refundData.get("orderPayNo"), refundData.get("amount"), refundData.get("rejectedNo"));
					JSONObject rspData = bankDoRefundServiceImpl.BankRefund(jsonParam, BankPayConfig.DO_REFUND_URL, BankPayConfig.CHARSET);
					if(rspData.get("rspCode").equals("SUC0000")) {
						result.setCode(1);
						result.setMessage("退款成功");
						return result;
					} else {
						throw new Exception();
					}
				default:
					result.setCode(0);
					result.setMessage("未找到支付方式");
					return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result.setCode(0);
			result.setMessage("退款失败，请稍后重试");
			return result;
		}
	}
	
	private boolean refundBalance (HashMap<String,String> refundData) {
		BigDecimal amount = new BigDecimal(refundData.get("amount"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
 		Date nowDate = new Date();
		Integer time = Integer.valueOf(DateUtils.getTime(sdf.format(nowDate),sdf));
		
		try {
			YskUserWealthEntity userWealthEntity = userWealthRepository.findByUid(Integer.valueOf(refundData.get("userId")));
			userWealthEntity.setMoney(userWealthEntity.getMoney().add(amount));
			userWealthRepository.saveAndFlush(userWealthEntity);
			
			YskMoneyDetailEntity moneyDetailEntity = new YskMoneyDetailEntity();
			moneyDetailEntity.setOrderNo(refundData.get("rejectedNo"));
			moneyDetailEntity.setMoney(amount);
			moneyDetailEntity.setUid(Integer.valueOf(refundData.get("userId")));
			moneyDetailEntity.setType("refundgood");
			moneyDetailEntity.setTypeName("商品退款");
			moneyDetailEntity.setStatus((byte)1);
			moneyDetailEntity.setCreateTime(time);
			moneyDetailEntity.setFromType((byte)1);
			moneyDetailEntity.setContent("商品退款,退款订单号" + refundData.get("rejectedNo"));
			moneyDetailEntity.setMoneyRecord(userWealthEntity.getMoney());
			moneyDetailRepository.saveAndFlush(moneyDetailEntity);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
