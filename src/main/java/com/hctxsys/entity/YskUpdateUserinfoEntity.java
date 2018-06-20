package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_update_userinfo", schema = "hctx_db", catalog = "")
public class YskUpdateUserinfoEntity {
    private int id;
    private int uid;
    private byte updateType;
    private String newInfo;
    private String content;
    private String img;
    private Integer status;
    private String reply;
    private Integer adminId;
    private int createTime;
    private Integer updateTime;
    private String imgBack;
    private String maxDistributionIntegral;
    private String countDistributionIntegral;
    private YskUserEntity user;
    @Transient
    public YskUserEntity getUser() {
        return user;
    }

    public void setUser(YskUserEntity user) {
        this.user = user;
    }

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
    @Column(name = "update_type")
    public byte getUpdateType() {
        return updateType;
    }

    public void setUpdateType(byte updateType) {
        this.updateType = updateType;
    }

    @Basic
    @Column(name = "new_info")
    public String getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(String newInfo) {
        this.newInfo = newInfo;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Basic
    @Column(name = "admin_id")
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "create_time")
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "img_back")
    public String getImgBack() {
        return imgBack;
    }

    public void setImgBack(String imgBack) {
        this.imgBack = imgBack;
    }
    
    @Basic
    @Column(name = "max_distribution_integral")
    public String getMaxDistributionIntegral() {
		return maxDistributionIntegral;
	}

	public void setMaxDistributionIntegral(String maxDistributionIntegral) {
		this.maxDistributionIntegral = maxDistributionIntegral;
	}
	
    @Basic
    @Column(name = "count_distribution_integral")
	public String getCountDistributionIntegral() {
		return countDistributionIntegral;
	}

	public void setCountDistributionIntegral(String countDistributionIntegral) {
		this.countDistributionIntegral = countDistributionIntegral;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskUpdateUserinfoEntity that = (YskUpdateUserinfoEntity) o;
        return id == that.id &&
                uid == that.uid &&
                updateType == that.updateType &&
                status == that.status &&
                createTime == that.createTime &&
                Objects.equals(newInfo, that.newInfo) &&
                Objects.equals(content, that.content) &&
                Objects.equals(img, that.img) &&
                Objects.equals(reply, that.reply) &&
                Objects.equals(adminId, that.adminId) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(imgBack, that.imgBack);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uid, updateType, newInfo, content, img, status, reply, adminId, createTime, updateTime, imgBack);
    }
}
