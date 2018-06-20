package com.hctxsys.vo.api;

import java.math.BigDecimal;

public class ApiUserLevelVo {
	
	private int levelId;
    private String levelName;
    private byte level;
    private BigDecimal levelFee;
    
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public byte getLevel() {
		return level;
	}
	public void setLevel(byte level) {
		this.level = level;
	}
	public BigDecimal getLevelFee() {
		return levelFee;
	}
	public void setLevelFee(BigDecimal levelFee) {
		this.levelFee = levelFee;
	}
    
}
