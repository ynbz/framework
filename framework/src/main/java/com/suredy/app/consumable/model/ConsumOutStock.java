package com.suredy.app.consumable.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.core.model.BaseModel;

/**
 * 耗材出库记录Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_consumableOutStock")
public class ConsumOutStock  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2605130189835844718L;
	
	private String consumableId; //耗材id
	private String stockNum;//出库数量
	private String departId;//领用部门
	private String departName;//部门名称
	private String userId;//领用人id
	private String userName;//领用人名称
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "outStocktime")
	private Date outStocktime;//入库时间
	
	private String equipId;//使用设备id
	
	@Transient
	private String equipModel;//设备型号
	
	@Transient
	private String assetId;//资产id
	
	@Transient
	private String typeName;//设备类型
	
	
	public String getConsumableId() {
		return consumableId;
	}
	public void setConsumableId(String consumableId) {
		this.consumableId = consumableId;
	}
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getOutStocktime() {
		return outStocktime;
	}
	public void setOutStocktime(Date outStocktime) {
		this.outStocktime = outStocktime;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getEquipModel() {
		return equipModel;
	}
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	

}
