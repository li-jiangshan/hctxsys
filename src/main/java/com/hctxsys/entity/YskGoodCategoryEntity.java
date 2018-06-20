package com.hctxsys.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_category", schema = "hctx_db", catalog = "")
public class YskGoodCategoryEntity {
    private short id;
    private String name;
    private short pid;
    private String pidPath;
    private Byte level;
    private Integer sortOrder;
    private byte isShow;
    private String image;
    private Byte isHot;
    private Byte catGroup;
    private Byte commissionRate;
    private List<YskGoodCategoryEntity> categoryList;
    @Transient
    public List<YskGoodCategoryEntity> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<YskGoodCategoryEntity> categoryList) {
        this.categoryList = categoryList;
    }

    @Id
    @Column(name = "id")
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Short.valueOf(id);
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pid")
    public short getPid() {
        return pid;
    }

    public void setPid(short pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "pid_path")
    public String getPidPath() {
        return pidPath;
    }

    public void setPidPath(String pidPath) {
        this.pidPath = pidPath;
    }

    @Basic
    @Column(name = "level")
    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    @Basic
    @Column(name = "sort_order")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Basic
    @Column(name = "is_show")
    public byte getIsShow() {
        return isShow;
    }

    public void setIsShow(byte isShow) {
        this.isShow = isShow;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "is_hot")
    public Byte getIsHot() {
        return isHot;
    }

    public void setIsHot(Byte isHot) {
        this.isHot = isHot;
    }

    @Basic
    @Column(name = "cat_group")
    public Byte getCatGroup() {
        return catGroup;
    }

    public void setCatGroup(Byte catGroup) {
        this.catGroup = catGroup;
    }

    @Basic
    @Column(name = "commission_rate")
    public Byte getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Byte commissionRate) {
        this.commissionRate = commissionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskGoodCategoryEntity that = (YskGoodCategoryEntity) o;
        return id == that.id &&
                pid == that.pid &&
                sortOrder == that.sortOrder &&
                isShow == that.isShow &&
                Objects.equals(name, that.name) &&
                Objects.equals(pidPath, that.pidPath) &&
                Objects.equals(level, that.level) &&
                Objects.equals(image, that.image) &&
                Objects.equals(isHot, that.isHot) &&
                Objects.equals(catGroup, that.catGroup) &&
                Objects.equals(commissionRate, that.commissionRate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, pid, pidPath, level, sortOrder, isShow, image, isHot, catGroup, commissionRate);
    }
}
