package com.hctxsys.util;

import java.math.BigDecimal;
import java.util.List;

public class TableResult {
    private Long total; //总记录数
    private List<?> rows; //集合数
    private BigDecimal sum; 
    private BigDecimal pageSum;
    private String recharge;
    private String rechargeType;
    private Integer pageNumber; // 当前页
    private Integer pageSize;   //当前页几条记录数
    private Integer level;
    private String beginDate;
    private String endDate;
    private String type;
    private String typeText;
    private Long pageCount; //总页数
    private String keyword;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getPageSum() {
        return pageSum;
    }

    public void setPageSum(BigDecimal pageSum) {
        this.pageSum = pageSum;
    }

    public String getRecharge() {
        return recharge;
    }

    public void setRecharge(String recharge) {
        this.recharge = recharge;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public TableResult(Long total, List<?> rows) {
        this.total=total;
        this.rows=rows;
    }
    public TableResult(){}
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Integer getPageNumber() {
        if(pageNumber==null) return 0;
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        if(pageSize==null) return 10;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
    
}
