package com.hctxsys.vo.api;

public class ApiNewsVo {
	private Integer newsId;
	private String title;
	private Integer addtime;
	private Integer newtop;  //1最新2置顶
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public Integer getNewtop() {
		return newtop;
	}
	public void setNewtop(Integer newtop) {
		this.newtop = newtop;
	}
	
	
}
