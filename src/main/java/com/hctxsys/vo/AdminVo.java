package com.hctxsys.vo;

import com.hctxsys.entity.YskAdminEntity;

public class AdminVo extends YskAdminEntity {

	// 确认密码
	private String repassword;

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

}
