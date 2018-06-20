package com.hctxsys.service.api.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.util.payUtil.BankPayUtils;

import net.sf.json.JSONObject;

@Service("apiBankPaySuccessService")
public class ApiBankPaySuccessServiceImpl {

	@Autowired
	private ApiPayServiceImpl apiPayServiceImpl;
	@Autowired
	private ApiBankGetPubKeyServiceImpl apiBankGetPubKeyService;
	
	
	public boolean apiBankPaySuccess(JSONObject request) {

		if (BankPayUtils.isValidSignature(
				BankPayUtils.getStrToSign(request.get("noticeData").toString()),
				request.get("sign").toString(), apiBankGetPubKeyService.getPubKey())) {
			
			// 返回参数
			JSONObject noticeData = JSONObject.fromObject(request.get("noticeData"));
			// orderno
			String orderNo = noticeData.get("merchantPara").toString();
			
			return apiPayServiceImpl.paySuccess(orderNo);
			
		} else {
			// 非招行请求
			return false;
		}
	}
}
