package com.hctxsys.controller.adminmall;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.service.RefundServiceImpl;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("Adminmall")
public class RefundController {
    
	@Autowired
	RefundServiceImpl refundServiceImpl;
	
	@RequestMapping(value = "/refund/rejectedNo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doRefund(HttpServletRequest req) {
		String rejectedNo = req.getParameter("rejectedNo");
		HashMap<String,String> refundData = refundServiceImpl.getRefundData(rejectedNo);
		
		return refundServiceImpl.setRefund(refundData);
	}
	
}
