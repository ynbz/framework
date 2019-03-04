package com.suredy.flow.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.suredy.core.mvc.model.Tree;

@JsonInclude(Include.NON_EMPTY)
public class SenderData {

	private String id;
	private String name;
	private String style;
	private String obj;
	private String topLayer;
	private String text;
	private List<Tree> users = null;

	public SenderData() {
		this.users = new ArrayList<Tree>();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getObj() {
		return this.obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getTopLayer() {
		return this.topLayer;
	}

	public void setTopLayer(String topLayer) {
		this.topLayer = topLayer;
	}

	public List<Tree> getUsers() {
		return this.users;
	}

	public void setUsers(List<Tree> users) {
		this.users = users;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
