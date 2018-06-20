package com.hctxsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "ysk_config", schema = "hctx_db", catalog = "")
public class YskConfigEntity {

	// 配置ID
	private int id;
	// 配置标题
	private String title;
	// 配置名称
	private String name;
	// 配置值
	private String value;
	// 配置分组
	private int block;
	// 配置类型
	private String type;
	// 配置额外值
	private String options;
	// 配置说明
	private String tip;
	// 创建时间
	private int createTime;
	// 更新时间
	private int updateTime;
	// 排序
	private int sort;
	// 状态
	private int status;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public int getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

}
