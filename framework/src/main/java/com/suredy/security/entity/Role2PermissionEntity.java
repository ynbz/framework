/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年4月27日
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
import com.suredy.security.model.Role2Permission;


/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_SECURITY_ROLE2PERMISSION")
public class Role2PermissionEntity extends BaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4529646227556472193L;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ROLEID")
	private RoleEntity role;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PERMISSIONID")
	private PermissionEntity permission;
	
	
	/**
	 * @return the role
	 */
	public RoleEntity getRole() {
		return role;
	}

	
	/**
	 * @param role the role to set
	 */
	public void setRole(RoleEntity role) {
		this.role = role;
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
	public Role2Permission toVO(){
		Role2Permission vo = new Role2Permission();
		vo.setId(id);
		vo.setPermissionId(permission.getId());
		vo.setRoleId(role.getId());
		return vo;
	}

}
