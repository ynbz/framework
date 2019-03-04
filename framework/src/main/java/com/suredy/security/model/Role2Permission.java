/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年7月15日
 * @version 0.1
 */
package com.suredy.security.model;

import java.io.Serializable;

import com.suredy.security.entity.PermissionEntity;
import com.suredy.security.entity.Role2PermissionEntity;
import com.suredy.security.entity.RoleEntity;

/**
 * @author ZhangMaoren
 *
 */
public class Role2Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5036088546252036985L;

	private String id;

	private String roleId;

	private String permissionId;
	
	
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
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	/**
	 * @return the permissionId
	 */
	public String getPermissionId() {
		return permissionId;
	}

	
	/**
	 * @param permissionId the permissionId to set
	 */
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public Role2PermissionEntity toPO(){
		Role2PermissionEntity po = new Role2PermissionEntity();
		po.setId(id);
		PermissionEntity permission = new PermissionEntity();
		permission.setId(permissionId);
		po.setPermission(permission);
		RoleEntity role = new RoleEntity();
		role.setId(roleId);
		po.setRole(role);
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Role2Permission other = (Role2Permission) o;
		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (roleId != null ? !roleId.equals(other.roleId) : other.roleId != null) {
			return false;
		}
		if (permissionId != null ? !permissionId.equals(other.permissionId) : other.permissionId != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = (id != null ? id.hashCode() : 0);
		result += (roleId != null ? roleId.hashCode() : 0);
		result += (permissionId != null ? permissionId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Role2Permssion[id=" + id + ", roleId=" + roleId + ", permissionId=" + permissionId + "]";
	}
}
