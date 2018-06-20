package com.hctxsys.vo.api;

import com.hctxsys.entity.YskGoodCarEntity;
import com.hctxsys.entity.YskUserAddressEntity;
import com.hctxsys.entity.YskUserEntity;

import java.util.List;

public class UserVo {
    private YskUserEntity userEntity;
    private YskUserAddressEntity addressEntity;
    private List<YskGoodCarEntity> carList;
    private List<YskUserAddressEntity> addressList;
    private int isDefault;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public YskUserAddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(YskUserAddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public List<YskUserAddressEntity> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<YskUserAddressEntity> addressList) {
        this.addressList = addressList;
    }

    public List<YskGoodCarEntity> getCarList() {
        return carList;
    }

    public void setCarList(List<YskGoodCarEntity> carList) {
        this.carList = carList;
    }

    public YskUserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(YskUserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
