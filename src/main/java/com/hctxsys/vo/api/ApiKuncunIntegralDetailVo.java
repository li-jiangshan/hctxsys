package com.hctxsys.vo.api;

import java.math.BigDecimal;

public class ApiKuncunIntegralDetailVo {
	
	//用户id
	private Integer uid;
	//购买金额
	private BigDecimal money;
	//库存积分
	private BigDecimal kucunIntegral;
	//验证码
	private String verificationCode;
	//手机号
	private String mobile;
	//交易说明
	private String content;
	//图片
    private String img;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getKucunIntegral() {
		return kucunIntegral;
	}
	public void setKucunIntegral(BigDecimal kucunIntegral) {
		this.kucunIntegral = kucunIntegral;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
