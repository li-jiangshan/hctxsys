package com.hctxsys.controller.examine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskSellerApplyEntity;
import com.hctxsys.service.SellerUserServiceImpl;
import com.hctxsys.util.PageTableDate;
import com.hctxsys.util.Result;

@Controller
public class SellerUserController {
	@Autowired
	private SellerUserServiceImpl sellerUser;
	
	/**
	 * @param model
	 * @param strstatus 状态
	 * @param page 页码
	 * @param date_start 开始日期
	 * @param date_end 结束日期
	 * @param querytype 搜索类型
	 * @param keyword 搜索的关键字
	 * @return
	 */
	@RequestMapping(value= {"/Admin/Sellerapply/apply","/Admin/Sellerapply/apply/type/{strstatus}"})
	//@ResponseBody
	public String findByStatusEqualsZero(Model model, @PathVariable(required = false) String strstatus,@RequestParam(defaultValue="0")Integer page,String date_start,String date_end,String querytype,String keyword) {
		byte status=0;
		if(null!=strstatus&&0!=strstatus.length()) {
			status = Byte.valueOf(strstatus);
		}
		Page<YskSellerApplyEntity> listPage = sellerUser.findByStatusEquals(status, page, 10, date_start, date_end, querytype, keyword);
		int rows =(int)listPage.getTotalElements();
		//如果有数据
		if(rows!=0) {
			//List<PageTableDate> pageTableDates = new ArrayList<PageTableDate>();
			model.addAttribute("rows", rows);
			/*int pagesize=rows/10+1;
			for(int i=0;i<pagesize;i++) {
				PageTableDate tableDate = new PageTableDate();
				//分页a标签的href
				String href = sellerUser.setHref("/Admin/Sellerapply/", status, i, date_start, date_end, querytype, keyword);
				tableDate.setHref(href);
				tableDate.setPage(i+1);
				pageTableDates.add(tableDate);
			}
			model.addAttribute("pageTableDates", pageTableDates);*/
		}
		//生产上一页的href
		//String previousPage = sellerUser.setHref("/Admin/Sellerapply/", status, page-1,  date_start, date_end, querytype, keyword);
		//生产下一页的href
		//String nextPage = sellerUser.setHref("/Admin/Sellerapply/", status, page+1,  date_start, date_end, querytype, keyword);
		model.addAttribute("pageCount",listPage.getTotalPages());
		model.addAttribute("sellerUserlist", listPage.getContent());
		model.addAttribute("status", status);
		model.addAttribute("date_start", date_start);
		model.addAttribute("date_end", date_end);
		model.addAttribute("querytype", querytype);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page",page);
		//model.addAttribute("previousPage", previousPage);
		//model.addAttribute("nextPage", nextPage);
		return "admin/sellerapply/apply";
	}
	
/*	@RequestMapping("/Admin/sellerapply/apply/type/{status}.html")
	//@ResponseBody
	public String findByStatusEquals(Model model, @PathVariable byte status,@RequestParam(defaultValue="0")Integer page,String date_start,String date_end,String querytype,String keyword) {
		Page<SellerApply> listPage = sellerUser.findByStatusEquals(status, page, 10);
		model.addAttribute("sellerUserlist", listPage.getContent());
		model.addAttribute("status", status);
		return "admin/sellerapply/apply";
	}*/
	
	/**
	 * @param model
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping("admin/sellerapply/detail/id/{id}")
	public String findById(Model model, @PathVariable Integer id) {
		YskSellerApplyEntity sellerApply = sellerUser.findById(id);
		model.addAttribute("sellerApply", sellerApply);
		model.addAttribute("id", id);
		return "admin/sellerapply/detail";
	}
	
	/**
	 * @param id 用户id
	 * @param olderid
	 * @param content 回复的信息
	 * @param agree 状态
	 * @return
	 */
	@RequestMapping("/admin/sellerapply/setdatastatus")
	@ResponseBody
	public Result setdatastatus(Integer id,Integer olderid,String content,Byte agree,String shopJ,String shopW) {
		Result result = sellerUser.checkSellerApply(id, content, agree, shopJ, shopW);
		return result;
	}
}
