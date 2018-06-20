package com.hctxsys.entity;

import javax.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ysk_user_checkinfo", schema = "hctx_db", catalog = "")
public class YskUserCheckinfoEntity {
    private int uid;
    private byte isCheckMobile;
    private String province;
    private String city;
    private String district;
    private String country;
    private String idcard;
    private Date idcarStartdate;
    private Date idcarEndtdate;
    private byte idcardType;
    private String idcardImgFace;
    private String idcardImgBack;
    private String idcardImgHand;
    private byte isCheckUser;
    private byte userType;
    private byte isThreeCard;
    private byte isChildCompany;
    private String creditNo;
    private String taxNo;
    private String organizeNo;
    private String legalName;
    private String companyType;
    private byte isLegal;
    private String manageParent;
    private String companyName;
    private String companyLicense;
    private String companyOrganize;
    private byte isCheckCompany;
    private String companyLicenseImg;
    private int createTime;

    @Id
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "is_check_mobile")
    public byte getIsCheckMobile() {
        return isCheckMobile;
    }

    public void setIsCheckMobile(byte isCheckMobile) {
        this.isCheckMobile = isCheckMobile;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province==null?"":province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city==null?"":city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "district")
    public String getDistrict() {
        return district==null?"":district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "idcard")
    public String getIdcard() {
        return idcard==null?"":idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Basic
    @Column(name = "idcar_startdate")
    public Date getIdcarStartdate() {
        return idcarStartdate;
    }

    public void setIdcarStartdate(Date idcarStartdate) {
        this.idcarStartdate = idcarStartdate;
    }

    @Basic
    @Column(name = "idcar_endtdate")
    public Date getIdcarEndtdate() {
        return idcarEndtdate;
    }

    public void setIdcarEndtdate(Date idcarEndtdate) {
        this.idcarEndtdate = idcarEndtdate;
    }

    @Basic
    @Column(name = "idcard_type")
    public byte getIdcardType() {
        return idcardType;
    }

    public void setIdcardType(byte idcardType) {
        this.idcardType = idcardType;
    }

    @Basic
    @Column(name = "idcard_img_face")
    public String getIdcardImgFace() {
        return idcardImgFace;
    }

    public void setIdcardImgFace(String idcardImgFace) {
        this.idcardImgFace = idcardImgFace;
    }

    @Basic
    @Column(name = "idcard_img_back")
    public String getIdcardImgBack() {
        return idcardImgBack;
    }

    public void setIdcardImgBack(String idcardImgBack) {
        this.idcardImgBack = idcardImgBack;
    }

    @Basic
    @Column(name = "idcard_img_hand")
    public String getIdcardImgHand() {
        return idcardImgHand;
    }

    public void setIdcardImgHand(String idcardImgHand) {
        this.idcardImgHand = idcardImgHand;
    }

    @Basic
    @Column(name = "is_check_user")
    public byte getIsCheckUser() {
        return isCheckUser;
    }

    public void setIsCheckUser(byte isCheckUser) {
        this.isCheckUser = isCheckUser;
    }

    @Basic
    @Column(name = "user_type")
    public byte getUserType() {
        return userType;
    }

    public void setUserType(byte userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "is_three_card")
    public byte getIsThreeCard() {
        return isThreeCard;
    }

    public void setIsThreeCard(byte isThreeCard) {
        this.isThreeCard = isThreeCard;
    }

    @Basic
    @Column(name = "is_child_company")
    public byte getIsChildCompany() {
        return isChildCompany;
    }

    public void setIsChildCompany(byte isChildCompany) {
        this.isChildCompany = isChildCompany;
    }

    @Basic
    @Column(name = "credit_no")
    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    @Basic
    @Column(name = "tax_no")
    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    @Basic
    @Column(name = "organize_no")
    public String getOrganizeNo() {
        return organizeNo;
    }

    public void setOrganizeNo(String organizeNo) {
        this.organizeNo = organizeNo;
    }

    @Basic
    @Column(name = "legal_name")
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    @Basic
    @Column(name = "company_type")
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Basic
    @Column(name = "is_legal")
    public byte getIsLegal() {
        return isLegal;
    }

    public void setIsLegal(byte isLegal) {
        this.isLegal = isLegal;
    }

    @Basic
    @Column(name = "manage_parent")
    public String getManageParent() {
        return manageParent;
    }

    public void setManageParent(String manageParent) {
        this.manageParent = manageParent;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "company_license")
    public String getCompanyLicense() {
        return companyLicense;
    }

    public void setCompanyLicense(String companyLicense) {
        this.companyLicense = companyLicense;
    }

    @Basic
    @Column(name = "company_organize")
    public String getCompanyOrganize() {
        return companyOrganize;
    }

    public void setCompanyOrganize(String companyOrganize) {
        this.companyOrganize = companyOrganize;
    }

    @Basic
    @Column(name = "is_check_company")
    public byte getIsCheckCompany() {
        return isCheckCompany;
    }

    public void setIsCheckCompany(byte isCheckCompany) {
        this.isCheckCompany = isCheckCompany;
    }

    @Basic
    @Column(name = "company_license_img")
    public String getCompanyLicenseImg() {
        return companyLicenseImg;
    }

    public void setCompanyLicenseImg(String companyLicenseImg) {
        this.companyLicenseImg = companyLicenseImg;
    }

    @Basic
    @Column(name = "create_time")
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskUserCheckinfoEntity that = (YskUserCheckinfoEntity) o;
        return uid == that.uid &&
                isCheckMobile == that.isCheckMobile &&
                idcardType == that.idcardType &&
                isCheckUser == that.isCheckUser &&
                userType == that.userType &&
                isThreeCard == that.isThreeCard &&
                isChildCompany == that.isChildCompany &&
                isLegal == that.isLegal &&
                isCheckCompany == that.isCheckCompany &&
                createTime == that.createTime &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(district, that.district) &&
                Objects.equals(country, that.country) &&
                Objects.equals(idcard, that.idcard) &&
                Objects.equals(idcarStartdate, that.idcarStartdate) &&
                Objects.equals(idcarEndtdate, that.idcarEndtdate) &&
                Objects.equals(idcardImgFace, that.idcardImgFace) &&
                Objects.equals(idcardImgBack, that.idcardImgBack) &&
                Objects.equals(idcardImgHand, that.idcardImgHand) &&
                Objects.equals(creditNo, that.creditNo) &&
                Objects.equals(taxNo, that.taxNo) &&
                Objects.equals(organizeNo, that.organizeNo) &&
                Objects.equals(legalName, that.legalName) &&
                Objects.equals(companyType, that.companyType) &&
                Objects.equals(manageParent, that.manageParent) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(companyLicense, that.companyLicense) &&
                Objects.equals(companyOrganize, that.companyOrganize) &&
                Objects.equals(companyLicenseImg, that.companyLicenseImg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, isCheckMobile, province, city, district, country, idcard, idcarStartdate, idcarEndtdate, idcardType, idcardImgFace, idcardImgBack, idcardImgHand, isCheckUser, userType, isThreeCard, isChildCompany, creditNo, taxNo, organizeNo, legalName, companyType, isLegal, manageParent, companyName, companyLicense, companyOrganize, isCheckCompany, companyLicenseImg, createTime);
    }
}
