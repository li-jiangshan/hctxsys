package com.hctxsys.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.service.BannerServiceImpl;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Controller
@RequestMapping("Admin/Banner")
public class BannerController {
	
	@Autowired
	private BannerServiceImpl bannerServiceImpl;
	
	/**
	 * 广告列表
	 * @param result
	 * @return
	 */
	@GetMapping(path = "/index")
	public ModelAndView getAllBanner(TableResult result) {
		TableResult tableResult = bannerServiceImpl.findlist(result);
		ModelAndView modelAndView = new ModelAndView("admin/banner/index");
		modelAndView.addObject("tableResult", tableResult);
		return modelAndView;
	}
	
	/**
	 * 隐藏，显示
	 * @param id
	 * @param result
	 * @return
	 */
	@GetMapping(path = "/showhide/{id}")
	@ResponseBody
	public Result showhide(@PathVariable String id,TableResult result) {
		YskBannerEntity entity = bannerServiceImpl.showhide(Integer.valueOf(id));
		if(1==entity.getStatus()) {
			Result res = new Result();
			res.setStatus(1);
			res.setInfo("操作成功");
			return res;
			//return new Result(1, "操作成功", "/Admin/Banner/index", "");
		}else if (0==entity.getStatus()) {
			Result res = new Result();
			res.setStatus(1);
			res.setInfo("操作成功");
			return res;
			//return new Result(1, "操作成功", "/Admin/Banner/index", "");
		}
		Result res = new Result();
		res.setStatus(0);
		res.setInfo("操作失败");
		return res;
		//return new Result(0, "操作失败", "/Admin/Banner/index", "");
	}
	/**
	 * 删除广告
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/delete/{id}")
	@ResponseBody
	public Result deleteAds(@PathVariable String id,TableResult result) {
		int i = bannerServiceImpl.deleteById(Integer.valueOf(id));
		if (i==1) {
			Result res = new Result();
			res.setStatus(1);
			res.setInfo("删除成功");
			return res;
			//return new Result(1, "删除成功", "/Admin/Banner/index", "");
		}
		Result res = new Result();
		res.setStatus(0);
		res.setInfo("删除失败");
		return res;
		//return new Result(0, "删除失败", "/Admin/Banner/index", "");
	}
	/**
	 * 单查广告
	 * @return
	 */
	@GetMapping("/edit")
	public @ResponseBody ModelAndView findById(@RequestParam(value = "bannerId",required = false,defaultValue = "0") String bannerId) {
		YskBannerEntity entity = bannerServiceImpl.findById(Integer.valueOf(bannerId));
		ModelAndView modelAndView = new ModelAndView("admin/banner/edit");
		modelAndView.addObject("ads", entity);
		return modelAndView;
	} 
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/update",method=RequestMethod.POST)
	@ResponseBody
	public Result updateAds(YskBannerEntity ads) {
		if(ads.getbType()==null||"".equals(ads.getbType())) {
			return new Result(0, "请选择广告位置", "/Admin/Banner/index", "");
		}
		ads.setStatus(1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = DateUtils.getTime(dateFormat.format(date), dateFormat);
        ads.setCreateTime(Integer.valueOf(time));
        if(ads.getbOrder()==null||"".equals(ads.getbOrder())) {
        	ads.setbOrder(0);
        }
        
		YskBannerEntity entity = bannerServiceImpl.updateAds(ads);
		if (entity!=null) {
			return new Result(1, "保存成功", "/Admin/Banner/index", "");
		}
		return new Result(0, "保存失败", "/Admin/Banner/index", "");
	}
}
