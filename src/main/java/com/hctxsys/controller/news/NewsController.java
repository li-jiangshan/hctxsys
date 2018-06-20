package com.hctxsys.controller.news;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.entity.YskSchoolPeopleEntity;
import com.hctxsys.service.NewsManageServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

/**
 * 新闻列表管理
 * @ClassName:NewsController
 * @Author:li
 * @CreateDate:2018年5月3日
 */
@Controller
@RequestMapping(value = "/Admin/News")
public class NewsController {

	@Autowired
	private NewsManageServiceImpl newsManageServiceImpl;
	
	@RequestMapping(value = "/head", method = RequestMethod.GET)
	public ModelAndView head(TableResult result) {
		ModelAndView view = new ModelAndView("/common/upload");
		view.addObject("num", 1);
		return view;
	}
	
	/**
	 * 查询新闻列表
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/entry", method = RequestMethod.GET)
	public ModelAndView getNewsList(TableResult result) {
		TableResult tableResult = newsManageServiceImpl.getNewsList(result);
		ModelAndView view = new ModelAndView("/admin/news/entry");
		view.addObject("tableResult", tableResult);
		return view;
	}
	
	/**
	 * 新增新闻
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addnews", method = RequestMethod.GET)
	public String addNews(Model model) {
		List<YskNewsTitleEntity> newsInfo = newsManageServiceImpl.getNewsType();
		String title = newsManageServiceImpl.getNewsTitle(6).getTitle();
		List<Object> newsType = new ArrayList<>();
		
		newsInfo.forEach(item->{
			Map<String, Object> type = new HashMap<>();
			if (item.getPid() == 6) {
				type.put("id", item.getId());
				type.put("title", title + "-" + item.getTitle());
			} else {
				type.put("id", item.getId());
				type.put("title", item.getTitle());
			}
			newsType.add(type);
		});
		model.addAttribute("typeInfo", newsType);
		return "admin/news/addnews";
	}
	
	/**
	 * 保存新闻
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savenews", method = RequestMethod.POST)
	@ResponseBody
	public Result saveNews(YskNewsEntity request) {
		String title = newsManageServiceImpl.getNewsTitle(request.getPid()).getTitle();
		request.setAddtime(Integer.parseInt(String.valueOf(Calendar.getInstance().getTimeInMillis()).substring(0, 10)));
		request.setType(title);
		if (request.getStatus() != 1 && request.getStatus() != 2) {
			request.setStatus(1);
		}
		if (request.getNewtop() != 1 && request.getNewtop() != 2) {
			request.setNewtop(1);
		}
		Result result = newsManageServiceImpl.saveNews(request);
		return result;
	}
	
	/**
	 * 编辑新闻
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editnews/{id}", method = RequestMethod.GET)
	public String editNews(@PathVariable Integer id, Model model) {
		List<YskNewsTitleEntity> newsInfo = newsManageServiceImpl.getNewsType();
		String title = newsManageServiceImpl.getNewsTitle(6).getTitle();
		List<Object> newsType = new ArrayList<>();
		
		newsInfo.forEach(item->{
			Map<String, Object> type = new HashMap<>();
			if (item.getPid() == 6) {
				type.put("id", item.getId());
				type.put("title", title + "-" + item.getTitle());
			} else {
				type.put("id", item.getId());
				type.put("title", item.getTitle());
			}
			newsType.add(type);
		});
		
		Optional<YskNewsEntity> info = newsManageServiceImpl.getNewsInfo(id);
		model.addAttribute("typeInfo", newsType);
		model.addAttribute("info", info.get());
		model.addAttribute("selected", info.get().getPid());
		return "admin/news/addnews";
	}
	
	/**
	 * 删除新闻
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deletenews", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteNews(HttpServletRequest req) {
		String id = req.getParameter("id");
		Result result = newsManageServiceImpl.deleteNews(Integer.parseInt(id));
		return result;
	}
	
	/**
	 * 学员列表页
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public ModelAndView getStudentList(TableResult result) {
		TableResult tableResult = newsManageServiceImpl.getStudentList(result);
		ModelAndView view = new ModelAndView("/admin/news/student");
		view.addObject("tableResult", tableResult);	
		return view;
	}
	
	/**
	 * 新增学员
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addpeople", method = RequestMethod.GET)
	public String addPeople(Model model) {
		return "admin/news/addpeople";
	}
	
	/**
	 * 保存学员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savepeople", method = RequestMethod.POST)
	@ResponseBody
	public Result savePeople(YskSchoolPeopleEntity request) {
		request.setAddtime(String.valueOf(Calendar.getInstance().getTimeInMillis()).substring(0, 10));
		Result result = newsManageServiceImpl.savePeople(request);
		return result;
	}
	
	/**
	 * 编辑学员
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editpeople/{id}", method = RequestMethod.GET)
	public String editPeople(@PathVariable Integer id, Model model) {
		Optional<YskSchoolPeopleEntity> info = newsManageServiceImpl.getPeopleInfo(id);
		model.addAttribute("info", info.get());
		return "admin/news/addpeople";
	}
	
	/**
	 * 删除学员
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deletepeople", method = RequestMethod.POST)
	@ResponseBody
	public Result deletePeople(HttpServletRequest req) {
		String id = req.getParameter("id");
		Result result = newsManageServiceImpl.deletePeople(Integer.parseInt(id));
		return result;
	}
	
	/**
	 * 查看报名人数
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/zuopin/id/{id}", method = RequestMethod.GET)
	public ModelAndView detailPeople(@PathVariable Integer id, TableResult result) {
		TableResult tableResult = newsManageServiceImpl.getPeopleList(id, result);
		ModelAndView view = new ModelAndView("/admin/news/zuopin");
		view.addObject("tableResult", tableResult);
		view.addObject("pid", id);
		return view;
	}
	
	/**
	 * 删除报名人
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deletedetail", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteDetail(HttpServletRequest req) {
		String id = req.getParameter("id");
		Result result = newsManageServiceImpl.deleteDetail(Integer.parseInt(id));
		return result;
	}
}
