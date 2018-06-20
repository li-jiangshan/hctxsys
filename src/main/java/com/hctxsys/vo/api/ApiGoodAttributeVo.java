package com.hctxsys.vo.api;

import java.util.List;

public class ApiGoodAttributeVo {
	//规格名称
	private String attributeName;
	//规格值
	private List<String> attributeValue;
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public List<String> getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(List<String> attributeValue) {
		this.attributeValue = attributeValue;
	}
	
}
