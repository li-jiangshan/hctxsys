package com.hctxsys.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ysk_huabao_detail", schema = "hctx_db", catalog = "")
public class YskHuabaoDetailEntity {
    private int id;
    private BigDecimal money;
    private int uid;
    private String type;
    private String typeName;
    private byte status;
    private int createTime;
    private byte fromType;
    private String content;
    private BigDecimal moneyRecord;
    private BigDecimal fee;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "money")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
    @Column(name = "create_time")
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "from_type")
    public byte getFromType() {
        return fromType;
    }

    public void setFromType(byte fromType) {
        this.fromType = fromType;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "money_record")
    public BigDecimal getMoneyRecord() {
        return moneyRecord;
    }

    public void setMoneyRecord(BigDecimal moneyRecord) {
        this.moneyRecord = moneyRecord;
    }

    @Basic
    @Column(name = "fee")
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskHuabaoDetailEntity that = (YskHuabaoDetailEntity) o;
        return id == that.id &&
                uid == that.uid &&
                status == that.status &&
                createTime == that.createTime &&
                fromType == that.fromType &&
                Objects.equals(money, that.money) &&
                Objects.equals(type, that.type) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(moneyRecord, that.moneyRecord) &&
                Objects.equals(fee, that.fee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, money, uid, type, typeName, status, createTime, fromType, content, moneyRecord, fee);
    }
}
