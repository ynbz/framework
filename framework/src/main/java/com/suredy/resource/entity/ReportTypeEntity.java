/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 */
package com.suredy.resource.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;
import com.suredy.resource.model.ReportType;

/**
 * @author ZhangMaoren 2017年2月28日
 *
 */
@Entity
@Table(name = "T_REPORT_TYPE")
public class ReportTypeEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3993461352136345012L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private Integer sort;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESOURCEID")
	private ResourceEntity resource;

	@OneToMany(mappedBy = "type", cascade = {CascadeType.ALL}, targetEntity = ReportEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private Set<ReportEntity> associationReports = new LinkedHashSet<ReportEntity>();

	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@OrderBy("sort")
	@Fetch(FetchMode.SUBSELECT)
	private List<ReportTypeEntity> children;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PARENTID")
	private ReportTypeEntity parent;

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
	 * @return the associationReports
	 */
	public Set<ReportEntity> getAssociationReports() {
		return associationReports;
	}

	/**
	 * @param associationReports the associationReports to set
	 */
	public void setAssociationReports(Set<ReportEntity> associationReports) {
		this.associationReports = associationReports;
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
	 * @return the children
	 */
	public List<ReportTypeEntity> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<ReportTypeEntity> children) {
		this.children = children;
	}

	/**
	 * @return the parent
	 */
	public ReportTypeEntity getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ReportTypeEntity parent) {
		this.parent = parent;
	}

	@Transient
	public ReportType toVO() {
		ReportType vo = new ReportType();
		vo.setId(id);
		vo.setName(name);
		vo.setResourceId(resource == null ? null : resource.getId());
		vo.setResourceUri(resource == null ? null : resource.getUri());
		vo.setSort(sort);
		vo.setParent(parent == null ? null : parent.getId());
		return vo;
	}
}
