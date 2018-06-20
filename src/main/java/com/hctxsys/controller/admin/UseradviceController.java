package com.hctxsys.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskUserAdviceEntity;
import com.hctxsys.service.UseradviceServiceImpl;
import com.hctxsys.util.Result;

@Controller
public class UseradviceController {
	@Autowired
	private UseradviceServiceImpl useradviceServiceImpl;
	
	/**查找未读和已读
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/Admin/Useradvice/index")
	public String findAdviceNotEqual(Model model,@RequestParam(defaultValue="0") Integer page,String keyword) {
		Page<YskUserAdviceEntity> findAdvice = useradviceServiceImpl.findAdviceNotEqual(page, 10, (byte)2,keyword);
		int rows =(int)findAdvice.getTotalElements();
		if(rows!=0) {
			model.addAttribute("rows", rows);//总记录数
		}
		model.addAttribute("adviceList", findAdvice.getContent());
		model.addAttribute("pageCount", findAdvice.getTotalPages());//总页数
		model.addAttribute("page",page);//当前页
		model.addAttribute("status", 0);
		model.addAttribute("keyword", keyword);
		return "/admin/useradvice/index";
	}
	
	/**查找已回复的
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/Admin/Useradvice/index/type/over")
	public String findAdviceEqual(Model model,@RequestParam(defaultValue="0") Integer page,String keyword) {
		Page<YskUserAdviceEntity> findAdvice = useradviceServiceImpl.findAdviceEqual(page, 10, (byte)2,keyword);
		int rows =(int)findAdvice.getTotalElements();
		if(rows!=0) {
			model.addAttribute("rows", rows);//总记录数
		}
		model.addAttribute("adviceList", findAdvice.getContent());
		model.addAttribute("pageCount", findAdvice.getTotalPages());//总页数
		model.addAttribute("page",page);//当前页
		model.addAttribute("status", 2);
		model.addAttribute("keyword", keyword);
		return "/admin/useradvice/index";
	}
	
	/**查看详情
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/useradvice/edit/id/{id}")
	public String findById(Model model,@PathVariable Integer id) {
		YskUserAdviceEntity findById = useradviceServiceImpl.findById(id);
		model.addAttribute("advice", findById);
		return "/admin/useradvice/edit";
	}
	
	
	/**删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/admin/sellerapply/delete/id/{id}")
	@ResponseBody
	public Result deleteById(@PathVariable Integer id) {
		Result result = useradviceServiceImpl.deleteById(id);
		return result;
	}
	
	/**保存回复
	 * @param uid
	 * @param reply
	 * @return
	 */
	@RequestMapping(value="/admin/useradvice/savemessage")
	@ResponseBody
	public Result saveByID(Integer id,String reply) {
		Result result = useradviceServiceImpl.saveById(id, reply);
		return result;
	}
}
