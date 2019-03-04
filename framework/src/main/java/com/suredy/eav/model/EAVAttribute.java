package com.suredy.eav.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * EAV模型之属性对象
 * 
 * @author VIVID.G
 * @since 2017-3-3
 * @version v0.1
 */
@MappedSuperclass
public abstract class EAVAttribute<M, V> extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 属性名称 */
	@Column(nullable = false)
	private String name;

	/* 属性元信息 */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "attribute")
	private M metadata;

	/* 属性值 */
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "attribute")
	private V value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public M getMetadata() {
		return metadata;
	}

	public void setMetadata(M metadata) {
		this.metadata = metadata;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
