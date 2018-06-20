package com.hctxsys.controller.api.pay;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.api.pay.ApiBankPaySuccessServiceImpl;
import com.hctxsys.service.api.pay.ApiBankScheduledSavePubKeyServiceImpl;
import com.hctxsys.service.api.pay.ApiBankSignSuccessServiceImpl;
import com.hctxsys.service.api.pay.ApiPayServiceImpl;
import com.hctxsys.vo.api.ApiPayVo;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/bank")
public class ApiBankPayController {

	@Autowired
	private ApiPayServiceImpl apiPayServiceImpl;
	@Autowired
	private ApiBankPaySuccessServiceImpl apiBankPaySuccessService;
	@Autowired
	private ApiBankSignSuccessServiceImpl apiBankSignSuccessService;
	@Autowired
	private ApiBankScheduledSavePubKeyServiceImpl apiBankScheduledSavePubKeyService;
	
	@RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
	@ResponseBody
	public void paySuccess(HttpServletRequest request ,HttpServletResponse response,@RequestParam(value = "jsonRequestData") String jsonRequestData) {
		JSONObject bankReqData = JSONObject.fromObject(jsonRequestData);
		// TODO
		// 返回参数
		JSONObject noticeData = JSONObject.fromObject(bankReqData.get("noticeData"));
		// orderno
		String orderNo = noticeData.get("merchantPara").toString();
		//查询支付结果
        ApiPayVo searchVo = apiPayServiceImpl.getOrderStatus(orderNo);
        
        if (!("1".equals(searchVo.getStatus()))) {
        	if (!apiBankPaySuccessService.apiBankPaySuccess(bankReqData)) {
    			int errCode = 601;
    			response.setStatus(errCode);
    			request.setAttribute("javax.servlet.error.status_code",errCode);
    		}
        }
	}
	

	@RequestMapping(value = "/signSuccess", method = RequestMethod.POST)
	@ResponseBody
	public void signSuccess(HttpServletRequest request ,HttpServletResponse response,@RequestParam(value = "jsonRequestData") String jsonRequestData) {
		JSONObject bankReqData = JSONObject.fromObject(jsonRequestData);
		// TODO
		if (!apiBankSignSuccessService.apiBankSignSuccess(bankReqData)) {
			int errCode = 601;
			response.setStatus(errCode);
			request.setAttribute("javax.servlet.error.status_code",errCode);
		}
	}
	
	// 每天2点15获取招行公钥
//	@Scheduled(cron = "0 15 2 * * ?")
//	public void GetFbPubKeyCtrl() {
//		apiBankScheduledSavePubKeyService.SavePubKey();
//	}
}
