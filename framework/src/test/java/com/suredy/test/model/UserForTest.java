package com.suredy.test.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.suredy.core.model.BaseModel;

@Entity()
@Table(name = "tb_user")
public class UserForTest extends BaseModel {

	private static final long serialVersionUID = 4113011478109292310L;

	private String name;
	private String pswd;

	@Transient
	private String checkCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

}
