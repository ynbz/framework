/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月23日
 * @version 0.1
 */
package com.suredy.security.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.suredy.core.model.BaseModel;
import com.suredy.security.model.User2Permission;


/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_SECURITY_USER2PERMISSION")
public class User2PermissionEntity extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480816221820960092L;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "USERID")
	private UserEntity user;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PERMISSIONID")
	private PermissionEntity permission;
		
	
	/**
	 * @return the user
	 */
	public UserEntity getUser() {
		return user;
	}

	
	/**
	 * @param user the user to set
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

	
	/**
	 * @return the permission
	 */
	public PermissionEntity getPermission() {
		return permission;
	}

	
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(PermissionEntity permission) {
		this.permission = permission;
	}



	@Transient
	public User2Permission toVO(){
		User2Permission vo = new User2Permission();
		vo.setId(id);
		vo.setPermissionId(permission.getId());
		vo.setUserId(user.getId());
		return vo;
	}

}
