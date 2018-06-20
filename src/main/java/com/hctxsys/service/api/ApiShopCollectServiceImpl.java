package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskShopCollectEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.GoodCommentRepository;
import com.hctxsys.repository.ShopCollectRepository;
import com.hctxsys.repository.ShopInfoRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.vo.api.ApiShopCollectVo;
import com.hctxsys.vo.api.ApiShopInfoVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiShopCollectService")
public class ApiShopCollectServiceImpl {
	@Autowired
	private ShopCollectRepository shopCollectRepository;
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	@Autowired
	private GoodCommentRepository goodCommentRepository;
	@Autowired
	private ApiGoodCommentServiceImpl goodComment;
	
	/**根据用户id查找收藏的店家
	 * @param uid 用户id
	 * @return
	 */
	@Transactional
	public JsonResult getShopCollectByUid(Integer uid) {
		JsonResult result = new JsonResult();
		ApiShopCollectVo apiShopCollectVo = new ApiShopCollectVo();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于1");
			return result;
		}
		List<YskShopCollectEntity> list = shopCollectRepository.findByUid(uid);
		if(list.size()!=0) {
			List<YskShopInfoEntity> shopInfoList = new ArrayList<YskShopInfoEntity>();
			for (YskShopCollectEntity yskShopCollectEntity : list) {
				Optional<YskShopInfoEntity> findById = shopInfoRepository.findById(yskShopCollectEntity.getSellerId());
				YskShopInfoEntity yskShopInfoEntity = findById.get();
				//查商家评价
				Page<YskGoodCommentEntity> sellerList = goodCommentRepository.findBySellerId(yskShopInfoEntity.getUid(), PageRequest.of(0, 1));
				//有评价
				if(sellerList.getContent().size()!=0) {
					//YskGoodCommentEntity seller = sellerList.getContent().get(0);
					YskGoodCommentEntity seller = goodComment.CalculationSeller(yskShopInfoEntity.getUid());
					yskShopInfoEntity.setStarAbility(seller.getStarAbility());
					yskShopInfoEntity.setStarAttitude(seller.getStarAttitude());
					yskShopInfoEntity.setStarPrice(seller.getStarPrice());
					yskShopInfoEntity.setGeneral((seller.getStarAbility()+seller.getStarAttitude()+seller.getStarPrice())/3);
				}else {
					yskShopInfoEntity.setStarAbility(0);
					yskShopInfoEntity.setStarAttitude(0);
					yskShopInfoEntity.setStarPrice(0);
					yskShopInfoEntity.setGeneral(0);
				}
				//
				yskShopInfoEntity.setUserCheckinfo(null);
				yskShopInfoEntity.setUser(null);
				shopInfoList.add(findById.get());
			}
			apiShopCollectVo.setMyCollect(shopInfoList);
			result.setCode(1);
			result.setMessage("查找收藏的商家成功");
			result.setData(apiShopCollectVo);
			return result;
		}
		result.setCode(1);
		result.setMessage("该用户没有收藏任何店家");
		return result;
	}
	
	/**添加用户收藏商家
	 * @param uid 用户id
	 * @param sellerId 商家id
 	 * @return
	 */
	@Transactional
	public JsonResult saveCollect(Integer uid,Integer sellerId) {
		JsonResult result = new JsonResult();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于0");
			return result;
		}
		if(sellerId<1||null==sellerId) {
			result.setCode(0);
			result.setMessage("商家id不能小于0");
			return result;
		}
		YskShopCollectEntity shopCollectEntity = new YskShopCollectEntity();
		shopCollectEntity.setUid(uid);
		shopCollectEntity.setSellerId(sellerId);
		shopCollectEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
		shopCollectRepository.saveAndFlush(shopCollectEntity);
		result.setCode(1);
		result.setMessage("收藏成功");
		return result;
	}
	
	/**根据用户删除收藏的店家
	 * @param uid 用户id
	 * @param sellerId 商家id
	 * @return
	 */
	@Transactional
	public JsonResult deleteCollect(Integer uid,Integer sellerId) {
		JsonResult result = new JsonResult();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于0");
			return result;
		}
		if(sellerId<1||null==sellerId) {
			result.setCode(0);
			result.setMessage("商家id不能小于0");
			return result;
		}
		shopCollectRepository.deleteByUidAndSellerId(uid, sellerId);
		result.setCode(1);
		result.setMessage("删除收藏成功");
		return result;
	}
	
}
