package com.suredy.tools.file.model;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * 基础文件对象模型
 * 
 * @author VIVID.G
 * @since 2016-10-31
 * @version v0.1
 */
@Entity
@Table(name = "tb_file")
public class FileModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 上传时间 */
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "upload_time", nullable = false)
	private Date uploadTime;

	/* 文件的实际名称 */
	@JsonIgnore
	@Column(name = "name", length = 200, nullable = false)
	private String name;

	/* 文件后缀 */
	@JsonIgnore
	@Column(name = "file_fuffix", nullable = false)
	private String suffix;

	/* 文件路径（相对路径） */
	@JsonIgnore
	@Column(name = "relative_file_path", length = 500, nullable = false)
	private String relativeFilePath;
	
	/* 文件摘要码 */
	@Column(name = "SHACODE", nullable = false)
	private String shaCode;
	
	@Transient
	private Boolean repeated;

	/* 文件大小 */
	@JsonIgnore
	@Column(name = "file_size", nullable = false)
	private Long size;

	/* 扩展字段 */
	@JsonIgnore
	@Column(name = "extend_data", nullable = true, columnDefinition = "longtext")
	private String extendData;

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getExtendData() {
		return extendData;
	}

	public void setExtendData(String extendData) {
		this.extendData = extendData;
	}

	/**
	 * @return the shaCode
	 */
	public String getShaCode() {
		return shaCode;
	}

	
	/**
	 * @param shaCode the shaCode to set
	 */
	public void setShaCode(String shaCode) {
		this.shaCode = shaCode;
	}
	
	// ========== 以下是业务方法 ========== //

	


	/**
	 * 文件全称
	 * 
	 * @return
	 */
	@Transient
	public String getFileName() {
		return this.getName() + this.getSuffix();
	}

	
	/**
	 * @return the repeated
	 */
	public Boolean getRepeated() {
		return repeated;
	}

	
	/**
	 * @param repeated the repeated to set
	 */
	public void setRepeated(Boolean repeated) {
		this.repeated = repeated;
	}

	/**
	 * 文件大小
	 * 
	 * @return
	 * 
	 */
	@Transient
	public String getFileSize() {
		if (this.getSize() == null)
			return "";

		DecimalFormat df = new DecimalFormat("0.##");

		float b = 1f; // B
		float k = b * 1024; // 1K
		float m = k * 1024; // 1M
		float g = m * 1024; // 1G
		float t = g * 1024; // 1T

		float val = 0f;

		if ((val = this.getSize() / t) > 1) {
			return df.format(val) + "T";
		}

		if ((val = this.getSize() / g) > 1) {
			return df.format(val) + "G";
		}

		if ((val = this.getSize() / m) > 1) {
			return df.format(val) + "M";
		}

		if ((val = this.getSize() / k) > 1) {
			return df.format(val) + "K";
		}

		return this.getSize() + "B";
	}

	/**
	 * 文件访问路径
	 * 
	 * @return
	 */
	@Transient
	public String getFilePath() {
		if (StringUtils.isBlank(this.getRelativeFilePath()))
			return null;

		return this.getRelativeFilePath().replace(File.separatorChar, '/');
	}

	/**
	 * 文件上传时间
	 * 
	 * @return
	 */
	public String getFileTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(this.getUploadTime());
	}

	/**
	 * 文件类型
	 * 
	 * @return
	 */
	public String getFileType() {
		return this.getSuffix();
	}

}
