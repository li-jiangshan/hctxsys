package com.hctxsys.util;

import com.hctxsys.entity.YskShopInfoEntity;

import java.util.Comparator;

public class DistanceComparator implements Comparator<YskShopInfoEntity> {
    @Override
    public int compare(YskShopInfoEntity o1, YskShopInfoEntity o2) {
        return (int)(o1.getDistance()-o2.getDistance());

    }
}
