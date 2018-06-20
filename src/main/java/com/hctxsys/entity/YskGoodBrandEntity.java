package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_brand", schema = "hctx_db", catalog = "")
public class YskGoodBrandEntity {
    private int id;
    private String brandName;
    private Integer brandOrder;
    private String brandContent;
    private byte status;
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
    @Column(name = "brand_name")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Basic
    @Column(name = "brand_order")
    public Integer getBrandOrder() {
        return brandOrder;
    }

    public void setBrandOrder(Integer brandOrder) {
        this.brandOrder = brandOrder;
    }
    
    public void setBrandOrder(String brandOrder) {
    	if(brandOrder==null||brandOrder=="") {
    		brandOrder="0";
    	}
        this.brandOrder = Integer.valueOf(brandOrder);
    }

    @Basic
    @Column(name = "brand_content")
    public String getBrandContent() {
        return brandContent;
    }

    public void setBrandContent(String brandContent) {
        this.brandContent = brandContent;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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
        YskGoodBrandEntity that = (YskGoodBrandEntity) o;
        return id == that.id &&
                brandOrder == that.brandOrder &&
                status == that.status &&
                sellerId == that.sellerId &&
                Objects.equals(brandName, that.brandName) &&
                Objects.equals(brandContent, that.brandContent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, brandName, brandOrder, brandContent, status, sellerId);
    }
}
