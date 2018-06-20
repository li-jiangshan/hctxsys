package com.hctxsys.entity;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_buy_kucun_integral_apply", schema = "hctx_db", catalog = "")
public class YskBuyKucunIntegralApplyEntity {

	//购买库存积分申请id
	private int id;
	//购买金额
	private BigDecimal money;
	//购买库存积分
	private BigDecimal kucunIntegral;
	//是否发放正常积分 0 否 1 是
	private byte hasIntegral;
	//发放积分
	private BigDecimal integral;
	//凭证图片
	private String voucherImg;
	//平台运营费
	private BigDecimal operatingAmount;
	//返现额度
	private BigDecimal returnAmount;
	//用户ID
	private int uid;
	//0-申请中 1-已通过 2-已拒绝
	private byte status;
	//时间
	private int createTime;
	
	@Id
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Basic
    @Column(name = "money")
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@Basic
    @Column(name = "kucun_integral")
	public BigDecimal getKucunIntegral() {
		return kucunIntegral;
	}
	public void setKucunIntegral(BigDecimal kucunIntegral) {
		this.kucunIntegral = kucunIntegral;
	}
	
	@Basic
    @Column(name = "has_integral")
	public byte getHasIntegral() {
		return hasIntegral;
	}
	public void setHasIntegral(byte hasIntegral) {
		this.hasIntegral = hasIntegral;
	}
	
	@Basic
    @Column(name = "integral")
	public BigDecimal getIntegral() {
		return integral;
	}
	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}
	
	@Basic
    @Column(name = "voucher_img")
	public String getVoucherImg() {
		return voucherImg;
	}
	public void setVoucherImg(String voucherImg) {
		this.voucherImg = voucherImg;
	}
	
	@Basic
    @Column(name = "operating_amount")
	public BigDecimal getOperatingAmount() {
		return operatingAmount;
	}
	public void setOperatingAmount(BigDecimal operatingAmount) {
		this.operatingAmount = operatingAmount;
	}
	
	@Basic
    @Column(name = "return_amount")
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}
	
	@Basic
    @Column(name = "uid")
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	@Basic
    @Column(name = "status")
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
	@Basic
    @Column(name = "create_time")
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	
}
