package com.suredy.security.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

/**
 * @author SDKJ
 *
 */
@Entity
@Table(name = "T_SECURITY_LOGINLOG")
public class LoginLogEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4198652924039653983L;

	private String loginName;// 登录人

	private String loginIp;// 登录ip

	private Date logindate;// 登录时间

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

}
