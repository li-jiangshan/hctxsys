package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.repository.YskGoodCategoryRepository;


@Service("yskGoodCategorySerivce")
public class YskGoodCategorySerivceImpl {
	@Autowired
	YskGoodCategorySerivceImpl yskGoodCategorySerivceImpl;

	@Autowired 
	private YskGoodCategoryRepository yskGoodCategoryRepository;
	
	/**
	 * 通过父id查分类列表     isShow=1
	 * @param parentId
	 * @return
	 */
	public List<YskGoodCategoryEntity> getCategoryList(short pid){
		List<YskGoodCategoryEntity> clist = yskGoodCategoryRepository.findByPidAndIsShow(pid);
		return clist;
	}
	
	/**
	 * 分类列表   3级
	 * @return
	 */
	public List<?> getCatList(int id) {
		//列表集合
		List<String> resultList = new ArrayList<>();
		//查询分类目录  返回list
		List<YskGoodCategoryEntity> list1 = yskGoodCategorySerivceImpl.getCategoryList((short)0);
		for (YskGoodCategoryEntity clist1 : list1) {
			if (clist1.getId()==id) {
				resultList.add("<option selected='true' value='"+clist1.getId()+"'>"+clist1.getName()+"</option>");
			}else {
				resultList.add("<option value='"+clist1.getId()+"'>"+clist1.getName()+"</option>");
			}
			List<YskGoodCategoryEntity> list2 = yskGoodCategorySerivceImpl.getCategoryList(clist1.getId());
			for (YskGoodCategoryEntity clist2 : list2) {
				if (clist2.getId()==id) {
					resultList.add("<option selected='true' value='"+clist2.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist2.getName()+"</option>");
				}else {
					resultList.add("<option value='"+clist2.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist2.getName()+"</option>");
				}
				List<YskGoodCategoryEntity> list3 = yskGoodCategorySerivceImpl.getCategoryList(clist2.getId());
				for (YskGoodCategoryEntity clist3 : list3) {
					if (clist3.getId()==id) {
						resultList.add("<option selected='true' value='"+clist3.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist3.getName()+"</option>");
					}else {
						resultList.add("<option value='"+clist3.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist3.getName()+"</option>");
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 分类列表   2级
	 * @param id
	 * @return
	 */
	public List<?> getCatList2(short id) {
		short pid =0;
		if (id!=0) {
			YskGoodCategoryEntity findById = yskGoodCategoryRepository.findById(id);
			pid = findById.getPid();
		}
		//列表集合
		List<String> resultList = new ArrayList<>();
		//查询分类目录  返回list
		List<YskGoodCategoryEntity> list1 = yskGoodCategorySerivceImpl.getCategoryList((short)0);
		for (YskGoodCategoryEntity clist1 : list1) {
			if (clist1.getId()==pid&&clist1.getLevel()==1) {
				resultList.add("<option selected='true' value='"+clist1.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist1.getName()+"</option>");
			}else {
				resultList.add("<option value='"+clist1.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist1.getName()+"</option>");
			}
			List<YskGoodCategoryEntity> list2 = yskGoodCategorySerivceImpl.getCategoryList(clist1.getId());
			for (YskGoodCategoryEntity clist2 : list2) {
				if (clist2.getId()==pid&&clist2.getLevel()==2) {
					resultList.add("<option selected='true' value='"+clist2.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist2.getName()+"</option>");
				}else {
					resultList.add("<option value='"+clist2.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+clist2.getName()+"</option>");
				}
			}
		}
		return resultList;
	}
	/**
	 * 分类树
	 * @param pid
	 * @return
	 */
	public StringBuffer getGoodTypeTree(){
		List<YskGoodCategoryEntity> list = yskGoodCategoryRepository.findByPid((short) 0);
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		for (YskGoodCategoryEntity cat: list) {
			if (cat.getLevel()==1) {
				sb.append("<li style=\"display:none\">");
				sb.append("<span>");
				sb.append("<i class=\"icon-plus-sign blue fa-plus\"></i>"+cat.getName()+"</span>");
				sb.append("<div class=\"right-cs\">");
				sb.append("<span>排序：<input data=\""+cat.getId()+"\" class=\"inputtxt\" type=\"text\" value=\""+cat.getSortOrder()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\"></span>");
				if(cat.getIsShow()==1){
					sb.append("<span><a data=\""+cat.getId()+"\" val=\""+cat.getIsShow()+"\" class=\"label label-success-outline label-pill show\" href=\"#\">显示</a></span>");
				}else {
					sb.append("<span><a data=\""+cat.getId()+"\" val=\""+cat.getIsShow()+"\" class=\"label label-warning-outline label-pill show\" href=\"#\">隐藏</a></span>");
				}	
				sb.append("<a class=\"label label-primary-outline label-pill\" href=\"/Adminmall/Goodtype/edit?id="+cat.getId()+"\">编辑</a>");	
				//sb.append("<a  name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Goodtype/deletecat/"+cat.getId()+"\" onclick=\"return confirmAct();\">删除</a>&nbsp;");	 
				//<a name="delete" title="删除" class="label label-danger-outline label-pill ajax-get confirm" href="/adminmall/goodtype/delete/id/863.html">删除</a>
				sb.append("<a name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Adminmall/Goodtype/deletecat/"+cat.getId()+"\">删除</a>&nbsp;");	 
				sb.append("</div>");
				sb.append("<ul>");
				List<YskGoodCategoryEntity> list2 = yskGoodCategoryRepository.findByPid(cat.getId());
				for (YskGoodCategoryEntity cat2 : list2) {
					if (cat2.getLevel()==2) {
						sb.append("<li style=\"display:none\">");
						sb.append("<span>");
						sb.append("<i class=\"icon-plus-sign blue fa-plus\"></i>"+cat2.getName()+"</span>");
						sb.append("<div class=\"right-cs\">");
						sb.append("<span>排序：<input data=\""+cat2.getId()+"\" class=\"inputtxt\" type=\"text\" value=\""+cat2.getSortOrder()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\"></span>");
						if(cat2.getIsShow()==1){
							sb.append("<span><a data=\""+cat2.getId()+"\" val=\""+cat2.getIsShow()+"\" class=\"label label-success-outline label-pill show\" href=\"#\">显示</a></span>");
						}else {
							sb.append("<span><a data=\""+cat2.getId()+"\" val=\""+cat2.getIsShow()+"\" class=\"label label-warning-outline label-pill show\" href=\"#\">隐藏</a></span>");
						}	
						sb.append("<a class=\"label label-primary-outline label-pill\" href=\"/Adminmall/Goodtype/edit?id="+cat2.getId()+"\">编辑</a>");	
						sb.append("<a  name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Adminmall/Goodtype/deletecat/"+cat2.getId()+"\">删除</a>&nbsp;");	 
						sb.append("</div>");
						sb.append("<ul>");
						List<YskGoodCategoryEntity> list3 = yskGoodCategoryRepository.findByPid(cat2.getId());
						for (YskGoodCategoryEntity cat3 : list3) {
							if (cat3.getLevel()==3) {
								sb.append("<li style=\"display:none\">");
								sb.append("<span>");
								sb.append("<i class=\"icon-plus-sign blue fa-plus\"></i>"+cat3.getName()+"</span>");
								sb.append("<div class=\"right-cs\">");
								sb.append("<span>排序：<input data=\""+cat3.getId()+"\" class=\"inputtxt\" type=\"text\" value=\""+cat3.getSortOrder()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\"></span>");
								if(cat3.getIsShow()==1){
									sb.append("<span><a data=\""+cat3.getId()+"\" val=\""+cat3.getIsShow()+"\" class=\"label label-success-outline label-pill show\" href=\"#\">显示</a></span>");
								}else {
									sb.append("<span><a data=\""+cat3.getId()+"\" val=\""+cat3.getIsShow()+"\" class=\"label label-warning-outline label-pill show\" href=\"#\">隐藏</a></span>");
								}	
								sb.append("<a class=\"label label-primary-outline label-pill\" href=\"/Adminmall/Goodtype/edit?id="+cat3.getId()+"\">编辑</a>");	
								sb.append("<a  name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Adminmall/Goodtype/deletecat/"+cat3.getId()+"\">删除</a>&nbsp;");	 
								sb.append("</div>");
								sb.append("</li>");
							}
						}
						sb.append("</ul>");
						sb.append("</li>");
					}
				}
				sb.append("</ul>");
				sb.append("</li>");
			}
		}
		sb.append("</ul>");
		return sb;
	}
	
	/**
	 * 修改排序
	 * @param id
	 * @param order
	 * @return
	 */
	@Transactional
	public YskGoodCategoryEntity chengeOrder(Short id,int order) {
		YskGoodCategoryEntity cat = yskGoodCategoryRepository.findById(id);
		cat.setSortOrder(order);
		YskGoodCategoryEntity newcat = null;
		try {
			newcat = yskGoodCategoryRepository.saveAndFlush(cat);
		} catch (Exception e) {
			
		}
		return newcat;
	}
	
	/**
	 * 隐藏 ，显示
	 * @param id
	 * @param isShow
	 * @return
	 */
	@Transactional
	public YskGoodCategoryEntity showhide(Short id) {
		YskGoodCategoryEntity cat = yskGoodCategoryRepository.findById(id);
		byte isShow = cat.getIsShow();
		if (isShow==1) {
			cat.setIsShow((byte) 0);
		}else {
			cat.setIsShow((byte) 1);
		}
		YskGoodCategoryEntity newcat = null;
		try {
			newcat = yskGoodCategoryRepository.saveAndFlush(cat);
		} catch (Exception e) {
			
		}
		
		return newcat;
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@Transactional
	public int deleteCat(short id) {
		try {
			yskGoodCategoryRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 查询某个分类信息
	 * @param id
	 * @return
	 */
	public YskGoodCategoryEntity findone(short id) {
		YskGoodCategoryEntity cat = yskGoodCategoryRepository.findById(id);
		return cat;
	}
	
	/**
	 * 修改
	 * @param cat
	 * @return
	 */
	@Transactional
	public YskGoodCategoryEntity updateCat(YskGoodCategoryEntity cat) {
		YskGoodCategoryEntity newcat = null;
		try {
			newcat = yskGoodCategoryRepository.saveAndFlush(cat);
		} catch (Exception e) {
			
		}
		return newcat;
	}
}
