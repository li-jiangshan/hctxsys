package com.hctxsys.vo.api;

import com.hctxsys.entity.YskOrderEntity;

import java.util.List;

public class OrderPageVo extends YskOrderEntity {
    private int pageNo;
    private int pageSize;
    private List<YskOrderEntity> orderList;

    public List<YskOrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<YskOrderEntity> orderList) {
        this.orderList = orderList;
    }
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        if(pageSize==0) return 10;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
