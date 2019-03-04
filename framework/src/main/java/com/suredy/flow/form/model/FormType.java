/**
 * 
 */
package com.suredy.flow.form.model;

import java.io.Serializable;

import com.suredy.flow.form.entity.FormTypeEntity;
import com.suredy.resource.entity.ResourceEntity;

/**
 * @author ZhangMaoren
 *
 */
public class FormType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3215412238054261032L;

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
	
	public FormTypeEntity toPO() {
		FormTypeEntity po = new FormTypeEntity();
		if (id != null) {
			po.setId(id);
		}
		po.setSort(sort);
		po.setName(name);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);
		
		if (parent != null) {
			FormTypeEntity pm = new FormTypeEntity();
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

		FormType other = (FormType) o;

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
		return "FormType[" + "id=" + id + ", name='" + name + "\']";
	}

}
