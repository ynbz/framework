/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年9月21日
 * @version 0.1
 */
package com.suredy.resource.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.suredy.core.model.BaseModel;
import com.suredy.resource.model.Segment;

/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_RESOURCE_SEGMENT")
public class SegmentEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6774488568705504134L;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "URI")
	private String uri;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private Integer sort;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESOURCEID")
	private ResourceEntity resource;

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
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	
	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
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

	@Transient
	public Segment toVO() {
		Segment vo = new Segment();
		vo.setId(this.id);
		vo.setName(name);
		vo.setSort(sort);
		vo.setResourceId(resource.getId());
		vo.setUri(uri);
		return vo;
	}
}
