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

import com.hctxsys.entity.YskFinanceEntity;
import com.hctxsys.entity.YskFinanceTypeEntity;
import com.hctxsys.entity.YskHouseEntity;
import com.hctxsys.entity.YskHouseTypeEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.service.FinanceServiceImpl;
import com.hctxsys.util.Result;

@Controller
public class FinanceController {
	@Autowired
	private FinanceServiceImpl financeServiceImpl;
	
	@RequestMapping("/Adminmall/Module/financeindex")
	public String findFinance(Model model,@RequestParam(defaultValue="0")Integer page,String contactsName,Integer financeType) {
		Page<YskFinanceEntity> selectFinance = financeServiceImpl.selectFinance(page, 10, contactsName, financeType);
		int rows = (int) selectFinance.getTotalElements();
		if (rows != 0) {
			model.addAttribute("rows", rows);// 总记录数
		}
		List<YskFinanceTypeEntity> typeList = financeServiceImpl.financeTypeList();
		model.addAttribute("typeList", typeList);
		model.addAttribute("financelist", selectFinance.getContent());
		model.addAttribute("pageCount", selectFinance.getTotalPages());// 总页数
		model.addAttribute("page", page);// 当前页
		model.addAttribute("contactsName", contactsName);
		model.addAttribute("financeType", financeType);
		return "/adminmall/module/financeindex";
	}
	
	
	
	/**跳转添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/addfinance")
	public String house(Model model) {
		List<YskFinanceTypeEntity> list = financeServiceImpl.financeTypeList();
		model.addAttribute("financetype", list);
		return "/adminmall/module/addfinance";
	}
	
	
	/**添加
	 * @param finance
	 * @param imgUrl
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/savefinance")
	@ResponseBody
	public Result saveHouse(YskFinanceEntity finance,@RequestParam(name="imgUrl[]",required = false)String[] imgUrl) {
		Result result = financeServiceImpl.saveFinance(finance, imgUrl);
		return result;
	}
	
	/**编辑页面跳转
	 * @param model
	 * @param moduleId
	 * @return
	 */
	@RequestMapping("/Adminmall/Module/editfinance/{moduleId}")
	public String editfinance(Model model, @PathVariable Integer moduleId) {
		YskFinanceEntity finance = financeServiceImpl.findFinance(moduleId);
		List<YskFinanceTypeEntity> typelist = financeServiceImpl.financeTypeList();
		model.addAttribute("finance", finance);
		model.addAttribute("typelist", typelist);
		return "/adminmall/module/editfinance";
	}
	
	
	@RequestMapping("/Adminmall/Module/financeImgs/{moduleId}")
	@ResponseBody
	public List<YskModuleImgEntity> getImgByModuleId(@PathVariable Integer moduleId){
		List<YskModuleImgEntity> list = financeServiceImpl.findModuleImg(moduleId);
		return list;
	}
	
	
	/**编辑跟新
	 */
	@RequestMapping("/Adminmall/Module/editfinance")
	@ResponseBody
	public Result updateHouse(YskFinanceEntity finance,@RequestParam(name="imgUrl[]",required = false)String[] imgUrl){
		financeServiceImpl.deleteModuleImg(finance.getModuleId());
		Result result = financeServiceImpl.saveFinance(finance, imgUrl);
		return result;
	}
	
	@RequestMapping("/Adminmall/Module/deletefinance/{id}")
	@ResponseBody
	public Result deleteFinance(@PathVariable Integer id) {
		financeServiceImpl.deleteModuleImg(id);
		Result result = financeServiceImpl.deleteFinance(id);
		return result;
	}
}
