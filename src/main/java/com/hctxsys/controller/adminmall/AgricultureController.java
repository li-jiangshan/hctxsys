package com.hctxsys.controller.adminmall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskAgricultureEntity;
import com.hctxsys.entity.YskAgricultureTypeEntity;
import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.service.AgricultureServiceImpl;
import com.hctxsys.util.Result;

@Controller
public class AgricultureController {
	@Autowired
	private AgricultureServiceImpl agricultureServiceImpl;
	
	
	/**跳转首页
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/agricultureindex")
	public String selectAgriculture(Model model,@RequestParam(defaultValue="0")Integer page,String contactsName,Integer agricultureType) {
		Page<YskAgricultureEntity> findAgriculture = agricultureServiceImpl.findAgriculture(page, 10, contactsName, agricultureType);
		int rows = (int) findAgriculture.getTotalElements();
		if (rows != 0) {
			model.addAttribute("rows", rows);// 总记录数
		}
		List<YskAgricultureTypeEntity> typelist = agricultureServiceImpl.findAgricultureType();
		model.addAttribute("agtypelist", typelist);
		model.addAttribute("aglist", findAgriculture.getContent());
		model.addAttribute("pageCount", findAgriculture.getTotalPages());// 总页数
		model.addAttribute("page", page);// 当前页
		model.addAttribute("contactsName", contactsName);
		model.addAttribute("agType", agricultureType);
		return "/adminmall/module/agricultureindex";
	}
	
	
	/**跳转添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/addagriculture")
	public String addAgriculture(Model model) {
		List<YskAgricultureTypeEntity> typelist = agricultureServiceImpl.findAgricultureType();
		model.addAttribute("agtypelist", typelist);
		return "/adminmall/module/addagriculture";
	}
	
	/**添加农产品
	 * @param agricultureEntity
	 * @param imgUrl
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/saveagriculture")
	@ResponseBody
	public Result saveAgriculture(YskAgricultureEntity agricultureEntity,@RequestParam(name="imgUrl[]",required = false)String[] imgUrl) {
		Result result = agricultureServiceImpl.saveAgriculture(agricultureEntity, imgUrl);
		return result;
	}
	
	
	/**跳转编辑页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/editagricultureId/{id}")
	public String editAgriculture(Model model,@PathVariable Integer id) {
		YskAgricultureEntity agriculture = agricultureServiceImpl.findById(id);
		List<YskAgricultureTypeEntity> typelist = agricultureServiceImpl.findAgricultureType();
		model.addAttribute("agriculture", agriculture);
		model.addAttribute("typelist", typelist);
		return "/adminmall/module/editagriculture";
	}
	
	/**修改保存
	 * @param agriculture
	 * @param imgUrl
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/editagriculture")
	@ResponseBody
	public Result upadte(YskAgricultureEntity agriculture,@RequestParam(name="imgUrl[]",required = false)String[] imgUrl) {
		agricultureServiceImpl.deleteModelImgs(agriculture.getAgricultureId());
		Result result = agricultureServiceImpl.saveAgriculture(agriculture, imgUrl);
		return result;
	}
	
	/**删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/deleteagricultureId/{id}")
	@ResponseBody
	public Result delete(@PathVariable Integer id) {
		agricultureServiceImpl.deleteModelImgs(id);
		Result result = agricultureServiceImpl.deleteAgriculture(id);
		return result;
	} 
	
	
	

	@RequestMapping("/Adminmall/Module/agImgs/{moduleId}")
	@ResponseBody
	public List<YskModuleImgEntity> findModuleImg(@PathVariable Integer moduleId){
		List<YskModuleImgEntity> list = agricultureServiceImpl.findModuleImg(moduleId);
		return list;
	}
}
