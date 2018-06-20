package com.hctxsys.vo.api;

import com.hctxsys.entity.YskUserWealthEntity;

import java.math.BigDecimal;

public class ApiWealthVo {
    private YskUserWealthEntity wealth;
    private int userId;
    private int bankNum;
    private int type;
    private int status;
    private int pageNo;
    private BigDecimal integralAdd;

    public BigDecimal getIntegralAdd() {
        return integralAdd;
    }

    public void setIntegralAdd(BigDecimal integralAdd) {
        this.integralAdd = integralAdd;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public YskUserWealthEntity getWealth() {
        return wealth;
    }

    public void setWealth(YskUserWealthEntity wealth) {
        this.wealth = wealth;
    }

    public int getBankNum() {
        return bankNum;
    }

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }
}
