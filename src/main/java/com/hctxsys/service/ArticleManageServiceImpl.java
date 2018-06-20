package com.hctxsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskArticleEntity;
import com.hctxsys.repository.ArticleRepository;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Service
public class ArticleManageServiceImpl {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	public TableResult getArticleList(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskArticleEntity> articlePage = articleRepository.findAll(new Specification<YskArticleEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<YskArticleEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (result.getKeyword() != null) {
					predicates.add(cb.like(root.get("title").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("id").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("type").as(String.class), '%' + result.getKeyword() + '%'));
					query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
				}
				query.orderBy(cb.asc(root.get("type")));
				return query.getRestriction();
			}
		}, request);
		
		result.setRows(articlePage.getContent());
		result.setTotal(articlePage.getTotalElements());
		result.setPageCount(Long.valueOf(articlePage.getTotalPages()));
		return result;
	}

	public Result saveArticle(YskArticleEntity request) {
		if (request.getType() == 2) {
			return new Result(0, "已经有用户注册协议，请编辑");
		} else if (request.getType() == 1) {
			return new Result(0, "已经有关于我们，请编辑");
		} else {
			articleRepository.saveAndFlush(request);
			return new Result(200, "保存成功 页面即将自动跳转~");
		}
	}
	
	public Optional<YskArticleEntity> getArticleInfo(int id) {
		return articleRepository.findById(id);
	}
	
	public Result deleteArticle(int id) {
		articleRepository.deleteById(id);
		return new Result(200, "删除成功 页面即将自动跳转~");
	}
}
