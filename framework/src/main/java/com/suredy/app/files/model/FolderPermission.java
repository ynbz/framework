package com.suredy.app.files.model;

import java.io.Serializable;
import java.util.Date;

import com.suredy.app.files.entity.FolderEntity;
import com.suredy.app.files.entity.FolderPermissionEntity;

public class FolderPermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7871896913810744796L;

	private String id;

	private String subject;

	private Integer subjectType;

	private String folderId;

	private String folderName;
	
	private Integer inherited;

	private Date lastModified;// 最后修改时间

	
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
	 * @return the folderId
	 */
	public String getFolderId() {
		return folderId;
	}

	
	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	
	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	
	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
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
	
	public FolderPermissionEntity toPO() {
		FolderPermissionEntity po = new FolderPermissionEntity();
		if (folderId != null) {
			FolderEntity ft = new FolderEntity();
			ft.setId(folderId);
			po.setFolder(ft);
		}
		po.setId(id);
		po.setInherited(inherited);
		po.setLastModified(lastModified);
		po.setSubject(subject);
		po.setSubjectType(subjectType);

		return po;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		FolderPermission other = (FolderPermission) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (folderId != null ? !folderId.equals(other.folderId) : other.folderId != null) {
			return false;
		}
		if (subject != null ? !subject.equals(other.subject) : other.subject != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (folderId != null ? folderId.hashCode() : 0) + (subject != null ? subject.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "FolderPermission[" + "id=" + id + ", folderId='" + folderId + "', subject='" + subject + "']";
	}

}
