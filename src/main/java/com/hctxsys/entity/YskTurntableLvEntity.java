package com.hctxsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "ysk_turntable_lv", schema = "hctx_db", catalog = "")
public class YskTurntableLvEntity {

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

	@Id
	@Column(name = "id")
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
