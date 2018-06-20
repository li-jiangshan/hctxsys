package com.hctxsys.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hctxsys.entity.YskConfigEntity;
import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskGoodImgEntity;
import com.hctxsys.entity.YskShopInfoEntity;
import com.hctxsys.repository.ConfigRepository;
import com.hctxsys.repository.GoodCommentRepository;
import com.hctxsys.repository.GoodDetailRepository;
import com.hctxsys.repository.GoodRepository;
import com.hctxsys.repository.ShopInfoRepository;

@Service("apiGoodDetailService")
public class ApiGoodDetailServiceImpl {
	
	@Autowired
	private GoodDetailRepository goodDetailRepository;
	
	@Autowired
	private GoodRepository goodRepository;
	
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private GoodCommentRepository goodCommentRepository;
	
	public List<YskGoodImgEntity> getGoodImgList(int good_id) {
		return goodDetailRepository.findGoodImgList(good_id);
	}
	
	public YskGoodEntity getGoodInfo(int good_id) {
		return goodRepository.findGoodInfo(good_id);
	}
	
	public YskShopInfoEntity getShopInfo(int seller_id) {
		return shopInfoRepository.findShopInfo(seller_id);
	}
	
	public Optional<YskConfigEntity> getConfig() {
		return configRepository.findById(9);
	}
	
	public List<YskGoodCommentEntity> getGoodCommentList(int good_id) {
		return goodCommentRepository.findGoodCommentList(good_id);
	}
	
	public List<YskGoodCommentEntity> getGoodCommentListAll(int good_id) {
		return goodCommentRepository.findGoodCommentListAll(good_id);
	}
	
	public List<YskGoodCommentEntity> getGoodCommentListLow(int good_id) {
		return goodCommentRepository.findGoodCommentListLow(good_id);
	}
	
	public List<YskGoodCommentEntity> getGoodCommentListMiddle(int good_id) {
		return goodCommentRepository.findGoodCommentListMiddle(good_id);
	}
	
	public List<YskGoodCommentEntity> getGoodCommentListHigh(int good_id) {
		return goodCommentRepository.findGoodCommentListHigh(good_id);
	}
	
	public List<YskGoodEntity> getRelateList(int good_category) {
		return goodRepository.findRelateList(good_category);
	}
}
