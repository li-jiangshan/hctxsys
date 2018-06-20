package com.hctxsys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ysk_user", schema = "hctx_db")
public class UserEntity implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userid;
    private Integer pid;
    private Integer gid;
    private Integer ggid;
    private String account;
    private String mobile;
    private String username;
    private String email;
    private String safetyPwd;
    private String safetySalt;
    private String loginPwd;
    private String loginSalt;
    private byte sex;
    private Integer regDate;
    private String regIp;
    private byte status;
    private byte activate;
    private String sessionId;
    private String wxNo;
    private String ywtNo;
    private String alipay;
    private String idcard;
    private Integer deep;
    private String path;
    private byte level;
    private byte userType;
    private String headImg;
    private String bankName;
    private String bankNo;
    private String bankUsername;
    private Integer signTotal;
    private Integer jfDaysign;
    private byte seller;
    private String areaProvince;
    private String areaCity;
    private String areaDistrict;
    private byte areaType;
    
    private Integer isSelfShop;
    private YskUserLevelEntity levelEntity;
    private YskUserWealthEntity wealthEntity;
    public UserEntity() {}
    public UserEntity(Integer userid, String account) {
        this.userid = userid;
        this.account = account;
    }

    
    @Transient
	public Integer getIsSelfShop() {
		return isSelfShop;
	}
	public void setIsSelfShop(Integer isSelfShop) {
		this.isSelfShop = isSelfShop;
	}
	
	@OneToOne
    @JoinColumn(name = "level",referencedColumnName = "level",updatable = false,insertable = false)
    public YskUserLevelEntity getLevelEntity() {
        return levelEntity;
    }

    public void setLevelEntity(YskUserLevelEntity levelEntity) {
        this.levelEntity = levelEntity;
    }
    @OneToOne
    @JoinColumn(name = "userid",referencedColumnName = "uid")
    public YskUserWealthEntity getWealthEntity() {
        return wealthEntity;
    }

    public void setWealthEntity(YskUserWealthEntity wealthEntity) {
        this.wealthEntity = wealthEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "gid")
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    @Basic
    @Column(name = "ggid")
    public Integer getGgid() {
        return ggid;
    }

    public void setGgid(Integer ggid) {
        this.ggid = ggid;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "safety_pwd")
    public String getSafetyPwd() {
        return safetyPwd;
    }

    public void setSafetyPwd(String safetyPwd) {
        this.safetyPwd = safetyPwd;
    }

    @Basic
    @Column(name = "safety_salt")
    public String getSafetySalt() {
        return safetySalt;
    }

    public void setSafetySalt(String safetySalt) {
        this.safetySalt = safetySalt;
    }

    @Basic
    @Column(name = "login_pwd")
    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    @Basic
    @Column(name = "login_salt")
    public String getLoginSalt() {
        return loginSalt;
    }

    public void setLoginSalt(String loginSalt) {
        this.loginSalt = loginSalt;
    }

    @Basic
    @Column(name = "sex")
    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "reg_date")
    public Integer getRegDate() {
        return regDate;
    }

    public void setRegDate(Integer regDate) {
        this.regDate = regDate;
    }

    @Basic
    @Column(name = "reg_ip")
    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "activate")
    public byte getActivate() {
        return activate;
    }

    public void setActivate(byte activate) {
        this.activate = activate;
    }

    @Basic
    @Column(name = "session_id")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "wx_no")
    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo;
    }
    
    @Basic
    @Column(name = "ywt_no")
    public String getYwtNo() {
        return ywtNo;
    }

    public void setYwtNo(String ywtNo) {
        this.ywtNo = ywtNo;
    }
    
    @Basic
    @Column(name = "alipay")
    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    @Basic
    @Column(name = "idcard")
    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Basic
    @Column(name = "deep")
    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
    @Column(name = "user_type")
    public byte getUserType() {
        return userType;
    }

    public void setUserType(byte userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "head_img")
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Basic
    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "bank_no")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Basic
    @Column(name = "bank_username")
    public String getBankUsername() {
        return bankUsername;
    }

    public void setBankUsername(String bankUsername) {
        this.bankUsername = bankUsername;
    }

    @Basic
    @Column(name = "sign_total")
    public Integer getSignTotal() {
        return signTotal;
    }

    public void setSignTotal(Integer signTotal) {
        this.signTotal = signTotal;
    }

    @Basic
    @Column(name = "jf_daysign")
    public Integer getJfDaysign() {
        return jfDaysign;
    }

    public void setJfDaysign(Integer jfDaysign) {
        this.jfDaysign = jfDaysign;
    }

    @Basic
    @Column(name = "seller")
    public byte getSeller() {
        return seller;
    }

    public void setSeller(byte seller) {
        this.seller = seller;
    }

    @Basic
    @Column(name = "area_province")
    public String getAreaProvince() {
        return areaProvince;
    }

    public void setAreaProvince(String areaProvince) {
        this.areaProvince = areaProvince;
    }

    @Basic
    @Column(name = "area_city")
    public String getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    @Basic
    @Column(name = "area_district")
    public String getAreaDistrict() {
        return areaDistrict;
    }

    public void setAreaDistrict(String areaDistrict) {
        this.areaDistrict = areaDistrict;
    }

    @Basic
    @Column(name = "area_type")
    public byte getAreaType() {
        return areaType;
    }

    public void setAreaType(byte areaType) {
        this.areaType = areaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userid == that.userid &&
                pid == that.pid &&
                gid == that.gid &&
                ggid == that.ggid &&
                sex == that.sex &&
                regDate == that.regDate &&
                status == that.status &&
                activate == that.activate &&
                deep == that.deep &&
                level == that.level &&
                userType == that.userType &&
                signTotal == that.signTotal &&
                jfDaysign == that.jfDaysign &&
                seller == that.seller &&
                areaType == that.areaType &&
                Objects.equals(account, that.account) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(safetyPwd, that.safetyPwd) &&
                Objects.equals(safetySalt, that.safetySalt) &&
                Objects.equals(loginPwd, that.loginPwd) &&
                Objects.equals(loginSalt, that.loginSalt) &&
                Objects.equals(regIp, that.regIp) &&
                Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(wxNo, that.wxNo) &&
                Objects.equals(alipay, that.alipay) &&
                Objects.equals(idcard, that.idcard) &&
                Objects.equals(path, that.path) &&
                Objects.equals(headImg, that.headImg) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankNo, that.bankNo) &&
                Objects.equals(bankUsername, that.bankUsername) &&
                Objects.equals(areaProvince, that.areaProvince) &&
                Objects.equals(areaCity, that.areaCity) &&
                Objects.equals(areaDistrict, that.areaDistrict);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userid, pid, gid, ggid, account, mobile, username, email, safetyPwd, safetySalt, loginPwd, loginSalt, sex, regDate, regIp, status, activate, sessionId, wxNo, alipay, idcard, deep, path, level, userType, headImg, bankName, bankNo, bankUsername, signTotal, jfDaysign, seller, areaProvince, areaCity, areaDistrict, areaType);
    }
}
