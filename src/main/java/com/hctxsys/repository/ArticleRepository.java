package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskArticleEntity;

public interface ArticleRepository extends JpaRepository<YskArticleEntity, Integer>,JpaSpecificationExecutor<YskArticleEntity> {
	
	@Query(value="SELECT * FROM ysk_article WHERE type='2'",nativeQuery=true)
	public YskArticleEntity findAgreement();
	
	@Query(value="SELECT * FROM ysk_article WHERE type='3' ORDER BY id DESC",nativeQuery=true)
	public List<YskArticleEntity> findUserHelp();

}
