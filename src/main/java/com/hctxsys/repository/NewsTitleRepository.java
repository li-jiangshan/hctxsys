package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskNewsTitleEntity;

public interface NewsTitleRepository extends JpaRepository<YskNewsTitleEntity, Integer> {
	@Query(value="SELECT * FROM ysk_news_title WHERE pid = ?1 AND id != 6 ORDER BY sort",nativeQuery=true)
	public List<YskNewsTitleEntity> findNewsTitleList(int pid);
	
	public List<YskNewsTitleEntity> findByIdNotOrderBySortDesc(int id);
	
	public YskNewsTitleEntity findById(int id);
}
