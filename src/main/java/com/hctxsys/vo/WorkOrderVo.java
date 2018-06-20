package com.hctxsys.vo;

import com.hctxsys.entity.YskUpdateUserinfoEntity;

public class WorkOrderVo extends YskUpdateUserinfoEntity {
    private String username;
    private byte userType;
    private String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte getUserType() {
        return userType;
    }

    public void setUserType(byte userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
