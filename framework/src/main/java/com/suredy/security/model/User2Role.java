/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年7月15日
 * @version 0.1
 */
package com.suredy.security.model;

import java.io.Serializable;

import com.suredy.security.entity.RoleEntity;
import com.suredy.security.entity.User2RoleEntity;
import com.suredy.security.entity.UserEntity;

/**
 * @author ZhangMaoren
 *
 */
public class User2Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5410014542464355296L;

	private String id;

	private String userId;

	private String roleId;
	
	
	
	
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

	public User2RoleEntity toPO(){
		User2RoleEntity po = new User2RoleEntity();
		po.setId(id);
		RoleEntity role = new RoleEntity();
		role.setId(roleId);
		po.setRole(role);
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

		User2Role other = (User2Role) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (userId != null ? !userId.equals(other.userId) : other.userId != null) {
			return false;
		}
		if (roleId != null ? !roleId.equals(other.roleId) : other.roleId != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result += (userId != null ? userId.hashCode() : 0);
		result += (roleId != null ? roleId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User2Role[id=" + id + ", userId=" + userId + ", roleId=" + roleId + "]";
	}
}
