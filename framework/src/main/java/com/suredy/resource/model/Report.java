/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 */
package com.suredy.resource.model;

import java.io.Serializable;
import java.util.Date;

import com.suredy.resource.entity.ReportEntity;
import com.suredy.resource.entity.ReportTypeEntity;
import com.suredy.resource.entity.ResourceEntity;

/**
 * @author ZhangMaoren 2017年2月28日
 *
 */
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4416052571298316585L;

	private String id;

	private String name;

	private String fileId;

	private Integer sort;

	private String resourceId;

	private String resourceUri;

	private String typeId;

	private String typeName;

	private Date createTime;

	private Date lastModifiedTime;

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
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * @return the lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @param lastModifiedTime the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public ReportEntity toPO() {
		ReportEntity po = new ReportEntity();
		if (id != null) {
			po.setId(id);
		}

		po.setFileId(fileId);
		po.setSort(sort);
		po.setName(name);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);

		if (typeId != null) {
			ReportTypeEntity pm = new ReportTypeEntity();
			pm.setId(typeId);
			po.setType(pm);
		}
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Report other = (Report) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (fileId != null ? !fileId.equals(other.fileId) : other.fileId != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (fileId != null ? fileId.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Report[" + "id=" + id + ", fileId='" + fileId + "\']";
	}

}
