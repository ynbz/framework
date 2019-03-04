package com.suredy.eav.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;
import com.suredy.formbuilder.eav.constant.DataType;

/**
 * EAV模型之属性元信息模型
 * 
 * @author VIVID.G
 * @since 2017-3-3
 * @version v0.1
 */
@MappedSuperclass
public abstract class EAVAttributeMetadata<A> extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 数据类型 */
	@Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false)
	private DataType dataType;

	/* 是否必填 */
	@Column(name = "required", nullable = true)
	private Boolean required;

	/* 数据格式 */
	@Column(name = "format", nullable = true)
	private String format;

	/* 是否可筛选 */
	@Column(name = "searchable", nullable = true)
	private Boolean searchable;

	/* 表单属性 */
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_id")
	private A attribute;

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	public A getAttribute() {
		return attribute;
	}

	public void setAttribute(A attribute) {
		this.attribute = attribute;
	}

}
