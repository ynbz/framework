/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年7月15日
 * @version 0.1
 */
package com.suredy.security.model;

import java.io.Serializable;

import com.suredy.security.entity.OrgEntity;


/**
 * @author ZhangMaoren
 *
 */
public class Org implements Serializable, IFlage {

	public String getFlag() {
		return "org";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6223989324429370176L;
	
	private String id;
	
	private String uniqueCode; //Organization Identification code

	private String code;

	private String name;

	private String alias;

	private Integer sort;

	private String fullName;

	private Integer buildIn;

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

	public OrgEntity toPO(){
		OrgEntity po = new OrgEntity();
		po.setAlias(alias);
		po.setAvailable(available);
		po.setBuildIn(buildIn);
		po.setCode(code);
		po.setDescription(description);
		po.setDocId(docId);
		po.setFullName(fullName);
		po.setId(id);
		po.setName(name);
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

		Org other = (Org) o;

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
		return "Org[" + "id=" + id + ", uniqueCode='" + uniqueCode + "', name='" + name + "']";
	}
}
