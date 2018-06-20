package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_user_bank", schema = "hctx_db", catalog = "")
public class YskUserBankEntity {
    private int id;
    private String bankName;
    private String bankNo;
    private String bankImg;
    private int uid;
    private String userName;
    private byte isDefault;
    private String bankBranch;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "bank_no")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Basic
    @Column(name = "bank_img")
    public String getBankImg() {
        return bankImg;
    }

    public void setBankImg(String bankImg) {
        this.bankImg = bankImg;
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
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "is_default")
    public byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(byte isDefault) {
        this.isDefault = isDefault;
    }

    @Basic
    @Column(name = "bank_branch")
    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskUserBankEntity that = (YskUserBankEntity) o;
        return id == that.id &&
                uid == that.uid &&
                isDefault == that.isDefault &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankNo, that.bankNo) &&
                Objects.equals(bankImg, that.bankImg) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(bankBranch, that.bankBranch);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, bankName, bankNo, bankImg, uid, userName, isDefault, bankBranch);
    }
}
