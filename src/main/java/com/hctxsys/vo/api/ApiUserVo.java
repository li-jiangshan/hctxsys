package com.hctxsys.vo.api;

import java.util.Date;

public class ApiUserVo {

	//用户id
	private Integer userid;
	//用户类型
	private String userType;
	//用户手机号
	private String mobile;
	//验证码
	private String verificationCode;
	//账号
    private String account;
	//旧安全密码
    private String oldSafetyPwd;
	//安全密码
    private String safetyPwd;
	//确认安全密码
    private String confirmSafetyPwd;
	//旧密码
    private String oldLoginPwd;
	//密码
    private String loginPwd;
	//确认密码
    private String confirmLoginPwd;
    //公司名称
    private String companyName;
    //营业执照
    private String companyLicense;
    //组织机构
    private String companyOrganize;
    //父级手机号
    private String parent;
    //访问ip
    private String regIp;
    //是否手机认证
    private String isCheckMobile;
    //个人认证
    private String isCheckUser;
    //企业认证
    private String isCheckCompany;
    //姓名
    private String username;
    //证件号
    private String idcard;
    //证件开始日期
    private Date idcarStartdate;
    //证件结束日期
    private Date idcarEndtdate;
	//0-大陆身份证 1-非大陆身份证
    private byte idcardType;
	//国家    
    private String country;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //身份证正面照
    private String idcardImgFace;
    //身份证反面照
    private String idcardImgBack;
    //手持身份证照
    private String idcardImgHand;
    //社会信用代码
    private String creditNo;
    //是否三证合一
    private byte isThreeCard;
    //税务登记证
    private String taxNo;
    //组织机构证
    private String organizeNo;
    //法人姓名
    private String legalName;
    //公司类型
    private String companyType;
    //0-法人  1-被授权人
    private byte isLegal;
    //经营负责人
    private String manageParent;
    //营业执照照片
    private String companyLicenseImg;
    //是否分公司
    private byte isChildCompany;
    
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getOldSafetyPwd() {
		return oldSafetyPwd;
	}

	public void setOldSafetyPwd(String oldSafetyPwd) {
		this.oldSafetyPwd = oldSafetyPwd;
	}

	public String getSafetyPwd() {
		return safetyPwd;
	}

	public void setSafetyPwd(String safetyPwd) {
		this.safetyPwd = safetyPwd;
	}

	public String getConfirmSafetyPwd() {
		return confirmSafetyPwd;
	}

	public void setConfirmSafetyPwd(String confirmSafetyPwd) {
		this.confirmSafetyPwd = confirmSafetyPwd;
	}

	public String getOldLoginPwd() {
		return oldLoginPwd;
	}

	public void setOldLoginPwd(String oldLoginPwd) {
		this.oldLoginPwd = oldLoginPwd;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getConfirmLoginPwd() {
		return confirmLoginPwd;
	}

	public void setConfirmLoginPwd(String confirmLoginPwd) {
		this.confirmLoginPwd = confirmLoginPwd;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLicense() {
		return companyLicense;
	}

	public void setCompanyLicense(String companyLicense) {
		this.companyLicense = companyLicense;
	}

	public String getCompanyOrganize() {
		return companyOrganize;
	}

	public void setCompanyOrganize(String companyOrganize) {
		this.companyOrganize = companyOrganize;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getIsCheckMobile() {
		return isCheckMobile;
	}

	public void setIsCheckMobile(String isCheckMobile) {
		this.isCheckMobile = isCheckMobile;
	}

	public String getIsCheckUser() {
		return isCheckUser;
	}

	public void setIsCheckUser(String isCheckUser) {
		this.isCheckUser = isCheckUser;
	}

	public String getIsCheckCompany() {
		return isCheckCompany;
	}

	public void setIsCheckCompany(String isCheckCompany) {
		this.isCheckCompany = isCheckCompany;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getIdcarStartdate() {
		return idcarStartdate;
	}

	public void setIdcarStartdate(Date idcarStartdate) {
		this.idcarStartdate = idcarStartdate;
	}

	public Date getIdcarEndtdate() {
		return idcarEndtdate;
	}

	public void setIdcarEndtdate(Date idcarEndtdate) {
		this.idcarEndtdate = idcarEndtdate;
	}

	public byte getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(byte idcardType) {
		this.idcardType = idcardType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getIdcardImgFace() {
		return idcardImgFace;
	}

	public void setIdcardImgFace(String idcardImgFace) {
		this.idcardImgFace = idcardImgFace;
	}

	public String getIdcardImgBack() {
		return idcardImgBack;
	}

	public void setIdcardImgBack(String idcardImgBack) {
		this.idcardImgBack = idcardImgBack;
	}

	public String getIdcardImgHand() {
		return idcardImgHand;
	}

	public void setIdcardImgHand(String idcardImgHand) {
		this.idcardImgHand = idcardImgHand;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public byte getIsThreeCard() {
		return isThreeCard;
	}

	public void setIsThreeCard(byte isThreeCard) {
		this.isThreeCard = isThreeCard;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getOrganizeNo() {
		return organizeNo;
	}

	public void setOrganizeNo(String organizeNo) {
		this.organizeNo = organizeNo;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public byte getIsLegal() {
		return isLegal;
	}

	public void setIsLegal(byte isLegal) {
		this.isLegal = isLegal;
	}

	public String getManageParent() {
		return manageParent;
	}

	public void setManageParent(String manageParent) {
		this.manageParent = manageParent;
	}

	public String getCompanyLicenseImg() {
		return companyLicenseImg;
	}

	public void setCompanyLicenseImg(String companyLicenseImg) {
		this.companyLicenseImg = companyLicenseImg;
	}

	public byte getIsChildCompany() {
		return isChildCompany;
	}

	public void setIsChildCompany(byte isChildCompany) {
		this.isChildCompany = isChildCompany;
	}
}
