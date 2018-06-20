package com.hctxsys.controller.admin;

import java.util.ArrayList;
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

import com.hctxsys.entity.YskGroupEntity;
import com.hctxsys.entity.YskMenuEntity;
import com.hctxsys.service.GroupServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.GroupVo;

@Controller
public class GroupController {

	@Autowired
	private GroupServiceImpl groupService;

	/**
	 * 角色管理首页查询所有角色信息
	 * 
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/Admin/Group/index", method = RequestMethod.GET)
	public ModelAndView getGroupList(TableResult result) {
		TableResult tableResult = groupService.selectGroup(result);
		ModelAndView view = new ModelAndView("admin/group/index");
		view.addObject("tableResult", tableResult);
		return view;
	}

	/**
	 * 跳转新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/Admin/Group/edit")
	public String addedit(Model model) {

		List<YskMenuEntity> toplist = groupService.getTopMenu();
		List<YskMenuEntity> firstmenulist = groupService.getFirstMenu();
		List<YskMenuEntity> secondmenulist = groupService.getSecondMenu();

		model.addAttribute("toplist", toplist);
		model.addAttribute("firstmenulist", firstmenulist);
		model.addAttribute("secondmenulist", secondmenulist);
		model.addAttribute("menuId", "");

		return "admin/group/edit";
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/Admin/Group/edit/id/{id}")
	public String updateedit(Model model, @PathVariable Integer id) {
		YskGroupEntity yskgroupentity = groupService.findById(id);

		String menuAuth = yskgroupentity.getMenuAuth();

		String[] menuId = menuAuth.split(",");

		List<Integer> menuIdlist = new ArrayList<Integer>();

		for (int i = 0; i < menuId.length; i++) {
			Integer a = null;
			a = Integer.valueOf(menuId[i]);
			menuIdlist.add(a);
		}

		model.addAttribute("menuId", menuIdlist);
		model.addAttribute("yskgroupentity", yskgroupentity);

		List<YskMenuEntity> toplist = groupService.getTopMenu();
		List<YskMenuEntity> firstmenulist = groupService.getFirstMenu();
		List<YskMenuEntity> secondmenulist = groupService.getSecondMenu();

		model.addAttribute("toplist", toplist);
		model.addAttribute("firstmenulist", firstmenulist);
		model.addAttribute("secondmenulist", secondmenulist);

		return "admin/group/edit";
	}

	/**
	 * 点击确定修改角色信息
	 * 
	 * @param groupvo
	 * @return
	 */
	@RequestMapping(path = "/Admin/Group/editupdate", method = RequestMethod.POST)
	@ResponseBody
	public Result updateGroup(GroupVo groupvo) {
		Result result = null;
		if (groupvo.getId() != 0) {
			result = groupService.updateGroup(groupvo);
		} else {
			result = groupService.saveGroup(groupvo);
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
	@RequestMapping("/Admin/Group/enable/id/{id}")
	@ResponseBody
	public Result updateGroupStatusEnable(@RequestParam(defaultValue = "1") byte status, @PathVariable Integer id) {
		Result result = groupService.updateGroupStatusEnable(status, id);
		return result;
	}

	/**
	 * 点击禁用修改状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("/Admin/Group/disable/id/{id}")
	@ResponseBody
	public Result updateGroupStatusDisable(@RequestParam(defaultValue = "0") byte status, @PathVariable Integer id) {
		Result result = groupService.updateGroupStatusDisable(status, id);
		return result;
	}

	/**
	 * 点击回收删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/Admin/Group/delete/id/{id}")
	@ResponseBody
	public Result deleteGroup(@PathVariable Integer id) {
		int i = groupService.deleteGroup(id);
		if (i == 1) {
			return new Result(1, "删除成功", "/Admin/Group/index", "");
		}
		return new Result(0, "删除失败", "/Admin/Group/index", "");
	}

	/**
	 * 点击启用批量修改状态
	 * 
	 * @param status
	 * @param ids
	 * @return
	 */
	@RequestMapping("/Admin/Group/enableList")
	@ResponseBody
	public Result updateGroupStatusEnableList(@RequestParam(defaultValue = "1") byte status, Integer[] ids) {
		Result result = groupService.updateGroupStatusEnableList(status, ids);
		return result;
	}

	/**
	 * 点击禁用批量修改状态
	 * 
	 * @param status
	 * @param ids
	 * @return
	 */
	@RequestMapping("/Admin/Group/disableList")
	@ResponseBody
	public Result updateGroupStatusDisableList(@RequestParam(defaultValue = "0") byte status, Integer[] ids) {
		Result result = groupService.updateGroupStatusDisableList(status, ids);
		return result;
	}

	/**
	 * 点击删除批量删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/Admin/Group/deleteList")
	@ResponseBody
	public Result deleteGroupList(Integer[] ids) {
		int i = groupService.deleteGroupList(ids);
		if (i == 1) {
			return new Result(1, "删除成功", "/Admin/Group/index", "");
		} else if (i == 2) {
			return new Result(0, "请选择要操作的数据", "/Admin/Group/index", "");
		}
		return new Result(0, "删除失败", "/Admin/Group/index", "");
	}

}
