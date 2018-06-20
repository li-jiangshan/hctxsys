package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_attribute", schema = "hctx_db", catalog = "")
public class YskGoodAttributeEntity {
    private int id;
    private Integer modelId;
    private String attrName;
    private Integer attrOrder;
    private Byte searchIndex;
    private String attrValue;
    private int sellerId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "attr_name")
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    @Basic
    @Column(name = "attr_order")
    public Integer getAttrOrder() {
        return attrOrder;
    }

    public void setAttrOrder(Integer attrOrder) {
        this.attrOrder = attrOrder;
    }

    @Basic
    @Column(name = "search_index")
    public Byte getSearchIndex() {
        return searchIndex;
    }

    public void setSearchIndex(Byte searchIndex) {
        this.searchIndex = searchIndex;
    }

    @Basic
    @Column(name = "attr_value")
    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    @Basic
    @Column(name = "seller_id")
    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskGoodAttributeEntity that = (YskGoodAttributeEntity) o;
        return id == that.id &&
                sellerId == that.sellerId &&
                Objects.equals(modelId, that.modelId) &&
                Objects.equals(attrName, that.attrName) &&
                Objects.equals(attrOrder, that.attrOrder) &&
                Objects.equals(searchIndex, that.searchIndex) &&
                Objects.equals(attrValue, that.attrValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, modelId, attrName, attrOrder, searchIndex, attrValue, sellerId);
    }
}
