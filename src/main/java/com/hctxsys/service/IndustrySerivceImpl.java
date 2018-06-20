package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskIndustryEntity;
import com.hctxsys.repository.IndustryRepository;


@Service("yskIndustrySerivce")
public class IndustrySerivceImpl {
	@Autowired
	IndustrySerivceImpl IndustrySerivceImpl;

	@Autowired 
	private IndustryRepository IndustryRepository;
	
	/**
	 * 通过父id查行业列表     isShow=1
	 * @param pid
	 * @return
	 */
	public List<YskIndustryEntity> getIndustryList(int pid){
		List<YskIndustryEntity> ilist = IndustryRepository.findByPidAndIsShow(pid);
		return ilist;
	}
	
	/**
	 * 分类列表   顶级
	 * @param id
	 * @return
	 */
	public List<?> getInList(int id) {
		int pid =0;
		if (id!=0) {
			YskIndustryEntity findById = IndustryRepository.findById(id);
			pid = findById.getPid();
		}
		//列表集合
		List<Object> resultList = new ArrayList<>();
		//查询分类目录  返回list
		List<YskIndustryEntity> list1 = IndustrySerivceImpl.getIndustryList(0);
		for (YskIndustryEntity ilist1 : list1) {
			if (ilist1.getId()==pid&&ilist1.getLevel()==1) {
				resultList.add("<option selected='true' value='"+ilist1.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ilist1.getName()+"</option>");
			}else {
				resultList.add("<option value='"+ilist1.getId()+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ilist1.getName()+"</option>");
			}
		}
		return resultList;
	}
	/**
	 * -------------------------------没做
	 * 分类树
	 * @param pid
	 * @return
	 */
	public StringBuffer getGoodTypeTree(){
		List<YskIndustryEntity> list = IndustryRepository.findByPid(0);
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		for (YskIndustryEntity in: list) {
			if (in.getLevel()==1) {
				sb.append("<li style=\"display:none\">");
				sb.append("<span>");
				sb.append("<i class=\"icon-plus-sign blue fa-plus\"></i>"+in.getName()+"</span>");
				sb.append("<div class=\"right-cs\">");
				sb.append("<span>排序：<input data=\""+in.getId()+"\" class=\"inputtxt\" type=\"text\" value=\""+in.getSortOrder()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\"></span>");
				if(in.getIsShow()==1){
					sb.append("<span><a data=\""+in.getId()+"\" val=\""+in.getIsShow()+"\" class=\"label label-success-outline label-pill show\" href=\"#\">显示</a></span>");
				}else {
					sb.append("<span><a data=\""+in.getId()+"\" val=\""+in.getIsShow()+"\" class=\"label label-warning-outline label-pill show\" href=\"#\">隐藏</a></span>");
				}	
				sb.append("<a class=\"label label-primary-outline label-pill\" href=\"/Adminmall/Industry/edit?id="+in.getId()+"\">编辑</a>");	
				sb.append("<a name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Adminmall/Industry/deletein/"+in.getId()+"\">删除</a>&nbsp;");	 
				sb.append("</div>");
				sb.append("<ul>");
				List<YskIndustryEntity> list2 = IndustryRepository.findByPid(in.getId());
				for (YskIndustryEntity in2 : list2) {
					if (in2.getLevel()==2) {
						sb.append("<li style=\"display:none\">");
						sb.append("<span>");
						sb.append("<i class=\"icon-plus-sign blue fa-plus\"></i>"+in2.getName()+"</span>");
						sb.append("<div class=\"right-cs\">");
						sb.append("<span>排序：<input data=\""+in2.getId()+"\" class=\"inputtxt\" type=\"text\" value=\""+in2.getSortOrder()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\"></span>");
						if(in2.getIsShow()==1){
							sb.append("<span><a data=\""+in2.getId()+"\" val=\""+in2.getIsShow()+"\" class=\"label label-success-outline label-pill show\" href=\"#\">显示</a></span>");
						}else {
							sb.append("<span><a data=\""+in2.getId()+"\" val=\""+in2.getIsShow()+"\" class=\"label label-warning-outline label-pill show\" href=\"#\">隐藏</a></span>");
						}	
						sb.append("<a class=\"label label-primary-outline label-pill\" href=\"/Adminmall/Industry/edit?id="+in2.getId()+"\">编辑</a>");	
						sb.append("<a  name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Adminmall/Industry/deletein/"+in2.getId()+"\">删除</a>&nbsp;");	 
						sb.append("</div>");
						sb.append("<ul>");
						List<YskIndustryEntity> list3 = IndustryRepository.findByPid(in2.getId());
						for (YskIndustryEntity in3 : list3) {
							if (in3.getLevel()==3) {
								sb.append("<li style=\"display:none\">");
								sb.append("<span>");
								sb.append("<i class=\"icon-plus-sign blue fa-plus\"></i>"+in3.getName()+"</span>");
								sb.append("<div class=\"right-cs\">");
								sb.append("<span>排序：<input data=\""+in3.getId()+"\" class=\"inputtxt\" type=\"text\" value=\""+in3.getSortOrder()+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\"></span>");
								if(in3.getIsShow()==1){
									sb.append("<span><a data=\""+in3.getId()+"\" val=\""+in3.getIsShow()+"\" class=\"label label-success-outline label-pill show\" href=\"#\">显示</a></span>");
								}else {
									sb.append("<span><a data=\""+in3.getId()+"\" val=\""+in3.getIsShow()+"\" class=\"label label-warning-outline label-pill show\" href=\"#\">隐藏</a></span>");
								}	
								sb.append("<a class=\"label label-primary-outline label-pill\" href=\"/Adminmall/Industry/edit?id="+in3.getId()+"\">编辑</a>");	
								sb.append("<a  name=\"delete\" title=\"删除\" class=\"label label-danger-outline label-pill ajax-get confirm\"  href=\"/Adminmall/Industry/deletein/"+in3.getId()+"\">删除</a>&nbsp;");	 
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
	public YskIndustryEntity chengeOrder(int id,int order) {
		YskIndustryEntity in = IndustryRepository.findById(id);
		in.setSortOrder(order);
		YskIndustryEntity newin = IndustryRepository.saveAndFlush(in);
		return newin;
	}
	
	/**
	 * 隐藏 ，显示
	 * @param id
	 * @param isShow
	 * @return
	 */
	@Transactional
	public YskIndustryEntity showhide(int id) {
		YskIndustryEntity in = IndustryRepository.findById(id);
		byte isShow = in.getIsShow();
		if (isShow==1) {
			in.setIsShow((byte) 0);
		}else {
			in.setIsShow((byte) 1);
		}
		YskIndustryEntity newin = IndustryRepository.saveAndFlush(in);
		return newin;
	}
	
	/**
	 * 删除分类
	 * @param id
	 */
	@Transactional
	public int deleteIn(int id) {
		try {
			IndustryRepository.deleteById(id);
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
	public YskIndustryEntity findone(int id) {
		YskIndustryEntity in = IndustryRepository.findById(id);
		return in;
	}
	
	/**=
	 * 添加
	 * @param in
	 */
	@Transactional
	public YskIndustryEntity insertIn(YskIndustryEntity in) {
		YskIndustryEntity newin = IndustryRepository.save(in);
		return newin;
	}
	
	/**
	 * 修改
	 * @param in
	 * @return
	 */
	@Transactional
	public YskIndustryEntity updateIn(YskIndustryEntity in) {
		YskIndustryEntity newin = IndustryRepository.saveAndFlush(in);
		return newin;
	}
}
