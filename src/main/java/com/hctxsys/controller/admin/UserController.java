package com.hctxsys.controller.admin;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hctxsys.entity.YskUpdateUserinfoEntity;
import com.hctxsys.service.OrderServiceImpl;
import com.hctxsys.util.EntityManagerUtils;
import com.hctxsys.vo.WorkOrderVo;
import com.hctxsys.vo.api.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskUserLevelEntity;
import com.hctxsys.service.UserLevelServiceImpl;
import com.hctxsys.service.UserSerivceImpl;
import com.hctxsys.service.UserWealthServiceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.UserVo;

@Controller
@RequestMapping("Admin")
@SessionAttributes(value = "uid")
public class UserController {
	@Autowired
	private UserSerivceImpl userSevice;

	@Autowired
	private UserLevelServiceImpl levelService;
	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private UserWealthServiceImpl wealthService;

	@GetMapping("User/detail/{id}")
	public ModelAndView detail(@PathVariable Integer id, TableResult result) {
		TableResult tableResult = orderService.userOrder(id, result);
		UserVo byId = userSevice.getById(String.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("admin/user/detail");
		modelAndView.addObject("tableResult", tableResult);
		modelAndView.addObject("user", byId);
		return modelAndView;
	}

	/**
	 * 会员首页
	 * 
	 * @return
	 */
	@GetMapping("User/index")
	public ModelAndView index(TableResult result, HttpServletRequest request, HttpSession session) {
		TableResult tableResult = userSevice.indexTable(result);
		List<YskUserLevelEntity> yskUserLevelEntities = levelService.getUserLevel();// 获取用户等级信息集合
		ModelAndView view = new ModelAndView("/admin/user/index");
		view.addObject("tableResult", tableResult);
		view.addObject("levelList", yskUserLevelEntities);
		return view; // 返回页面
	}

	/**
	 * 会员财富首页
	 *
	 * @return
	 */
	@GetMapping("User/userlist") // url，业务结构/一级模块/二级模块
	public ModelAndView userList(TableResult result) {
		TableResult tableResult = userSevice.indexTable(result);
		List<YskUserLevelEntity> yskUserLevelEntities = levelService.getUserLevel();// 获取用户等级信息集合
		ModelAndView view = new ModelAndView("/admin/user/userlist");
		view.addObject("tableResult", tableResult);
		view.addObject("levelList", yskUserLevelEntities);
		return view; // 返回页面
	}

	/**
	 * 锁定与解锁
	 *
	 * @return
	 */
	@PostMapping("User/setstatus/status/forbid/ids/{id}")
	@ResponseBody
	public Result LockUser(@PathVariable String id) {
		return userSevice.lockUser(id);
	}

	/**
	 * 编辑会员
	 * 
	 * @return
	 */
	@GetMapping("User/edit/id/{id}")
	public ModelAndView edit(@PathVariable String id) {
		UserVo userVo = userSevice.editForm(id);
		ModelAndView modelAndView = new ModelAndView("admin/user/edit");
		modelAndView.addObject("userVo", userVo);
		return modelAndView;
	}

	/**
	 * 更新会员信息
	 *
	 * @return
	 */
	@PostMapping("User/edit/update")
	@ResponseBody
	public Result editUpdate(UserVo userVo) {
		Result result = userSevice.editUpdate(userVo);
		return result;
	}

	// /**
	// * 根据用户ID进行登录，目前不知返回数据格式和主页位置
	// * @param id
	// * @return
	// */
	// @GetMapping("/user/userlogin/userid/{id}")
	// public ModelAndView userLogin(@PathVariable String id) {
	// UserVo byId = userSevice.getById(id);
	// ModelAndView modelAndView = new ModelAndView("index");
	// modelAndView.addObject("user",byId);
	// return modelAndView;
	// }

	/**
	 * 根据ID查询代理信息
	 * 
	 * @param id
	 *            用户ID
	 * @return 用户代理信息
	 */
	@GetMapping("User/setarea/id/{id}")
	public ModelAndView setarea(@PathVariable String id) {
		UserVo user = userSevice.getById(id);
		List<YskUserLevelEntity> area = levelService.getArea((byte) 4);// 查询代理商等级
		ModelAndView modelAndView = new ModelAndView("/admin/user/setarea");
		modelAndView.addObject("levelList", area);
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	/**
	 * 更新代理
	 * 
	 * @param userVo
	 *            前台传来用户实体类
	 * @return 更新结果
	 */
	@PostMapping("User/setarea/update")
	@ResponseBody
	public Result updateArea(UserVo userVo) {
		return userSevice.updateArea(userVo);
	}

	/**
	 * 根据用户ID跳转到财富编辑界面
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	@GetMapping("User/wealth/userid/{id}")
	public ModelAndView addFruits(@PathVariable String id) {
		UserVo user = userSevice.getById(id);
		ModelAndView modelAndView = new ModelAndView("/admin/user/addFruits");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	/**
	 * 根际用户ID修改财富数据
	 * 
	 * @param userid
	 *            用户ID
	 * @param type
	 *            增加或减少
	 * @param field
	 *            修改类型
	 * @param num
	 *            修改金额
	 * @return
	 */
	@PostMapping("User/wealth/updateFruits")
	@ResponseBody
	public Result updateFruits(String userid, String type, String field, String num) {
		return userSevice.updateFruits(userid, type, field, num);
	}

	/**
	 * 通过审核的充值记录
	 * 
	 * @param result
	 *            查询条件实体类
	 * @return
	 */
	@GetMapping("Rechargedetail/index")
	public ModelAndView rechargeIndex(TableResult result) {
		TableResult tableResult = userSevice.RechargeIndex(result);
		ModelAndView modelAndView = new ModelAndView("/admin/user/userRecharge");
		modelAndView.addObject("tableResult", tableResult);
		return modelAndView;
	}

	/**
	 * 根际查询条件返回用户审核
	 * 
	 * @param result
	 *            查询条件
	 * @param status
	 *            null代表未审核 2代表已审核 3代表已拒绝
	 * @return
	 */
	@GetMapping(value = { "Shopstatus/index", "Shopstatus/index/type/{status}" })
	public ModelAndView UserStatus(TableResult result, @PathVariable(required = false) String status) {
		TableResult tableResult = userSevice.shopStatusIndex(result, status);
		ModelAndView modelAndView = new ModelAndView("admin/shopstatus/index");
		modelAndView.addObject("tableResult", tableResult);
		modelAndView.addObject("status", status);
		return modelAndView;
	}

	@GetMapping("Shopstatus/detail/uid/{id}")
	public ModelAndView StatusDeatil(@PathVariable String id) {
		UserVo userVo = userSevice.StatusDetail(id);
		ModelAndView modelAndView = new ModelAndView("admin/shopstatus/detail");
		modelAndView.addObject("user", userVo);
		return modelAndView;
	}

	@PostMapping("Shopstatus/detail/update")
	@ResponseBody
	public Result updateStatus(Integer userid, String content, Byte agree) {
		return userSevice.updateStatus(userid, content, agree);
	}

	@GetMapping(value = { "Shopstatus/orderlist", "Shopstatus/orderlist/type/{status}" })
	public ModelAndView orderList(TableResult result, @PathVariable(required = false) String status) {
		TableResult tableResult = userSevice.orderList(result, status);
		ModelAndView modelAndView = new ModelAndView("admin/shopstatus/orderlist");
		modelAndView.addObject("tableResult", tableResult);
		modelAndView.addObject("status", status);
		return modelAndView;
	}

	@GetMapping("Shopstatus/orderdetail/id/{id}")
	public ModelAndView orderDeatil(@PathVariable Integer id) {
		WorkOrderVo workOrderVo = userSevice.orderDetail(id);
		ModelAndView modelAndView = new ModelAndView("admin/shopstatus/orderdetail");
		modelAndView.addObject("info", workOrderVo);
		return modelAndView;
	}

	@PostMapping("Shopstatus/orderdetail/update")
	@ResponseBody
	public Result updateWork(Integer id, Integer agree, String reply, Integer uid) {
		Result result = userSevice.updateWork(id, agree, reply, uid);
		return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 跳转积分详情页面
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@GetMapping("User/integraldetail/{id}")
	public ModelAndView integraldetail(@PathVariable Integer id, TableResult result) {
		TableResult tableResult = userSevice.integraldetail(id, result);
		UserVo byId = userSevice.getById(String.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("admin/user/integraldetail");
		modelAndView.addObject("tableResult", tableResult);
		modelAndView.addObject("user", byId);
		return modelAndView;
	}
	
	/**
	 * 跳转余额详情页面
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@GetMapping("User/moneydetail/{id}")
	public ModelAndView moneydetail(@PathVariable Integer id, TableResult result) {
		TableResult tableResult = userSevice.moneydetail(id, result);
		UserVo byId = userSevice.getById(String.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("admin/user/moneydetail");
		modelAndView.addObject("tableResult", tableResult);
		modelAndView.addObject("user", byId);
		return modelAndView;
	}

}
