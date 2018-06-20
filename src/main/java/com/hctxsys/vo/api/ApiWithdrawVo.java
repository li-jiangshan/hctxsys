package com.hctxsys.vo.api;

import java.math.BigDecimal;

public class ApiWithdrawVo {
	
	//用户id
    private Integer uid;
	//页码
    private Integer page;
	//每页总数
    private Integer pageSize;
    //用户余额
    private BigDecimal balance;
    //单笔提现金额
    private String moneyMax;
    //提现金额倍数
    private String moneyMultiple;
    //提现金额
    private BigDecimal money;
    //卡号id
    private int userBankId;
    //卡号
    private String cardNo;
    //手机号
    private String mobile;
	//验证码
	private String verificationCode;
	//安全密码
    private String safetyPwd;
    
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getMoneyMax() {
		return moneyMax;
	}
	public void setMoneyMax(String moneyMax) {
		this.moneyMax = moneyMax;
	}
	public String getMoneyMultiple() {
		return moneyMultiple;
	}
	public void setMoneyMultiple(String moneyMultiple) {
		this.moneyMultiple = moneyMultiple;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public int getUserBankId() {
		return userBankId;
	}
	public void setUserBankId(int userBankId) {
		this.userBankId = userBankId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getSafetyPwd() {
		return safetyPwd;
	}
	public void setSafetyPwd(String safetyPwd) {
		this.safetyPwd = safetyPwd;
	}
	
}
