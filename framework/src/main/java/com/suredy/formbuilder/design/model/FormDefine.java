package com.suredy.formbuilder.design.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.core.model.BaseModel;

/**
 * 表单定义模型
 * 
 * @author VIVID.G
 * @since 2017-2-28
 * @version v0.1
 */
@Entity
@Table(name = "tb_form_define")
public class FormDefine extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 是否启用 */
	@Column(nullable = false)
	private Boolean enable;

	/* 表单名称 */
	@Column(nullable = false)
	private String name;

	/* 版本 */
	@Column(nullable = false)
	private String version;

	/* 创建时间 */
	@Column(name = "create_time", nullable = false, columnDefinition = "timestamp")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/* 最后修改时间 */
	@Column(name = "last_edit_time", nullable = false, columnDefinition = "timestamp")
	private Date lastEditTime;

	/* 表单数据（json格式） */
	@Column(name = "form_data", nullable = false, columnDefinition = "longtext")
	private String formData;

	/* 表单描述 */
	@Column(name = "form_desc", columnDefinition = "longtext")
	private String formDesc;

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	/* //////////////////// 以下是业务方法及属性 //////////////////// */

	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date minCreateTime;
	@Transient
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date maxCreateTime;

	public Date getMinCreateTime() {
		return minCreateTime;
	}

	public void setMinCreateTime(Date minCreateTime) {
		this.minCreateTime = minCreateTime;
	}

	public Date getMaxCreateTime() {
		return maxCreateTime;
	}

	public void setMaxCreateTime(Date maxCreateTime) {
		this.maxCreateTime = maxCreateTime;
	}

}
