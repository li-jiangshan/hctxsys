package com.hctxsys.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_daydetail", schema = "hctx_db", catalog = "")
public class YskDaydetailEntity {

	private int dId;
	private int dUid;
	private String dAddtime;
	private String dContent;
	private int dMoney;

	@Id
	@Column(name = "d_id")
	public int getDId() {
		return dId;
	}
	public void setDId(int dId) {
		this.dId = dId;
	}
	
	@Basic
	@Column(name = "d_uid")
	public int getDUid() {
		return dUid;
	}
	public void setDUid(int dUid) {
		this.dUid = dUid;
	}
	
	@Basic
	@Column(name = "d_addtime")
	public String getDAddtime() {
		return dAddtime;
	}
	public void setDAddtime(String dAddtime) {
		this.dAddtime = dAddtime;
	}
	
	@Basic
	@Column(name = "d_content")
	public String getDContent() {
		return dContent;
	}
	public void setDContent(String dContent) {
		this.dContent = dContent;
	}
	
	@Basic
	@Column(name = "d_money")
	public int getDMoney() {
		return dMoney;
	}
	public void setDMoney(int dMoney) {
		this.dMoney = dMoney;
	}

}