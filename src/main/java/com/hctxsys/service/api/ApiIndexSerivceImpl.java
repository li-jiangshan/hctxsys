package com.hctxsys.service.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hctxsys.entity.YskAppEditionEntity;
import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskGoodCategoryEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskNewsEntity;
import com.hctxsys.entity.YskUserLevelEntity;
import com.hctxsys.repository.AppEditionRepository;
import com.hctxsys.repository.BannerRepository;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.GoodCategoryRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.repository.NewsRepository;
import com.hctxsys.repository.UserLevelRepository;
import com.hctxsys.vo.api.ApiIndexVo;
import com.hctxsys.vo.api.ApiUserLevelVo;
import com.hctxsys.vo.api.JsonResult;

@Service("apiIndexSerivce")
public class ApiIndexSerivceImpl {
	
	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	private NewsRepository newsRepository;
	@Autowired
	private GoodRepository goodRepository;
	@Autowired
	private UserLevelRepository userLevelRepository;
	@Autowired
	private AppEditionRepository appEditionRepository;
	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private GoodCategoryRepository goodCategoryRepository;
	

    /**
     * 查询banner信息
     */
	@Transactional
	public List<YskBannerEntity> getBannerListByType(ApiIndexVo vo) {
//		return bannerRepository.findAll();  //查询全部
//		return bannerRepository.findBybType(vo.getbType());  //通过类型查询
		return bannerRepository.findBybType("mall_index_wap");  //通过类型查询
	}
	
    /**
     * 查询资讯信息
     */
	@Transactional
	public List<YskNewsEntity> getFiveNewsList() {
		return newsRepository.findFiveNewsList();
	}
	
    /**
     * 查询商品信息
     */
	@Transactional
	public List<YskGoodEntity> getLikeGoodList() {
		return goodRepository.findLikeGoodList();
	}
	
	//查询用户等级信息
	@Transactional
	public List<ApiUserLevelVo> getUserLevelList(ApiIndexVo vo) {
		List<Byte> levels = new ArrayList<Byte>();
		levels.add((byte) 1); 
		levels.add((byte) 2); 
		List<YskUserLevelEntity> userLevelList = userLevelRepository.findByLevelIn(levels);  //通过用户id查询
		
		List<ApiUserLevelVo> returnList = new ArrayList<ApiUserLevelVo>();
		
		for (int i = 0; i < userLevelList.size(); i++) {
			ApiUserLevelVo returnVo = new ApiUserLevelVo();
			returnVo.setLevelId(userLevelList.get(i).getId());
	        BeanUtils.copyProperties(userLevelList.get(i), returnVo);
			returnList.add(returnVo);
		}
		
		return returnList;  
	}
	
    /**
     * 查询安卓版本信息
     */
	@Transactional
	public YskAppEditionEntity getAndroidVersion(ApiIndexVo vo) {
		List<YskAppEditionEntity> list = appEditionRepository.findAll();
		YskAppEditionEntity  appEditionEntity = list.get(0);
		return appEditionEntity;
	}
	
    //查询客服信息
	@Transactional
	public JsonResult getCustomerService() {
		JsonResult returnVo = new JsonResult();
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(9); //联系电话
		ids.add(10); //联系手机
		ids.add(11); //QQ1
		ids.add(12); //QQ2
		ids.add(13); //客服微信
		List<YskConfigEntity> configList = configRepository.findByIdIn(ids);  //通过用户id查询
		ApiIndexVo data = new ApiIndexVo();
		data.setWebTel(configList.get(0).getValue());  //联系电话
		data.setWebMobile(configList.get(1).getValue()); //联系手机
		data.setWebQQ1(configList.get(2).getValue()); //QQ1
		data.setWebQQ2(configList.get(3).getValue()); //QQ2
		data.setWebWX(configList.get(4).getValue()); //客服微信
		returnVo.setData(data);
		return returnVo;
	}
	
    //通过大分类查询五件商品
	@Transactional
	public JsonResult getGoodByCategory(ApiIndexVo vo) {

		JsonResult returnVo = new JsonResult();
		
		List<YskGoodCategoryEntity> categoryList = goodCategoryRepository.findByPidPathLike("%-" + vo.getCategoryId() + "-%");

		List<Integer> categoryIds = new ArrayList<Integer>();
		for (int i = 0; i < categoryList.size(); i++) {
			categoryIds.add(Integer.valueOf(categoryList.get(i).getId()));
		}
		Pageable pageable = PageRequest.of(0, 5);
		List<YskGoodEntity> goodList = goodRepository.findByStatusAndCategoryIdInOrderByGoodSortDesc(1, categoryIds,pageable);
		
		if (goodList.size() < 5) {
			Pageable pageableAdd = PageRequest.of(0, 5 - goodList.size());
			List<YskGoodEntity> goodListAdd = goodRepository.findByStatus(1,pageableAdd);
			goodList.addAll(goodListAdd);
		}
		
		returnVo.setData(goodList);
		return returnVo;
	}

}
