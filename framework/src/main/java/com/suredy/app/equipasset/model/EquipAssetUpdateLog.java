package com.suredy.app.equipasset.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.core.model.BaseModel;

/**
 * 
 * @author lhl 设备物品资产表
 *
 */
@Entity
@Table(name = "t_app_equpdatelog")
public class EquipAssetUpdateLog extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8382644638724667134L;

	@Column(name = "eq_id", length = 50)
	private String eqId;// 设备id

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updateEq")
	private Date updateEq;// 修改日期

	@Column(length = 11)
	private Integer updateType;// 修改类型 （0：页面修改，1：pc软件修改，2：巡检app修改）

	@Column(name = "name", length = 50)
	private String name;// 修改人

	@Column(name = "laterContent", length = 5000)
	private String laterContent;// 修改后的内容

	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public Date getUpdateEq() {
		return updateEq;
	}

	public void setUpdateEq(Date updateEq) {
		this.updateEq = updateEq;
	}

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLaterContent() {
		return laterContent;
	}

	public void setLaterContent(String laterContent) {
		this.laterContent = laterContent;
	}

}
