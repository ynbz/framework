package com.suredy.app.consumable.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.suredy.core.model.BaseModel;

/**
 * 耗材类型Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_consumType")
public class ConsumerType extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9133543970809800117L;
	
	private String consumerName;
	private int sort;
	private int isChildNode;
	
	@Transient
	private String resourceid;
	
	@Transient
	private String resourcename;
	
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name = "parentId")
	private ConsumerType parent;
	
	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	private List<ConsumerType> childrenItems;

	@OneToMany(mappedBy = "type",cascade={CascadeType.REFRESH,CascadeType.REMOVE})
	@OrderBy("sort desc")
	private List<ConsumProperty> consumPropertylist;

	@OneToMany(mappedBy = "type",cascade={CascadeType.REFRESH,CascadeType.REMOVE})
	private List<ConsumableManage> consumableManageList;
	
	@OneToMany(mappedBy = "type",cascade={CascadeType.REFRESH,CascadeType.REMOVE})
	private List<ConsumableTemplateInfo> consumTemplateInfolist;
	
	public String getConsumerName() {
		return consumerName;
	}

	
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	
	public int getSort() {
		return sort;
	}

	
	public void setSort(int sort) {
		this.sort = sort;
	}

	
	public ConsumerType getParent() {
		return parent;
	}

	
	public void setParent(ConsumerType parent) {
		this.parent = parent;
	}

	
	public List<ConsumerType> getChildrenItems() {
		return childrenItems;
	}

	
	public void setChildrenItems(List<ConsumerType> childrenItems) {
		this.childrenItems = childrenItems;
	}


	
	public int getIsChildNode() {
		return isChildNode;
	}


	
	public void setIsChildNode(int isChildNode) {
		this.isChildNode = isChildNode;
	}


	
	public String getResourceid() {
		return resourceid;
	}


	
	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}


	
	public String getResourcename() {
		return resourcename;
	}


	
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}


	
	public List<ConsumProperty> getConsumPropertylist() {
		return consumPropertylist;
	}


	
	public void setConsumPropertylist(List<ConsumProperty> consumPropertylist) {
		this.consumPropertylist = consumPropertylist;
	}


	public List<ConsumableManage> getConsumableManageList() {
		return consumableManageList;
	}


	public void setConsumableManageList(List<ConsumableManage> consumableManageList) {
		this.consumableManageList = consumableManageList;
	}


	public List<ConsumableTemplateInfo> getConsumTemplateInfolist() {
		return consumTemplateInfolist;
	}


	public void setConsumTemplateInfolist(List<ConsumableTemplateInfo> consumTemplateInfolist) {
		this.consumTemplateInfolist = consumTemplateInfolist;
	}
	

}
