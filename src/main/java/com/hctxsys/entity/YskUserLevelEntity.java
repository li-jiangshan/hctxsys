package com.hctxsys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ysk_user_level", schema = "hctx_db", catalog = "")
public class YskUserLevelEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String levelName;
    private byte level;
    private BigDecimal levelFee;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "level_name")
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Basic
    @Column(name = "level")
    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    @Basic
    @Column(name = "level_fee")
    public BigDecimal getLevelFee() {
        return levelFee;
    }

    public void setLevelFee(BigDecimal levelFee) {
        this.levelFee = levelFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskUserLevelEntity that = (YskUserLevelEntity) o;
        return id == that.id &&
                level == that.level &&
                Objects.equals(levelName, that.levelName) &&
                Objects.equals(levelFee, that.levelFee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, levelName, level, levelFee);
    }
}
