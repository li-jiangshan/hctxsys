package com.hctxsys.service;

import com.hctxsys.entity.YskGoodBrandEntity;
import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.repository.YskGoodRepository;
import com.hctxsys.util.Result;
import com.hctxsys.util.TableResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("yskGoodSerivce")
public class YskGoodSerivceImpl {
	@Autowired 
	private YskGoodRepository yskGoodRepository;
	/**
	 * 查询商品列表 (自营,有分类名)
	 * @return
	 */
	//TODO:暂时操作，uodate方法
//	@Transactional
//	public int update(YskGoodEntity entity) {
//		return yskGoodRepository.updateSelctive(entity.getGoodCoverImg(),entity.getGoodId());
//	}
	public TableResult findList(TableResult result) {
		PageRequest request = PageRequest.of(result.getPageNumber(), result.getPageSize());
		Page<Object[]> goodlist = null;
		if(result.getTypeText()==null||"".equals(result.getTypeText())) {
			if("isRecommend".equals(result.getType())) {
				goodlist = yskGoodRepository.findRecommendList("", request);
			}else if ("isNew".equals(result.getType())) {
				goodlist = yskGoodRepository.findNewList("", request);
			}else if ("isHot".equals(result.getType())) {
				goodlist = yskGoodRepository.findHotList("", request);
			}else {
				goodlist = yskGoodRepository.findList(request);
			}
		}else {
			if("isRecommend".equals(result.getType())) {
				goodlist = yskGoodRepository.findRecommendList(result.getTypeText(), request);
			}else if ("isNew".equals(result.getType())) {
				goodlist = yskGoodRepository.findNewList(result.getTypeText(), request);
			}else if ("isHot".equals(result.getType())) {
				goodlist = yskGoodRepository.findHotList(result.getTypeText(), request);
			}else {
				goodlist = yskGoodRepository.findkeywordList(result.getTypeText(), request);
			}
		}
	    List<YskGoodEntity> goods = new ArrayList<>();
	      for (Object[] objects : goodlist) {
	          YskGoodEntity yskGoodEntity = new YskGoodEntity();
	          //BeanUtils.copyProperties(objects, yskGoodEntity);
	          YskGoodCategoryEntity categoryEntity=new YskGoodCategoryEntity();
	          yskGoodEntity=(YskGoodEntity) objects[0];
	          categoryEntity=(YskGoodCategoryEntity)objects[1];
	          yskGoodEntity.setYskGoodCategoryEntity(categoryEntity);
	          goods.add(yskGoodEntity);
	    }
	    TableResult tableResult = new TableResult();
	    BeanUtils.copyProperties(result, tableResult);//将条件信息复制给tableResult
	    tableResult.setTotal(goodlist.getTotalElements());//设置总记录数
	    tableResult.setRows(goods);
	    //tableResult.setRows(goodlist.getContent());
	    tableResult.setPageCount(Long.valueOf(goodlist.getTotalPages()));//设置总页数
	    return tableResult;
	}
	/**
	 * 上架	下架
	 * @param yskGoodEntity
	 * @return
	 */
	@Transactional
	public YskGoodEntity putOnSale(int id){
		YskGoodEntity yskGoodEntity = yskGoodRepository.findById(id).get();
		int status = yskGoodEntity.getStatus();
		if (status==0) {
			yskGoodEntity.setStatus(1);
		}else {
			yskGoodEntity.setStatus(0);
		}
		try {
			yskGoodRepository.save(yskGoodEntity);
		} catch (Exception e) {
			
		}
		return yskGoodEntity;
	}
	
