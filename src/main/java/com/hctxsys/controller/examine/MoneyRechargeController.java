package com.hctxsys.controller.examine;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskMoneyRechargeEntity;
import com.hctxsys.service.MoneyRechargeServiceImpl;
import com.hctxsys.util.PageTableDate;
import com.hctxsys.util.Result;

@Controller
public class MoneyRechargeController {
	@Autowired
	private MoneyRechargeServiceImpl rechargeImpl;
	
	/**
	 * @param model
	 * @param strstatus 0-未支付 1-已支付 2-不通过
	 * @param page 页码
	 * @param date_start 开始日期
	 * @param date_end 结束日期
	 * @param querytype 搜索的类型
	 * @param keyword 搜索的关键字
	 * @return
	 */
	@RequestMapping(value= {"/Admin/Recharge/index","/Admin/Recharge/index/type/{strstatus}"})
	public String findByStatusEqualsZero(Model model, @PathVariable(required = false) String strstatus,@RequestParam(defaultValue="0")Integer page,String date_start,String date_end,String querytype,String keyword) {
		byte status=0;
		if(null!=strstatus&&0!=strstatus.length()) {
			status = Byte.valueOf(strstatus);
		}
		Page<YskMoneyRechargeEntity> list = rechargeImpl.findByStatusEquals(status, page, 10, date_start, date_end, querytype, keyword);
		int rows = (int)list.getTotalElements();
		//如果有数据
		if(rows!=0) {
			//List<PageTableDate> pageTableDates = new ArrayList<PageTableDate>();
			model.addAttribute("rows", rows);
			/*int pagesize=rows/10+1;
			for(int i=0;i<pagesize;i++) {
				PageTableDate tableDate = new PageTableDate();
				//分页a标签的href
				String href = rechargeImpl.setHref("/Admin/Recharge/", status, i, date_start, date_end, querytype, keyword);
				tableDate.setHref(href);
				tableDate.setPage(i+1);
				pageTableDates.add(tableDate);
			}
			model.addAttribute("pageTableDates", pageTableDates);*/
		}
		//生产上一页的href
		//String previousPage = rechargeImpl.setHref("/Admin/Recharge/", status, page-1, date_start, date_end, querytype, keyword);
		//生产下一页的href
		//String nextPage = rechargeImpl.setHref("/Admin/Recharge/", status, page+1, date_start, date_end, querytype, keyword);
		model.addAttribute("pageCount",list.getTotalPages());
		model.addAttribute("moneyrechargelist", list);
		model.addAttribute("status",status);
		model.addAttribute("date_start", date_start);
		model.addAttribute("date_end", date_end);
		model.addAttribute("querytype", querytype);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page",page);
		//model.addAttribute("previousPage", previousPage);
		//model.addAttribute("nextPage", nextPage);
		return "/admin/recharge/index";
	}
	
	/**
	 * @param reply 回复的信息
	 * @param status 是否通过的状态
	 * @param 用户id
	 * @return
	 */
	@RequestMapping("/Admin/Recharge/check")
	@ResponseBody
	public Result checkMoneyGet(String reply,Byte status,Integer id,HttpServletRequest request) {
		Integer adminid = (Integer)request.getSession().getAttribute("uid");
		Result result = rechargeImpl.updateMoneyRecharge(reply, status, id, adminid);
		return result;
	}
}
