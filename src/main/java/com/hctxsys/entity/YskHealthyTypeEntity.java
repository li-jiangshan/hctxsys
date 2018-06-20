package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_healthy_type", schema = "hctx_db", catalog = "")
public class YskHealthyTypeEntity {
    private int healthyType;
    private String typeName;

    @Id
    @Column(name = "healthy_type")
    public int getHealthyType() {
        return healthyType;
    }

    public void setHealthyType(int healthyType) {
        this.healthyType = healthyType;
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
        YskHealthyTypeEntity that = (YskHealthyTypeEntity) o;
        return healthyType == that.healthyType &&
                Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(healthyType, typeName);
    }
}
