package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodModelEntity;
import com.hctxsys.repository.YskGoodModelRepository;
import com.hctxsys.util.TableResult;

@Service("yskGoodModelSerivce")
public class YskGoodModelSerivceImpl {
	@Autowired
	YskGoodModelSerivceImpl yskGoodModelSerivceImpl;

	@Autowired 
	private YskGoodModelRepository yskGoodModelRepository;
	
	/**
	 * 查询模型list    
	 * @return
	 */
	public List<YskGoodModelEntity> getModelList(){
		List<YskGoodModelEntity> list = yskGoodModelRepository.findAll();
		return list;
	}
	
	/**
	 * @return
	 */
	public TableResult getModelListPage(TableResult result){
		//获取分页对象
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskGoodModelEntity> page = yskGoodModelRepository.findAll(new Specification<YskGoodModelEntity>() {
			@Override
			public Predicate toPredicate(Root<YskGoodModelEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();//条件集合
				if (null != result.getTypeText() && !("".equals(result.getTypeText()))) {//按类型条件查询
					predicates.add(criteriaBuilder.like(root.get("modelName"), '%' + result.getTypeText() + '%'));
				}
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		},request);
		//List<YskGoodModelEntity> list = yskGoodModelRepository.findAll();
		TableResult tableResult = new TableResult();
        
        BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
        tableResult.setTotal(page.getTotalElements());//设置总记录数
        tableResult.setRows(page.getContent());
        tableResult.setPageCount(Long.valueOf(page.getTotalPages()));//设置总页数
        return tableResult;
	}
	
	/**
	 * 模型列表
	 * @param id
	 * @return
	 */
	public List<?> getMoList(int id){
		List<String> resultList = new ArrayList<>();
		List<YskGoodModelEntity> list = yskGoodModelSerivceImpl.getModelList();
		for (YskGoodModelEntity model : list) {
			if (model.getId()==id) {
				resultList.add("<option selected='true' value='"+model.getId()+"'>"+model.getModelName()+"</option>");
			}else {
				resultList.add("<option value='"+model.getId()+"'>"+model.getModelName()+"</option>");
			}
		}
		return resultList;
	}
	/**
	 * 关键字查询
	 * @param keyword
	 * @return
	 */
	public List<YskGoodModelEntity> findKeyword(String keyword){
		List<YskGoodModelEntity> list = yskGoodModelRepository.findByModelName(keyword);
		return list;
	}
	
	/**
	 * 查询单个模型
	 * @param id
	 * @return
	 */
	public YskGoodModelEntity findById(int id) {
		YskGoodModelEntity entity = yskGoodModelRepository.findById(id);
		return entity;
	}
	/**
	 * 新增、更新
	 * @param model
	 * @return
	 */
	@Transactional
	public YskGoodModelEntity updateModel(YskGoodModelEntity model) {
		YskGoodModelEntity entity = null;
		try {
			entity = yskGoodModelRepository.saveAndFlush(model);
		} catch (Exception e) {
			
		}
		return entity;
	}
	/**
	 * 删除
	 * @param id
	 */
	@Transactional
	public int deleteModel(int id) {
		try {
			yskGoodModelRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
