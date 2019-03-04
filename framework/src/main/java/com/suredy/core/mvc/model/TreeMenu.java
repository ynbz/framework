package com.suredy.core.mvc.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * tree menu data model
 * 
 * @author VIVID.G
 * @since 2015-7-6
 * @version v0.1
 */
@JsonInclude(Include.NON_EMPTY)
public class TreeMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean collapse;

	private Boolean active;

	private String icon;

	private String text;

	private String uri;

	private Object badge;

	private List<TreeMenu> children;

	private String loadDataFrom; // 异步加载数据的uri

	public Boolean getCollapse() {
		return collapse;
	}

	public void setCollapse(Boolean collapse) {
		this.collapse = collapse;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Object getBadge() {
		return badge;
	}

	public void setBadge(Object badge) {
		this.badge = badge;
	}

	public List<TreeMenu> getChildren() {
		return children;
	}

	public void setChildren(List<TreeMenu> children) {
		this.children = children;
	}

	public String getLoadDataFrom() {
		return loadDataFrom;
	}

	public void setLoadDataFrom(String loadDataFrom) {
		this.loadDataFrom = loadDataFrom;
	}

}
