package com.hctxsys.vo.api;

import java.math.BigDecimal;

public class ApiOrderDetailVo  {

    //订单明细id名称
    private Integer orderDetailId;
    //商品详细价格id
    private Integer priceId;
    //商品id
    private Integer goodId;
    //商家id
    private Integer sellerId;
    //商品名称
    private String goodName;
    //商品价格
    private BigDecimal goodPrice;
    //商品图片
    private String goodCoverImg;
    //商品数量
    private String goodNum;
    //退货状态
    private Integer orderRejectedStatus;
    private String attrValue;

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public BigDecimal getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(BigDecimal goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getGoodCoverImg() {
		return goodCoverImg;
	}
	public void setGoodCoverImg(String goodCoverImg) {
		this.goodCoverImg = goodCoverImg;
	}
	public String getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(String goodNum) {
		this.goodNum = goodNum;
	}
	public Integer getOrderRejectedStatus() {
		return orderRejectedStatus;
	}
	public void setOrderRejectedStatus(Integer orderRejectedStatus) {
		this.orderRejectedStatus = orderRejectedStatus;
	}
	
}
