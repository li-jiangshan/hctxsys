package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskGoodAttributeEntity;
import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.entity.YskGoodCollectEntity;
import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.entity.YskShopCollectEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.GoodCategoryRepository;
import com.hctxsys.repository.GoodCollectRepository;
import com.hctxsys.repository.GoodCommentRepository;
import com.hctxsys.repository.GoodPriceRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.repository.ShopCollectRepository;
import com.hctxsys.repository.ShopInfoRepository;
import com.hctxsys.repository.YskGoodAttributeRepository;
import com.hctxsys.vo.api.ApiGoodAttributeVo;
import com.hctxsys.vo.api.ApiGoodByKeywordVo;
import com.hctxsys.vo.api.ApiOutGoodVo;
import com.hctxsys.vo.api.GoodDetailsVo;
import com.hctxsys.vo.api.GoodPageVo;
import com.hctxsys.vo.api.JsonResult;

@Service("shoppingMallIndexService")
public class ApiShoppingMallServiceImpl {
	
	@Autowired
	private GoodRepository goodRepository;
	@Autowired
	private GoodCommentRepository goodCommentRepository;
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	@Autowired
	private GoodCollectRepository goodCollectRepository;
	@Autowired
	private GoodCategoryRepository goodCategoryRepository;
	@Autowired
	private ShopCollectRepository shopCollectRepository;
	@Autowired
	private ApiGoodDetailServiceImpl apiGoodDetailService;
	@Autowired
	private YskGoodAttributeRepository goodAttributeRepository;
	@Autowired
	private GoodPriceRepository goodPriceRepository;
	
	/** 按类型查找商品
	 * @param type isNew-新品，isHot-热卖，isRecommend-推荐
	 * @return 商品集合
	 */
	@Transactional
	public List<YskGoodEntity> getGood(String type,Integer page,Integer pagesize) {
		Sort sort = new Sort(Direction.DESC,"goodSort");
		PageRequest pageable = PageRequest.of(page, pagesize, sort);
		Page<YskGoodEntity> findAll = goodRepository.findAll(new Specification<YskGoodEntity>() {
			
			private static final long serialVersionUID = 5888878811536827417L;

			@Override
			public Predicate toPredicate(Root<YskGoodEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("status"), 1));
				if(!"default".equals(type)) {
					predicates.add(cb.equal(root.get(type), 1));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll.getContent();
	}
	
	/**搜索商品
	 * @param keyword 搜索关键字
	 * @return 商品分页集合
	 */
	@Transactional
	public Page<YskGoodEntity> getGoodBykeywords(ApiGoodByKeywordVo vo){
		Sort sort = new Sort(Direction.DESC,"goodSort");
		if(!StringUtils.isEmpty(vo.getOrderBy())&&!StringUtils.isEmpty(vo.getSort())) {
			if("DESC".equals(vo.getSort())) {
				sort = new Sort(Direction.DESC,vo.getOrderBy());
			}
			if("ASC".equals(vo.getSort())) {
				sort = new Sort(Direction.ASC,vo.getOrderBy());
			}
		}

		List<Integer> categoryIds = new ArrayList<Integer>();
		if(vo.getCategory_id() != null && vo.getCategory_id() != 0) {
			YskGoodCategoryEntity goodCategory = goodCategoryRepository.findById(vo.getCategory_id().shortValue()).get();
			if (goodCategory.getLevel() == 1 || goodCategory.getLevel() == 2) {
				List<YskGoodCategoryEntity> categoryList = goodCategoryRepository.findByPidPathLike("%-" + vo.getCategory_id() + "-%");
				for (int i = 0; i < categoryList.size(); i++) {
					categoryIds.add(Integer.valueOf(categoryList.get(i).getId()));
				}
			}
		}
		
		PageRequest pageable = PageRequest.of(vo.getPage(), vo.getPageSize(), sort);
		Page<YskGoodEntity> findAll = goodRepository.findAll(new Specification<YskGoodEntity>() {
			
			private static final long serialVersionUID = -718259094453874573L;

			@Override
			public Predicate toPredicate(Root<YskGoodEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.equal(root.get("status"), 1));
				if(vo.getCategory_id() != null && vo.getCategory_id() != 0) {
					
					if (categoryIds != null && categoryIds.size() > 0) {
		                In<Object> in = cb.in(root.get("categoryId"));
		                for (Integer categoryId : categoryIds) {
		                    in.value(categoryId);
		                }
		                predicates.add(in);
		            } else {
		            	predicates.add(cb.equal(root.get("categoryId"), vo.getCategory_id()));
		            }
					
				}
				if(StringUtils.isNotEmpty(vo.getKeyword())) {
					predicates.add(cb.like(root.get("goodName"), "%"+vo.getKeyword()+"%"));
				}
				if(vo.getSellerId() != null && vo.getSellerId() != 0) {
					predicates.add(cb.equal(root.get("sellerId"), vo.getSellerId()));
				}
				if (!StringUtils.isEmpty(vo.getStart_price())) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("goodPrice"), vo.getStart_price()));
				}
				if (!StringUtils.isEmpty(vo.getEnd_price())) {
					predicates.add(cb.lessThanOrEqualTo(root.get("goodPrice"), vo.getEnd_price()));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		},pageable);
		return findAll;
	}
	
	
	/**查询商品详情
	 * @param id 商品id
	 * @return 商品详情
	 */
	@Transactional
	public JsonResult getGoodDetails(Integer id) {
    	JsonResult returnVo = new JsonResult();
		GoodDetailsVo detailsVo = new GoodDetailsVo();
		if(id<1||null==id) {
			returnVo.setCode(0);
			returnVo.setMessage("商品id不能小于1");
			return returnVo;
		}
		Optional<YskGoodEntity> findById = goodRepository.findById(id);
		Page<YskGoodCommentEntity> page = goodCommentRepository.findByGoodId(id, PageRequest.of(0, 2));
		//YskGoodImgEntity goodImg = goodImageRepository.getOne(id);
		if(findById.isPresent()) {
			Optional<YskShopInfoEntity> shopInfo = shopInfoRepository.findById(findById.get().getSellerId());
			returnVo.setCode(1);
			returnVo.setMessage("查找出id为"+id+"的商品详情");
			detailsVo.setGoodEntity(findById.get());
			detailsVo.setLikeList(this.getGood("isRecommend", 0, 4));
			detailsVo.setShopInfoEntity(shopInfo.get());
			if(page.getContent().size()!=0) {
				detailsVo.setGoodCommentList(page.getContent());
			}
			//detailsVo.setGoodImage(goodImg.getImg_url());
			returnVo.setData(detailsVo);
			return returnVo;
		}
		returnVo.setCode(1);
		returnVo.setMessage("没有id为"+id+"的商品详情");
		return returnVo;
	}
	
