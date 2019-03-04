package com.suredy.app.notice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

/**
 * 公告对象
 * 
 * @author zyh
 * 
 *
 */
@Entity
@Table(name = "T_APP_NOTICE")
public class NoticeEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5209553844181080241L;

	@Column(name = "TITLE")
	private String title;// 标题

	@Column(name = "ISSUERID")
	private String issuerId;// 发布人id

	@Column(name = "ISSUER")
	private String issuer;// 发布人

	@Column(name = "ISSUEUNITID")
	private String issueUnitId;// 发布部门id

	@Column(name = "ISSUEUNIT")
	private String issueUnit;// 发布部门

	@Column(name = "CREATEDATE")
	private Date createDate;// 发布时间

	@Column(name = "VALIDDATE")
	private Date validDate;// 有效时间

	@Column(name = "TYPE")
	private String type;// 1：公司公告，2：部门公告

	@Column(name = "FILEID")
	private String fileId;// 附件id

	@Column(name = "CONTENT")
	private String content;// 内容

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getIssueUnitId() {
		return issueUnitId;
	}

	public void setIssueUnitId(String issueUnitId) {
		this.issueUnitId = issueUnitId;
	}

	public String getIssueUnit() {
		return issueUnit;
	}

	public void setIssueUnit(String issueUnit) {
		this.issueUnit = issueUnit;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
