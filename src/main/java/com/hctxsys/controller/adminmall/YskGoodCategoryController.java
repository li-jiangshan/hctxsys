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

import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.repository.YskGoodCategoryRepository;
import com.hctxsys.service.YskGoodCategorySerivceImpl;
import com.hctxsys.util.Result;

@Controller
@RequestMapping("Adminmall/Goodtype")
public class YskGoodCategoryController {
	
	@Autowired  
    private YskGoodCategorySerivceImpl yskGoodCategorySerivceImpl;
	
	@Autowired 
	private YskGoodCategoryRepository yskGoodCategoryRepository;
	
	/**
	 * 分类list   3级
	 * @return
	 */
	@GetMapping(path = "/all/{id}")
	public @ResponseBody List<?> getCatList(@PathVariable String id) {
		List<?> list = yskGoodCategorySerivceImpl.getCatList(Integer.valueOf(id));
		return list;
	}
	
	/**
	 * 分类list   2级
	 * @return
	 */
	@GetMapping(path = "/all2/{id}")
	public @ResponseBody List<?> getCatList2(@PathVariable String id) {
		List<?> list = yskGoodCategorySerivceImpl.getCatList2(Short.valueOf(id));
		return list;
	}
	/**
	 * 分类树
	 * @param pid
	 * @return
	 */
	@RequestMapping(path = "/index",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getGoodTypeTree() {
		StringBuffer treestr = yskGoodCategorySerivceImpl.getGoodTypeTree();
		ModelAndView modelAndView = new ModelAndView("adminmall/goodtype/index");
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
		YskGoodCategoryEntity ncat = yskGoodCategorySerivceImpl.chengeOrder(Short.valueOf(id), Integer.valueOf(order));
		if (ncat!=null) {
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
		YskGoodCategoryEntity ncat = yskGoodCategorySerivceImpl.showhide(Short.valueOf(id));
		if (ncat!=null) {
			return new Result(1, "修改成功！","","");
		}
		return new Result(0, "修改失败！","","");
	}
	/**
	 * 删除分类
	 * @param id
	 */
	@GetMapping(path = "/deletecat/{id}")
	@ResponseBody
	public Result deleteCat(@PathVariable String id) {
		
		int i = yskGoodCategorySerivceImpl.deleteCat(Short.valueOf(id));
		
		if (i==1) {
			return new Result(1, "删除成功！","/Adminmall/Goodtype/index","");
		}
		return new Result(0, "删除失败！","/Adminmall/Goodtype/index","");
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	@GetMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id",required = false,defaultValue = "0") String id) {
		YskGoodCategoryEntity cat = yskGoodCategorySerivceImpl.findone(Short.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/goodtype/edit");
		modelAndView.addObject("cat",cat);
		return modelAndView; //返回页面
	}
	
	/**
	 * 新增，修改
	 * @param cat
	 * @return
	 */
	@PostMapping("/update")
	@ResponseBody
	//CommonsMultipartFile
	public Result updateCat(YskGoodCategoryEntity cat) {
		if (cat.getPid()==-1) {
			return new Result(0, "请选择上级", "/Adminmall/Goodtype/index", "");
		}
		if (cat.getName()==null||"".equals(cat.getName())) {
			return new Result(0, "分类名称不能为空", "/Adminmall/Goodtype/index", "");
		}
		if(cat.getSortOrder()==null||"".equals(cat.getSortOrder())) {
			cat.setSortOrder(50);
		}
		if(cat.getCommissionRate()==null||"".equals(cat.getCommissionRate())) {
			cat.setCommissionRate(Byte.valueOf("0"));
		}
		String pidPath = "";
		short pid = cat.getPid();
		if(cat.getId()==0) {
			short id = 0;
			List<YskGoodCategoryEntity> maxId = yskGoodCategoryRepository.findMaxId();
			id = (short) (maxId.get(0).getId()+1);
			if(pid!=0) {//12
				short pid1 = yskGoodCategorySerivceImpl.findone(pid).getPid();//1
				if(pid1!=0) {
					//short pid2 = yskGoodCategorySerivceImpl.findone(pid1).getPid();//0
					pidPath="0-"+pid1+"-"+pid+"-"+id+"-";
					cat.setLevel((byte) 3);
				}
				else {
					pidPath="0-"+pid+"-"+id+"-";
					cat.setLevel((byte) 2);
				}
			}else {
				pidPath="0-"+id+"-";
				cat.setLevel((byte) 1);
			}
			if (cat.getIsHot()==null) {
				cat.setIsHot((byte) 0);
			}
			if (cat.getCommissionRate()==null) {
				cat.setCommissionRate((byte) 0);
			}
			if (cat.getCatGroup()==null) {
				cat.setCatGroup((byte) 0);
			}
		}else {
			YskGoodCategoryEntity entity = yskGoodCategoryRepository.findById(cat.getId());
			short id = entity.getId();
			if(pid!=0) {//12
				short pid1 = yskGoodCategorySerivceImpl.findone(pid).getPid();//1
				if(pid1!=0) {
					//short pid2 = yskGoodCategorySerivceImpl.findone(pid1).getPid();//0
					pidPath="0-"+pid1+"-"+pid+"-"+id+"-";
					cat.setLevel((byte) 3);
				}
				else {
					pidPath="0-"+pid+"-"+id+"-";
					cat.setLevel((byte) 2);
				}
			}else {
				pidPath="0-"+id+"-";
				cat.setLevel((byte) 1);
			}
			if (cat.getIsHot()==null) {
				cat.setIsHot(entity.getIsHot());
			}
			if (cat.getCommissionRate()==null) {
				cat.setCommissionRate(entity.getCommissionRate());
			}
			if (cat.getCatGroup()==null) {
				cat.setCatGroup(entity.getCatGroup());
			}
		}
		cat.setIsShow((byte) 1);
		cat.setPidPath(pidPath);
		YskGoodCategoryEntity insertCat = yskGoodCategorySerivceImpl.updateCat(cat);
		if (insertCat!=null) {
			return new Result(1, "保存成功", "/Adminmall/Goodtype/index", "");
		}
		return new Result(0, "保存失败", "/Adminmall/Goodtype/index", "");
	}
}
