/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年9月21日
 * @version 0.1
 */
package com.suredy.resource.model;

import java.io.Serializable;

import com.suredy.resource.entity.SegmentEntity;
import com.suredy.resource.entity.ResourceEntity;


/**
 * @author ZhangMaoren
 *
 */
public class Segment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1095752041813244688L;
	
	private String id;

	private String name;
	
	private String uri;

	private Integer sort;
  
    private String resourceId;

	
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


	public SegmentEntity toPO() {
		SegmentEntity po = new SegmentEntity();
		if (id != null) {
			po.setId(id);
		}
		po.setId(id);
		po.setName(name);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);
		po.setSort(sort);
		po.setUri(uri);
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Segment other = (Segment) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}

		if (name != null ? !name.equals(other.name) : other.name != null) {
			return false;
		}

		if (uri != null ? !uri.equals(other.uri) : other.uri != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (name != null ? name.hashCode() : 0) + (uri != null ? uri.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Resource[" + "id=" + id + ", name='" + name + "\'" + ", uri='" + uri + "\'" + "]";
	}
    
    
}
