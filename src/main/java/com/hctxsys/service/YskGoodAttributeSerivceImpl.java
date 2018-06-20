package com.hctxsys.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodAttributeEntity;
import com.hctxsys.repository.YskGoodAttributeRepository;
import com.hctxsys.util.TableResult;


@Service("yskGoodAttributeSerivce")
public class YskGoodAttributeSerivceImpl {

	@Autowired 
	private YskGoodAttributeRepository yskGoodAttributeRepository;
	
	/**
	 * 查询模型属性list   
	 * @param parentId    brandOrder降序
	 * @return
	 */
	public TableResult getAttributeListpage(int id,TableResult result){
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskGoodAttributeEntity> page = yskGoodAttributeRepository.findByModelIdpage(id,request);
		TableResult tableResult = new TableResult();
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(page.getTotalElements());//设置总记录数
        tableResult.setRows(page.getContent());
        tableResult.setPageCount(Long.valueOf(page.getTotalPages()));//设置总页数
        return tableResult;
	}
	
	public List<YskGoodAttributeEntity> getAttributeList(int id){
		List<YskGoodAttributeEntity> list = yskGoodAttributeRepository.findByModelId(id);
		return list;
	}
	
	/**
	 * 新增、更新
	 * @param attr
	 * @return
	 */
	@Transactional
	public YskGoodAttributeEntity updateAttr(YskGoodAttributeEntity attr) {
		YskGoodAttributeEntity entity = null;
		try {
			entity = yskGoodAttributeRepository.saveAndFlush(attr);
		} catch (Exception e) {
			
		}
		return entity;
	}
	
	/**
	 * 一个属性值
	 * @param id
	 * @return
	 */
	public YskGoodAttributeEntity findone(int id) {
		YskGoodAttributeEntity findById = yskGoodAttributeRepository.findById(id);
		return findById;
	}
	
	/**
	 * 更新排序
	 * @param id
	 * @param order
	 * @return
	 */
	@Transactional
	public YskGoodAttributeEntity chengeOrder(int id,int order) {
		YskGoodAttributeEntity attr = yskGoodAttributeRepository.findById(id);
		YskGoodAttributeEntity entity = null;
		attr.setAttrOrder(order);
		try {
			entity = yskGoodAttributeRepository.saveAndFlush(attr);
		} catch (Exception e) {
			
		}
		return entity;
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public int deleteAttr(int id) {
		try {
			yskGoodAttributeRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
