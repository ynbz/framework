/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年7月15日
 * @version 0.1
 */
package com.suredy.security.model;

import java.io.Serializable;

import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.RoleEntity;


/**
 * @author ZhangMaoren
 *
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1658106459735623908L;

	private String id;
	
	private String uniqueCode; //role Identification code
	
	private String code;
	
	private String name;
	
	private String alias;

	private Integer sort;
	
	private Integer available;
	
	private Integer buildIn;

	private Integer isMailGroup;
	
	private String description;
	
	private String docId;
	
	private String orgId;
	
	private String orgName;
	
	private String orgUniqueCode;
	
	private String appId;
	
	
	
	
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
	 * @return the uniqueCode
	 */
	public String getUniqueCode() {
		return uniqueCode;
	}

	
	/**
	 * @param uniqueCode the uniqueCode to set
	 */
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	
	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	
	/**
	 * @return the available
	 */
	public Integer getAvailable() {
		return available;
	}

	
	/**
	 * @param available the available to set
	 */
	public void setAvailable(Integer available) {
		this.available = available;
	}

	
	/**
	 * @return the buildIn
	 */
	public Integer getBuildIn() {
		return buildIn;
	}

	
	/**
	 * @param buildIn the buildIn to set
	 */
	public void setBuildIn(Integer buildIn) {
		this.buildIn = buildIn;
	}

	
	/**
	 * @return the isMailGroup
	 */
	public Integer getIsMailGroup() {
		return isMailGroup;
	}

	
	/**
	 * @param isMailGroup the isMailGroup to set
	 */
	public void setIsMailGroup(Integer isMailGroup) {
		this.isMailGroup = isMailGroup;
	}

	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * @return the docId
	 */
	public String getDocId() {
		return docId;
	}

	
	/**
	 * @param docId the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}

	
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	/**
	 * @return the orgUniqueCode
	 */
	public String getOrgUniqueCode() {
		return orgUniqueCode;
	}

	
	/**
	 * @param orgUniqueCode the orgUniqueCode to set
	 */
	public void setOrgUniqueCode(String orgUniqueCode) {
		this.orgUniqueCode = orgUniqueCode;
	}


	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public RoleEntity toPO(){
		RoleEntity po = new RoleEntity();
		po.setAlias(alias);
		po.setAppId(appId);
		po.setAvailable(available);
		po.setBuildIn(buildIn);
		po.setCode(code);
		po.setDescription(description);
		po.setDocId(docId);
		po.setId(id);
		po.setIsMailGroup(isMailGroup);
		po.setName(name);
		if (orgId != null) {
			OrgEntity org = new OrgEntity();
			org.setId(orgId);
			po.setOrg(org);
		}
		po.setSort(sort);
		po.setUniqueCode(uniqueCode);
		return po;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Role other = (Role) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}


		if (uniqueCode != null ? !uniqueCode.equals(other.uniqueCode) : other.uniqueCode != null) {
			return false;
		}
		
		if (name != null ? !name.equals(other.name) : other.name != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0)  + (uniqueCode != null ? uniqueCode.hashCode() : 0)  + (name != null ? name.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Role[" + "id=" + id + ", uniqueCode='" + uniqueCode + "', name='" + name + "']";
	}
}
