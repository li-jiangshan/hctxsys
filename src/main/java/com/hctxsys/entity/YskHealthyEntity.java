package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_healthy", schema = "hctx_db", catalog = "")
public class YskHealthyEntity {
	private int id;
	private String title;
	private String phone;
	private	String content;
	private int healthyType;
	private String contacts;
	private YskHealthyTypeEntity type;
    private String coverimg;
    private String province;
    private String city;
    private String district;
    private String detailed;

    @OneToOne
    @JoinColumn(name = "healthy_type",referencedColumnName = "healthy_type",insertable = false,updatable = false)
	public YskHealthyTypeEntity getType() {
		return type;
	}

	public void setType(YskHealthyTypeEntity type) {
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
    @Column(name = "phone")
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
    @Column(name = "healthy_type")
    public int getHealthyType() {
		return healthyType;
	}

	public void setHealthyType(int healthyType) {
		this.healthyType = healthyType;
	}

	@Basic
    @Column(name = "contacts")
    public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

    @Basic
    @Column(name = "coverimg")
    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskHealthyEntity that = (YskHealthyEntity) o;
        return id == that.id &&
                healthyType == that.healthyType &&
                Objects.equals(title, that.title) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(content, that.content) &&
                Objects.equals(contacts, that.contacts) &&
                Objects.equals(coverimg, that.coverimg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, phone, content, healthyType, contacts, coverimg);
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
    @Column(name = "detailed")
	public String getDetailed() {
		return detailed;
	}

	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}
}