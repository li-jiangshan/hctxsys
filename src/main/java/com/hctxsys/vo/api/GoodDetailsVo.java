package com.hctxsys.vo.api;

import java.util.List;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskShopInfoEntity;

public class GoodDetailsVo {
	private YskGoodEntity goodEntity;
	private YskShopInfoEntity shopInfoEntity;
	private List<YskGoodEntity> likeList;
	private List<YskGoodCommentEntity> goodCommentList;
	private String goodImage;
	public YskShopInfoEntity getShopInfoEntity() {
		return shopInfoEntity;
	}
	public void setShopInfoEntity(YskShopInfoEntity shopInfoEntity) {
		this.shopInfoEntity = shopInfoEntity;
	}
	public YskGoodEntity getGoodEntity() {
		return goodEntity;
	}
	public void setGoodEntity(YskGoodEntity goodEntity) {
		this.goodEntity = goodEntity;
	}
	
	public List<YskGoodEntity> getLikeList() {
		return likeList;
	}
	public void setLikeList(List<YskGoodEntity> likeList) {
		this.likeList = likeList;
	}
	public List<YskGoodCommentEntity> getGoodCommentList() {
		return goodCommentList;
	}
	public void setGoodCommentList(List<YskGoodCommentEntity> goodCommentList) {
		this.goodCommentList = goodCommentList;
	}
	public String getGoodImage() {
		return goodImage;
	}
	public void setGoodImage(String goodImage) {
		this.goodImage = goodImage;
	}
	
}
