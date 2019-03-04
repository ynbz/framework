/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年7月15日
 * @version 0.1
 */
package com.suredy.security.model;

import java.io.Serializable;

import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.UnitEntity;

/**
 * @author ZhangMaoren
 *
 */
public class Unit implements Serializable, IFlage {

	public String getFlag() {
		return "unit";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8466824976429459324L;

	private String id;

	private String orgId;

	private String parentId;
	
	private String parentName;

	private String uniqueCode; // UnitEntity Identification code

	private String code;

	private String name;

	private String alias;

	private Integer sort;

	private String fullName;

	private Integer available;

	private String description;

	private String docId;

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
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public UnitEntity toPO() {
		UnitEntity po = new UnitEntity();
		po.setAlias(alias);
		po.setAvailable(available);
		po.setCode(code);
		po.setDescription(description);
		po.setDocId(docId);
		po.setFullName(fullName);
		po.setId(id);
		if (parentId != null) {
			UnitEntity pu = new UnitEntity();
			pu.setId(parentId);
			po.setParent(pu);
		}
		po.setSort(sort);
		po.setUniqueCode(uniqueCode);
		if (orgId != null) {
			OrgEntity org = new OrgEntity();
			org.setId(orgId);
			po.setOrg(org);
		}
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Unit other = (Unit) o;

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
		return ((id != null ? id.hashCode() : 0) + (uniqueCode != null ? uniqueCode.hashCode() : 0) + (name != null ? name.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Unit[" + "id=" + id + ", uniqueCode='" + uniqueCode + "', name='" + name + "']";
	}
}
