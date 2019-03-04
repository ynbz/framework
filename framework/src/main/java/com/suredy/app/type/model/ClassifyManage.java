package com.suredy.app.type.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

@Entity
@Table(name="t_app_classifyproperty")
public class ClassifyManage extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2657155813287387764L;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="typeId")
	private Type type;
	
	private String propertyName;//属性名称
	private Integer  sort;//排序
	private String  field;//所存字段
	private Integer  isSearch;//是否搜索项
	private Integer  isShow;//是否显示
	private String propertyUnit;//属性单位
	
	private Integer isPrimeAttribute;//是否是主要配置信息
	private Integer isNeed;//是否是需求信息
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public Integer getIsSearch() {
		return isSearch;
	}
	
	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}
	
	public Integer getIsShow() {
		return isShow;
	}
	
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	public Integer getIsPrimeAttribute() {
		return isPrimeAttribute;
	}
	
	public void setIsPrimeAttribute(Integer isPrimeAttribute) {
		this.isPrimeAttribute = isPrimeAttribute;
	}
	
	public Integer getIsNeed() {
		return isNeed;
	}
	
	public void setIsNeed(Integer isNeed) {
		this.isNeed = isNeed;
	}
	public String getPropertyUnit() {
		return propertyUnit;
	}
	public void setPropertyUnit(String propertyUnit) {
		this.propertyUnit = propertyUnit;
	}
	

	
	
	

}
