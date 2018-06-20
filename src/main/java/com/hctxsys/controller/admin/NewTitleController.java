package com.hctxsys.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskHealthyEntity;
import com.hctxsys.entity.YskHealthyTypeEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.service.NewTitleServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;


import com.hctxsys.vo.NewsVo;


@Controller
@RequestMapping("Admin")
public class NewTitleController {
	@Autowired
	NewTitleServiceImpl newTitleServiceImpl;

	/**
	 * 新闻列表
	 * 
	 * @param result
	 *            分页动态查询
	 * @return
	 */
	@GetMapping("News/index")
	public ModelAndView getNewList(TableResult result) {

		TableResult tableResult = newTitleServiceImpl.indexTable(result);
		ModelAndView view = new ModelAndView("/admin/new/index");
		view.addObject("tableResult", tableResult);
		return view;

	}
	/**
	 * 删除分类
	 * 
	 * @param id
	 */
	@RequestMapping(path = "new/deleteNew", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteNew(@RequestParam String id) {
		int i = newTitleServiceImpl.deleteNew(Integer.valueOf(id));
		if (i == 1) {
			return new Result(1, "删除成功，不可恢复", "/Admin/News/index", "");
		}
		return new Result(0, "删除失败", "/Admin/News/index", "");
	}


	/**
	 * 跳转添加页面
	 * 
	 * @return
	 */
	@GetMapping("new/add")
	public ModelAndView edit(@RequestParam(value = "id", required = false, defaultValue = "0") String id) {
		YskNewsTitleEntity newTitle = newTitleServiceImpl.findById(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("/admin/new/add");
		modelAndView.addObject("newTitle", newTitle);
		return modelAndView;
	}
	
	@RequestMapping(value="new/add")
	public String add() {
		return "/admin/new/add";
	}

	/**
	 * 新增、更新
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "new/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateNew(YskNewsTitleEntity News) {

		if (News.getTitle() == null || News.getTitle() == "") {
			return new Result(0, "标题名称不能为空", "/Admin/News/index", "");
			
		}
		
		if (News.getSort() == null ) {
			News.setSort(0);
		}
		boolean b=newTitleServiceImpl.isMatches(String.valueOf(News.getSort()));
		if (b==false) {
			return new Result(0, "排序为正整数", "/Admin/News/index", "");
		}
		

		YskNewsTitleEntity entity = newTitleServiceImpl.updateNew(News);
		if (entity != null) {
			return new Result(1, "保存成功", "/Admin/News/index", "");
		}
		return new Result(0, "保存失败", "/Admin/News/index", "");

	}

	
	
	/**查看详细
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/new/edit/{id}" ,method = RequestMethod.GET)
	public String getNew(Model model,@PathVariable Integer id) {
		NewsVo newsVo = newTitleServiceImpl.getNews(id);
		model.addAttribute("newTitle", newsVo);
		return "/admin/new/edit";
	}
}
