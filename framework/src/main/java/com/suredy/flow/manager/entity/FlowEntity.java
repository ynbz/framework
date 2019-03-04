package com.suredy.flow.manager.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "T_APP_FLOW")
public class FlowEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7840825558094851444L;
	
	@Column(name = "NAME")
	private String name;// 流程名称

	@Column(name = "CODE")
	private String code;// 对应流程中的fileCode

	/* 创建日期 */
	@Column(nullable = false, name = "CREATETIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createtime;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
