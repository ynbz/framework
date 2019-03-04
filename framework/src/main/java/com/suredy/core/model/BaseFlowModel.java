package com.suredy.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "JavassistLazyInitializer"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseFlowModel extends BaseModel {

	private static final long serialVersionUID = 2317945260473801015L;

	/* 流程实例处理ID */
	@Column(length = 32, nullable = false)
	private String processId;

	/* 文件类型ID */
	@Column(length = 32, nullable = false)
	private String fileTypeCode;

	/* 创建日期 */
	@Column(nullable = false)
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date createTime;

	/* 删除日期 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date deleteTime;

	/* 创建人code */
	@Column(nullable = false)
	private String creatorCode;

	/* 创建人全名（包含部门路径） */
	@Column(nullable = false)
	private String creatorFullName;

	/* 创建人单位code */
	@Column(nullable = false)
	private String creatorUnitCode;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	public String getCreatorFullName() {
		return creatorFullName;
	}

	public void setCreatorFullName(String creatorFullName) {
		this.creatorFullName = creatorFullName;
	}

	public String getCreatorUnitCode() {
		return creatorUnitCode;
	}

	public void setCreatorUnitCode(String creatorUnitCode) {
		this.creatorUnitCode = creatorUnitCode;
	}

	@Transient
	public String getCreatorShortName() {
		String fn = this.getCreatorFullName();

		int index = -1;

		if (StringUtils.isBlank(fn) || (index = fn.indexOf('/')) < 0)
			return fn;

		return fn.substring(0, index);
	}

	@Transient
	public String getCreatorFullUnitName() {
		String sn = this.getCreatorShortName();
		String fn = this.getCreatorFullName();

		if (StringUtils.isBlank(sn))
			sn = "";

		if (StringUtils.isBlank(fn))
			fn = "";

		return fn.replace(sn + "/", "");
	}

	@Transient
	public String getCreatorShortUnitName() {
		String fn = this.getCreatorFullUnitName();

		int index = -1;

		if (StringUtils.isBlank(fn) || (index = fn.indexOf('/')) < 0)
			return fn;

		return fn.substring(0, index);
	}

}
