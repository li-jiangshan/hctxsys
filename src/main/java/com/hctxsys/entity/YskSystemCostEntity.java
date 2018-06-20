package com.hctxsys.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_system_cost", schema = "hctx_db", catalog = "")
public class YskSystemCostEntity {

	// 平台费用表ID
	private int id;
	// 平台运营费
	private BigDecimal operatingAmount;
	// 返现额度
	private BigDecimal returnAmount;
	// 创建时间
	private int createTime;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getOperatingAmount() {
		return operatingAmount;
	}

	public void setOperatingAmount(BigDecimal operatingAmount) {
		this.operatingAmount = operatingAmount;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

}
