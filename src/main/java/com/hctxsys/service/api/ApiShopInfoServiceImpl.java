package com.hctxsys.service.api;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskGoodPriceEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.GoodCommentRepository;
import com.hctxsys.repository.GoodPriceRepository;
import com.hctxsys.repository.ShopInfoRepository;
import com.hctxsys.vo.api.ApiShopInfoVo;
import com.hctxsys.vo.api.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service("shopInfoService")
public class ApiShopInfoServiceImpl {
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	@Autowired
	private GoodCommentRepository goodCommentRepository;
    @Autowired
    private GoodPriceRepository goodPriceRepository;
	/**查询商家列表
	 * @param page
	 * @param pageSize
	 * @param longitude 经度
	 * @param latitude 维度
	 * @return
	 */
	@Transactional
	public Page<YskShopInfoEntity> getShopinfo(Integer page,Integer pageSize,String longitude,String latitude,String industryid) {
		PageRequest pageable=PageRequest.of(page, pageSize);
		Page<YskShopInfoEntity> findAll = shopInfoRepository.findAll(new Specification<YskShopInfoEntity>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8795327540817636097L;

			@Override
			public Predicate toPredicate(Root<YskShopInfoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 条件集合
				predicates.add(cb.greaterThanOrEqualTo(root.get("shopJ"),Double.parseDouble(longitude)-0.5));
				predicates.add(cb.lessThanOrEqualTo(root.get("shopJ"),Double.parseDouble(longitude)+0.5));
				predicates.add(cb.greaterThanOrEqualTo(root.get("shopW"),Double.parseDouble(latitude)-0.5));
				predicates.add(cb.lessThanOrEqualTo(root.get("shopW"),Double.parseDouble(latitude)+0.5));
				if(!StringUtils.isEmpty(industryid)) {
					predicates.add(cb.equal(root.get("industryId"), industryid));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			}
		}, pageable);
//		List<YskShopInfoEntity> content = findAll.getContent();
		return findAll;
	}
	
	/**查找商家id与评论
	 * @param sellerId 商家id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Transactional
	public Page<YskGoodCommentEntity> getComment(Integer sellerId,Integer page,Integer pageSize){
		PageRequest pageable=PageRequest.of(page, pageSize);
		Page<YskGoodCommentEntity> findBySellerId = goodCommentRepository.findBySellerId(sellerId, pageable);
		return findBySellerId;
	}
	
	
	/**查询商家详情
	 * @param uid 商家id
	 * @return
	 */
	@Transactional
	public JsonResult shopInfoDetail(Integer sellerId) {
		JsonResult result = new JsonResult();
		ApiShopInfoVo apiShopInfoVo = new ApiShopInfoVo();
		if(sellerId<1||null==sellerId) {
			result.setCode(0);
			result.setMessage("商家id不能小于0");
			return result;
		}
		YskShopInfoEntity shopInfoDetail = shopInfoRepository.findByUid(sellerId);
		Page<YskGoodCommentEntity> comment = this.getComment(sellerId, 0, 2);
		if(null==shopInfoDetail) {
			result.setCode(0);
			result.setMessage("该商家不存在");
			return result;
		}
		if(!comment.hasContent()) {
			result.setCode(1);
			result.setMessage("查询商家详情成功，该商家还没有评论");
			apiShopInfoVo.setShopInfo(shopInfoDetail);
			result.setData(apiShopInfoVo);
			return result;
		}
		result.setCode(1);
		result.setMessage("查找商家详情成功");
		apiShopInfoVo.setShopInfo(shopInfoDetail);
		apiShopInfoVo.setComment(comment.getContent());
		result.setData(apiShopInfoVo);
		return result;
	}

    /**
     * 根据商品ID与规格返回价格与库存
     *
     * @param goodPrice 规格实体
     * @return Json规格对象
     */
    @Transactional
    public JsonResult getGoodAttr(YskGoodPriceEntity goodPrice) {
        YskGoodPriceEntity price = null;//实际传回前台对象
        String attr = goodPrice.getGoodAttrValue();
        String[] attrValue = attr.split(",");//前台传来规格项拆分
        List<String> attrList = Arrays.asList(attrValue);//前台规格项List
        Collections.sort(attrList);//排序前台规格项List

        List<YskGoodPriceEntity> allByGoodId = goodPriceRepository.findAllByGoodId(goodPrice.getGoodId());
        for (YskGoodPriceEntity yskGoodPriceEntity : allByGoodId) {
            String goodAttrValue = yskGoodPriceEntity.getGoodAttrValue();
            String[] split = goodAttrValue.split(",");//数据库取出规格项拆分
            if (split.length != attrList.size()) {
                continue;
            }
            List<String> splitList = Arrays.asList(split);
            Collections.sort(splitList);
            //后台取出规格项包含于前台传来规格项中并且两者长度相等，排除包含情况
            //前台：1,2,3 后台List 【1,2,3】 【1,2】
            if (attrList.containsAll(splitList)) {
                price = yskGoodPriceEntity;
                break;
            }

        }
        if (price == null) {
            return new JsonResult(0, "无此规格商品");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", price.getId());//ID
        map.put("store", price.getStore());//库存
        map.put("price", price.getPrice());//价格
        return new JsonResult(1, "获取成功", map);
    }
}
