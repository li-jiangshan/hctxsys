package com.hctxsys.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ysk_car", schema = "hctx_db", catalog = "")
public class YskCarEntity {
    private int moduleId;
    private String content;
    private String contact;
    private String phone;
    private String coverImg;
    private String title;
    private String richContent;
    private int typeId;
    private String detailContent;
    private int carType;
    private String province;
    private String city;
    private String district;
    private YskCarTypeEntity carBrand;
    @OneToOne
    @JoinColumn(name ="typeID" ,referencedColumnName ="type_id" ,updatable = false,insertable = false)
    public YskCarTypeEntity getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(YskCarTypeEntity carBrand) {
        this.carBrand = carBrand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "cover_img")
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "rich_content")
    public String getRichContent() {
        return richContent;
    }

    public void setRichContent(String richContent) {
        this.richContent = richContent;
    }

    @Basic
    @Column(name = "typeID")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "detail_content")
    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    @Basic
    @Column(name = "car_type")
    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskCarEntity that = (YskCarEntity) o;
        return moduleId == that.moduleId &&
                typeId == that.typeId &&
                carType == that.carType &&
                Objects.equals(content, that.content) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(coverImg, that.coverImg) &&
                Objects.equals(title, that.title) &&
                Objects.equals(richContent, that.richContent) &&
                Objects.equals(detailContent, that.detailContent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(moduleId, content, contact, phone, coverImg, title , richContent, typeId, detailContent, carType);
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) { this.province = province;
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
}
