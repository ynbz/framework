/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @since 2017年3月29日
 * @version 1.0
 */
package com.suredy.app.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_APP_SCHEDULE")
public class ScheduleEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4157354168134512533L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "STARTTIME")
	private Date start;

	@Column(name = "ENDTIME")
	private Date end;

	@Column(name = "CREATETIME")
	private Date createTime;

	@Column(name = "CREATORID")
	private String creatorId;

	@Column(name = "CREATORNAME")
	private String creatorName;

	@Column(name = "SUBJECTID")
	private String subjectId;

	@Column(name = "SUBJECTNAME")
	private String subjectName;

	@Column(name = "SUBJECTTYPE")
	private Integer subjectType;

	@Column(name = "CLASSNAME")
	private String className;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return the subjectType
	 */
	public Integer getSubjectType() {
		return subjectType;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param subjectType the subjectType to set
	 */
	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	/**
	 * @return the creator
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

}
