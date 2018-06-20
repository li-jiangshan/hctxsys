package com.hctxsys.controller.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskShopnewEntity;
import com.hctxsys.service.api.ApiIndexSerivceImpl;
import com.hctxsys.service.api.ApiShopNewServiceImpl;
import com.hctxsys.service.api.ApiShoppingMallServiceImpl;
import com.hctxsys.vo.api.ApiGoodByKeywordVo;
import com.hctxsys.vo.api.ApiIndexVo;
import com.hctxsys.vo.api.GoodIndex;
import com.hctxsys.vo.api.GoodPageVo;
import com.hctxsys.vo.api.GoodVo;
import com.hctxsys.vo.api.InputGoogUserVo;
import com.hctxsys.vo.api.JsonResult;

@Controller
@RequestMapping("/api/shoppingmall")
public class ApiShoppingMallController {
	@Autowired
	private ApiShoppingMallServiceImpl shoppingMallIndexService;
	@Autowired
	private ApiIndexSerivceImpl apiIndexSerivce;
	@Autowired
	private ApiShopNewServiceImpl shopNewServiceImpl;
	/**
	 * @param type isNew-新品，isHot-热卖，isRecommend-推荐
	 * @return 商品json数据
	 */
	@RequestMapping(value="/getGoodByType", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getGoodByType(@RequestParam(defaultValue="default") String type,Integer page,Integer pageSize) {
		
		JsonResult returnVo = new JsonResult();
		GoodVo goodVo = new GoodVo();
		List<YskGoodEntity> list = shoppingMallIndexService.getGood(type,page,pageSize);
		if(list.size()!=0) {
			returnVo.setCode(1);
			returnVo.setMessage("查找出"+type+"类型的商品");
			goodVo.setGoodlist(list);
			returnVo.setData(goodVo);
			return returnVo;
		}
		returnVo.setCode(1);
		returnVo.setMessage("没有符合查询条件商品");
		return returnVo;
	}
	
	/**商品搜索
	 * @param keyword 搜索关键字
	 * @param category_id 商品分类
	 * @param page 
	 * @param pageSize
	 * @return 商品json数据
	 */
	@RequestMapping(value="/getGoodByKeyword", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getGoodByKeyword(@RequestBody ApiGoodByKeywordVo vo) {
		
		JsonResult returnVo = new JsonResult();
		GoodPageVo goodPageVo = new GoodPageVo();
		if(vo.getPage() == null || vo.getPage()<0) {
			returnVo.setCode(0);
			returnVo.setMessage("参数page不能小于0");
			return returnVo;
		}
		if(vo.getPageSize() == null || vo.getPageSize()==0) {
			returnVo.setCode(0);
			returnVo.setMessage("缺少参数pageSize");
			return returnVo;
		}
		Page<YskGoodEntity> pageList = shoppingMallIndexService.getGoodBykeywords(vo);	
		if(pageList.getContent().size()!=0) {
			goodPageVo.setPageList(pageList.getContent());
			goodPageVo.setPageSize(pageList.getSize());
			goodPageVo.setPage(vo.getPage());
			goodPageVo.setTotalPages(pageList.getTotalPages());
			returnVo.setData(goodPageVo);
			return returnVo;
		}
		returnVo.setCode(1);
		returnVo.setMessage("没有符合查询条件商品");
		return returnVo;
	}
	
	//通过商家id获取产品列表
	@RequestMapping(value="/getGoodBySeller", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getGoodBySeller(@RequestBody ApiGoodByKeywordVo vo) {
		
		JsonResult returnVo = new JsonResult();
		GoodPageVo goodPageVo = new GoodPageVo();
		if(vo.getPage()<0) {
			returnVo.setCode(0);
			returnVo.setMessage("参数page不能小于0");
			return returnVo;
		}
		if(vo.getPageSize()==0) {
			returnVo.setCode(0);
			returnVo.setMessage("缺少参数pageSize");
			return returnVo;
		}
		if(vo.getSellerId() == null) {
			returnVo.setCode(0);
			returnVo.setMessage("商家id不能为空");
			return returnVo;
		}
		
		String shopName = "大连华彩自营";
		goodPageVo.setShopName(shopName);
		if (vo.getSellerId() != 0) {
			goodPageVo = shoppingMallIndexService.getShopInfo(vo);
		}
		
		
		Page<YskGoodEntity> pageList = shoppingMallIndexService.getGoodBykeywords(vo);	
		if(pageList.getContent().size()!=0) {
			goodPageVo.setPageList(pageList.getContent());
			goodPageVo.setPageSize(pageList.getSize());
			goodPageVo.setPage(vo.getPage());
			goodPageVo.setTotalPages(pageList.getTotalPages());
			returnVo.setData(goodPageVo);
			return returnVo;
		}
		returnVo.setCode(1);
		returnVo.setMessage("没有符合查询条件商品");
		return returnVo;
	}
	
	/**商品详情
	 * @param id 商品id
	 * @return 商品详情的json数据
	 */
	@RequestMapping(value="/getGoodDetails", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getGoodDetails(@RequestBody InputGoogUserVo vo) {
		JsonResult returnVo = shoppingMallIndexService.getGoodDetail(vo.getGoodId(),vo.getUserId());
		return returnVo;
	}
	
	/**
	 * @return 商品首页要的数据
	 */
	@RequestMapping(value="/index",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult index() {
		JsonResult returnVo = new JsonResult();
		GoodIndex goodIndex = new GoodIndex();
		List<YskGoodEntity> isRecommendList = shoppingMallIndexService.getGood("isRecommend", 0, 5);//新品
		List<YskGoodEntity> isHotList = shoppingMallIndexService.getGood("isHot", 0, 5);//热门
		List<YskGoodEntity> likeList = shoppingMallIndexService.getGood("isNew", 0, 5);//猜你喜欢
		List<YskBannerEntity> bannerList = apiIndexSerivce.getBannerListByType(new ApiIndexVo());
		List<YskShopnewEntity> shopNew = shopNewServiceImpl.getShopNew();
		//解决UnsupportedOperationException异常转换为ArrayList
		ArrayList isRecommend = new ArrayList<>(isRecommendList);
		ArrayList isHot = new ArrayList<>(isHotList);
		ArrayList like = new ArrayList<>(likeList);
		//如果新品数量不够5个，按照商品（排除isRecommendList的几个商品）的创建时间降序补齐，
		if(isRecommendList.size()<5) {
			Integer pageSize = 5-isRecommendList.size();
			List<Integer> ids = new ArrayList<Integer>();
			for (YskGoodEntity recommend : isRecommendList) {
				ids.add(recommend.getGoodId());
			}
			List<YskGoodEntity> addGood = shoppingMallIndexService.addGoodDesc(pageSize, "createTime", ids);
			isRecommend.addAll(addGood);
			//Collections.addAll(isRecommend, addGood);
		}
		//热门商品不够5个，按照商品（排除isHot的几个商品）销量补齐
		if(isHot.size()<5) {
			Integer pageSize = 5-isHot.size();
			List<Integer> ids = new ArrayList<Integer>();
			for (YskGoodEntity hot : isHotList) {
				ids.add(hot.getGoodId());
			}
			List<YskGoodEntity> addGood = shoppingMallIndexService.addGoodDesc(pageSize, "goodSellNum", ids);
			isHot.addAll(addGood);
		}
		//猜你喜欢不足5个，按照商品（排除likeList的几个商品）创建时间升序补齐
		if(likeList.size()<5) {
			Integer pageSize = 5-likeList.size();
			List<Integer> ids = new ArrayList<Integer>();
			for (YskGoodEntity goodlike : likeList) {
				ids.add(goodlike.getGoodId());
			}
			List<YskGoodEntity> addGood = shoppingMallIndexService.addGoodAsc(pageSize, "createTime", ids);
			like.addAll(addGood);
		}
		
		if(null!=isRecommend&&null!=isHot&&null!=like&&null!=bannerList) {
			returnVo.setCode(1);
			returnVo.setMessage("查找成功");
			goodIndex.setIsHotGood(isHot);
			goodIndex.setIsRecommend(isRecommend);
			goodIndex.setLikeList(like);
			goodIndex.setBannerList(bannerList);
			goodIndex.setShopNew(shopNew);
			returnVo.setData(goodIndex);
			return returnVo;
		}
		returnVo.setCode(0);
		returnVo.setMessage("查找失败");
		return returnVo;
	}
}
