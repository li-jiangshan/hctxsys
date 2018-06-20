package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodBrandEntity;
import com.hctxsys.repository.YskGoodBrandRepository;
import com.hctxsys.util.TableResult;


@Service("yskGoodBrandSerivce")
public class YskGoodBrandSerivceImpl {
	@Autowired
	private YskGoodBrandSerivceImpl yskGoodBrandSerivceImpl;

	@Autowired 
	private YskGoodBrandRepository yskGoodBrandRepository;
	
	/**
	 * 查询品牌list   
	 * @param parentId    brandOrder降序    ststue=1
	 * @return
	 */
	public List<YskGoodBrandEntity> getBrandList(){
		List<YskGoodBrandEntity> list = yskGoodBrandRepository.findByStatus();
		return list;
	}
	
	/**
	 * 查询品牌list    select list
	 * @param id
	 * @return
	 */
	public List<?> getBrList(int id){
		List resultList = new ArrayList<>();
		List<YskGoodBrandEntity> list = yskGoodBrandSerivceImpl.getBrandList();
		for (YskGoodBrandEntity brand : list) {
			if (brand.getId()==id) {
				resultList.add("<option selected='true' value='"+brand.getId()+"'>"+brand.getBrandName()+"</option>");
			}else {
				resultList.add("<option value='"+brand.getId()+"'>"+brand.getBrandName()+"</option>");
			}
		}
		return resultList;
	}
	
	/**
	 * 查询品牌list   只排序
	 * @return
	 */
	public TableResult findByOrderPage(TableResult result) {
		//获取分页对象
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskGoodBrandEntity> page = yskGoodBrandRepository.findByOrderPage(request);
		
		TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(page.getTotalElements());//设置总记录数
        tableResult.setRows(page.getContent());
        tableResult.setPageCount(Long.valueOf(page.getTotalPages()));//设置总页数
        return tableResult;
	}
	
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	public TableResult findKeyword(TableResult result){
		String keyword = result.getTypeText();
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskGoodBrandEntity> page = yskGoodBrandRepository.findByName(keyword,request);
		
		TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(page.getTotalElements());//设置总记录数
        tableResult.setRows(page.getContent());
        tableResult.setPageCount(Long.valueOf(page.getTotalPages()));//设置总页数
        return tableResult;
	}
	
	/**
	 * 查询单个品牌
	 * @param id
	 * @return
	 */
	public YskGoodBrandEntity findById(int id) {
		YskGoodBrandEntity entity = yskGoodBrandRepository.findById(id);
		return entity;
	}
	
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@Transactional
	public YskGoodBrandEntity updateBrand(YskGoodBrandEntity brand) {
		YskGoodBrandEntity entity = null;
		try {
			entity = yskGoodBrandRepository.saveAndFlush(brand);
		} catch (Exception e) {
			
		}
		return entity;
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public int deleteBrand(int id) {
		try {
			yskGoodBrandRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
