package com.hctxsys.entity;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_amount_money", schema = "hctx_db", catalog = "")
public class YskAmountMoneyEntity {

	//id
	private int id;
	//金额
	private BigDecimal amountMoney;
	//时间
	private int createTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Basic
    @Column(name = "amount_money")
	public BigDecimal getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
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
