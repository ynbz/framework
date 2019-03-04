package com.suredy.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 字典
 * 
 * @author VIVID.G
 * @since 2015-7-29
 * @version v0.1
 */
@Entity
@Table(name = "tb_dictionary", uniqueConstraints = {@UniqueConstraint(columnNames = {"groupCode", "val"})})
public class Dictionary extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 字典分组编码 */
	@Column(length = 50, nullable = false)
	private String groupCode;

	/* 字典值 */
	@Column(length = 100, nullable = false)
	private String val;

	/* 是否为系统初始化内容 */
	@Column(name = "is_system", nullable = false, columnDefinition = "int default 0")
	private Boolean system;

	@Column(name = "sort", nullable = false, columnDefinition = "int default 999")
	private Integer sort;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Boolean getSystem() {
		return system;
	}

	public void setSystem(Boolean system) {
		this.system = system;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
