package com.suredy.flow.form.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;
import com.suredy.flow.form.model.FormType;
import com.suredy.resource.entity.ResourceEntity;

@Entity
@Table(name = "T_APP_FORMTYPE")
public class FormTypeEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 878065767648149792L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private Integer sort;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESOURCEID")
	private ResourceEntity resource;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PARENTID")
	private FormTypeEntity parent;

	
	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, targetEntity = FormTypeEntity.class)
	@OrderBy("sort")
	@Fetch(FetchMode.SUBSELECT)
	private List<FormTypeEntity> children;

	@OneToMany(mappedBy = "type", cascade = {CascadeType.ALL}, targetEntity = FormEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private List<FormEntity> associationForms = new ArrayList<FormEntity>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}


	/**
	 * @return the resource
	 */
	public ResourceEntity getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	/**
	 * @return the parent
	 */
	public FormTypeEntity getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(FormTypeEntity parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<FormTypeEntity> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<FormTypeEntity> children) {
		this.children = children;
	}

	/**
	 * @return the associationForms
	 */
	public List<FormEntity> getAssociationForms() {
		return associationForms;
	}

	/**
	 * @param associationForms the associationForms to set
	 */
	public void setAssociationForms(List<FormEntity> associationForms) {
		this.associationForms = associationForms;
	}
	
	@Transient
	public FormType toVO() {
		FormType vo = new FormType();
		vo.setId(id);
		vo.setName(name);
		vo.setParent(parent == null ? null : parent.getId());
		vo.setResourceId(resource == null ? null : resource.getId());
		vo.setResourceUri(resource == null ? null : resource.getUri());		
		vo.setSort(sort);
		return vo;
	}

}
