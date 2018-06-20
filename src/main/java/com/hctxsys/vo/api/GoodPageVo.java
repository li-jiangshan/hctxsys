package com.hctxsys.vo.api;

import com.hctxsys.entity.YskGoodEntity;

public class GoodPageVo extends PageVo<YskGoodEntity>{
	
	//店铺名称
	private String shopName;
	//店铺logo
	private String shopImg;
	//店铺收藏数量
	private Integer shopCollectNum;
	//我是否收藏该店铺
	private Integer shopCollect;  //1 是 0 否
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public Integer getShopCollectNum() {
		return shopCollectNum;
	}
	public void setShopCollectNum(Integer shopCollectNum) {
		this.shopCollectNum = shopCollectNum;
	}
	public Integer getShopCollect() {
		return shopCollect;
	}
	public void setShopCollect(Integer shopCollect) {
		this.shopCollect = shopCollect;
	}
	
}
