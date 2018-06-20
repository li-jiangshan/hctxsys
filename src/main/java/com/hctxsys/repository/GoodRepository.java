package com.hctxsys.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hctxsys.entity.YskGoodEntity;

public interface GoodRepository extends JpaRepository<YskGoodEntity, Integer>,JpaSpecificationExecutor<YskGoodEntity> {
	
	@Query(value="SELECT * FROM ysk_good WHERE is_recommend = 1 and status = 1  order by good_sort desc limit 0, 6",nativeQuery=true)
	public List<YskGoodEntity> findLikeGoodList();
	
	@Query(value = "SELECT * FROM ysk_good WHERE good_id=?1 AND status=1", nativeQuery = true)
	public YskGoodEntity findGoodInfo(int good_id);
	
	@Query(value = "SELECT * FROM ysk_good where status = 1 AND category_id IN (SELECT id FROM ysk_good_category WHERE pid_path LIKE CONCAT('%-', :good_category ,'-%')) ORDER BY good_sort DESC,is_recommend DESC,is_new DESC,is_hot DESC LIMIT 3", nativeQuery = true)
	public List<YskGoodEntity> findRelateList(@Param("good_category")int good_category);
	
	public List<YskGoodEntity> findByStatusAndCategoryIdInOrderByGoodSortDesc(Integer status, List<Integer> categoryIds, Pageable pageable);
	
	public List<YskGoodEntity> findByStatus(Integer status, Pageable pageable);
	
	public List<YskGoodEntity> findByCategoryIdAndStatus(int categoryId, int status);
	
	public List<YskGoodEntity> findByStatusAndGoodIdNotIn(Integer status,List<Integer> ids,Pageable pageable);
}
