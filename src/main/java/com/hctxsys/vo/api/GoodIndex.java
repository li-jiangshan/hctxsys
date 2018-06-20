package com.hctxsys.vo.api;

import java.util.ArrayList;
import java.util.List;

import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskShopnewEntity;

public class GoodIndex {
	private List<YskGoodEntity> likeList=new ArrayList<YskGoodEntity>();
	private List<YskGoodEntity> isHotGood=new ArrayList<YskGoodEntity>();
	private List<YskGoodEntity> isRecommend=new ArrayList<YskGoodEntity>();
	private List<YskBannerEntity> bannerList=new ArrayList<YskBannerEntity>();
	private List<YskShopnewEntity> shopNew=new ArrayList<YskShopnewEntity>();
	
	
	public List<YskBannerEntity> getBannerList() {
		return bannerList;
	}
	public void setBannerList(List<YskBannerEntity> bannerList) {
		this.bannerList = bannerList;
	}
	
	public List<YskShopnewEntity> getShopNew() {
		return shopNew;
	}
	public void setShopNew(List<YskShopnewEntity> shopNew) {
		this.shopNew = shopNew;
	}
	public List<YskGoodEntity> getLikeList() {
		return likeList;
	}
	public void setLikeList(List<YskGoodEntity> likeList) {
		this.likeList = likeList;
	}
	public List<YskGoodEntity> getIsHotGood() {
		return isHotGood;
	}
	public void setIsHotGood(List<YskGoodEntity> isHotGood) {
		this.isHotGood = isHotGood;
	}
	public List<YskGoodEntity> getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(List<YskGoodEntity> isRecommend) {
		this.isRecommend = isRecommend;
	}

}
