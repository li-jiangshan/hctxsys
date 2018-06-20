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

import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskHouseTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.service.HouseServiceImpl;
import com.hctxsys.util.Result;

@Controller
public class HouseController {
	@Autowired
	private HouseServiceImpl houseServiceImpl;
	
	/**跳转首页
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/houseindex")
	public String selectHouse(Model model,@RequestParam(defaultValue="0")Integer page,String houseProvince,String houseCity,String houseDistrict,Integer houseType,String contactsName) {
		Page<YskHouseEntity> selectHouse = houseServiceImpl.selectHouse(page, 10, houseProvince, houseCity, houseDistrict, houseType,contactsName);
		int rows = (int) selectHouse.getTotalElements();
		if (rows != 0) {
			model.addAttribute("rows", rows);// 总记录数
		}
		List<YskHouseTypeEntity> list = houseServiceImpl.houseTypeList();
		model.addAttribute("housetype", list);
		model.addAttribute("houselist", selectHouse.getContent());
		model.addAttribute("pageCount", selectHouse.getTotalPages());// 总页数
		model.addAttribute("page", page);// 当前页
		model.addAttribute("houseProvince", houseProvince);
		model.addAttribute("houseCity", houseCity);
		model.addAttribute("houseDistrict", houseDistrict);
		model.addAttribute("houseType", houseType);
		model.addAttribute("contactsName", contactsName);
		return "/adminmall/module/houseindex";
	}
	
	/**跳转添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/addhouse")
	public String house(Model model) {
		List<YskHouseTypeEntity> list = houseServiceImpl.houseTypeList();
		model.addAttribute("housetype", list);
		return "/adminmall/module/addhouse";
	}
	
	/**保存房屋信息
	 * @param house 房屋实体
	 * @param imgUrl 图片集
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/savehouse")
	@ResponseBody
	public Result saveHouse(YskHouseEntity house,@RequestParam(name="imgUrl[]",required = false)String[] imgUrl) {
		Result result = houseServiceImpl.saveHouse(house, imgUrl);
		return result;
	}
	
	
	/**删除房屋信息
	 * @param houseId
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/deletehouse/{houseId}")
	@ResponseBody
	public Result deleteHouse(@PathVariable Integer houseId) {
		Result result = houseServiceImpl.delete(houseId);
		return result;
	}
	
	/**编辑页跳转
	 * @param model
	 * @param houseId
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/edithouse/{houseId}")
	public String editHouse(Model model,@PathVariable Integer houseId) {
		YskHouseEntity yskHouseEntity = houseServiceImpl.findById(houseId);
		List<YskHouseTypeEntity> list = houseServiceImpl.houseTypeList();
		List<YskModuleImgEntity> imgs = houseServiceImpl.getModelImg(houseId);
		model.addAttribute("imgs",imgs);
		model.addAttribute("housetype", list);
		model.addAttribute("house", yskHouseEntity);
		return "/adminmall/module/edithouse";
	}
	
	
	/**编辑跟新
	 * @param house
	 * @param imgUrl
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/edithouse")
	@ResponseBody
	public Result updateHouse(YskHouseEntity house,@RequestParam(name="imgUrl[]",required = false)String[] imgUrl){
		houseServiceImpl.deleteModelImgs(house.getHouseId());
		Result result = houseServiceImpl.saveHouse(house, imgUrl);
		return result;
	}
	
	
	@RequestMapping("/Adminmall/Module/houseImgs/{moduleId}")
	@ResponseBody
	public List<YskModuleImgEntity> getImgByModuleId(@PathVariable Integer moduleId){
		List<YskModuleImgEntity> list = houseServiceImpl.getModelImg(moduleId);
		return list;
	}
}
