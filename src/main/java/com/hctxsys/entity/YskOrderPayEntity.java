package com.hctxsys.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ysk_order_pay", schema = "hctx_db", catalog = "")
public class YskOrderPayEntity {
    private int id;
    private String orderNo;
    private String orderIdList;
    private BigDecimal orderTotalPrice;
    private byte orderStatus;
    private int userId;

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
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "order_id_list")
    public String getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(String orderIdList) {
        this.orderIdList = orderIdList;
    }

    @Basic
    @Column(name = "order_total_price")
    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    @Basic
    @Column(name = "order_status")
    public byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskOrderPayEntity that = (YskOrderPayEntity) o;
        return id == that.id &&
                orderStatus == that.orderStatus &&
                userId == that.userId &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(orderIdList, that.orderIdList) &&
                Objects.equals(orderTotalPrice, that.orderTotalPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderNo, orderIdList, orderTotalPrice, orderStatus, userId);
    }
}
