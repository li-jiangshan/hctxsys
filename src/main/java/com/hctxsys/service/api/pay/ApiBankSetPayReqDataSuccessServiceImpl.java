package com.hctxsys.service.api.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.config.payConfig.BankPayConfig;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.UserRepository;
import com.hctxsys.vo.api.ApiPayVo;

import net.sf.json.JSONObject;

@Service("apiBankSetPayReqDataSuccessService")
public class ApiBankSetPayReqDataSuccessServiceImpl {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 设置签约支付参数
	 */
	@Transactional
	public JSONObject setPayReqData(ApiPayVo request) {
		try {
			JSONObject reqData = new JSONObject();
			
			Date day=new Date();
			SimpleDateFormat timeForDate = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat timeForDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
			//TODO
			reqData.put("dateTime", timeForDateTime.format(day).toString()); //订单日期,格式：yyyyMMdd
			reqData.put("branchNo", BankPayConfig.BRANCH_NO); //商户分行号，4位数字*
			reqData.put("merchantNo", BankPayConfig.MERCHANT_NO); //商户号，6位数字*
			reqData.put("date", timeForDate.format(day).toString()); //订单日期,格式：yyyyMMdd
			reqData.put("orderNo", request.getOrderNo()); //订单号*
			reqData.put("amount", request.getAmount().toString()); //金额*
			//TODO 更换ip
			reqData.put("payNoticeUrl", BankPayConfig.PAY_NOTICE_URL); //成功支付结果通知地址,商户接收成功支付结果通知的地址*
			//orderno
			reqData.put("payNoticePara", request.getOrderNo()); //成功支付结果通知附加参数
			
			// TODO 客户协议号*
			String agrNo;
			YskUserEntity userEntity = userRepository.findById(request.getUserID()).get();
			if (userEntity.getYwtNo() == null || userEntity.getYwtNo() == "") {
				agrNo = timeForDateTime.format(day).toString() + request.getUserID().toString();
				userEntity.setYwtNo(agrNo);
				userRepository.saveAndFlush(userEntity);
			} else {
				agrNo = userEntity.getYwtNo();
			}
			// 客户协议号*
			reqData.put("agrNo", agrNo);
			//TODO 如果储存增加一张表,协议开通请求流水号
			reqData.put("merchantSerialNo", timeForDateTime.format(day).toString() + request.getUserID().toString());
			
			reqData.put("expireTimeSpan", BankPayConfig.EXPIRE_TIME_SPAN); //过期时间跨度
			// TODO 未定
			reqData.put("returnUrl", BankPayConfig.RETURN_URL); //成功页返回商户地址,支付成功页面上“返回商户”按钮跳转地址*
			reqData.put("cardType", BankPayConfig.CARD_TYPE); //允许支付的卡类型
			
			reqData.put("clientIP", ""); //TODO 可以不要，商户取得的客户IP
			reqData.put("userID", request.getUserID().toString()); //用于标识商户用户的唯一ID*
			reqData.put("mobile", request.getMobile()); //商户用户的手机号
			reqData.put("riskLevel", "3"); //TODO 可以不要，风险等级
			//TODO 更换ip
			reqData.put("signNoticeUrl", BankPayConfig.SIGN_NOTICE_URL); //成功签约结果通知地址*
			reqData.put("signNoticePara", request.getOrderNo()); //成功签约结果通知附加参数*
			return reqData;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//          jsonResult.setCode(0);
//          jsonResult.setMessage("提交订单失败");
			return null;
		}
		
	}
}