	/**
	 * 插入商品
	 * @return
	 */
	@Transactional
	public YskGoodEntity insertGood(YskGoodEntity yskGoodEntity) {
		YskGoodEntity good = null;
		try {
			good = yskGoodRepository.save(yskGoodEntity);
		} catch (Exception e) {
			
		}
		return good;
	}
	/**
	 * 单查商品
	 * @return
	 */
	public YskGoodEntity getGood(String id) {
		if (id==null||id=="") {
			id="0";
		}
		List<Object[]> good = yskGoodRepository.findGood(Integer.valueOf(id));
		YskGoodEntity yskGoodEntity = new YskGoodEntity();
        YskGoodCategoryEntity categoryEntity=new YskGoodCategoryEntity();
        YskGoodBrandEntity brandEntity = new YskGoodBrandEntity();
        List<YskGoodEntity> goods = new ArrayList<>();
		for (Object[] objects : good) {
	        yskGoodEntity = (YskGoodEntity) objects[0];
	        categoryEntity=(YskGoodCategoryEntity) objects[1];
	        brandEntity=(YskGoodBrandEntity) objects[2];
	        yskGoodEntity.setYskGoodCategoryEntity(categoryEntity);
	        yskGoodEntity.setYskGoodBrandEntity(brandEntity);
	        goods.add(yskGoodEntity);
		}
		return yskGoodEntity;
	} 
	/**
	 * 修改商品
	 * @return
	 */
	@Transactional
	public YskGoodEntity updateGood(YskGoodEntity yskGoodEntity) {
		YskGoodEntity good = null;
		try {
			good = yskGoodRepository.saveAndFlush(yskGoodEntity);
		} catch (Exception e) {
			
		}
		return good;
	}
	/**
	 * 修改排序
	 * @param id
	 * @param sort
	 * @return
	 */
	@Transactional
	public YskGoodEntity updateSort(int id,Short sort) {
		YskGoodEntity good = yskGoodRepository.findById(id).get();
		good.setGoodSort(sort);

		YskGoodEntity good1 = null;
		try {
			good1 = yskGoodRepository.saveAndFlush(good);
		} catch (Exception e) {
		}
		return good1;
	}
	
	/**
	 * 删除商品
	 * @return
	 */
	@Transactional
	public int deleteGood(int id) {
		try {
			yskGoodRepository.deleteById(id);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 最大id
	 * @return
	 */
	public YskGoodEntity findMaxId() {
		YskGoodEntity entity = yskGoodRepository.findMaxId().get(0);
		return entity;
	}
	
	public List<YskGoodEntity> findByGoodNo(String goodNo) {
		List<YskGoodEntity> list = yskGoodRepository.findByGoodNo(goodNo);
		return list;
	}
	
	/**
	 * 设置推荐    取消推荐
	 * isRecommend   1-推荐    0-不推荐
	 * @param yskGoodEntity
	 * @return
	 */
	@Transactional
	public Result turnRecommend(int id){
		YskGoodEntity yskGoodEntity = yskGoodRepository.findById(id).get();
		int isRecommend = yskGoodEntity.getIsRecommend();
		if (isRecommend==0) {
			yskGoodEntity.setIsRecommend(1);
		}else {
			yskGoodEntity.setIsRecommend(0);
		}
		try {
			yskGoodRepository.save(yskGoodEntity);
			Result result = new Result();
			result.setStatus(1);
			result.setInfo("操作成功");
			return result;
			//return new Result(1, "操作成功", "", "");
		} catch (Exception e) {
			Result result = new Result();
			result.setStatus(0);
			result.setInfo("操作失败");
			return result;
			//return new Result(0, "操作失败", "", "");
		}
	}
	
	/**
	 * 设置新品    取消新品 
	 * isNew   1-新品     0-不是新品 
	 * @param yskGoodEntity
	 * @return
	 */
	@Transactional
	public Result turnNew(int id){
		YskGoodEntity yskGoodEntity = yskGoodRepository.findById(id).get();
		int isNew = yskGoodEntity.getIsNew();
		if (isNew==0) {
			yskGoodEntity.setIsNew(1);
		}else {
			yskGoodEntity.setIsNew(0);
		}
		try {
			yskGoodRepository.save(yskGoodEntity);
			Result result = new Result();
			result.setStatus(1);
			result.setInfo("操作成功");
			return result;
			//return new Result(1, "操作成功", "/Adminmall/Good/index", "");
		} catch (Exception e) {
			Result result = new Result();
			result.setStatus(0);
			result.setInfo("操作失败");
			return result;
			//return new Result(0, "操作失败", "/Adminmall/Good/index", "");
		}
	}
	
	/**
	 * 设置热卖   取消热卖
	 * isHot   1-热卖    0-不热卖
	 * @param yskGoodEntity
	 * @return
	 */
	@Transactional
	public Result turnHot(int id){
		YskGoodEntity yskGoodEntity = yskGoodRepository.findById(id).get();
		int isHot = yskGoodEntity.getIsHot();
		if (isHot==0) {
			yskGoodEntity.setIsHot(1);
		}else {
			yskGoodEntity.setIsHot(0);
		}
		try {
			yskGoodRepository.save(yskGoodEntity);
			Result result = new Result();
			result.setStatus(1);
			result.setInfo("操作成功");
			return result;
			//return new Result(1, "操作成功", "/Adminmall/Good/index", "");
		} catch (Exception e) {
			Result result = new Result();
			result.setStatus(0);
			result.setInfo("操作失败");
			return result;
			//return new Result(0, "操作失败", "/Adminmall/Good/index", "");
		}
	}
}
