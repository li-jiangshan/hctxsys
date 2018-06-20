package com.hctxsys.controller.adminmall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskGoodImgEntity;
import com.hctxsys.service.YskGoodImgSerivceImpl;



@Controller
@RequestMapping("/goodImg")
public class YskGoodImgControlle {
	
	@Autowired  
    private YskGoodImgSerivceImpl yskGoodImgSerivceImpl;
	
	@RequestMapping("/page") //url，业务结构/一级模块/二级模块
	public String page() {
		return "admin/uploadify/upload"; //返回页面
	}
	
	/**
	 * 通过id查图片
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/imgById")
	public ModelAndView getImgById(int id) {
		YskGoodImgEntity img = yskGoodImgSerivceImpl.getImgById(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("goodImg",img);
		return modelAndView;
	}
	
	/**
	 * 通过商品id查图片list
	 * @param goodId
	 * @return
	 */
	@GetMapping(path = "/imgByGoodId/{goodId}")
	@ResponseBody
	public List<YskGoodImgEntity> getImgByGoodId(@PathVariable String goodId) {
		List<YskGoodImgEntity> list = yskGoodImgSerivceImpl.getImgByGoodId(Integer.valueOf(goodId));
		//ModelAndView modelAndView = new ModelAndView();
		//modelAndView.addObject("imgstr",imgstr);
		return list;
	}
	
	/**
	 * 插入图片
	 * @param goodImgEntity
	 * @return
	 */
	@RequestMapping("/insertImg/{id}/{goodId}/{imgUrl}")
	public ModelAndView insertImg(@PathVariable String id,
								  @PathVariable String goodId,
								  @PathVariable String imgUrl) {
		YskGoodImgEntity img = new YskGoodImgEntity();
		img.setId(Integer.valueOf(id));
		img.setGoodId(Integer.valueOf(goodId));
		img.setImgUrl(imgUrl);
		YskGoodImgEntity goodImg = yskGoodImgSerivceImpl.insertImg(img);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("img",goodImg);
		return modelAndView;
	}
	
	/**
	 * 更新图片
	 * @param goodImgEntity
	 * @return
	 */
	@RequestMapping("/updateImg/{id}/{goodId}/{imgUrl}")
	public ModelAndView updateImg(@PathVariable String id,
			  					  @PathVariable String goodId,
			  					  @PathVariable String imgUrl) {
		YskGoodImgEntity img = new YskGoodImgEntity();
		img.setId(Integer.valueOf(id));
		img.setGoodId(Integer.valueOf(goodId));
		img.setImgUrl(imgUrl);
		YskGoodImgEntity goodImg = yskGoodImgSerivceImpl.updateImg(img);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("img",goodImg);
		return modelAndView;
	}
	
	/**
	 * 删除图片
	 * @param goodImgEntity
	 * @return
	 */
	@GetMapping("/delImg/{id}/{goodId}")
	@ResponseBody
	public List<YskGoodImgEntity> deleteImg(@PathVariable String id,@PathVariable String goodId) {
		yskGoodImgSerivceImpl.deleteImg(Integer.valueOf(id));
		List<YskGoodImgEntity> list = yskGoodImgSerivceImpl.getImgByGoodId(Integer.valueOf(goodId));
		return list;
	}
	
	/**
	 * 删除商品所有图片
	 * @param goodId
	 * @return
	 */
	@GetMapping("/delImgAll/{goodId}")
	public void deleteImgMany(int goodId) {
		yskGoodImgSerivceImpl.deleteImgMany(goodId);
	}
}
