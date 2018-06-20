package com.hctxsys.service.api.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.repository.MessageRepository;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.payUtil.BankPayUtils;

import net.sf.json.JSONObject;

@Service("apiBankSignSuccessService")
public class ApiBankSignSuccessServiceImpl {

	@Autowired
	private MessageRepository messageRepository;
	
	@Transactional
	public boolean apiBankSignSuccess(JSONObject request) {
		try {
			if (BankPayUtils.isValidSignature(BankPayUtils.getStrToSign(request.get("noticeData").toString()), 
					request.get("sign").toString(),
					BankPayUtils.GetFbPubKey())) {
				// TODO
//			YskMessageEntity message = new YskMessageEntity();
//			message.setType(1);
//			message.setContent("签约成功");
//			// TODO userId
//			JSONObject noticeData = JSONObject.fromObject(request.get("noticeData"));
//			int uid = new Integer(noticeData.get("merchantPara").toString());
//			message.setUid(uid);
//			// create time
//			Date date = new Date();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//			message.setCreateTime(Integer.valueOf(DateUtils.getTime(dateFormat.format(date), dateFormat)));
//			// title
//			message.setTitle("一网通签约成功");
//			// TODO state
//			message.setStatus((byte) 0);
//			// TODO send
//			message.setSend(1);
//
//			// TODO 成功签约后操作
//			messageRepository.saveAndFlush(message);
				
				
				return true;
			} else {
				// 非招行请求
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//          jsonResult.setCode(0);
//          jsonResult.setMessage("提交订单失败");
			return false;
		}
		
	}
}
