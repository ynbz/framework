package com.suredy.app.liveform.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.suredy.core.model.BaseFlowModel;
import com.suredy.formbuilder.eav.model.FormEntry;

/**
 * 动态表单模型
 * 
 * @author VIVID.G
 * @since 2017-3-13
 * @version v0.1
 */
@Entity
@Table(name = "tb_live_form")
public class LiveForm extends BaseFlowModel {

	private static final long serialVersionUID = 1L;

	/* 标题 */
	@Column(nullable = false)
	private String title;

	/* 文件类型名称 */
	@Column(name = "file_type_name", nullable = false)
	private String fileTypeName;

	/* 文件类型ID */
	@Column(name = "file_type_id", nullable = false)
	private String fileTypeId;

	/* 表单内容 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "form_entry_id")
	private FormEntry formEntry;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public FormEntry getFormEntry() {
		return formEntry;
	}

	public void setFormEntry(FormEntry formEntry) {
		this.formEntry = formEntry;
	}

}
