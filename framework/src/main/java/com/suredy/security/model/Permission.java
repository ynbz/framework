/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年7月15日
 * @version 0.1
 */
package com.suredy.security.model;

import java.io.Serializable;

import com.suredy.resource.entity.ResourceEntity;
import com.suredy.security.entity.PermissionEntity;


/**
 * @author ZhangMaoren
 *
 */
public class Permission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6149298185123559032L;

	private String id;
	
	private String resourceId;
	
	private Integer action; // 资源访问方式
	
	
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
	 * @return the action
	 */
	public Integer getAction() {
		return action;
	}

	
	/**
	 * @param action the action to set
	 */
	public void setAction(Integer action) {
		this.action = action;
	}

	public PermissionEntity toPO(){
		PermissionEntity po = new PermissionEntity();
		po.setAction(action);
		po.setId(id);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Permission p = (Permission) o;

		if (id != null ? !id.equals(p.id) : p.id != null) {
			return false;
		}


		if (action != null ? !action.equals(p.action) : p.action != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0)  + (action != null ? action.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Permisssion[" + "id=" + id + ", action='" + ResouceAction.parse(action).getDescription() + "\'" + "]";
	}
}
