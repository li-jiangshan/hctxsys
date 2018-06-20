package com.hctxsys.controller.adminmall;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hctxsys.entity.YskIndustryEntity;
import com.hctxsys.repository.IndustryRepository;
import com.hctxsys.repository.YskGoodCategoryRepository;
import com.hctxsys.service.IndustrySerivceImpl;
import com.hctxsys.service.YskGoodCategorySerivceImpl;
import com.hctxsys.util.Result;

@Controller
@RequestMapping("Adminmall/Industry")
public class IndustryController {
	
	@Autowired  
    private IndustrySerivceImpl industrySerivceImpl;
	
	@Autowired 
	private IndustryRepository industryRepository;
	
	/**
	 * 分类list   2级
	 * @return
	 */
	@GetMapping(path = "/all/{id}")
	public @ResponseBody List<?> getInList2(@PathVariable String id) {
		List<?> list = industrySerivceImpl.getInList(Integer.valueOf(id));
		return list;
	}
	/**
	 * 分类树
	 * @param pid
	 * @return
	 */
	@RequestMapping(path = "/index",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getGoodTypeTree() {
		StringBuffer treestr = industrySerivceImpl.getGoodTypeTree();
		ModelAndView modelAndView = new ModelAndView("adminmall/industry/index");
		modelAndView.addObject("tree",treestr);
		return modelAndView;
	}
	/**
	 * 修改排序
	 * @param id
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/chengeOrder")
	public @ResponseBody Result chengeOrder(String id,String order){
		YskIndustryEntity in = industrySerivceImpl.chengeOrder(Integer.valueOf(id), Integer.valueOf(order));
		if (in!=null) {
			return new Result(1, "修改成功！","","");
		}
		return new Result(0, "修改失败！","","");
	}
	
	/**
	 * 显示，隐藏
	 * @param id
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/showhide")
	public @ResponseBody Result showhide(String id){
		YskIndustryEntity in = industrySerivceImpl.showhide(Integer.valueOf(id));
		if (in!=null) {
			return new Result(1, "修改成功！","","");
		}
		return new Result(0, "修改失败！","","");
	}
	/**
	 * 删除分类
	 * @param id
	 */
	@GetMapping(path = "/deletein/{id}")
	@ResponseBody
	public Result deleteCat(@PathVariable String id) {
		
		int i = industrySerivceImpl.deleteIn(Integer.valueOf(id));
		
		if (i==1) {
			return new Result(1, "删除成功！","/Adminmall/Industry/index","");
		}
		return new Result(0, "删除失败！","/Adminmall/Industry/index","");
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id",required = false,defaultValue = "0") String id) {
		YskIndustryEntity in = industrySerivceImpl.findone(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/industry/edit");
		modelAndView.addObject("ads",in);
		return modelAndView; //返回页面
	}
	
	/**
	 * 新增，修改
	 * @param cat
	 * @return
	 */
	@PostMapping("/update")
	@ResponseBody
	public Result updateIn(YskIndustryEntity in) {
		if (in.getPid()==-1) {
			return new Result(0, "请选择上级", "/Adminmall/Industry/index", "");
		}
		if (in.getName()==null||"".equals(in.getName())) {
			return new Result(0, "行业名称不能为空", "/Adminmall/Industry/index", "");
		}
		if (in.getSortOrder()==null||"".equals(in.getSortOrder())) {
			in.setSortOrder(0);
		}
		String path = "";
		int pid = in.getPid();
		if(in.getId()==0) {
			int id = 0;
			List<YskIndustryEntity> maxId = industryRepository.findMaxId();
			id = maxId.get(0).getId()+1;
			if(pid!=0) {
				//int pid1 = industrySerivceImpl.findone(pid).getPid();
				path="0,"+pid+","+id+",";
				in.setLevel((byte) 2);
			}else {
				path="0,"+id+",";
				in.setLevel((byte) 1);
			}
			
		}else {
			YskIndustryEntity entity = industryRepository.findById(in.getId());
			int id = entity.getId();
			if(pid!=0) {
				//int pid1 = industrySerivceImpl.findone(pid).getPid();
				path="0,"+pid+","+id+",";
				in.setLevel((byte) 2);
			}else {
				path="0,"+id+",";
				in.setLevel((byte) 1);
			}
		}
		in.setIsShow((byte) 1);
		in.setPath(path);
		in.setCategoryId("0");
		YskIndustryEntity insertIn = industrySerivceImpl.updateIn(in);
		if (insertIn!=null) {
			return new Result(1, "保存成功", "/Adminmall/Industry/index", "");
		}
		return new Result(0, "保存失败", "/Adminmall/Industry/index", "");
	}
}
