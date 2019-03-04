package com.suredy.app.consumable.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * 耗材采购模板Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_cousumableTemplateInfo")
public class ConsumableTemplateInfo extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6703092171259030983L;
	
	//模板字段
	private String contemplateinfo;
	//耗材类型
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="consumTypeId")
	@JsonIgnore
	private ConsumerType type;
	
	private String comm;

	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getContemplateinfo() {
		return contemplateinfo;
	}
	public void setContemplateinfo(String contemplateinfo) {
		this.contemplateinfo = contemplateinfo;
	}
	public ConsumerType getType() {
		return type;
	}
	public void setType(ConsumerType type) {
		this.type = type;
	}




	
	
	

}
