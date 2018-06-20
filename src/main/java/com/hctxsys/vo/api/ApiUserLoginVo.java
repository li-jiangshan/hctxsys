package com.hctxsys.vo.api;

public class ApiUserLoginVo {

	//用户id
	private Integer userid;
	//父级id
    private Integer pid;
	//用户类型
	private String userType;
	//用户手机号
	private String mobile;
	//账号
    private String account;
	//是否设置过安全密码 0、没有；1、有
    private String isSetSafePwd;
	//登录session
    private String sessionId;
    
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIsSetSafePwd() {
		return isSetSafePwd;
	}
	public void setIsSetSafePwd(String isSetSafePwd) {
		this.isSetSafePwd = isSetSafePwd;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
