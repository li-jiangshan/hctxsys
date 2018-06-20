package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_car", schema = "hctx_db", catalog = "")
public class YskGoodCarEntity {
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int uid;
    private int goodId;
    private int createTime;
    private Integer goodNum;
    private YskGoodEntity goodEntity;
    private Integer priceId;
    private String attrValue;
    @Transient
    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    @OneToOne
    @JoinColumn(name = "good_id",referencedColumnName = "good_id",insertable = false,updatable = false)
    public YskGoodEntity getGoodEntity() {
        return goodEntity;
    }

    public void setGoodEntity(YskGoodEntity goodEntity) {
        this.goodEntity = goodEntity;
    }

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

    @Basic
    @Column(name = "good_num")
    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskGoodCarEntity that = (YskGoodCarEntity) o;
        return id == that.id &&
                uid == that.uid &&
                goodId == that.goodId &&
                createTime == that.createTime &&
                Objects.equals(goodNum, that.goodNum);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uid, goodId, createTime, goodNum);
    }

    @Basic
    @Column(name = "price_id")
    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }
}
