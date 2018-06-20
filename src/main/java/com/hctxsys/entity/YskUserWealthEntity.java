package com.hctxsys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ysk_user_wealth", schema = "hctx_db", catalog = "")
public class YskUserWealthEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int uid;
    private BigDecimal money;
    private BigDecimal integral;
    private BigDecimal huabao;
    private BigDecimal kucunIntegral;
    private BigDecimal totalMoney;
    private BigDecimal totalIntegral;
    private BigDecimal totalHuabao;
    private String uptime;
    private Timestamp uptimeing;
    private Set<YskUserBankEntity> bankList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid",referencedColumnName = "uid",updatable = false,insertable = false)
    public Set<YskUserBankEntity> getBankList() {
        return bankList;
    }

    public void setBankList(Set<YskUserBankEntity> bankList) {
        this.bankList = bankList;
    }

    @Id
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "money")
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Basic
    @Column(name = "integral")
    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    @Basic
    @Column(name = "huabao")
    public BigDecimal getHuabao() {
        return huabao;
    }

    public void setHuabao(BigDecimal huabao) {
        this.huabao = huabao;
    }

    @Basic
    @Column(name = "kucun_integral")
    public BigDecimal getKucunIntegral() {
        return kucunIntegral;
    }

    public void setKucunIntegral(BigDecimal kucunIntegral) {
        this.kucunIntegral = kucunIntegral;
    }

    @Basic
    @Column(name = "total_money")
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Basic
    @Column(name = "total_integral")
    public BigDecimal getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(BigDecimal totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    @Basic
    @Column(name = "total_huabao")
    public BigDecimal getTotalHuabao() {
        return totalHuabao;
    }

    public void setTotalHuabao(BigDecimal totalHuabao) {
        this.totalHuabao = totalHuabao;
    }

    @Basic
    @Column(name = "uptime")
    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    @Basic
    @Column(name = "uptimeing")
    public Timestamp getUptimeing() {

        return uptimeing;
    }

    public void setUptimeing(Timestamp uptimeing) {
        this.uptimeing = uptimeing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YskUserWealthEntity that = (YskUserWealthEntity) o;
        return uid == that.uid &&
                Objects.equals(money, that.money) &&
                Objects.equals(integral, that.integral) &&
                Objects.equals(huabao, that.huabao) &&
                Objects.equals(kucunIntegral, that.kucunIntegral) &&
                Objects.equals(totalMoney, that.totalMoney) &&
                Objects.equals(totalIntegral, that.totalIntegral) &&
                Objects.equals(totalHuabao, that.totalHuabao) &&
                Objects.equals(uptime, that.uptime) &&
                Objects.equals(uptimeing, that.uptimeing);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, money, integral, huabao, kucunIntegral, totalMoney, totalIntegral, totalHuabao, uptime, uptimeing);
    }
}
