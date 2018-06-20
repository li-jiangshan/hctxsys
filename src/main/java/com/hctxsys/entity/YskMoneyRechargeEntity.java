package com.hctxsys.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ysk_money_recharge", schema = "hctx_db", catalog = "")
public class YskMoneyRechargeEntity {
    private int id;
    private String orderNo;
    private BigDecimal money;
    private int uid;
    private String type;
    private String typeName;
    private byte status;
    private String bankName;
    private String userName;
    private String cardNo;
    private String img;
    private int createTime;
    private byte fromType;
    private String content;
    private BigDecimal moneyRecord;
    private String reply;
    private Integer adminId;
    private BigDecimal fee;
    private int rId;
    private String username;
    private String account;
    private String mobile;
    private String paytype;
    private Integer payTime;

    @Id
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
    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Basic
    @Column(name = "admin_id")
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "fee")
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Basic
    @Column(name = "r_id")
    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "paytype")
    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    @Basic
    @Column(name = "pay_time")
    public Integer getPayTime() {
        return payTime;
    }

    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskMoneyRechargeEntity that = (YskMoneyRechargeEntity) o;
        return id == that.id &&
                uid == that.uid &&
                status == that.status &&
                createTime == that.createTime &&
                fromType == that.fromType &&
                rId == that.rId &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(money, that.money) &&
                Objects.equals(type, that.type) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(cardNo, that.cardNo) &&
                Objects.equals(img, that.img) &&
                Objects.equals(content, that.content) &&
                Objects.equals(moneyRecord, that.moneyRecord) &&
                Objects.equals(reply, that.reply) &&
                Objects.equals(adminId, that.adminId) &&
                Objects.equals(fee, that.fee) &&
                Objects.equals(username, that.username) &&
                Objects.equals(account, that.account) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(paytype, that.paytype) &&
                Objects.equals(payTime, that.payTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderNo, money, uid, type, typeName, status, bankName, userName, cardNo, img, createTime, fromType, content, moneyRecord, reply, adminId, fee, rId, username, account, mobile, paytype, payTime);
    }
}
