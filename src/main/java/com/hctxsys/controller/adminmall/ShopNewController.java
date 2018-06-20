package com.hctxsys.controller.adminmall;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskShopnewEntity;
import com.hctxsys.service.ShopNewServiceImpl;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;



@Controller
@RequestMapping("/Adminmall/Shopnews")
public class ShopNewController {
	
	@Autowired
	private ShopNewServiceImpl shopNewServiceImpl;
	
	/**
	 * 公告列表
	 * @return
	 */
	@GetMapping(path = "/index")
	public @ResponseBody ModelAndView getModelList(TableResult result) {
		TableResult tableResult = null;
		if(result.getTypeText()==null||"".equals(result.getTypeText())) {
			tableResult = shopNewServiceImpl.findAll(result);
		}else {
			tableResult = shopNewServiceImpl.findBykeyword(result);
		}
		ModelAndView modelAndView = new ModelAndView("adminmall/shopnews/index");
		modelAndView.addObject("tableResult", tableResult);
		return modelAndView;
	}

	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id",required = false,defaultValue = "0") String id) {
		YskShopnewEntity news = shopNewServiceImpl.findById(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/shopnews/add");
		modelAndView.addObject("news",news);
		return modelAndView;
	}
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/update",method=RequestMethod.POST)
	@ResponseBody
	public Result updateNews(YskShopnewEntity news) {
		if(news.getType()==0) {
			return new Result(0, "分类不能为空", "/Adminmall/Goodbrand/index", "");
		}
		if(news.getTitle()==null||"".equals(news.getTitle())) {
			return new Result(0, "标题不能为空", "/Adminmall/Goodbrand/index", "");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
        news.setCreateTime(Integer.valueOf(time));
		YskShopnewEntity entity = shopNewServiceImpl.updateNews(news);
		if (entity!=null) {
			return new Result(1, "保存成功", "/Adminmall/Shopnews/index", "");
		}
		return new Result(0, "保存失败", "/Adminmall/Shopnews/index", "");
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@RequestMapping(path = "/deletenews",method=RequestMethod.GET)
	@ResponseBody
	public Result deleteNews(@RequestParam String id) {
		int i = shopNewServiceImpl.deleteNews(Integer.valueOf(id));
		if (i==1) {
			return new Result(1, "删除成功，不可恢复", "/Adminmall/Shopnews/index", "");
		}
		return new Result(0, "删除失败", "/Adminmall/Shopnews/index", "");
	}
}
