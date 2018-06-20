package com.hctxsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "ysk_news", schema = "hctx_db", catalog = "")
public class YskNewsEntity {

	//新闻id
    private int newsId;
    //标题
    private String title;
    //排序
    private int px;
    //上传时间
    private int addtime;
    //内容
    private String content;
    //浏览次数
    private int views;
    //1最新2置顶
    private int newtop;
    //上级名称
    private String type;
    //上级id
    private int pid;
    //是否展示  1展示  2 不展示
    private int status;
    //浏览次数
    private int times;

    @Id
    @Column(name = "id")
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPx() {
		return px;
	}
	public void setPx(int px) {
		this.px = px;
	}
	public int getAddtime() {
		return addtime;
	}
	public void setAddtime(int addtime) {
		this.addtime = addtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getNewtop() {
		return newtop;
	}
	public void setNewtop(int newtop) {
		this.newtop = newtop;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
    
}
