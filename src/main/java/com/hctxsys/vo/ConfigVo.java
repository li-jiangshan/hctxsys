package com.hctxsys.vo;

import com.hctxsys.entity.YskConfigEntity;

public class ConfigVo extends YskConfigEntity {

	// 网站logo
	private String WEB_SITE_LOGO;
	// 网站名称
	private String WEB_SITE_NAME;
	// 网站标题
	private String WEB_SITE_TITLE;
	// 网站描述
	private String WEB_SITE_DESCRIPTION;
	// 网站关键字
	private String WEB_SITE_KEYWORD;
	// 网站备案号
	private String WEB_SITE_ICP;
	// 联系电话
	private String WEB_TEL;
	// 联系手机
	private String WEB_MOBILE;
	// 平台客服QQ1
	private String WEB_QQ1;
	// 平台客服QQ2
	private String WEB_QQ2;
	// 平台客服微信
	private String WEB_WX;
	// configId数组
	private Integer[] configId;
	// name数组
	private String[] nameShuzu;

	// 转盘配置id
	private int id;
	// 1000华宝
	private int one;
	// 500积分
	private int two;
	// 谢谢参与
	private int three;
	// 200积分
	private int four;

	public String getWEB_SITE_LOGO() {
		return WEB_SITE_LOGO;
	}

	public void setWEB_SITE_LOGO(String wEB_SITE_LOGO) {
		WEB_SITE_LOGO = wEB_SITE_LOGO;
	}

	public String getWEB_SITE_NAME() {
		return WEB_SITE_NAME;
	}

	public void setWEB_SITE_NAME(String wEB_SITE_NAME) {
		WEB_SITE_NAME = wEB_SITE_NAME;
	}

	public String getWEB_SITE_TITLE() {
		return WEB_SITE_TITLE;
	}

	public void setWEB_SITE_TITLE(String wEB_SITE_TITLE) {
		WEB_SITE_TITLE = wEB_SITE_TITLE;
	}

	public String getWEB_SITE_DESCRIPTION() {
		return WEB_SITE_DESCRIPTION;
	}

	public void setWEB_SITE_DESCRIPTION(String wEB_SITE_DESCRIPTION) {
		WEB_SITE_DESCRIPTION = wEB_SITE_DESCRIPTION;
	}

	public String getWEB_SITE_KEYWORD() {
		return WEB_SITE_KEYWORD;
	}

	public void setWEB_SITE_KEYWORD(String wEB_SITE_KEYWORD) {
		WEB_SITE_KEYWORD = wEB_SITE_KEYWORD;
	}

	public String getWEB_SITE_ICP() {
		return WEB_SITE_ICP;
	}

	public void setWEB_SITE_ICP(String wEB_SITE_ICP) {
		WEB_SITE_ICP = wEB_SITE_ICP;
	}

	public String getWEB_TEL() {
		return WEB_TEL;
	}

	public void setWEB_TEL(String wEB_TEL) {
		WEB_TEL = wEB_TEL;
	}

	public String getWEB_MOBILE() {
		return WEB_MOBILE;
	}

	public void setWEB_MOBILE(String wEB_MOBILE) {
		WEB_MOBILE = wEB_MOBILE;
	}

	public String getWEB_QQ1() {
		return WEB_QQ1;
	}

	public void setWEB_QQ1(String wEB_QQ1) {
		WEB_QQ1 = wEB_QQ1;
	}

	public String getWEB_QQ2() {
		return WEB_QQ2;
	}

	public void setWEB_QQ2(String wEB_QQ2) {
		WEB_QQ2 = wEB_QQ2;
	}

	public String getWEB_WX() {
		return WEB_WX;
	}

	public void setWEB_WX(String wEB_WX) {
		WEB_WX = wEB_WX;
	}

	public Integer[] getConfigId() {
		return configId;
	}

	public void setConfigId(Integer[] configId) {
		this.configId = configId;
	}

	public String[] getNameShuzu() {
		return nameShuzu;
	}

	public void setNameShuzu(String[] nameShuzu) {
		this.nameShuzu = nameShuzu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public int getTwo() {
		return two;
	}

	public void setTwo(int two) {
		this.two = two;
	}

	public int getThree() {
		return three;
	}

	public void setThree(int three) {
		this.three = three;
	}

	public int getFour() {
		return four;
	}

	public void setFour(int four) {
		this.four = four;
	}

}
