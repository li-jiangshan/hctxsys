package com.hctxsys.vo;

import java.math.BigDecimal;

public class IntegralAuditVo {
	//id
	private int id;
	//用户id
	private int uid;
	//用户名
	private String userName;
	//说明
	private String content;
	//金额
	private BigDecimal money;
	//0-未审核1-已审核 2-未通过
	private byte status;
	//支付凭证
	private String img;
	//时间
	private int createTime;
	//充值记录
	private BigDecimal moneyRecord;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getMoneyRecord() {
		return moneyRecord;
	}
	public void setMoneyRecord(BigDecimal moneyRecord) {
		this.moneyRecord = moneyRecord;
	}
}
