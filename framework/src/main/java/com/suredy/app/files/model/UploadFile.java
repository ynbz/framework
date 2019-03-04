package com.suredy.app.files.model;

import java.io.Serializable;
import java.util.Date;

import com.suredy.app.files.entity.FolderEntity;
import com.suredy.app.files.entity.UploadFileEntity;

public class UploadFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7949732912302025586L;

	private String id;

	private String title;

	private String keyWord;

	private Integer status;
	
	private String fileId;

	private String folderId;

	private String folderName;

	private String uploader;// 上传人

	private Date uploadTime;// 上传时间

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
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
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
	 * @return the uploader
	 */
	public String getUploader() {
		return uploader;
	}

	/**
	 * @param uploader the uploader to set
	 */
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	/**
	 * @return the uploadTime
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public UploadFileEntity toPO() {
		UploadFileEntity po = new UploadFileEntity();
		if (id != null) {
			po.setId(id);
		}
		po.setFileUrlId(fileId);
		po.setTitle(title);
		po.setUploader(uploader);
		po.setUploadTime(uploadTime);
		po.setKeyWord(keyWord);
		po.setStatus(status);
		if (folderId != null) {
			FolderEntity ft = new FolderEntity();
			ft.setId(folderId);
			po.setFolder(ft);
		}
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UploadFile other = (UploadFile) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (title != null ? !title.equals(other.title) : other.title != null) {
			return false;
		}
		if (fileId != null ? !fileId.equals(other.fileId) : other.fileId != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (title != null ? title.hashCode() : 0) + (fileId != null ? fileId.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "UploadFile[" + "id=" + id + ", title='" + title + "', fileId='" + fileId + "']";
	}

}
