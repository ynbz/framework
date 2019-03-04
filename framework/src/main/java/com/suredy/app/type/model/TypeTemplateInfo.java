package com.suredy.app.type.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

@Entity
@Table(name="t_app_typeTemplateInfo")
public class TypeTemplateInfo extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6703092171259030983L;
	
	private String templateinfo;
	//资产类型
	
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="typeId")
	@JsonIgnore
	private Type type;
	
	private String comm;
	
	

	public Type getType() {
		return type;
	}

	
	public void setType(Type type) {
		this.type = type;
	}

	
	public String getTemplateinfo() {
		return templateinfo;
	}


	
	public void setTemplateinfo(String templateinfo) {
		this.templateinfo = templateinfo;
	}


	public String getComm() {
		return comm;
	}


	
	public void setComm(String comm) {
		this.comm = comm;
	}




	
	
	

}
