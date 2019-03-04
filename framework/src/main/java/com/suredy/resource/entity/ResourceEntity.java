/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年4月13日
 * @version 0.1
 */
package com.suredy.resource.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;
import com.suredy.security.entity.PermissionEntity;




/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_SECURITY_RESOURCE")
public class ResourceEntity extends BaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8086832315669302656L;

	@Column(name = "CREATETIME")
	private Date createTime;


	@Column(name = "LASTMODIFIEDTIME")
	private Date lastModifiedTime;

	@Column(name = "NAME", length = 512)
	private String name;


	@Column(name = "TYPE")
	private Integer type;

	@Column(name = "URI", length = 512)
	private String uri;
	
	@OneToMany(mappedBy = "resource", cascade={CascadeType.ALL}, targetEntity = PermissionEntity.class)  
	private Set<PermissionEntity> associationPermissions = new HashSet<PermissionEntity>();
	
	
	public ResourceEntity(){
		
	}


	
	/**
	 * @return the associationPermissions
	 */
	public Set<PermissionEntity> getAssociationPermissions() {
		return associationPermissions;
	}



	
	/**
	 * @param associationPermissions the associationPermissions to set
	 */
	public void setAssociationPermissions(Set<PermissionEntity> associationPermissions) {
		this.associationPermissions = associationPermissions;
	}



	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}


	/**
	 * @return the lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}


	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	/**
	 * @param lastModifiedTime the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	



//	@Transient
//	public Resource toVO() {
//		Resource vo = new Resource();
//		vo.setCreateTime(createTime);
//		vo.setId(id);
//		vo.setLastModifiedTime(lastModifiedTime);
//		vo.setName(name);
//		vo.setType(type);
//		vo.setUri(uri);
//		return vo;
//	}
	
}
