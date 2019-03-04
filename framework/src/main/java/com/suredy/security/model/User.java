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
import com.suredy.security.entity.UserEntity;

/**
 * @author ZhangMaoren
 *
 */
public class User implements Serializable, IFlage {

	public String getFlag() {
		return "user";
	}

	private static final long serialVersionUID = 6379714772597470702L;

	private String id;

	private String unitUniqueCode; // unit Identification code

	private String unitId;
	
	private String unitName;

	private String orgId;
	
	private String orgName;

	private String uniqueCode; // user Identification code

	private String code;

	private String name;
	
	private String shortPinyin;

	private String alias;

	private String title;

	private Integer sort;

	private String fullName;

	private Integer actionType;

	private Integer available;

	private String email;

	private Integer isMailUser;

	private String password;

	private String description;

	private String docId;

	private String actUserId;
	
	private Integer isEmployee;//是否是正式员工（0：否，1：是）
	
	private Integer isLongUser;//是否是登陆用户（0：否，1：是）
	
	private String userphone;
	
	private String fullUnitName;
	
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
	 * @return the unitUniqueCode
	 */
	public String getUnitUniqueCode() {
		return unitUniqueCode;
	}

	
	/**
	 * @param unitUniqueCode the unitUniqueCode to set
	 */
	public void setUnitUniqueCode(String unitUniqueCode) {
		this.unitUniqueCode = unitUniqueCode;
	}

	
	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	
	
	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}


	
	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	 * @return the shortPinyin
	 */
	public String getShortPinyin() {
		return shortPinyin;
	}


	
	/**
	 * @param shortPinyin the shortPinyin to set
	 */
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the actionType
	 */
	public Integer getActionType() {
		return actionType;
	}

	
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * @return the isMailUser
	 */
	public Integer getIsMailUser() {
		return isMailUser;
	}

	
	/**
	 * @param isMailUser the isMailUser to set
	 */
	public void setIsMailUser(Integer isMailUser) {
		this.isMailUser = isMailUser;
	}

	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the actUserId
	 */
	public String getActUserId() {
		return actUserId;
	}

	
	/**
	 * @param actUserId the actUserId to set
	 */
	public void setActUserId(String actUserId) {
		this.actUserId = actUserId;
	}


	
	public Integer getIsEmployee() {
		return isEmployee;
	}


	
	public void setIsEmployee(Integer isEmployee) {
		this.isEmployee = isEmployee;
	}

	
	public Integer getIsLongUser() {
		return isLongUser;
	}


	
	public void setIsLongUser(Integer isLongUser) {
		this.isLongUser = isLongUser;
	}


	
	public String getUserphone() {
		return userphone;
	}


	
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	
	
	public String getFullUnitName() {
		return fullUnitName;
	}
	
	
	public void setFullUnitName(String fullUnitName) {
		this.fullUnitName = fullUnitName;
	}


	public UserEntity toPO() {
		UserEntity po = new UserEntity();
		po.setActionType(actionType);
		po.setActUserId(actUserId);
		po.setAlias(alias);
		po.setAvailable(available);
		po.setCode(code);
		po.setDescription(description);
		po.setDocId(docId);
		po.setEmail(email);
		po.setFullName(fullName);
		po.setId(id);
		po.setIsMailUser(isMailUser);
		po.setName(name);
		po.setShortPinyin(shortPinyin);
		po.setPassword(password);
		po.setSort(sort);
		po.setTitle(title);
		po.setUniqueCode(uniqueCode);
		po.setUnitUC(unitUniqueCode);
		po.setIsEmployee(isEmployee);
		po.setIsLongUser(isLongUser);
		po.setUserphone(userphone);
		if (orgId != null) {
			OrgEntity org = new OrgEntity();
			org.setId(orgId);
			po.setOrg(org);
		}
		if (unitId != null) {
			UnitEntity unit = new UnitEntity();
			unit.setId(unitId);
			po.setUnit(unit);
		}
		return po;
	}
	

	public String getUserCode() {
		return this.getUniqueCode();
	}
	public String getMobile() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User other = (User) o;

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
		return "User[" + "id=" + id + ", uniqueCode='" + uniqueCode + "', name='" + name + "']";
	}

}
