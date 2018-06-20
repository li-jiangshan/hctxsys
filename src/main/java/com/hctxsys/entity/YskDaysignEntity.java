package com.hctxsys.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_daysign", schema = "hctx_db", catalog = "")
public class YskDaysignEntity {
	
	private int id;
	private int uid;
	private String year;
	private String moth;
	private String day;
	private int totalday;
	private int lianDay;
	private String savetime;
	private int totalJifen;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	@Column(name = "year")
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Basic
	@Column(name = "moth")
	public String getMoth() {
		return moth;
	}
	public void setMoth(String moth) {
		this.moth = moth;
	}
	
	@Basic
	@Column(name = "day")
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	@Basic
	@Column(name = "totalday")
	public int getTotalday() {
		return totalday;
	}
	public void setTotalday(int totalday) {
		this.totalday = totalday;
	}
	
	@Basic
	@Column(name = "lian_day")
	public int getLianDay() {
		return lianDay;
	}
	public void setLianDay(int lianDay) {
		this.lianDay = lianDay;
	}
	
	@Basic
	@Column(name = "savetime")
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	
	@Basic
	@Column(name = "total_jifen")
	public int getTotalJifen() {
		return totalJifen;
	}
	public void setTotalJifen(int totalJifen) {
		this.totalJifen = totalJifen;
	}

}