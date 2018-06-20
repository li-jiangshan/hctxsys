package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskNewsEntity;

public interface NewsRepository extends JpaRepository<YskNewsEntity, Integer>,JpaSpecificationExecutor<YskNewsEntity> {
	
	@Query(value="SELECT * FROM ysk_news  order by addtime desc limit 0, 5",nativeQuery=true)
	public List<YskNewsEntity> findFiveNewsList();
	
	@Query(value="SELECT * FROM ysk_news WHERE status = 1 ORDER BY newtop desc LIMIT ?1",nativeQuery=true)
	public List<YskNewsEntity> findNewsList(int limit);
	
	@Query(value="SELECT * FROM ysk_news WHERE status = 1 AND pid = ?1 ORDER BY newtop desc",nativeQuery=true)
	public List<YskNewsEntity> findNewsListNotLimit(int pid);
	
	@Query(value="SELECT * FROM ysk_news WHERE id = ?1",nativeQuery=true)
	public YskNewsEntity findNewsDetail(int id);
	
	public List<YskNewsEntity> findByTypeAndStatus(String type,Integer status,Pageable pageable);
}
