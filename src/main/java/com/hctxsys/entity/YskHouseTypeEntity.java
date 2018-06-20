package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_house_type", schema = "hctx_db", catalog = "")
public class YskHouseTypeEntity {
    private int houseType;
    private String typeName;

    @Id
    @Column(name = "house_type")
    public int getHouseType() {
        return houseType;
    }

    public void setHouseType(int houseType) {
        this.houseType = houseType;
    }

    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskHouseTypeEntity that = (YskHouseTypeEntity) o;
        return houseType == that.houseType &&
                Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(houseType, typeName);
    }
}
