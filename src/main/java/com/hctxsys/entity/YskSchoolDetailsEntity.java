package com.hctxsys.entity;
// Generated 2018-5-4 13:25:23 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * YskSchoolDetails generated by hbm2java
 */
@Entity
@Table(name = "ysk_school_details")
public class YskSchoolDetailsEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String mobile;
	private int uid;
	private String content;
	private int createTime;
	private int pid;

	public YskSchoolDetailsEntity() {
	}

	public YskSchoolDetailsEntity(String name, String mobile, int uid, String content, int createTime, int pid) {
		this.name = name;
		this.mobile = mobile;
		this.uid = uid;
		this.content = content;
		this.createTime = createTime;
		this.pid = pid;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mobile", nullable = false, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "uid", nullable = false)
	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_time", nullable = false)
	public int getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	@Column(name = "pid", nullable = false)
	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

}