	/**修改后的商品详情接口
	 * @param id 商品id
	 * @return
	 */
	@Transactional
	public JsonResult getGoodDetail(Integer goodid,Integer userid) {
		JsonResult returnVo = new JsonResult();
		ApiOutGoodVo apiOutGoodVo = new ApiOutGoodVo();
		if(goodid<1||null==goodid) {
			returnVo.setCode(0);
			returnVo.setMessage("商品id不能小于1");
			returnVo.setData(apiOutGoodVo);
			return returnVo;
		}
		if(null!=userid) {
			//查找是否收藏
			YskGoodCollectEntity goodCollectEntity = goodCollectRepository.findByGoodIdAndUid(goodid, userid);
			if(null==goodCollectEntity) {
				apiOutGoodVo.setIsCollect(0);
			}else {
				apiOutGoodVo.setIsCollect(1);
			}
		}else {
			apiOutGoodVo.setIsCollect(0);
		}
		//查找商品
		YskGoodEntity yskGoodEntity = goodRepository.findById(goodid).get();
		String shopMobile;
		//查找店家
		if (yskGoodEntity.getSellerId() > 0) {
			YskShopInfoEntity yskShopInfoEntity = shopInfoRepository.findShopInfo(yskGoodEntity.getSellerId());
			shopMobile = yskShopInfoEntity.getServerTel();
			
		} else {
			Optional<YskConfigEntity> config = apiGoodDetailService.getConfig();
			shopMobile = config.get().getValue();
		}
		
		/** 查询商品规格 Start */
		//查询商品规格价格信息
		List<YskGoodPriceEntity> goodPriceList = goodPriceRepository.findByGoodId(yskGoodEntity.getGoodId());
		//声明属性list
		List<ApiGoodAttributeVo> goodAttributeListVo = new ArrayList<ApiGoodAttributeVo>();
		//如果大于0 表示商品有添加规格
		if (goodPriceList.size() > 0) {
			//查询商品模型属性
			List<YskGoodAttributeEntity> goodAttributeList = goodAttributeRepository.findByModelId(yskGoodEntity.getModelId());
			for (int i = 0; i < goodAttributeList.size(); i++) {
				//声明属性map
				ApiGoodAttributeVo attributeVo = new ApiGoodAttributeVo();
				//通过逗号，将属性转换为list
				List<String> attrValueList = Arrays.asList(goodAttributeList.get(i).getAttrValue().split(","));
				attributeVo.setAttributeName(goodAttributeList.get(i).getAttrName());
				attributeVo.setAttributeValue(attrValueList);
				//将属性转换为map保存 key属性  value属性值
				goodAttributeListVo.add(attributeVo);
			}
		}
		apiOutGoodVo.setGoodAttributeList(goodAttributeListVo);
		/** 查询商品规格 End */
		
		apiOutGoodVo.setGoodId(goodid);
		apiOutGoodVo.setGoodName(yskGoodEntity.getGoodName());
		apiOutGoodVo.setGoodPrice(yskGoodEntity.getGoodPrice());
		apiOutGoodVo.setGoodStore(yskGoodEntity.getGoodStore());
		apiOutGoodVo.setGoodCoverImg(yskGoodEntity.getGoodCoverImg());
		apiOutGoodVo.setSellerId(yskGoodEntity.getSellerId());
		apiOutGoodVo.setStatus(yskGoodEntity.getStatus());
		
		//TODO: 暂时写死
		YskGoodCategoryEntity categoryList = goodCategoryRepository.findById((short) yskGoodEntity.getCategoryId()).get();
		
		if(categoryList.getPidPath().indexOf("865") != -1){  
			apiOutGoodVo.setResponMobile("0411-39987715");
			apiOutGoodVo.setIsBuy(0); //是否可以购买
		} else {
			apiOutGoodVo.setIsBuy(1); //是否可以购买
			apiOutGoodVo.setResponMobile(shopMobile);
		}
		
		returnVo.setCode(1);
		returnVo.setMessage("查询出id为"+goodid+"的商品详情成功");
		returnVo.setData(apiOutGoodVo);
		return returnVo;
	}
	
