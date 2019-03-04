/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年6月26日
 * @version 0.1
 */
package com.suredy.resource.entity;

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
import com.suredy.resource.model.Menu;

/**
 * @author ZhangMaoren
 *
 */
@Entity
@Table(name = "T_RESOURCE_MENU")
public class MenuEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365810619847576336L;

	@Column(name = "URL")
	private String url;

	@Column(name = "NAME", nullable = false)
	private String text;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private Integer sort;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "ACTIVE")
	private Boolean active;

	@Column(name = "COLLAPSE")
	private Boolean collapse;

	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn(name="RESOURCEID")  
    private ResourceEntity resource;  
	
	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@OrderBy("sort")
	@Fetch(FetchMode.SUBSELECT)
	private List<MenuEntity> children;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PARENTID")
	private MenuEntity parent;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the collapse
	 */
	public Boolean getCollapse() {
		return collapse;
	}

	/**
	 * @param collapse the collapse to set
	 */
	public void setCollapse(Boolean collapse) {
		this.collapse = collapse;
	}


	
	/**
	 * @return the resourceId
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
	public MenuEntity getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(MenuEntity parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<MenuEntity> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<MenuEntity> children) {
		this.children = children;
	}

	@Transient
	public Menu toVO() {
		Menu vo = new Menu();
		vo.setActive(active);
		vo.setCollapse(collapse);
		vo.setIcon(icon);
		vo.setId(id);
		vo.setSort(sort);
		vo.setText(text);
		vo.setUrl(url);
		vo.setResourceId(resource.getId());
		vo.setParent(parent == null ? null : parent.getId());
		return vo;
	}
}
