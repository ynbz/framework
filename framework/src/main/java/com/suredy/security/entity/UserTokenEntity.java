package com.suredy.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "T_SECURITY_USERTOKEN")
public class UserTokenEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3325018614812232646L;
	
	@Column(name = "USERCODE", nullable = false)
	private String userCode;

	@Column(name = "TOKEN", nullable = false)
	private String token;
	
	@Column(name = "CREATETIME")
	private Date createTime;
	
	@Column(name = "REFRESHTIME")
	private Date refreshTime;
	
	@Column(name = "ACTIVETIME")
	private Date activeTime;
	
	@Column(name = "ISACTIVE")
	private Integer isActive;

	

	
	
	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}


	
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * @return the refreshTime
	 */
	public Date getRefreshTime() {
		return refreshTime;
	}

	
	/**
	 * @param refreshTime the refreshTime to set
	 */
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	
	/**
	 * @return the activeTime
	 */
	public Date getActiveTime() {
		return activeTime;
	}

	
	/**
	 * @param activeTime the activeTime to set
	 */
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	
	/**
	 * @return the isActive
	 */
	public Integer getIsActive() {
		return isActive;
	}

	
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
	

}
