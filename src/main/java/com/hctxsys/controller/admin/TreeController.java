package com.hctxsys.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.service.UserSerivceImpl;

@Controller
@RequestMapping("Admin/Tree")
public class TreeController {
	@Autowired
	private UserSerivceImpl userSevice;
	
	@GetMapping("/index")
	@ResponseBody
	public ModelAndView tree(@RequestParam(value = "keyword",required = false,defaultValue = "") String keyword) {
		StringBuffer tree = null;
		if("".equals(keyword)||keyword==null) {
        	tree = userSevice.treeList();
        }else {
			tree = userSevice.treeListkey(keyword);
		}
		ModelAndView modelAndView = new ModelAndView("admin/tree/index");
		modelAndView.addObject("tree",tree);
		modelAndView.addObject("keyword",keyword);
		return modelAndView;
	}
}
