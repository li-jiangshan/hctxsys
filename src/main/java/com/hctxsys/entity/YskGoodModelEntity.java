package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_model", schema = "hctx_db", catalog = "")
public class YskGoodModelEntity {
    private int id;
    private String modelName;
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
    @Column(name = "model_name")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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
        YskGoodModelEntity that = (YskGoodModelEntity) o;
        return id == that.id &&
                sellerId == that.sellerId &&
                Objects.equals(modelName, that.modelName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, modelName, sellerId);
    }
}
