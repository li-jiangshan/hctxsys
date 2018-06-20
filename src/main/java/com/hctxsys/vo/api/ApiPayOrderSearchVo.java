package com.hctxsys.vo.api;

public class ApiPayOrderSearchVo {
	
    private String orderNo;
	//支付类型 1:余额 2: 微信 3:支付宝 4:一网通
    private Integer payType;
    private String payTime;
    
    
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getPayType() {
		return payType;
	}
	
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayTime() {
		return payTime;
	}
}