package com.hctxsys.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_app_edition", schema = "hctx_db", catalog = "")
public class YskAppEditionEntity {

	// APP版本表id
	private int id;
	// APP版本
	private String appEdition;
	// app下载路径
	private String downloadingPath;
	// 是否强制更新：0代表是；1代表不是
	private int whetherUpdate;
	// 更新时间
	private Timestamp uptimeing;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppEdition() {
		return appEdition;
	}

	public void setAppEdition(String appEdition) {
		this.appEdition = appEdition;
	}

	public int getWhetherUpdate() {
		return whetherUpdate;
	}

	public void setWhetherUpdate(int whetherUpdate) {
		this.whetherUpdate = whetherUpdate;
	}

	public Timestamp getUptimeing() {
		return uptimeing;
	}

	public void setUptimeing(Timestamp uptimeing) {
		this.uptimeing = uptimeing;
	}

	public String getDownloadingPath() {
		return downloadingPath;
	}

	public void setDownloadingPath(String downloadingPath) {
		this.downloadingPath = downloadingPath;
	}

}
