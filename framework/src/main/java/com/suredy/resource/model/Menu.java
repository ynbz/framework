/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年7月14日
 * @version 0.1
 */
package com.suredy.resource.model;

import java.io.Serializable;

import com.suredy.resource.entity.MenuEntity;
import com.suredy.resource.entity.ResourceEntity;


/**
 * @author ZhangMaoren
 *
 */
public class Menu implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1003156712397803363L;
	
	private String id;

	private String url;

	private String text;

	private Integer sort;
	
	private String resourceId;
	
	private String icon;
	
	private Boolean active;
	
	private Boolean collapse;
	
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	
	/**
	 * @return the collapse
	 */
	public Boolean getCollapse() {
		return collapse;
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


	/**
	 * @param collapse the collapse to set
	 */
	public void setCollapse(Boolean collapse) {
		this.collapse = collapse;
	}
	
	public MenuEntity toPO() {
		MenuEntity po = new MenuEntity();
		if (id != null) {
			po.setId(id);
		}
		po.setActive(active);
		po.setCollapse(collapse);
		po.setIcon(icon);
		po.setSort(sort);
		po.setText(text);
		po.setUrl(url);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);
		
		if (parent != null) {
			MenuEntity pm = new MenuEntity();
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

		Menu other = (Menu) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}

		if (text != null ? !text.equals(other.text) : other.text != null) {
			return false;
		}

		if (url != null ? !url.equals(other.url) : other.url != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (text != null ? text.hashCode() : 0) + (url != null ? url.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Resource[" + "id=" + id + ", text='" + text + "\'" + ", url='" + url + "\'" + "]";
	}

}
