package com.hctxsys.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskArticleEntity;
import com.hctxsys.repository.ArticleRepository;

@Service("apiUserHelpService")
public class ApiUserHelpServiceImpl {

	@Autowired
	private ArticleRepository articleRepository;
	
	public List<YskArticleEntity> findUserHelp() {
		return articleRepository.findUserHelp();
	}
	
	public Optional<YskArticleEntity> findUserHelpDetail(int id) {
		return articleRepository.findById(id);
	}
}
