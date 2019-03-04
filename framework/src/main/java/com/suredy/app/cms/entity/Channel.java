/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @since 2017年3月29日
 * @version 1.0
 */
package com.suredy.app.cms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_APP_CMS_CHANNEL")
public class Channel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7431261067929187430L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private Integer sort;

	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@OrderBy("sort")
	@Fetch(FetchMode.SUBSELECT)
	private List<Channel> children;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PARENTID")
	private Channel parent;

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
	 * @return the children
	 */
	public List<Channel> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Channel> children) {
		this.children = children;
	}

	/**
	 * @return the parent
	 */
	public Channel getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Channel parent) {
		this.parent = parent;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
