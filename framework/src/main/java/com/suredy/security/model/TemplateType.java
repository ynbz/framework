package com.suredy.security.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "TB_TEMPLATE_TYPE")
public class TemplateType extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4052257035792311073L;
	private String typeName;
	private int sort;
	private String code;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "parentId")
	private TemplateType parent;

	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@Fetch(FetchMode.SUBSELECT)
	private List<TemplateType> childrenItems;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TemplateType getParent() {
		return parent;
	}

	public void setParent(TemplateType parent) {
		this.parent = parent;
	}

	public List<TemplateType> getChildrenItems() {
		return childrenItems;
	}

	public void setChildrenItems(List<TemplateType> childrenItems) {
		this.childrenItems = childrenItems;
	}

}
