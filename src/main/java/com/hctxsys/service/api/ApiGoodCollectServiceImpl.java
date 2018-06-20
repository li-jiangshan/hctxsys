package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskGoodCollectEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.repository.GoodCollectRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.util.MyUtils;
import com.hctxsys.vo.api.ApiGoodCollectVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiGoodCollectService")
public class ApiGoodCollectServiceImpl {
	@Autowired
	private GoodCollectRepository goodCollectRepository;
	@Autowired
	private GoodRepository goodRepository;
	
	/**根据用户id找商品
	 * @param uid 用户id
	 * @return
	 */
	@Transactional
	public JsonResult getGoodCollectByUid(Integer uid){
		JsonResult result = new JsonResult();
		ApiGoodCollectVo apiGoodCollectVo = new ApiGoodCollectVo();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于1");
			return result;
		}
		List<YskGoodCollectEntity> list = goodCollectRepository.findByUid(uid);
		if(list.size()!=0) {
			List<YskGoodEntity> goodCollect = new ArrayList<YskGoodEntity>();
			for (YskGoodCollectEntity yskGoodCollectEntity : list) {
				Optional<YskGoodEntity> findById = goodRepository.findById(yskGoodCollectEntity.getGoodId());
				goodCollect.add(findById.get());
			}
			apiGoodCollectVo.setMyCollect(goodCollect);
			result.setCode(1);
			result.setMessage("查找用户收藏商品成功");
			result.setData(apiGoodCollectVo);
			return result;
		}
		result.setCode(1);
		result.setMessage("查找的用户没有收藏商品");
		return result;
	}
	
	/**收藏商品
	 * @param uid 用户id
	 * @param goodId 商品id
	 * @return
	 */
	@Transactional
	public JsonResult saveCollect(Integer uid,Integer goodId) {
		JsonResult result = new JsonResult();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于0");
			return result;
		}
		if(goodId<1||null==goodId) {
			result.setCode(0);
			result.setMessage("商品id不能小于0");
			return result;
		}
		YskGoodCollectEntity goodCollectEntity=new YskGoodCollectEntity();
		goodCollectEntity.setUid(uid);
		goodCollectEntity.setGoodId(goodId);
		goodCollectEntity.setCreateTime(MyUtils.getSecondTimestamp(new Date()));
		goodCollectRepository.saveAndFlush(goodCollectEntity);
		result.setCode(1);
		result.setMessage("收藏商品成功");
		return result;
	}
	
	/**删除我的收藏
	 * @param uid 用户id
	 * @param goodId 商品id
	 * @return
	 */
	@Transactional
	public JsonResult delectCollect(Integer uid,Integer goodId) {
		JsonResult result = new JsonResult();
		if(uid<1||null==uid) {
			result.setCode(0);
			result.setMessage("用户id不能小于0");
			return result;
		}
		if(goodId<1||null==goodId) {
			result.setCode(0);
			result.setMessage("商品id不能小于0");
			return result;
		}
		goodCollectRepository.deleteByUidAndGoodId(uid, goodId);
		result.setCode(1);
		result.setMessage("删除收藏的商品成功");
		return result;
	}
	
}
