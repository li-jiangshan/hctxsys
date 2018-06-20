package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_shop_info", schema = "hctx_db", catalog = "")
public class YskShopInfoEntity {
    private int uid;
    private String shopName;
    private String shopImg;
    private String shopLogo;
    private int shopComment;
    private int shopCollect;
    private String responName;
    private String responMobile;
    private String responEmail;
    private String province;
    private String city;
    private String district;
    private String addresssDetail;
    private int createTime;
    private String industryId;
    private int fee;
    private String industryName;
    private String content;
    private String serverTel;
    private String workTime;
    private String shopJ;
    private String shopW;
    private Double distance;
    private Integer starAbility;
    private Integer starAttitude;
    private Integer starPrice;
    private Integer general;
    private YskUserCheckinfoEntity userCheckinfo;
    private YskUserEntity user;
    private Integer isSelfShop;
    
    
    @Basic
    @Column(name = "is_self_shop")
    public Integer getIsSelfShop() {
		return isSelfShop;
	}
	public void setIsSelfShop(Integer isSelfShop) {
		this.isSelfShop = isSelfShop;
	}
	
	@Transient
    public Integer getGeneral() {
		return general;
	}
	public void setGeneral(Integer general) {
		this.general = general;
	}
	@Transient
    public Integer getStarAbility() {
		return starAbility;
	}
	public void setStarAbility(Integer starAbility) {
		this.starAbility = starAbility;
	}
	@Transient
	public Integer getStarAttitude() {
		return starAttitude;
	}
	public void setStarAttitude(Integer starAttitude) {
		this.starAttitude = starAttitude;
	}
	@Transient
	public Integer getStarPrice() {
		return starPrice;
	}
	public void setStarPrice(Integer starPrice) {
		this.starPrice = starPrice;
	}
	@Transient
    public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	@OneToOne
    @JoinColumn(name = "uid",referencedColumnName = "uid",updatable = false,insertable = false)
    public YskUserCheckinfoEntity getUserCheckinfo() {
        return userCheckinfo;
    }
    public void setUserCheckinfo(YskUserCheckinfoEntity userCheckinfo) {
        this.userCheckinfo = userCheckinfo;
    }
    @OneToOne
    @JoinColumn(name = "uid",referencedColumnName = "userId",updatable = false,insertable = false)
   
    
    
    public YskUserEntity getUser() {
        return user;
    }

    public void setUser(YskUserEntity user) {
        this.user = user;
    }

    @Id
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
    @Column(name = "shop_img")
    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    @Basic
    @Column(name = "shop_logo")
    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    @Basic
    @Column(name = "shop_comment")
    public int getShopComment() {
        return shopComment;
    }

    public void setShopComment(int shopComment) {
        this.shopComment = shopComment;
    }

    @Basic
    @Column(name = "shop_collect")
    public int getShopCollect() {
        return shopCollect;
    }

    public void setShopCollect(int shopCollect) {
        this.shopCollect = shopCollect;
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
    @Column(name = "industry_id")
    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "server_tel")
    public String getServerTel() {
        return serverTel;
    }

    public void setServerTel(String serverTel) {
        this.serverTel = serverTel;
    }

    @Basic
    @Column(name = "work_time")
    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
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
        YskShopInfoEntity that = (YskShopInfoEntity) o;
        return uid == that.uid &&
                shopComment == that.shopComment &&
                shopCollect == that.shopCollect &&
                createTime == that.createTime &&
                fee == that.fee &&
                Objects.equals(shopName, that.shopName) &&
                Objects.equals(shopImg, that.shopImg) &&
                Objects.equals(shopLogo, that.shopLogo) &&
                Objects.equals(responName, that.responName) &&
                Objects.equals(responMobile, that.responMobile) &&
                Objects.equals(responEmail, that.responEmail) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(district, that.district) &&
                Objects.equals(addresssDetail, that.addresssDetail) &&
                Objects.equals(industryId, that.industryId) &&
                Objects.equals(industryName, that.industryName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(serverTel, that.serverTel) &&
                Objects.equals(workTime, that.workTime) &&
                Objects.equals(shopJ, that.shopJ) &&
                Objects.equals(shopW, that.shopW);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, shopName, shopImg, shopLogo, shopComment, shopCollect, responName, responMobile, responEmail, province, city, district, addresssDetail, createTime, industryId, fee, industryName, content, serverTel, workTime, shopJ, shopW);
    }
}
