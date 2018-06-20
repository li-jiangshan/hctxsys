package com.hctxsys.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_user_wealth_detail", schema = "hctx_db", catalog = "")
public class YskUserWealthDetailEntity {

	// 用户积分明细表ID
	private int id;
	// 用户ID
	private int uid;
	// 积分
	private BigDecimal integral;
	// 创建时间
	private int createTime;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getIntegral() {
		return integral;
	}

	public void setIntegral(BigDecimal integral) {
		this.integral = integral;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

}
