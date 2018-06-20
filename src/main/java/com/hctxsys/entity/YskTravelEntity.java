package com.hctxsys.entity;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ysk_travel", schema = "hctx_db", catalog = "")
public class YskTravelEntity {
    private int moduleId;
    private String content;
    private String contact;
    private String phone;
    private String coverImg;
    private String title;
    private String richContent;
    private int typeId;
    private String detailContent;
    private Timestamp begindate;
    private String address;
    private String province;
    private String city;
    private String district;
    private YskTravelTypeEntity ysktraveltypeentity;
    @OneToOne
    @JoinColumn(name = "typeID",referencedColumnName = "type_id",insertable = false,updatable = false)
    public YskTravelTypeEntity getYsktraveltypeentity() {
		return ysktraveltypeentity;
	}

	public void setYsktraveltypeentity(YskTravelTypeEntity ysktraveltypeentity) {
		this.ysktraveltypeentity = ysktraveltypeentity;
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
    @Column(name = "begindate")
    public Timestamp getBegindate() {
        return begindate;
    }

    public void setBegindate(Timestamp begindate) {
        this.begindate = begindate;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskTravelEntity that = (YskTravelEntity) o;
        return moduleId == that.moduleId &&
                typeId == that.typeId &&
                Objects.equals(content, that.content) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(coverImg, that.coverImg) &&
                Objects.equals(title, that.title) &&
                Objects.equals(richContent, that.richContent) &&
                Objects.equals(detailContent, that.detailContent) &&
                Objects.equals(begindate, that.begindate) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(moduleId, content, contact, phone, coverImg, title,richContent, typeId, detailContent, begindate, address);
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

}
