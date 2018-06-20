package com.hctxsys.vo.api;

import java.util.ArrayList;
import java.util.List;

import com.hctxsys.entity.YskGoodCommentEntity;
import com.hctxsys.entity.YskShopInfoEntity;

public class ApiShopInfoVo {
	private YskShopInfoEntity shopInfo;
	private List<YskGoodCommentEntity> comment = new ArrayList<YskGoodCommentEntity>();
	public YskShopInfoEntity getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(YskShopInfoEntity shopInfo) {
		this.shopInfo = shopInfo;
	}
	public List<YskGoodCommentEntity> getComment() {
		return comment;
	}
	public void setComment(List<YskGoodCommentEntity> comment) {
		this.comment = comment;
	}

	
}
