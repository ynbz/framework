/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 */
package com.suredy.resource.model;

import java.io.Serializable;

import com.suredy.resource.entity.ReportTypeEntity;
import com.suredy.resource.entity.ResourceEntity;

/**
 * @author ZhangMaoren 2017年2月28日
 *
 */
public class ReportType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6741641262112555145L;

	
	private String id;

	private String name;

	private Integer sort;
	
	private String resourceId;
	
	private String resourceUri;
	
	private String parent;


	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
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
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	
	/**
	 * @return the resourceUri
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * @param resourceUri the resourceUri to set
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}
	
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	public ReportTypeEntity toPO() {
		ReportTypeEntity po = new ReportTypeEntity();
		if (id != null) {
			po.setId(id);
		}
		po.setSort(sort);
		po.setName(name);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);
		
		if (parent != null) {
			ReportTypeEntity pm = new ReportTypeEntity();
			pm.setId(parent);
			po.setParent(pm);
		}
		return po;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ReportType other = (ReportType) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}

		if (name != null ? !name.equals(other.name) : other.name != null) {
			return false;
		}


		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (name != null ? name.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "ReportType[" + "id=" + id + ", name='" + name + "\']";
	}


}
