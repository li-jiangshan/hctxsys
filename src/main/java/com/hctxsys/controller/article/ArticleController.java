package com.hctxsys.controller.article;

import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskArticleEntity;
import com.hctxsys.service.ArticleManageServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

/**
 * 文章管理
 * @ClassName:ArticleController
 * @Author:li
 * @CreateDate:2018年5月4日
 */
@Controller
@RequestMapping(value = "/Admin/Article")
public class ArticleController {
	
	@Autowired
	private ArticleManageServiceImpl articleManageService;

	/**
	 * 文章列表
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView getNewsList(TableResult result) {
		TableResult tableResult = articleManageService.getArticleList(result);
		ModelAndView view = new ModelAndView("/admin/article/index");
		view.addObject("tableResult", tableResult);	
		return view;
	}
	
	/**
	 * 添加文章
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addarticle", method = RequestMethod.GET)
	public String addArticle(Model model) {
		return "admin/article/addarticle";
	}
	
	/**
	 * 保存文章
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savearticle", method = RequestMethod.POST)
	@ResponseBody
	public Result saveNews(YskArticleEntity request) {
		request.setSavetime(String.valueOf(Calendar.getInstance().getTimeInMillis()).substring(0, 10));
		Result result = articleManageService.saveArticle(request);
		return result;
	}
	
	/**
	 * 编辑文章
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editarticle/{id}", method = RequestMethod.GET)
	public String editNews(@PathVariable Integer id, Model model) {
		Optional<YskArticleEntity> info = articleManageService.getArticleInfo(id);
		model.addAttribute("info", info.get());
		model.addAttribute("selected", info.get().getType());
		return "admin/article/addarticle";
	}
	
	/**
	 * 删除文章
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deletearticle", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteNews(HttpServletRequest req) {
		String id = req.getParameter("id");
		Result result = articleManageService.deleteArticle(Integer.parseInt(id));
		return result;
	}
}
