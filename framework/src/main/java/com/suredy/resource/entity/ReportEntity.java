/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 */
package com.suredy.resource.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.suredy.core.model.BaseModel;
import com.suredy.resource.model.Report;

/**
 * @author ZhangMaoren 2017年2月28日
 *
 */
@Entity
@Table(name = "T_RESOURCE_REPORT")
public class ReportEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8951056988441154673L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "FILEID", nullable = false)
	private String fileId;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private Integer sort;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESOURCEID")
	private ResourceEntity resource;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "TYPEID")
	private ReportTypeEntity type;

	@Transient
	private List<ReportTypeEntity> types;

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
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * @return the resource
	 */
	public ResourceEntity getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	/**
	 * @return the type
	 */
	public ReportTypeEntity getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ReportTypeEntity type) {
		this.type = type;
	}

	/**
	 * @return the types
	 */
	public List<ReportTypeEntity> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<ReportTypeEntity> types) {
		this.types = types;
	}

	@Transient
	public Report toVO() {
		Report vo = new Report();
		vo.setName(name);
		vo.setFileId(fileId);
		vo.setResourceId(resource == null ? null : resource.getId());
		vo.setResourceUri(resource == null ? null : resource.getUri());
		vo.setSort(sort);
		vo.setId(id);
		vo.setTypeId(type == null ? null : type.getId());
		vo.setTypeName(type == null ? null : type.getName());
		vo.setCreateTime(resource == null ? null : resource.getCreateTime());
		vo.setLastModifiedTime(resource == null ? null : resource.getLastModifiedTime());
		return vo;
	}

}
