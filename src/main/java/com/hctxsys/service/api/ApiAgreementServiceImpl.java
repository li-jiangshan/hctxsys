package com.hctxsys.service.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskArticleEntity;
import com.hctxsys.repository.ArticleRepository;

@Service("apiAgreementService")
public class ApiAgreementServiceImpl {

	@Autowired
	private ArticleRepository articleRepository;
	
	public Optional<YskArticleEntity> getAboutus(int id) {
		return articleRepository.findById(id);
	}
}
