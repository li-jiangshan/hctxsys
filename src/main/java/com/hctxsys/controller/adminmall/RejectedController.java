package com.hctxsys.controller.adminmall;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderRejectedEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.service.OrderRejectServiceImpl;
import com.hctxsys.util.Contant;
import com.hctxsys.util.TableResult;

/**
 * 退货
 * @ClassName:RejectedController
 * @Author:li
 * @CreateDate:2018年5月23日
 */
@RequestMapping(value="/Adminmall/Order/index")
@Controller
public class RejectedController {
	
	@Autowired
	private OrderRejectServiceImpl orderRejectService;

	/**
	 * 退货列表
	 * @param model
	 * @param result
	 * @param req
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/order/reject")
	public String getRejectList(Model model, TableResult result, HttpServletRequest req, @RequestParam(defaultValue="0")Integer page) {
		TableResult tableResult = orderRejectService.getRejectList(result, 0);
		model.addAttribute("tableResult", tableResult);
		return "/adminmall/order/reject";
	}
	
	/**
	 * 退货详细
	 * @param model
	 * @param rejectId
	 * @return
	 */
	@RequestMapping(value="/order/rejectDetail/reject_id/{reject_id}")
	public String getRejectDetail(Model model, @PathVariable(value = "reject_id") Integer rejectId) {
		YskOrderRejectedEntity rejectDetail = orderRejectService.getRejectDetail(rejectId);
		YskUserEntity user = orderRejectService.getUser(rejectDetail.getUserId());
		Optional<YskOrderDetailEntity> orderDetail = orderRejectService.getOrderDetail(rejectDetail.getOrderDetailId());
		YskOrderEntity order = orderRejectService.getOrderByid(orderDetail.get().getOrderId());
		model.addAttribute("info", rejectDetail);
		model.addAttribute("order", order);
		model.addAttribute("detail", orderDetail.get());
		model.addAttribute("user", user);
		return "/adminmall/order/rejectDetail";
	}
	
	/**
	 * 拒绝退货
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/reject")
	@ResponseBody
	public String reject(Model model, HttpServletRequest req) {
		int rejectId = Integer.parseInt(req.getParameter("rejectId"));
		String orderStatusReason = req.getParameter("orderStatusReason");
		int result = orderRejectService.updateRejectOrderStatus(rejectId, orderStatusReason);
		if (result > 0) {
			return Contant.SUCCESS;
		} else {
			return Contant.FAIL;
		}
	}
	
	/**
	 * 同意退货
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/agree")
	@ResponseBody
	public String agree(Model model, HttpServletRequest req) {
		int rejectId = Integer.parseInt(req.getParameter("rejectId"));
		String rejectedName = req.getParameter("rejectedName");
		String receivingAddress = req.getParameter("receivingAddress");
		String rejectedPhone = req.getParameter("rejectedPhone");
		int result = orderRejectService.updateOrderName(rejectId, rejectedName, rejectedPhone, receivingAddress);
		if (result > 0) {
			return Contant.SUCCESS;
		} else {
			return Contant.FAIL;
		}
	}
	
	/**
	 * 确认收货
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/confirmGoods")
	@ResponseBody
	public String confirmGoods(Model model, HttpServletRequest req) {
		int rejectId = Integer.parseInt(req.getParameter("rejectId"));
		int result = orderRejectService.updateOrderStatus(rejectId, 4);
		if (result > 0) {
			return Contant.SUCCESS;
		} else {
			return Contant.FAIL;
		}
	}
	
}
