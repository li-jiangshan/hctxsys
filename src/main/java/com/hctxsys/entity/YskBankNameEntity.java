package com.hctxsys.entity;

import javax.persistence.*;

@Entity
@Table(name = "ysk_bank_name", schema = "hctx_db", catalog = "")
public class YskBankNameEntity {

	//银行信息id
    private int id;
	//银行名称
    private String bankName;
	//银行图片
    private String bankImg;
	//排序
    private int sort;
	//状态
    private int status;

    @Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankImg() {
		return bankImg;
	}
	public void setBankImg(String bankImg) {
		this.bankImg = bankImg;
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
}