	@Transactional
	public GoodPageVo getShopInfo(ApiGoodByKeywordVo vo) {
		
		GoodPageVo goodPageVo = new GoodPageVo();
		
		YskShopInfoEntity shopInfoEntity = shopInfoRepository.findShopInfo(vo.getSellerId());
		
		goodPageVo.setShopImg(shopInfoEntity.getShopImg());
		goodPageVo.setShopCollectNum(shopInfoEntity.getShopCollect());
		goodPageVo.setShopName(shopInfoEntity.getShopName());
		if (vo.getUid() != null && vo.getUid() > 0) {
			YskShopCollectEntity shopCollectEntity = shopCollectRepository.findByUidAndSellerId(vo.getUid(), vo.getSellerId());
			if (shopCollectEntity != null) {
				goodPageVo.setShopCollect(1);
			} else {
				goodPageVo.setShopCollect(0);
			}
		}
		
		return goodPageVo ;
	}
	
	public List<YskGoodEntity> addGoodDesc(Integer pageSize,String sortType,List<Integer> ids){
		Sort sort = new Sort(Direction.DESC,sortType);
		PageRequest pageable = PageRequest.of(0, pageSize, sort);
		List<YskGoodEntity> addGood = goodRepository.findByStatusAndGoodIdNotIn(1, ids, pageable);
		return addGood;
	}
	
	public List<YskGoodEntity> addGoodAsc(Integer pageSize,String sortType,List<Integer> ids){
		Sort sort = new Sort(Direction.ASC,sortType);
		PageRequest pageable = PageRequest.of(0, pageSize, sort);
		List<YskGoodEntity> addGood = goodRepository.findByStatusAndGoodIdNotIn(1, ids, pageable);
		return addGood;
	}
	
}
