/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @since 2017年4月26日
 * @version 1.0
 */
package com.suredy.app.files.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.app.files.model.FolderPermission;
import com.suredy.core.model.BaseModel;

/**
 * @author ZhangMaoren
 *
 *         2017年4月26日
 */
@Entity
@Table(name = "T_APP_FMFOLDERPERMISSION")
public class FolderPermissionEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5176839506784391666L;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "SUBJECTTYPE")
	private Integer subjectType; //1--User, 2--Role, 3--Unit;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FOLDER")
	private FolderEntity folder;//

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "LASTMODIFIED", nullable = false)
	private Date lastModified;

	@Column(name = "INHERITED", columnDefinition = "int default 0") // 1--继承自先辈, 0--自身设置的权限
	private Integer inherited;

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the subjectType
	 */
	public Integer getSubjectType() {
		return subjectType;
	}

	/**
	 * @param subjectType the subjectType to set
	 */
	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	/**
	 * @return the folder
	 */
	public FolderEntity getFolder() {
		return folder;
	}

	/**
	 * @param folder the folder to set
	 */
	public void setFolder(FolderEntity folder) {
		this.folder = folder;
	}

	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the inherited
	 */
	public Integer getInherited() {
		return inherited;
	}

	/**
	 * @param inherited the inherited to set
	 */
	public void setInherited(Integer inherited) {
		this.inherited = inherited;
	}
	
	@Transient
	public FolderPermission toVO() {
		FolderPermission vo = new FolderPermission();
		vo.setFolderId(folder == null ? null : folder.getId());
		vo.setFolderName(folder == null ? null : folder.getName());
		vo.setId(id);
		vo.setInherited(inherited);
		vo.setLastModified(lastModified);
		vo.setSubject(subject);
		vo.setSubjectType(subjectType);

		return vo;
	}

}
