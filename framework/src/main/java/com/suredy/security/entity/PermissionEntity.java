/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年4月13日
 * @version 0.1
 */
package com.suredy.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.suredy.core.model.BaseModel;
import com.suredy.resource.entity.ResourceEntity;
import com.suredy.security.model.Permission;

/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_SECURITY_PERMISSION")
public class PermissionEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7135822368851983710L;

	@Column(name = "ACTION")
	private Integer action; // 资源访问方式

	@OneToMany(mappedBy = "permission", cascade = {CascadeType.ALL}, targetEntity = User2PermissionEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private Set<User2PermissionEntity> associationUser2Permissions = new HashSet<User2PermissionEntity>();

	@OneToMany(mappedBy = "permission", cascade = {CascadeType.ALL}, targetEntity = Role2PermissionEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private Set<Role2PermissionEntity> associationRole2Permissions = new HashSet<Role2PermissionEntity>();

	@OneToMany(mappedBy = "permission", cascade = {CascadeType.ALL}, targetEntity = BasicPermissionEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private Set<BasicPermissionEntity> associationBasePermissions = new HashSet<BasicPermissionEntity>();

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "RESOURCEID")
	private ResourceEntity resource;

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

	/**
	 * @return the associationUser2Permissions
	 */
	public Set<User2PermissionEntity> getAssociationUser2Permissions() {
		return associationUser2Permissions;
	}

	/**
	 * @param associationUser2Permissions the associationUser2Permissions to set
	 */
	public void setAssociationUser2Permissions(Set<User2PermissionEntity> associationUser2Permissions) {
		this.associationUser2Permissions = associationUser2Permissions;
	}

	/**
	 * @return the associationRole2Permissions
	 */
	public Set<Role2PermissionEntity> getAssociationRole2Permissions() {
		return associationRole2Permissions;
	}

	/**
	 * @param associationRole2Permissions the associationRole2Permissions to set
	 */
	public void setAssociationRole2Permissions(Set<Role2PermissionEntity> associationRole2Permissions) {
		this.associationRole2Permissions = associationRole2Permissions;
	}

	/**
	 * @return the associationBasePermissions
	 */
	public Set<BasicPermissionEntity> getAssociationBasePermissions() {
		return associationBasePermissions;
	}

	/**
	 * @param associationBasePermissions the associationBasePermissions to set
	 */
	public void setAssociationBasePermissions(Set<BasicPermissionEntity> associationBasePermissions) {
		this.associationBasePermissions = associationBasePermissions;
	}

	/**
	 * @return the resource
	 */
	public ResourceEntity getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	@Transient
	public Permission toVO() {
		Permission vo = new Permission();
		vo.setAction(action);
		vo.setId(id);
		vo.setResourceId(resource.getId());
		return vo;
	}
}
