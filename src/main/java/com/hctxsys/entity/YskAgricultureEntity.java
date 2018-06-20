package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_agriculture", schema = "hctx_db", catalog = "")
public class YskAgricultureEntity {
    private int agricultureId;
    private int agricultureType;
    private String agricultureTitle;
    private String agricultureName;
    private String agricultureProvince;
    private String agricultureCity;
    private String agricultureDistrict;
    private String agricultureCoverImg;
    private String agricultureDescription;
    private String agricultureText;
    private String contactsName;
    private String contactsPhone;
    private YskAgricultureTypeEntity type;
    @OneToOne
    @JoinColumn(name = "agriculture_type",referencedColumnName = "agriculture_type",insertable = false,updatable = false)
    public YskAgricultureTypeEntity getType() {
        return type;
    }

    public void setType(YskAgricultureTypeEntity type) {
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "agriculture_id")
    public int getAgricultureId() {
        return agricultureId;
    }

    public void setAgricultureId(int agricultureId) {
        this.agricultureId = agricultureId;
    }

    @Basic
    @Column(name = "agriculture_type")
    public int getAgricultureType() {
        return agricultureType;
    }

    public void setAgricultureType(int agricultureType) {
        this.agricultureType = agricultureType;
    }

    @Basic
    @Column(name = "agriculture_title")
    public String getAgricultureTitle() {
        return agricultureTitle;
    }

    public void setAgricultureTitle(String agricultureTitle) {
        this.agricultureTitle = agricultureTitle;
    }

    @Basic
    @Column(name = "agriculture_name")
    public String getAgricultureName() {
        return agricultureName;
    }

    public void setAgricultureName(String agricultureName) {
        this.agricultureName = agricultureName;
    }

    @Basic
    @Column(name = "agriculture_province")
    public String getAgricultureProvince() {
        return agricultureProvince;
    }

    public void setAgricultureProvince(String agricultureProvince) {
        this.agricultureProvince = agricultureProvince;
    }

    @Basic
    @Column(name = "agriculture_city")
    public String getAgricultureCity() {
        return agricultureCity;
    }

    public void setAgricultureCity(String agricultureCity) {
        this.agricultureCity = agricultureCity;
    }

    @Basic
    @Column(name = "agriculture_district")
    public String getAgricultureDistrict() {
        return agricultureDistrict;
    }

    public void setAgricultureDistrict(String agricultureDistrict) {
        this.agricultureDistrict = agricultureDistrict;
    }

    @Basic
    @Column(name = "agriculture_cover_img")
    public String getAgricultureCoverImg() {
        return agricultureCoverImg;
    }

    public void setAgricultureCoverImg(String agricultureCoverImg) {
        this.agricultureCoverImg = agricultureCoverImg;
    }

    @Basic
    @Column(name = "agriculture_description")
    public String getAgricultureDescription() {
        return agricultureDescription;
    }

    public void setAgricultureDescription(String agricultureDescription) {
        this.agricultureDescription = agricultureDescription;
    }

    @Basic
    @Column(name = "agriculture_text")
    public String getAgricultureText() {
        return agricultureText;
    }

    public void setAgricultureText(String agricultureText) {
        this.agricultureText = agricultureText;
    }

    @Basic
    @Column(name = "contacts_name")
    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    @Basic
    @Column(name = "contacts_phone")
    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskAgricultureEntity that = (YskAgricultureEntity) o;
        return agricultureId == that.agricultureId &&
                agricultureType == that.agricultureType &&
                Objects.equals(agricultureTitle, that.agricultureTitle) &&
                Objects.equals(agricultureName, that.agricultureName) &&
                Objects.equals(agricultureProvince, that.agricultureProvince) &&
                Objects.equals(agricultureCity, that.agricultureCity) &&
                Objects.equals(agricultureDistrict, that.agricultureDistrict) &&
                Objects.equals(agricultureCoverImg, that.agricultureCoverImg) &&
                Objects.equals(agricultureDescription, that.agricultureDescription) &&
                Objects.equals(agricultureText, that.agricultureText) &&
                Objects.equals(contactsName, that.contactsName) &&
                Objects.equals(contactsPhone, that.contactsPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(agricultureId, agricultureType, agricultureTitle, agricultureName, agricultureProvince, agricultureCity, agricultureDistrict, agricultureCoverImg, agricultureDescription, agricultureText, contactsName, contactsPhone);
    }
}
