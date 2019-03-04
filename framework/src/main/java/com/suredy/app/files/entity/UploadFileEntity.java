package com.suredy.app.files.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.app.files.model.UploadFile;
import com.suredy.core.model.BaseModel;
import com.suredy.tools.file.model.FileModel;

@Entity
@Table(name = "T_APP_FMFILE")
public class UploadFileEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1044857790870698089L;

	@Column(name = "TITLE")
	private String title;// 文件名称

	@Column(name = "KEYWORD")
	private String keyWord;

	@Column(name = "UPLOADER")
	private String uploader;// 上传人

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPLOADTIME", nullable = false)
	private Date uploadTime;// 上传时间
	
	
	@Column(name = "STATUS", columnDefinition = "int default 0", nullable = false)
	private int status;

	@Column(name = "FILEURLID")
	private String fileUrlId;// 关联文件路径id

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "FOLDER")
	private FolderEntity folder;// 文件类型id

	@Transient
	private List<FolderEntity> folders;

	@Transient
	private List<FileModel> files;

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
	
	

	
	public int getStatus() {
		return status;
	}

	
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the fileUrlId
	 */
	public String getFileUrlId() {
		return fileUrlId;
	}

	/**
	 * @param fileUrlId the fileUrlId to set
	 */
	public void setFileUrlId(String fileUrlId) {
		this.fileUrlId = fileUrlId;
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
	 * @return the folders
	 */
	public List<FolderEntity> getFolders() {
		return folders;
	}

	
	/**
	 * @param folders the folders to set
	 */
	public void setFolders(List<FolderEntity> folders) {
		this.folders = folders;
	}

	/**
	 * @return the files
	 */
	public List<FileModel> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<FileModel> files) {
		this.files = files;
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

	@Transient
	public UploadFile toVO() {
		UploadFile vo = new UploadFile();
		vo.setFolderId(folder == null ? null : folder.getId());
		vo.setFolderName(folder == null ? null : folder.getName());
		vo.setFileId(fileUrlId);
		vo.setKeyWord(keyWord);
		vo.setId(id);
		vo.setTitle(title);
		vo.setUploader(uploader);
		vo.setUploadTime(uploadTime);
		vo.setStatus(status);
		return vo;
	}

}
