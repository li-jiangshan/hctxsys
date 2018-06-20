package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_collect", schema = "hctx_db", catalog = "")
public class YskGoodCollectEntity {
    private int id;
    private int uid;
    private int goodId;
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
    @Column(name = "good_id")
    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
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
        YskGoodCollectEntity that = (YskGoodCollectEntity) o;
        return id == that.id &&
                uid == that.uid &&
                goodId == that.goodId &&
                createTime == that.createTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uid, goodId, createTime);
    }
}
