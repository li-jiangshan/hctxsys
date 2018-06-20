package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_shop_collect", schema = "hctx_db", catalog = "")
public class YskShopCollectEntity {
    private int id;
    private int uid;
    private int sellerId;
    private int createTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "seller_id")
    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "create_time")
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskShopCollectEntity that = (YskShopCollectEntity) o;
        return id == that.id &&
                uid == that.uid &&
                sellerId == that.sellerId &&
                createTime == that.createTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uid, sellerId, createTime);
    }
}
