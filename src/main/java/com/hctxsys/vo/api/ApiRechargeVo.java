package com.hctxsys.vo.api;

import java.math.BigDecimal;

public class ApiRechargeVo {
	
	//用户id
    private Integer uid;
    //订单号
    private String orderNo;
	//页码
    private Integer page;
	//每页总数
    private Integer pageSize;
	//收款账户名称
    private String companyAccountName;
	//收款开户行
    private String companyAccountBank;
	//收款账号
    private String companyAccountNo;
    //充值金额
    private BigDecimal money;
    //转账银行
    private String bankName;
    //户名
    private String userName;
    //卡号
    private String cardNo;
    //支付凭证
    private String img;
    //充值类型
    private String rechargeType;
    
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getCompanyAccountName() {
		return companyAccountName;
	}

	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
	}

	public String getCompanyAccountBank() {
		return companyAccountBank;
	}

	public void setCompanyAccountBank(String companyAccountBank) {
		this.companyAccountBank = companyAccountBank;
	}

	public String getCompanyAccountNo() {
		return companyAccountNo;
	}

	public void setCompanyAccountNo(String companyAccountNo) {
		this.companyAccountNo = companyAccountNo;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	
}
