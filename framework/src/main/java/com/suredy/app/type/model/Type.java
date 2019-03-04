package com.suredy.app.type.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.suredy.app.equipasset.model.EquipAsset;
import com.suredy.core.model.BaseModel;

@Entity
@Table(name="T_APP_TYPE")
public class Type extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeName;
	private int sort;
	private int isChildNode;
	private String hint;//提示信息
	
	@Transient
	private String isNeed;//是否有需求项
	
	@Transient
	private String isBeiYong;//是否是备用状态
	
	
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name = "parentId")
	private Type parent;

	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@Fetch(FetchMode.SUBSELECT)
	private List<Type> childrenItems;
	
	/*@OneToMany(mappedBy = "type",cascade={CascadeType.REFRESH,CascadeType.REMOVE})
	private List<Article> articleList;*/
	
	@OneToMany(mappedBy = "type", cascade={CascadeType.ALL}, targetEntity = EquipAsset.class)  
	@Fetch(FetchMode.SUBSELECT)
	private List<EquipAsset> equipList;
	
		
	@OneToMany(mappedBy = "type", cascade = {CascadeType.ALL}, targetEntity = ClassifyManage.class)
	@OrderBy("sort desc")
	@Fetch(FetchMode.SUBSELECT)
	private List<ClassifyManage> classifyList;
	
	@OneToMany(mappedBy = "type",cascade={CascadeType.REFRESH,CascadeType.REMOVE})
	private List<TypeTemplateInfo> typeTemplateInfolist;
	
	/*@OneToMany(mappedBy = "type",cascade={CascadeType.REFRESH,CascadeType.REMOVE})
	private List<Applyment> applymentList;*/
	
	
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

	
	public List<Type> getChildrenItems() {
		return childrenItems;
	}

	
	public void setChildrenItems(List<Type> childrenItems) {
		this.childrenItems = childrenItems;
	}

	/*
	public List<Article> getArticleList() {
		return articleList;
	}

	
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}*/

	
	public Type getParent() {
		return parent;
	}

	
	public void setParent(Type parent) {
		this.parent = parent;
	}

	

	
	public int getIsChildNode() {
		return isChildNode;
	}

	
	public void setIsChildNode(int isChildNode) {
		this.isChildNode = isChildNode;
	}

	public List<EquipAsset> getEquipList() {
		return equipList;
	}

	public void setEquipList(List<EquipAsset> equipList) {
		this.equipList = equipList;
	}

	public List<ClassifyManage> getClassifyList() {
		return classifyList;
	}

	public void setClassifyList(List<ClassifyManage> classifyList) {
		this.classifyList = classifyList;
	}

	
	public List<TypeTemplateInfo> getTypeTemplateInfolist() {
		return typeTemplateInfolist;
	}

	
	public void setTypeTemplateInfolist(List<TypeTemplateInfo> typeTemplateInfolist) {
		this.typeTemplateInfolist = typeTemplateInfolist;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

		
	
	public String getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}

	public String getIsBeiYong() {
		return isBeiYong;
	}

	public void setIsBeiYong(String isBeiYong) {
		this.isBeiYong = isBeiYong;
	}
	
	
}
