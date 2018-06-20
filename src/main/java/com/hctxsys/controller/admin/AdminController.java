package com.hctxsys.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskAdminEntity;
import com.hctxsys.entity.YskGroupEntity;
import com.hctxsys.service.AdminServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.AdminVo;

@Controller
public class AdminController {

	@Autowired
	private AdminServiceImpl adminService;

	/**
	 * 管理员管理首页管理员查询
	 * 
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/Admin/Manage/index", method = RequestMethod.GET)
	public ModelAndView selectAdmin(TableResult result) {
		TableResult tableResult = adminService.selectAdmin(result);
		ModelAndView view = new ModelAndView("admin/manage/index");
		view.addObject("tableResult", tableResult);
		return view;
	}

	/**
	 * 跳转新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/Admin/Manage/edit")
	public String addedit(Model model) {

		List<YskGroupEntity> grouplist = adminService.selectGroup();

		model.addAttribute("grouplist", grouplist);

		return "admin/manage/edit";
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/Admin/Manage/edit/id/{id}")
	public String updateedit(Model model, @PathVariable Integer id) {
		List<YskGroupEntity> grouplist = adminService.selectGroup();
		model.addAttribute("grouplist", grouplist);

		YskAdminEntity yskadminentity = adminService.findById(id);
		model.addAttribute("yskadminentity", yskadminentity);

		return "admin/manage/edit";
	}

	/**
	 * 点击确定修改管理员信息
	 * 
	 * @param groupvo
	 * @return
	 */
	@RequestMapping(path = "/Admin/Manage/editupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result updateAdmin(AdminVo adminvo) {
		Result result = null;
		if (adminvo.getId() != 0) {
			result = adminService.updateAdmin(adminvo);
		} else {
			result = adminService.saveAdmin(adminvo);
		}
		return result;
	}
	
	/**
	 * 点击启用修改状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("/Admin/Manage/enable/id/{id}")
	@ResponseBody
	public Result updateAdminStatusEnable(@RequestParam(defaultValue = "1") byte status, @PathVariable Integer id) {
		Result result = adminService.updateAdminStatusEnable(status, id);
		return result;
	}
	
	/**
	 * 点击禁用修改状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("/Admin/Manage/disable/id/{id}")
	@ResponseBody
	public Result updateAdminStatusDisable(@RequestParam(defaultValue = "0") byte status, @PathVariable Integer id) {
		Result result = adminService.updateAdminStatusDisable(status, id);
		return result;
	}

	/**
	 * 点击回收删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/Admin/Manage/delete/id/{id}")
	@ResponseBody
	public Result deleteAdmin(@PathVariable Integer id) {
		int i = adminService.deleteAdmin(id);
		if (i == 1) {
			return new Result(1, "删除成功", "/Admin/Manage/index", "");
		}
		return new Result(0, "删除失败", "/Admin/Manage/index", "");
	}

	/**
	 * 点击启用批量修改状态
	 * 
	 * @param status
	 * @param ids
	 * @return
	 */
	@RequestMapping("/Admin/Manage/enableList")
	@ResponseBody
	public Result updateAdminStatusEnableList(@RequestParam(defaultValue = "1") byte status, Integer[] ids) {
		Result result = adminService.updateAdminStatusEnableList(status, ids);
		return result;
	}

	/**
	 * 点击禁用批量修改状态
	 * 
	 * @param status
	 * @param ids
	 * @return
	 */
	@RequestMapping("/Admin/Manage/disableList")
	@ResponseBody
	public Result updateAdminStatusDisableList(@RequestParam(defaultValue = "0") byte status, Integer[] ids) {
		Result result = adminService.updateAdminStatusDisableList(status, ids);
		return result;
	}

	/**
	 * 点击删除批量删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/Admin/Manage/deleteList")
	@ResponseBody
	public Result deleteAdminList(Integer[] ids) {
		int i = adminService.deleteAdminList(ids);
		if (i == 1) {
			return new Result(1, "删除成功", "/Admin/Manage/index", "");
		} else if (i == 2) {
			return new Result(0, "请选择要操作的数据", "/Admin/Manage/index", "");
		}
		return new Result(0, "删除失败", "/Admin/Manage/index", "");
	}

}
