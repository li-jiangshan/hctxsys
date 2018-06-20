package com.hctxsys.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.entity.YskNewsTitleEntity;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.NewsRepository;
import com.hctxsys.repository.NewsTitleRepository;

@Service("apiInformationService")
public class ApiInformationServiceImpl {

	@Autowired
	private NewsTitleRepository newsTitleRepository;
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private ConfigRepository configRepository;
	
	public List<YskNewsTitleEntity> findNewsTitleList(int pid) {	
		return newsTitleRepository.findNewsTitleList(pid);
	}
	
	public List<YskNewsEntity> findNewsList(int limit) {
		return newsRepository.findNewsList(limit);
	}
	
	public List<YskNewsEntity> findNewsListNotLimit(int pid) {
		return newsRepository.findNewsListNotLimit(pid);
	}
	
	public YskNewsEntity findNewsDetail(int id) {
		return newsRepository.findNewsDetail(id);
	}
	
	public void setTimes(YskNewsEntity entity) {
		newsRepository.save(entity);
	}
	
	public Optional<YskConfigEntity> findById(int id) {
		return configRepository.findById(id);
	}
}
