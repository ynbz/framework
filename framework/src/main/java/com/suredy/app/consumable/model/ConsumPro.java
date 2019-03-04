package com.suredy.app.consumable.model;

public class ConsumPro {
	
	
	private String propertyName;//属性名称
	private String consumTypeId;	
	private Integer sort;//排序
	private String field;//所存字段
	private Integer isShow;//是否显示
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getConsumTypeId() {
		return consumTypeId;
	}
	public void setConsumTypeId(String consumTypeId) {
		this.consumTypeId = consumTypeId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
}
