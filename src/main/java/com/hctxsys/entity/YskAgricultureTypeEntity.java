package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_agriculture_type", schema = "hctx_db", catalog = "")
public class YskAgricultureTypeEntity {
    private int agricultureType;
    private String agricultureName;

    @Id
    @Column(name = "agriculture_type")
    public int getAgricultureType() {
        return agricultureType;
    }

    public void setAgricultureType(int agricultureType) {
        this.agricultureType = agricultureType;
    }

    @Basic
    @Column(name = "agriculture_name")
    public String getAgricultureName() {
        return agricultureName;
    }

    public void setAgricultureName(String agricultureName) {
        this.agricultureName = agricultureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskAgricultureTypeEntity that = (YskAgricultureTypeEntity) o;
        return agricultureType == that.agricultureType &&
                Objects.equals(agricultureName, that.agricultureName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(agricultureType, agricultureName);
    }
}
