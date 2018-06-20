package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_good_img", schema = "hctx_db", catalog = "")
public class YskGoodImgEntity {
    private int id;
    private int goodId;
    private String imgUrl;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "good_id")
    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    @Basic
    @Column(name = "img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskGoodImgEntity that = (YskGoodImgEntity) o;
        return id == that.id &&
                goodId == that.goodId &&
                Objects.equals(imgUrl, that.imgUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, goodId, imgUrl);
    }
}
