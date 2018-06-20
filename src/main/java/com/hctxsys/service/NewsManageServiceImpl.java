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

import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.entity.YskSchoolDetailsEntity;
import com.hctxsys.entity.YskSchoolPeopleEntity;
import com.hctxsys.repository.NewsRepository;
import com.hctxsys.repository.NewsTitleRepository;
import com.hctxsys.repository.SchoolDetailRepository;
import com.hctxsys.repository.SchoolPeopleRepository;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;

@Service
public class NewsManageServiceImpl {

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private NewsTitleRepository newsTitleRepository;
	
	@Autowired
	private SchoolPeopleRepository schoolPeopleRepository;
	
	@Autowired
	private SchoolDetailRepository schoolDetailRepository;
	
	public TableResult getNewsList(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskNewsEntity> newsPage = newsRepository.findAll(new Specification<YskNewsEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<YskNewsEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (result.getKeyword() != null) {
					predicates.add(cb.like(root.get("type").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("newsId").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("px").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("title"), '%' + result.getKeyword() + '%'));
					query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
				}
				query.orderBy(cb.desc(root.get("px")));
				return query.getRestriction();
			}
		}, request);

		result.setRows(newsPage.getContent());
		result.setTotal(newsPage.getTotalElements());
		result.setPageCount(Long.valueOf(newsPage.getTotalPages()));
		return result;
	}

	public List<YskNewsTitleEntity> getNewsType() {
		return newsTitleRepository.findByIdNotOrderBySortDesc(6);
	}

	public YskNewsTitleEntity getNewsTitle(int pid) {
		return newsTitleRepository.findById(pid);
	}

	public Result saveNews(YskNewsEntity request) {
		newsRepository.saveAndFlush(request);
		return new Result(200, "保存成功 页面即将自动跳转~");
	}

	public Optional<YskNewsEntity> getNewsInfo(int id) {
		return newsRepository.findById(id);
	}

	public Result deleteNews(int id) {
		newsRepository.deleteById(id);
		return new Result(200, "删除成功 页面即将自动跳转~");
	}
	
	public TableResult getStudentList(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskSchoolPeopleEntity> schoolPeoplePage = schoolPeopleRepository.findAll(new Specification<YskSchoolPeopleEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<YskSchoolPeopleEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (result.getKeyword() != null) {
					predicates.add(cb.like(root.get("name").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("id").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("addres").as(String.class), '%' + result.getKeyword() + '%'));
					predicates.add(cb.like(root.get("sort").as(String.class), '%' + result.getKeyword() + '%'));
					query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
				}
				query.orderBy(cb.desc(root.get("addtime")));
				return query.getRestriction();
			}
		}, request);
		
		result.setRows(schoolPeoplePage.getContent());
		result.setTotal(schoolPeoplePage.getTotalElements());
		result.setPageCount(Long.valueOf(schoolPeoplePage.getTotalPages()));
		return result;
	}
	
	public Result savePeople(YskSchoolPeopleEntity request) {
		schoolPeopleRepository.saveAndFlush(request);
		return new Result(200, "保存成功 页面即将自动跳转~");
	}
	
	public Optional<YskSchoolPeopleEntity> getPeopleInfo(int id) {
		return schoolPeopleRepository.findById(id);
	}
	
	public Result deletePeople(int id) {
		schoolPeopleRepository.deleteById(id);
		return new Result(200, "删除成功 页面即将自动跳转~");
	}
	
	public TableResult getPeopleList(int id, TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<YskSchoolDetailsEntity> peoplePage = schoolDetailRepository.findAll(new Specification<YskSchoolDetailsEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<YskSchoolDetailsEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (result.getKeyword() != null) {
					predicates.add(cb.like(root.get("name").as(String.class), '%' + result.getKeyword() + '%'));
				}
				predicates.add(cb.equal(root.get("pid").as(String.class), id));
				query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				query.orderBy(cb.desc(root.get("id")));
				return query.getRestriction();
			}
		}, request);
		
		result.setRows(peoplePage.getContent());
		result.setTotal(peoplePage.getTotalElements());
		result.setPageCount(Long.valueOf(peoplePage.getTotalPages()));
		return result;
	}
	
	public Result deleteDetail(int id) {
		schoolDetailRepository.deleteById(id);
		return new Result(200, "删除成功 页面即将自动跳转~");
	}
}
