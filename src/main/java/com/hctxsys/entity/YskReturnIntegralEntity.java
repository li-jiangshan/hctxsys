package com.hctxsys.entity;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_return_integral", schema = "hctx_db", catalog = "")
public class YskReturnIntegralEntity {
	
    private int id;
    //平台跟商家返现积分开关
    private int toSeller;
    //平台跟消费者返现积分开关
    private int toCustomer;
    //平台收取运行费百分比
    private String runcost;
    //商家返还消费者积分百分比
    private String returnIntegral;
    //商家购买库存积分百分比
    private String stockIntegral;
    //积分转现金手续费百分比
    private BigDecimal integralMoneyFee;
    
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "to_seller")
	public int getToSeller() {
		return toSeller;
	}
	public void setToSeller(int toSeller) {
		this.toSeller = toSeller;
	}
	
	@Basic
    @Column(name = "to_customer")
	public int getToCustomer() {
		return toCustomer;
	}
	public void setToCustomer(int toCustomer) {
		this.toCustomer = toCustomer;
	}
	
	@Basic
    @Column(name = "runcost")
	public String getRuncost() {
		return runcost;
	}
	public void setRuncost(String runcost) {
		this.runcost = runcost;
	}
	
	@Basic
    @Column(name = "return_integral")
	public String getReturnIntegral() {
		return returnIntegral;
	}
	public void setReturnIntegral(String returnIntegral) {
		this.returnIntegral = returnIntegral;
	}
	
	@Basic
    @Column(name = "stock_integral")
	public String getStockIntegral() {
		return stockIntegral;
	}
	public void setStockIntegral(String stockIntegral) {
		this.stockIntegral = stockIntegral;
	}
	
	@Basic
    @Column(name = "integral_money_fee")
	public BigDecimal getIntegralMoneyFee() {
		return integralMoneyFee;
	}

	public void setIntegralMoneyFee(BigDecimal integralMoneyFee) {
		this.integralMoneyFee = integralMoneyFee;
	}
}
