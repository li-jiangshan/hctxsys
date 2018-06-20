package com.hctxsys.vo.api;

import java.math.BigDecimal;
import java.util.List;

public class ApiOutGoodVo {
	private Integer goodId;
	private String goodName;
	private Integer goodStore;
	private BigDecimal goodPrice;
	private String goodCoverImg;
	private Integer sellerId;
	private String responMobile;
	private Integer status;
	private Integer isCollect;
	//是否可以购买 0,否，1，是
    private Integer isBuy;
	private List<ApiGoodAttributeVo> goodAttributeList;
	
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Integer getGoodStore() {
		return goodStore;
	}
	public void setGoodStore(Integer goodStore) {
		this.goodStore = goodStore;
	}
	public String getGoodCoverImg() {
		return goodCoverImg;
	}
	public void setGoodCoverImg(String goodCoverImg) {
		this.goodCoverImg = goodCoverImg;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public BigDecimal getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(BigDecimal goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getResponMobile() {
		return responMobile;
	}
	public void setResponMobile(String responMobile) {
		this.responMobile = responMobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public Integer getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(Integer isBuy) {
		this.isBuy = isBuy;
	}
	public List<ApiGoodAttributeVo> getGoodAttributeList() {
		return goodAttributeList;
	}
	public void setGoodAttributeList(List<ApiGoodAttributeVo> goodAttributeList) {
		this.goodAttributeList = goodAttributeList;
	}
}
