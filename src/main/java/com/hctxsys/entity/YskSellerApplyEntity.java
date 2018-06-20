package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_seller_apply", schema = "hctx_db", catalog = "")
public class YskSellerApplyEntity {
    private int id;
    private int uid;
    private String username;
    private String mobile;
    private String account;
    private String conName;
    private String conMobile;
    private String conEmail;
    private String shopName;
    private String responName;
    private String responMobile;
    private String responEmail;
    private String province;
    private String city;
    private String district;
    private String addresssDetail;
    private int createTime;
    private Integer adminId;
    private byte status;
    private int industryId;
    private int fee;
    private String industryName;
    private String shopJ;
    private String shopW;
    private YskUserCheckinfoEntity userinfo;
    
    @OneToOne
    @JoinColumn(name="uid",insertable=false,updatable=false)
    public YskUserCheckinfoEntity getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(YskUserCheckinfoEntity userinfo) {
		this.userinfo = userinfo;
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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "con_name")
    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    @Basic
    @Column(name = "con_mobile")
    public String getConMobile() {
        return conMobile;
    }

    public void setConMobile(String conMobile) {
        this.conMobile = conMobile;
    }

    @Basic
    @Column(name = "con_email")
    public String getConEmail() {
        return conEmail;
    }

    public void setConEmail(String conEmail) {
        this.conEmail = conEmail;
    }

    @Basic
    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Basic
    @Column(name = "respon_name")
    public String getResponName() {
        return responName;
    }

    public void setResponName(String responName) {
        this.responName = responName;
    }

    @Basic
    @Column(name = "respon_mobile")
    public String getResponMobile() {
        return responMobile;
    }

    public void setResponMobile(String responMobile) {
        this.responMobile = responMobile;
    }

    @Basic
    @Column(name = "respon_email")
    public String getResponEmail() {
        return responEmail;
    }

    public void setResponEmail(String responEmail) {
        this.responEmail = responEmail;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "addresss_detail")
    public String getAddresssDetail() {
        return addresssDetail;
    }

    public void setAddresssDetail(String addresssDetail) {
        this.addresssDetail = addresssDetail;
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
    @Column(name = "admin_id")
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
    @Column(name = "industry_id")
    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    @Basic
    @Column(name = "fee")
    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @Basic
    @Column(name = "industry_name")
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
    
    @Basic
    @Column(name = "shop_j")
    public String getShopJ() {
		return shopJ;
	}

	public void setShopJ(String shopJ) {
		this.shopJ = shopJ;
	}
	
    @Basic
    @Column(name = "shop_w")
	public String getShopW() {
		return shopW;
	}

	public void setShopW(String shopW) {
		this.shopW = shopW;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskSellerApplyEntity that = (YskSellerApplyEntity) o;
        return id == that.id &&
                uid == that.uid &&
                createTime == that.createTime &&
                status == that.status &&
                industryId == that.industryId &&
                fee == that.fee &&
                Objects.equals(username, that.username) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(account, that.account) &&
                Objects.equals(conName, that.conName) &&
                Objects.equals(conMobile, that.conMobile) &&
                Objects.equals(conEmail, that.conEmail) &&
                Objects.equals(shopName, that.shopName) &&
                Objects.equals(responName, that.responName) &&
                Objects.equals(responMobile, that.responMobile) &&
                Objects.equals(responEmail, that.responEmail) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(district, that.district) &&
                Objects.equals(addresssDetail, that.addresssDetail) &&
                Objects.equals(adminId, that.adminId) &&
                Objects.equals(industryName, that.industryName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uid, username, mobile, account, conName, conMobile, conEmail, shopName, responName, responMobile, responEmail, province, city, district, addresssDetail, createTime, adminId, status, industryId, fee, industryName);
    }
}
