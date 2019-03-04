package com.suredy.eav.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * EAV模型之属性值模型
 * 
 * @author VIVID.G
 * @since 2017-3-3
 * @version v0.1
 */
@MappedSuperclass
public abstract class EAVAttributeValue<E, A> extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 属性值 */
	@Column(nullable = false)
	private String value;

	/* 表单实体 */
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "entry_id")
	private E entry;

	/* 数据对应的表单属性 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "attribute_id")
	private A attribute;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public E getEntry() {
		return entry;
	}

	public void setEntry(E entry) {
		this.entry = entry;
	}

	public A getAttribute() {
		return attribute;
	}

	public void setAttribute(A attribute) {
		this.attribute = attribute;
	}

}
