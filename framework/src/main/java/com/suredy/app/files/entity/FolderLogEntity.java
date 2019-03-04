package com.suredy.app.files.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "T_APP_FMFOLDER_LOG")
public class FolderLogEntity extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2448783406794288561L;
	private String folderId;
	private String logContent;
	private Date logDate;
	private String addUser;
	
	public String getFolderId() {
		return folderId;
	}

	
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getLogContent() {
		return logContent;
	}
	
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	
	public Date getLogDate() {
		return logDate;
	}
	
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
	public String getAddUser() {
		return addUser;
	}
	
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
}
