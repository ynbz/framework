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
import com.suredy.security.entity.User2PermissionEntity;
import com.suredy.security.entity.UserEntity;

/**
 * @author ZhangMaoren
 *
 */
public class User2Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -428844892011672644L;

	private String id;

	private String userId;

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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

	public User2PermissionEntity toPO(){
		User2PermissionEntity po = new User2PermissionEntity();
		po.setId(id);
		PermissionEntity permission = new PermissionEntity();
		permission.setId(permissionId);
		po.setPermission(permission);
		UserEntity user = new UserEntity();
		user.setId(userId);
		po.setUser(user);
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User2Permission other = (User2Permission) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (userId != null ? !userId.equals(other.userId) : other.userId != null) {
			return false;
		}
		if (permissionId != null ? !permissionId.equals(other.permissionId) : other.permissionId != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result += (userId != null ? userId.hashCode() : 0);
		result += (permissionId != null ? permissionId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User2Permission[id=" + id + ", userId=" + userId + ", permissionId=" + permissionId + "]";
	}
}
