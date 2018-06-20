package com.hctxsys.vo.api;

import com.hctxsys.entity.YskUserEntity;

public class ApiLoginVo {
	private ApiUserLoginVo apiUserLoginVo;
	private YskUserEntity userEntity;
	public ApiUserLoginVo getApiUserLoginVo() {
		return apiUserLoginVo;
	}
	public void setApiUserLoginVo(ApiUserLoginVo apiUserLoginVo) {
		this.apiUserLoginVo = apiUserLoginVo;
	}
	public YskUserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(YskUserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
}
