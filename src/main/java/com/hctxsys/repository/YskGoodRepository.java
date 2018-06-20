package com.hctxsys.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hctxsys.entity.YskGoodEntity;

public interface YskGoodRepository extends JpaRepository<YskGoodEntity, Integer>,JpaSpecificationExecutor<YskGoodEntity> {
	//@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id where g.goodType=0 order by g.goodSort desc")
	@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id  order by g.goodSort desc")
	public Page<Object[]> findList(Pageable pageable);
	//TODO:暂时操作,把注释打开，删除现有Query
//	@Modifying
//	@Query("update YskGoodEntity  g set g.goodCoverImg=?1 where g.goodId=?2")
//	public int updateSelctive(String img,Integer goodId);
	
	//推荐
	//	@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id where g.isRecommend=1 and g.goodName like %?1% order by g.goodSort desc")
	@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id where  g.goodName like %?1% order by g.goodSort desc")
    public Page<Object[]> findkeywordList(String keywords,Pageable pageable);
    
    //推荐
  	@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id where g.isRecommend=1 and g.goodName like %?1% order by g.goodSort desc")
    public Page<Object[]> findRecommendList(String keywords,Pageable pageable);
    
    //上新
  	@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id where g.isNew=1 and g.goodName like %?1% order by g.goodSort desc")
    public Page<Object[]> findNewList(String keywords,Pageable pageable);
      
    //热卖
  	@Query("select g,gc from YskGoodEntity g left join YskGoodCategoryEntity gc on g.categoryId=gc.id where g.isHot=1 and g.goodName like %?1% order by g.goodSort desc")
    public Page<Object[]> findHotList(String keywords,Pageable pageable);
	
	@Query("select g,gc,gb from YskGoodEntity g "
			+ "left join YskGoodCategoryEntity gc on g.categoryId=gc.id "
			+ "left join YskGoodBrandEntity gb on g.brandId=gb.id "
			+ "where g.goodId=?1")
	public List<Object[]> findGood(int goodId);
	
	@Query("select g from YskGoodEntity g order by goodId desc")
	public List<YskGoodEntity> findMaxId();
	
	@Query("select g from YskGoodEntity g where g.goodNo=?1")
	public List<YskGoodEntity> findByGoodNo(String goodNo);
}
