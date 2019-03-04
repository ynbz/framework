package com.suredy.app.consumable.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;
/**
 * 耗材属性Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_consumProperty")
public class ConsumProperty extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8572645486080616172L;
	
	private String propertyName;//属性名称
	
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="consumTypeId")
	private ConsumerType type;
	
	private Integer sort;//排序
	private String field;//所存字段
	private Integer isShow;//是否显示
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public ConsumerType getType() {
		return type;
	}
	
	public void setType(ConsumerType type) {
		this.type = type;
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
