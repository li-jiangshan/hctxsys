package com.hctxsys.entity;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Objects;

@Entity
@Table(name = "ysk_admin", schema = "hctx_db", catalog = "")
public class YskAdminEntity {
    private int id;
    private int authId;
    private String nickname;
    private String username;
    private String password;
    private String mobile;
    private long regIp;
    private int createTime;
    private int updateTime;
    private byte status;
    private String regType;
    private YskGroupEntity groupEntity;

    @ManyToOne
	@JoinColumn(name = "auth_id", referencedColumnName = "id",insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public YskGroupEntity getGroupEntity() {
		return groupEntity;
	}

	public void setGroupEntity(YskGroupEntity groupEntity) {
		this.groupEntity = groupEntity;
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
    @Column(name = "auth_id")
    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "reg_ip")
    public long getRegIp() {
        return regIp;
    }

    public void setRegIp(long regIp) {
        this.regIp = regIp;
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
    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
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
    @Column(name = "reg_type")
    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskAdminEntity that = (YskAdminEntity) o;
        return id == that.id &&
                authId == that.authId &&
                regIp == that.regIp &&
                createTime == that.createTime &&
                updateTime == that.updateTime &&
                status == that.status &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(regType, that.regType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, authId, nickname, username, password, mobile, regIp, createTime, updateTime, status, regType);
    }

}
