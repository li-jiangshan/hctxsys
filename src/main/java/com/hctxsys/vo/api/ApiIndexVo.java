package com.hctxsys.vo.api;

import java.util.List;

import com.hctxsys.entity.YskBannerEntity;
import com.hctxsys.entity.YskGoodEntity;
import com.hctxsys.entity.YskNewsEntity;

public class ApiIndexVo {
	
	//商品分类id
	private String categoryId;
	//联系电话
	private String webTel;
	//联系手机
	private String webMobile;
	//QQ1
	private String webQQ1;
	//QQ2
	private String webQQ2;
	//客服微信
	private String webWX;
	//banner图片类型 mall_index_wap:手机  mall_index_pc:pc
    private String bType;
	//banner图片list
	private List<YskBannerEntity> yskBannerList;
	//商品list
	private List<YskGoodEntity> yskGoodList;
	//新闻资讯list
	private List<YskNewsEntity> yskNewsList;
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getbType() {
		return bType;
	}

	public void setbType(String bType) {
		this.bType = bType;
	}
	
	public String getWebTel() {
		return webTel;
	}

	public void setWebTel(String webTel) {
		this.webTel = webTel;
	}

	public String getWebMobile() {
		return webMobile;
	}

	public void setWebMobile(String webMobile) {
		this.webMobile = webMobile;
	}

	public String getWebQQ1() {
		return webQQ1;
	}

	public void setWebQQ1(String webQQ1) {
		this.webQQ1 = webQQ1;
	}

	public String getWebQQ2() {
		return webQQ2;
	}

	public void setWebQQ2(String webQQ2) {
		this.webQQ2 = webQQ2;
	}

	public String getWebWX() {
		return webWX;
	}

	public void setWebWX(String webWX) {
		this.webWX = webWX;
	}

	public List<YskBannerEntity> getYskBannerList() {
		return yskBannerList;
	}

	public void setYskBannerList(List<YskBannerEntity> yskBannerList) {
		this.yskBannerList = yskBannerList;
	}

	public List<YskGoodEntity> getYskGoodList() {
		return yskGoodList;
	}

	public void setYskGoodList(List<YskGoodEntity> yskGoodList) {
		this.yskGoodList = yskGoodList;
	}

	public List<YskNewsEntity> getYskNewsList() {
		return yskNewsList;
	}

	public void setYskNewsList(List<YskNewsEntity> yskNewsList) {
		this.yskNewsList = yskNewsList;
	}
	
}
