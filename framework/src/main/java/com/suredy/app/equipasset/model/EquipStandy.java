package com.suredy.app.equipasset.model;


public class EquipStandy {
	private String assetId;//资产id
	private String equipModel;//设备型号
	private String primeAttribute;//主要配置信息
	private String buydate;//购买日期
	private String isNewEquip;//新旧设备（1：新设备，0：旧设备）
	
	public String getAssetId() {
		return assetId;
	}
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public String getEquipModel() {
		return equipModel;
	}
	
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}

	
	public String getPrimeAttribute() {
		return primeAttribute;
	}

	
	public void setPrimeAttribute(String primeAttribute) {
		this.primeAttribute = primeAttribute;
	}

	
	public String getBuydate() {
		return buydate;
	}

	
	public void setBuydate(String buydate) {
		this.buydate = buydate;
	}

	
	public String getIsNewEquip() {
		return isNewEquip;
	}

	
	public void setIsNewEquip(String isNewEquip) {
		this.isNewEquip = isNewEquip;
	}
	

	
	
}
