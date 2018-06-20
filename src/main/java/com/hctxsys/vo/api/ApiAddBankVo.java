package com.hctxsys.vo.api;

public class ApiAddBankVo {
	private Integer uid;
	private String bankname;
	private String username;
	private String bankno;
	private String bankbranch;
	private byte isdefault;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBankno() {
		return bankno;
	}
	public void setBankno(String bankno) {
		this.bankno = bankno;
	}
	public String getBankbranch() {
		return bankbranch;
	}
	public void setBankbranch(String bankbranch) {
		this.bankbranch = bankbranch;
	}
	public byte getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(byte isdefault) {
		this.isdefault = isdefault;
	}
}
