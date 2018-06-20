package com.hctxsys.vo.api;

import com.hctxsys.entity.YskGoodCategoryEntity;

import java.util.List;

public class ApiGoodCategoryVo {
    private List<YskGoodCategoryEntity> goodCategoryList;

    public List<YskGoodCategoryEntity> getGoodCategoryList() {
        return goodCategoryList;
    }

    public void setGoodCategoryList(List<YskGoodCategoryEntity> goodCategoryList) {
        this.goodCategoryList = goodCategoryList;
    }
}
