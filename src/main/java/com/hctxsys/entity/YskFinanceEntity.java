package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_finance", schema = "hctx_db", catalog = "")
public class YskFinanceEntity {
    private int moduleId;
    private String content;
    private String contact;
    private String phone;
    private String title;
    private String richContent;
    private int typeId;
    private String detailContent;
    private String province;
    private String city;
    private String district;
    private YskFinanceTypeEntity type;
    private String coverimg;

    @OneToOne
    
    @JoinColumn(name = "typeID",referencedColumnName = "type_id",insertable = false,updatable = false)
    public YskFinanceTypeEntity getType() {
        return type;
    }

    public void setType(YskFinanceTypeEntity type) {
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskFinanceEntity that = (YskFinanceEntity) o;
        return moduleId == that.moduleId &&
                typeId == that.typeId &&
                Objects.equals(content, that.content) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(title, that.title) &&
                Objects.equals(richContent, that.richContent) &&
                Objects.equals(detailContent, that.detailContent) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(district, that.district);
    }

    @Override
    public int hashCode() {

        return Objects.hash(moduleId, content, contact, phone, title, richContent, typeId, detailContent, province, city, district);
    }

    @Basic
    @Column(name = "coverimg")
    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }
}
