package com.hctxsys.vo;

import com.hctxsys.entity.YskGroupEntity;

public class GroupVo extends YskGroupEntity {

	private String[] menuAuthId;

	public String[] getMenuAuthId() {
		return menuAuthId;
	}

	public void setMenuAuthId(String[] menuAuthId) {
		this.menuAuthId = menuAuthId;
	}

}
