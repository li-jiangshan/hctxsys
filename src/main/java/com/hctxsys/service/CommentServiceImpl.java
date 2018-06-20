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

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.CommentRepositry;
import com.hctxsys.util.TableResult;

@Service
public class CommentServiceImpl {
	@Autowired
	CommentRepositry commentRepositry;

	public TableResult indexTable(TableResult result) {

		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());// 获取分页对象

		Page<YskGoodCommentEntity> page = commentRepositry.findAll(new Specification<YskGoodCommentEntity>() {
			@Override
			public Predicate toPredicate(Root<YskGoodCommentEntity> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				
				if (null != result.getKeyword() && !("".equals(result.getKeyword()))) {// 标题模糊查询
					Predicate p1=criteriaBuilder.like(root.get("id").as(String.class), '%' + result.getKeyword() + '%');
					Predicate p2=criteriaBuilder.like(root.get("username"), "%" + result.getKeyword() + '%');
					Predicate p3=criteriaBuilder.like(root.get("mobile"), "%" + result.getKeyword() + '%');
 
					predicates.add(criteriaBuilder.or(criteriaBuilder.and(p1),criteriaBuilder.and(p2),criteriaBuilder.and(p3)));
				}
				predicates.add(criteriaBuilder.greaterThan(root.get("sellerId"),Integer.valueOf("0")));
				Predicate[] pre =new Predicate[predicates.size()];
				criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

				return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
			}
		}, request);
		TableResult tableResult = new TableResult();
		BeanUtils.copyProperties(result, tableResult);// 将条件信息复制给tableResult
		tableResult.setTotal(page.getTotalElements());// 设置总记录数
		tableResult.setRows(page.getContent());// 设置分页后的集合
		tableResult.setPageCount(Long.valueOf(page.getTotalPages()));// 设置总页数
		return tableResult;

	}
	
	/**
	 * 新增、更新
	 * 
	 * @param model
	 * @return
	 */
	@Transactional
	public YskGoodCommentEntity updateNew(YskGoodCommentEntity New) {
		YskGoodCommentEntity entity = commentRepositry.saveAndFlush(New);
		return entity;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public int deleteBrand(int id) {
		try {
			commentRepositry.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}


	
}
