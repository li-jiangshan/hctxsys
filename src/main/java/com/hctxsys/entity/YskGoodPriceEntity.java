package com.hctxsys.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_price", schema = "hctx_db", catalog = "")
public class YskGoodPriceEntity {
    private int id;
    private int goodId;
    private String goodAttrName;
    private String goodAttrValue;
    private int goodArrOrder;
    private BigDecimal price;
    private int store;
    private String goodAttrText;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "good_attr_name")
    public String getGoodAttrName() {
        return goodAttrName;
    }

    public void setGoodAttrName(String goodAttrName) {
        this.goodAttrName = goodAttrName;
    }

    @Basic
    @Column(name = "good_attr_value")
    public String getGoodAttrValue() {
        return goodAttrValue;
    }

    public void setGoodAttrValue(String goodAttrValue) {
        this.goodAttrValue = goodAttrValue;
    }

    @Basic
    @Column(name = "good_arr_order")
    public int getGoodArrOrder() {
        return goodArrOrder;
    }

    public void setGoodArrOrder(int goodArrOrder) {
        this.goodArrOrder = goodArrOrder;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "store")
    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    @Basic
    @Column(name = "good_attr_text")
    public String getGoodAttrText() {
        return goodAttrText;
    }

    public void setGoodAttrText(String goodAttrText) {
        this.goodAttrText = goodAttrText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskGoodPriceEntity that = (YskGoodPriceEntity) o;
        return id == that.id &&
                goodId == that.goodId &&
                goodArrOrder == that.goodArrOrder &&
                store == that.store &&
                Objects.equals(goodAttrName, that.goodAttrName) &&
                Objects.equals(goodAttrValue, that.goodAttrValue) &&
                Objects.equals(price, that.price) &&
                Objects.equals(goodAttrText, that.goodAttrText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, goodId, goodAttrName, goodAttrValue, goodArrOrder, price, store, goodAttrText);
    }
}
