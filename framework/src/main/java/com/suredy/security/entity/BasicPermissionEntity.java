package com.suredy.security.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

/**
 * @author SDKJ
 *
 */
@Entity
@Table(name = "T_SECURITY_BASICPERMISSION")
public class BasicPermissionEntity  extends BaseModel  {

	private static final long serialVersionUID = -3239391625999251714L;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PERMISSIONID")
	private PermissionEntity permission;

	
	public PermissionEntity getPermission() {
		return permission;
	}

	
	public void setPermission(PermissionEntity permission) {
		this.permission = permission;
	}
}
