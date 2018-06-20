package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hctxsys.entity.YskGoodBrandEntity;
import com.hctxsys.entity.YskHealthyEntity;
import com.hctxsys.entity.YskHealthyTypeEntity;
import com.hctxsys.entity.YskMessageEntity;
import com.hctxsys.entity.YskModuleImgEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.entity.YskUserEntity;
import com.hctxsys.repository.NewTitleRepositry;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import com.hctxsys.vo.HealthyVo;
import com.hctxsys.vo.MessageVo;
import com.hctxsys.vo.NewsVo;
import com.hctxsys.vo.UserVo;

@Service
public class NewTitleServiceImpl {
	@Autowired
	NewTitleRepositry newTitleRepositry;

	public TableResult indexTable(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());// 获取分页对象
		Page<YskNewsTitleEntity> page = newTitleRepositry.findAll(new Specification<YskNewsTitleEntity>() {
			@Override
			public Predicate toPredicate(Root<YskNewsTitleEntity> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				// criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sort")));
				if (null != result.getKeyword() && !("".equals(result.getKeyword()))) {// 标题模糊查询
					predicates.add(
							criteriaBuilder.like(root.get("id").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(
							criteriaBuilder.like(root.get("sort").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(criteriaBuilder.like(root.get("title"), "%" + result.getKeyword() + '%'));
					criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])));
				}
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sort")));
				return criteriaQuery.getRestriction();
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
	 * 查询单个ID信息
	 * 
	 * @param id
	 * @return
	 */
	public YskNewsTitleEntity findById(int id) {
		YskNewsTitleEntity entity = newTitleRepositry.findById(id);
		return entity;
	}
	
	
	
	/**
	 * 新增、更新
	 * 
	 * @param model
	 * @return
	 */
	@Transactional
	public YskNewsTitleEntity updateNew(YskNewsTitleEntity New) {
		YskNewsTitleEntity entity = null;
		try {
		 entity = newTitleRepositry.saveAndFlush(New);
		}catch (Exception e) {
			
		}
		return entity;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public int deleteNew(int id) {
		try {
			newTitleRepositry.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	public boolean isMatches(String id) {
		boolean flag = false;
		try {
			String regex = "^[1-9]+[0-9]*$";
			// ^[1-9]+\\d*$
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(id);
			if (m.find()) {
				System.out.println("successss");
				return true;
			} else {
				System.out.println("falsss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**查询具体的消息
	 * @param id
	 * @return
	 */
	public NewsVo getNews(Integer id) {
		System.out.println(id);
		NewsVo newsVo = new NewsVo();
		YskNewsTitleEntity newsTitleEntity = newTitleRepositry.findById(id).get();
		BeanUtils.copyProperties(newsTitleEntity, newsVo);
		return newsVo;
	}

}
