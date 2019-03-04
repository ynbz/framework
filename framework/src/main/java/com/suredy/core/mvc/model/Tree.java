package com.suredy.core.mvc.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * tree model
 * 
 * @author VIVID.G
 * @since 2015-4-23
 * @version v0.1
 */
@JsonInclude(Include.NON_NULL)
public class Tree implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean collapse;

	private Boolean active;

	private String icon;

	private String text;

	private Boolean checked;

	private Object data;

	private String loadDataFrom; // 异步加载数据的uri

	private List<Tree> children;

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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getLoadDataFrom() {
		return loadDataFrom;
	}

	public void setLoadDataFrom(String loadDataFrom) {
		this.loadDataFrom = loadDataFrom;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

}
