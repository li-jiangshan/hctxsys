package com.hctxsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "ysk_banner", schema = "hctx_db", catalog = "")
public class YskBannerEntity {

    private int bannerId;
    private String bImg;
    private String bLink;
    private String bType;
    private String bName;
    private Integer bOrder;
    private int createTime;
    private int status;
    
    @Id
    @Column(name = "id")
	public int getBannerId() {
		return bannerId;
	}
	public void setBannerId(int bannerId) {
		this.bannerId = bannerId;
	}
	public String getbImg() {
		return bImg;
	}
	public void setbImg(String bImg) {
		this.bImg = bImg;
	}
	
	public String getbLink() {
		return bLink;
	}
	public void setbLink(String bLink) {
		this.bLink = bLink;
	}
	
	public String getbType() {
		return bType;
	}
	public void setbType(String bType) {
		this.bType = bType;
	}

	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}

	public Integer getbOrder() {
		return bOrder;
	}
	public void setbOrder(Integer bOrder) {
		this.bOrder = bOrder;
	}
	
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
