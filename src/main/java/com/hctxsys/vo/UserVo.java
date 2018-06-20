package com.hctxsys.vo;

import com.hctxsys.entity.YskUserEntity;

public class UserVo extends YskUserEntity {
    private String parentName;
    private String areaProvince;
    private String areaCity;
    private String areaDistrict;

    @Override
    public String getAreaProvince() {
        if (this.areaProvince==null) return "";
        return areaProvince;
    }

    @Override
    public void setAreaProvince(String areaProvince) {
        this.areaProvince = areaProvince;
    }

    @Override
    public String getAreaCity() {
        if (this.areaCity==null) return "";
        return areaCity;
    }

    @Override
    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    @Override
    public String getAreaDistrict() {
        if (this.areaDistrict==null) return "";
        return areaDistrict;
    }

    @Override
    public void setAreaDistrict(String areaDistrict) {
        this.areaDistrict = areaDistrict;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
