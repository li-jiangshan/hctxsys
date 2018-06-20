package com.hctxsys.controller.adminmall;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskOrderDetailEntity;
import com.hctxsys.entity.YskOrderEntity;
import com.hctxsys.service.OrderServiceImpl;
import com.hctxsys.util.PageTableDate;
import com.hctxsys.util.Result;

@Controller
@RequestMapping("Adminmall")
public class OrderController {
	@Autowired
	private OrderServiceImpl ordeService;

	/**
	 * 订单首页
	 *
	 * @param model
	 * @param page
	 * @param dateStart
	 * @param dateEnd
	 * @param querytype
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = { "/Order/index", "/Order/index/order_status/{orderStatus}" })
	public String index(Model model, @RequestParam(defaultValue = "0") Integer page, String dateStart, String dateEnd,
			String querytype, String keyword, @PathVariable(required = false) String orderStatus,
			HttpServletRequest request, HttpSession session) {

		byte zhuangtai = 0;
		if (null != orderStatus && 0 != orderStatus.length()) {
			zhuangtai = Byte.valueOf(orderStatus);
		}

		Page<YskOrderEntity> list = ordeService.shouyefindAll(page, 10, dateStart, dateEnd, querytype, keyword,
				zhuangtai, request, session);

		int rows = (int) list.getTotalElements();
		// 如果有数据
		if (rows != 0) {
			List<PageTableDate> pageTableDates = new ArrayList<PageTableDate>();
			model.addAttribute("rows", rows);
			int pagesize = rows / 10 + 1;
			for (int i = 0; i < pagesize; i++) {
				PageTableDate tableDate = new PageTableDate();
				// 分页a标签的href
				String href = ordeService.setHref("/Adminmall/order/", i, dateStart, dateEnd, querytype, keyword);
				tableDate.setHref(href);
				tableDate.setPage(i + 1);
				pageTableDates.add(tableDate);
			}
			model.addAttribute("pageTableDates", pageTableDates);
		}
		// 生产上一页的href
		String previousPage = ordeService.setHref("/Adminmall/order/", page - 1, dateStart, dateEnd, querytype,
				keyword);
		// 生产下一页的href
		String nextPage = ordeService.setHref("/Adminmall/order/", page + 1, dateStart, dateEnd, querytype, keyword);
		model.addAttribute("orderlist", list.getContent());
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("querytype", querytype);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("previousPage", previousPage);
		model.addAttribute("nextPage", nextPage);
		return "adminmall/order/index"; // 返回页面
	}

	/**
	 * 跳转编辑页面并查询详细数据
	 *
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/Order/detail/orderId/{orderId}")
	public String findById(Model model, @PathVariable Integer orderId) {
		YskOrderEntity yskorderentity = ordeService.findById(orderId);
		List<YskOrderDetailEntity> list = ordeService.findByIdgood(orderId);

		model.addAttribute("goodlist", list);
		model.addAttribute("yskorderentity", yskorderentity);
		model.addAttribute("orderId", orderId);
		return "adminmall/order/detail";
	}

	/**
	 * 更新状态
	 *
	 * @param orderStatus
	 * @return
	 */
	@RequestMapping("/Order/detail")
	@ResponseBody
	public Result checkOrderStatus(Integer orderId, Byte orderStatus) {
		Result result = ordeService.checkOrderStatus(orderId, orderStatus);
		return result;
	}

	/**
	 * 删除订单信息(修改用户假删除字段)
	 *
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/Order/index/orderId/{orderId}")
	@ResponseBody
	public Result deletById(Model model, @PathVariable Integer orderId) {
		Result result = ordeService.checkOrderIsDelete(orderId);
		return result;
	}

}
