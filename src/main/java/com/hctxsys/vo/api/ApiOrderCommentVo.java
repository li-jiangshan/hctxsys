package com.hctxsys.vo.api;

public class ApiOrderCommentVo {
	private Integer orderId;
	private Integer id;
	private String goodName;
	private String goodCoverImg;
	private Integer level;
	private String content;
	private String OrderNo;
	private Integer goodid;
	

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getGoodid() {
		return goodid;
	}
	public void setGoodid(Integer goodid) {
		this.goodid = goodid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodCoverImg() {
		return goodCoverImg;
	}
	public void setGoodCoverImg(String goodCoverImg) {
		this.goodCoverImg = goodCoverImg;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	
	
	
}
