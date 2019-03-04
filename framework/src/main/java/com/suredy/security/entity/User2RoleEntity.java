/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年4月27日
 * @version 0.1
 */
package com.suredy.security.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.suredy.security.model.User2Role;

/**
 * @author ZhangMaoren
 * 
 */
@Entity
@Table(name = "T_E_QZYH")
public class User2RoleEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1353714858463404223L;
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name="ID_E_QZYH",length = 32)
	protected String id;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_YH")
	private UserEntity user;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_QZ")
	private RoleEntity role;

	@Column(name = "PLXH", columnDefinition = "int default 0")
	private int px;
	
	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

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

	@Transient
	public User2Role toVO() {
		User2Role vo = new User2Role();
		vo.setId(id);
		vo.setRoleId(role.getId());
		vo.setUserId(user.getId());
		return vo;
	}
}
