package com.suredy.app.consumable.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

/**
 * 关联设备Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_consumerAndEquipType")
public class ConsumableAndEquipType extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1102341528287776574L;
	
	private String consumableID;
	private String equipTypeID;
	
	public String getConsumableID() {
		return consumableID;
	}
	
	public void setConsumableID(String consumableID) {
		this.consumableID = consumableID;
	}
	
	public String getEquipTypeID() {
		return equipTypeID;
	}
	
	public void setEquipTypeID(String equipTypeID) {
		this.equipTypeID = equipTypeID;
	}
	
	

}
