package com.hctxsys.vo;

import com.hctxsys.entity.UserEntity;

public class MemberVo extends UserEntity {
    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
