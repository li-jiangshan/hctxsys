package com.hctxsys.vo;

import com.hctxsys.entity.YskCarEntity;

import java.util.List;

public class CarVo extends YskCarEntity {
    private List<String> imgList;

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
