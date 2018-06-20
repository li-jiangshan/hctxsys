package com.hctxsys.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ysk_menu", schema = "hctx_db", catalog = "")
public class YskMenuEntity {
    private int id;
    private String name;
    private int pid;
    private int gid;
    private String mod;
    private String col;
    private String act;
    private String param;
    private String paramValue;
    private String patch;
    private int level;
    private String icon;
    private String sort;
    private byte status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "gid")
    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    @Basic
    @Column(name = "mod")
    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    @Basic
    @Column(name = "col")
    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    @Basic
    @Column(name = "act")
    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    @Basic
    @Column(name = "param")
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Basic
    @Column(name = "param_value")
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Basic
    @Column(name = "patch")
    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    @Basic
    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "sort")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskMenuEntity that = (YskMenuEntity) o;
        return id == that.id &&
                pid == that.pid &&
                gid == that.gid &&
                level == that.level &&
                status == that.status &&
                Objects.equals(name, that.name) &&
                Objects.equals(mod, that.mod) &&
                Objects.equals(col, that.col) &&
                Objects.equals(act, that.act) &&
                Objects.equals(param, that.param) &&
                Objects.equals(paramValue, that.paramValue) &&
                Objects.equals(patch, that.patch) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, pid, gid, mod, col, act, param, paramValue, patch, level, icon, sort, status);
    }
}
