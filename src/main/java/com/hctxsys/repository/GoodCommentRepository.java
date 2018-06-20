package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodCommentEntity;

public interface GoodCommentRepository extends JpaRepository<YskGoodCommentEntity, Integer>,JpaSpecificationExecutor<YskGoodCommentEntity> {
	public Page<YskGoodCommentEntity> findByGoodId(Integer id,Pageable pageable);
	public List<YskGoodCommentEntity> findByUid(int uid);
	public Page<YskGoodCommentEntity> findBySellerId(Integer seller,Pageable pageable);
	
	@Query(value = "SELECT * FROM ysk_good_comment WHERE good_id=?1 ORDER BY id DESC LIMIT 3", nativeQuery = true)
	public List<YskGoodCommentEntity> findGoodCommentList(int good_id);
	
	@Query(value = "SELECT * FROM ysk_good_comment WHERE good_id=?1 ORDER BY id DESC", nativeQuery = true)
	public List<YskGoodCommentEntity> findGoodCommentListAll(int good_id);
	
	@Query(value = "SELECT * FROM ysk_good_comment WHERE good_id=?1 AND level=0 ORDER BY id DESC", nativeQuery = true)
	public List<YskGoodCommentEntity> findGoodCommentListLow(int good_id);
	
	@Query(value = "SELECT * FROM ysk_good_comment WHERE good_id=?1 AND level=1 ORDER BY id DESC", nativeQuery = true)
	public List<YskGoodCommentEntity> findGoodCommentListMiddle(int good_id);
	
	@Query(value = "SELECT * FROM ysk_good_comment WHERE good_id=?1 AND level=2 ORDER BY id DESC", nativeQuery = true)
	public List<YskGoodCommentEntity> findGoodCommentListHigh(int good_id);
	
	@Query(value = "SELECT * FROM ysk_good_comment WHERE seller_id=?1 ORDER BY id DESC LIMIT 100", nativeQuery = true)
	public List<YskGoodCommentEntity> findGoodComment(int uid);
}
