package com.hctxsys.vo;

import java.util.List;

import com.hctxsys.entity.YskTravelEntity;

public class TravelVo extends YskTravelEntity {
    private List<String> imgList;

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
