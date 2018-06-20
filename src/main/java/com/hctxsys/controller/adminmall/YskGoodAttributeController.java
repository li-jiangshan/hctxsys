package com.hctxsys.controller.adminmall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.hctxsys.entity.YskGoodAttributeEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.repository.YskGoodAttributeRepository;
import com.hctxsys.service.YskGoodAttributeSerivceImpl;
import com.hctxsys.service.YskGoodPriceSerivceImpl;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;



@Controller
@RequestMapping("/goodAttr")
public class YskGoodAttributeController {
	
	@Autowired
	private YskGoodAttributeSerivceImpl yskGoodAttributeSerivceImpl;
	
	@Autowired
	private YskGoodPriceSerivceImpl yskGoodPriceSerivceImpl;
	
	@Autowired 
	private YskGoodAttributeRepository yskGoodAttributeRepository;
	
	/**
	 * 查询模型属性list   
	 * @param modelId=parentId    brandOrder降序
	 * @return
	 */
	@GetMapping(path = "/all/{modelId}")	
	public @ResponseBody List<YskGoodAttributeEntity> getAttrList(@PathVariable String modelId) {
		List<YskGoodAttributeEntity> attrlist = yskGoodAttributeSerivceImpl.getAttributeList(Integer.valueOf(modelId));
		return attrlist;
	}
	/**
	 * 属性table
	 * @param goodId
	 * @param modelId
	 * @return
	 */
	@RequestMapping(path = "/getTable")
	public @ResponseBody ModelAndView getAttrTable(@RequestParam String goodId, @RequestParam String modelId) {
		List<YskGoodPriceEntity> priceList = yskGoodPriceSerivceImpl.getPriceList(Integer.valueOf(goodId));
		Map<String,String> pMap = new HashMap<String,String>();
		for (YskGoodPriceEntity yskGoodPriceEntity : priceList) {
			String[] value = yskGoodPriceEntity.getGoodAttrValue().split(",");
			for (String pString : value) {
				pMap.put(pString, pString);
			}
		}
		List<String> plist = new ArrayList<String>();
		Iterator<Entry<String, String>> iter = pMap.entrySet().iterator();  
		while(iter.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>)iter.next();
			plist.add((String)entry.getValue());
		}
		if(modelId==null||"".equals(modelId)) {
			modelId="0";
		}
		List<YskGoodAttributeEntity> attrlist = yskGoodAttributeSerivceImpl.getAttributeList(Integer.valueOf(modelId));
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (YskGoodAttributeEntity attr : attrlist) {
			String[] str = attr.getAttrValue().split(",");
			Map<String, String> map2 = new HashMap<String, String>();
			for (String string : str) {
				if(plist.indexOf(string)==-1){
					map2.put(string, "btn btn-default");
				}else {
					map2.put(string, "btn btn-success");
				}
			}
			map.put(attr.getId(), map2);
		}
		ModelAndView modelAndView = new ModelAndView("adminmall/good/ajax_spec_select");
		modelAndView.addObject("attrListTable", attrlist);
		modelAndView.addObject("priceListTable", priceList);
		modelAndView.addObject("valueList", map);
		//modelAndView.addObject("pMap", pMap);
		return modelAndView;
	}
	
	/**
	 * 模型属性list
	 * @param id
	 * @return
	 */
	@RequestMapping("/{id}")
	public ModelAndView page(@PathVariable String id,TableResult result) {
		TableResult tableResult = yskGoodAttributeSerivceImpl.getAttributeListpage(Integer.valueOf(id), result);
		ModelAndView modelAndView = new ModelAndView("adminmall/goodmodel/attribute");
		modelAndView.addObject("tableResult",tableResult);
		modelAndView.addObject("mid",id);
		return modelAndView; //返回页面
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam String modelid,@RequestParam(value = "id",required = false,defaultValue = "0") String id) {
		YskGoodAttributeEntity attr = yskGoodAttributeSerivceImpl.findone(Integer.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("adminmall/goodmodel/editattribute");
		modelAndView.addObject("attr",attr);
		modelAndView.addObject("mid",modelid);
		return modelAndView; //返回页面
	}
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result updateAttr(YskGoodAttributeEntity attr) {
		if(attr.getModelId()==null||attr.getModelId()==0) {
			return new Result(0, "请选择所属模型", "", "");
		}
		if(attr.getAttrName()==null||"".equals(attr.getAttrName())) {
			return new Result(0, "请添加规格名称", "", "");
		}
		if(attr.getAttrValue()==null||"".equals(attr.getAttrValue())) {
			return new Result(0, "请添加规格项", "", "");
		}
		if(attr.getAttrOrder()==null||"".equals(attr.getAttrOrder())) {
			attr.setAttrOrder(50);
		}
		if (attr.getId()==0) {
			int id = yskGoodAttributeRepository.findMaxId().get(0).getId()+1;
			attr.setId(id);
		}
		attr.setSearchIndex((byte) 1);
		YskGoodAttributeEntity entity = yskGoodAttributeSerivceImpl.updateAttr(attr);
		if (entity!=null) {
			return new Result(1, "保存成功", "/goodAttr/"+attr.getModelId(), "");
		}
		return new Result(0, "保存失败", "", "");
	}
	
	/**
	 * 修改排序
	 * @param id
	 * @param order
	 * @return
	 */
	@PostMapping(path = "/chengeOrder")
	public @ResponseBody Result chengeOrder(String id,String order){
		YskGoodAttributeEntity attr = yskGoodAttributeSerivceImpl.chengeOrder(Short.valueOf(id), Integer.valueOf(order));
		if (attr!=null) {
			return new Result(1, "修改成功！","/goodAttr/"+id,"");
		}
		return new Result(0, "修改失败！","","");
	}
	
	/**
	 * 删除属性
	 * @param id
	 */
	@RequestMapping(path = "/deleteattr",method=RequestMethod.GET)
	@ResponseBody
	public Result deleteCat(@RequestParam String id) {
		int mid = yskGoodAttributeSerivceImpl.findone(Integer.valueOf(id)).getModelId();
		int i = yskGoodAttributeSerivceImpl.deleteAttr(Integer.valueOf(id));
		if (i==1) {
			return new Result(1, "删除成功，不可恢复", "/goodAttr/"+mid,"");
		}
		return new Result(0, "删除失败", "", "");
	}
}
