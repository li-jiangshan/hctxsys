package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_house", schema = "hctx_db", catalog = "")
public class YskHouseEntity {
    private int houseId;
    private String houseTitle;
    private String houseProvince;
    private String houseCity;
    private String houseDistrict;
    private String houseAddress;
    private int houseType;
    private int houseSize;
    private String houseDescription;
    private String houseText;
    private String houseCoverImg;
    private String contactsName;
    private String contactsPhone;
    private YskHouseTypeEntity yskHouseType;
    
    
    @OneToOne
    @JoinColumn(name="house_type",referencedColumnName="house_type",insertable=false,updatable=false)
    public YskHouseTypeEntity getYskHouseType() {
		return yskHouseType;
	}

	public void setYskHouseType(YskHouseTypeEntity yskHouseType) {
		this.yskHouseType = yskHouseType;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "house_id")
    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    @Basic
    @Column(name = "house_title")
    public String getHouseTitle() {
        return houseTitle;
    }

    public void setHouseTitle(String houseTitle) {
        this.houseTitle = houseTitle;
    }

    @Basic
    @Column(name = "house_province")
    public String getHouseProvince() {
        return houseProvince;
    }

    public void setHouseProvince(String houseProvince) {
        this.houseProvince = houseProvince;
    }

    @Basic
    @Column(name = "house_city")
    public String getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(String houseCity) {
        this.houseCity = houseCity;
    }

    @Basic
    @Column(name = "house_district")
    public String getHouseDistrict() {
        return houseDistrict;
    }

    public void setHouseDistrict(String houseDistrict) {
        this.houseDistrict = houseDistrict;
    }

    @Basic
    @Column(name = "house_address")
    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    @Basic
    @Column(name = "house_type")
    public int getHouseType() {
        return houseType;
    }

    public void setHouseType(int houseType) {
        this.houseType = houseType;
    }

    @Basic
    @Column(name = "house_size")
    public int getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(int houseSize) {
        this.houseSize = houseSize;
    }

    @Basic
    @Column(name = "house_description")
    public String getHouseDescription() {
        return houseDescription;
    }

    public void setHouseDescription(String houseDescription) {
        this.houseDescription = houseDescription;
    }

    @Basic
    @Column(name = "house_text")
    public String getHouseText() {
        return houseText;
    }

    public void setHouseText(String houseText) {
        this.houseText = houseText;
    }

    @Basic
    @Column(name = "house_cover_img")
    public String getHouseCoverImg() {
        return houseCoverImg;
    }

    public void setHouseCoverImg(String houseCoverImg) {
        this.houseCoverImg = houseCoverImg;
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
        YskHouseEntity that = (YskHouseEntity) o;
        return houseId == that.houseId &&
                houseType == that.houseType &&
                houseSize == that.houseSize &&
                Objects.equals(houseTitle, that.houseTitle) &&
                Objects.equals(houseProvince, that.houseProvince) &&
                Objects.equals(houseCity, that.houseCity) &&
                Objects.equals(houseDistrict, that.houseDistrict) &&
                Objects.equals(houseAddress, that.houseAddress) &&
                Objects.equals(houseDescription, that.houseDescription) &&
                Objects.equals(houseText, that.houseText) &&
                Objects.equals(houseCoverImg, that.houseCoverImg) &&
                Objects.equals(contactsName, that.contactsName) &&
                Objects.equals(contactsPhone, that.contactsPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(houseId, houseTitle, houseProvince, houseCity, houseDistrict, houseAddress, houseType, houseSize, houseDescription, houseText, houseCoverImg, contactsName, contactsPhone);
    }
}
