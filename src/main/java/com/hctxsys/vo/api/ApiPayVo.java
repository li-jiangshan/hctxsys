package com.hctxsys.vo.api;

import java.math.BigDecimal;

public class ApiPayVo {

	//用户id
    private Integer userID;
    //订单号
    private String orderNo;
    //退款订单号
    private String refundOrderNo;
    //订单金额
    private BigDecimal amount;
    //退款金额
    private BigDecimal refundAmount;
    //电话
    private String mobile;
    //支付类型 1:余额 2: 微信 3:支付宝 4:一网通
    private Integer payType;
    //安全码
    private String safePwd;
    //支付状态
    private String status;
    //产品名称
    private String goodsName;
    //付款者
    private String payer;
    //收款者
    private String payee;
    //付款时间
    private int payTime;
    
    public void setUserID(Integer userID) {
         this.userID = userID;
     }
     public Integer getUserID() {
         return userID;
     }

    public void setOrderNo(String orderNo) {
         this.orderNo = orderNo;
     }
     public String getOrderNo() {
         return orderNo;
     }
     
    public String getRefundOrderNo() {
		return refundOrderNo;
	}
	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}
	public void setAmount(BigDecimal amount) {
         this.amount = amount;
     }
     public BigDecimal getAmount() {
         return amount;
     }
    public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public void setMobile(String mobile) {
         this.mobile = mobile;
     }
     public String getMobile() {
         return mobile;
     }

    public void setPayType(Integer payType) {
         this.payType = payType;
     }
     public Integer getPayType() {
         return payType;
     }

    public void setSafePwd(String safePwd) {
         this.safePwd = safePwd;
     }
     public String getSafePwd() {
         return safePwd;
     }
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public int getPayTime() {
		return payTime;
	}
	public void setPayTime(int payTime) {
		this.payTime = payTime;
	}
}