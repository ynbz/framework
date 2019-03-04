package com.suredy.app.consumable.model;

import java.lang.reflect.Method;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.suredy.core.model.BaseModel;

/**
 * 耗材Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_consumeableManage")
public class ConsumableManage extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -825638795516230481L;
	
	
	private String cunsumModel;//耗材型号
	//耗材类型
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="consumTypeID")
	private ConsumerType type;
	
	private String consumableName;//耗材名称
	private String unitPrice;//单价
	private String stock;//库存量
	private String hasTheDosage;//已用量
	private String footprint;//占用量
	private String usrEquipType;//适用设备类型未使用
	private String comm;//备注
	private String vendor;//生产厂商
	private String supplier;//供应商
	private String equipModelApply;//适用设备型号
	private String cunsumbrand;//耗材品牌
	private String unit;//单位
	private String cunsumCode;//耗材代码
	
	@Transient
	private String typeID;
	
	
	@Column(length = 100)
	private String col0;
	@Column(length = 100)
	private String col1;
	@Column(length = 100)
	private String col2;
	@Column(length = 100)
	private String col3;
	@Column(length = 100)
	private String col4;
	@Column(length = 100)
	private String col5;
	@Column(length = 100)
	private String col6;
	@Column(length = 100)
	private String col7;
	@Column(length = 100)
	private String col8;
	@Column(length = 100)
	private String col9;
	@Column(length = 100)
	private String col10;
	@Column(length = 100)
	private String col11;
	@Column(length = 100)
	private String col12;
	@Column(length = 100)
	private String col13;
	@Column(length = 100)
	private String col14;
	@Column(length = 100)
	private String col15;
	@Column(length = 100)
	private String col16;
	@Column(length = 100)
	private String col17;
	@Column(length = 100)
	private String col18;
	@Column(length = 100)
	private String col19;
	public String getCunsumModel() {
		return cunsumModel;
	}
	public void setCunsumModel(String cunsumModel) {
		this.cunsumModel = cunsumModel;
	}
	

	public String getConsumableName() {
		return consumableName;
	}
	public void setConsumableName(String consumableName) {
		this.consumableName = consumableName;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getHasTheDosage() {
		return hasTheDosage;
	}
	public void setHasTheDosage(String hasTheDosage) {
		this.hasTheDosage = hasTheDosage;
	}
	public String getFootprint() {
		return footprint;
	}
	public void setFootprint(String footprint) {
		this.footprint = footprint;
	}
	public String getUsrEquipType() {
		return usrEquipType;
	}
	public void setUsrEquipType(String usrEquipType) {
		this.usrEquipType = usrEquipType;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	
	
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	
	
	public String getEquipModelApply() {
		return equipModelApply;
	}
	public void setEquipModelApply(String equipModelApply) {
		this.equipModelApply = equipModelApply;
	}
	public String getCunsumbrand() {
		return cunsumbrand;
	}
	public void setCunsumbrand(String cunsumbrand) {
		this.cunsumbrand = cunsumbrand;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCunsumCode() {
		return cunsumCode;
	}
	public void setCunsumCode(String cunsumCode) {
		this.cunsumCode = cunsumCode;
	}
	public String getTypeID() {
		return this.type.getId();
	}
	public String getCol0() {
		return col0;
	}
	public void setCol0(String col0) {
		this.col0 = col0;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getCol6() {
		return col6;
	}
	public void setCol6(String col6) {
		this.col6 = col6;
	}
	public String getCol7() {
		return col7;
	}
	public void setCol7(String col7) {
		this.col7 = col7;
	}
	public String getCol8() {
		return col8;
	}
	public void setCol8(String col8) {
		this.col8 = col8;
	}
	public String getCol9() {
		return col9;
	}
	public void setCol9(String col9) {
		this.col9 = col9;
	}
	public String getCol10() {
		return col10;
	}
	public void setCol10(String col10) {
		this.col10 = col10;
	}
	public String getCol11() {
		return col11;
	}
	public void setCol11(String col11) {
		this.col11 = col11;
	}
	public String getCol12() {
		return col12;
	}
	public void setCol12(String col12) {
		this.col12 = col12;
	}
	public String getCol13() {
		return col13;
	}
	public void setCol13(String col13) {
		this.col13 = col13;
	}
	public String getCol14() {
		return col14;
	}
	public void setCol14(String col14) {
		this.col14 = col14;
	}
	public String getCol15() {
		return col15;
	}
	public void setCol15(String col15) {
		this.col15 = col15;
	}
	public String getCol16() {
		return col16;
	}
	public void setCol16(String col16) {
		this.col16 = col16;
	}
	public String getCol17() {
		return col17;
	}
	public void setCol17(String col17) {
		this.col17 = col17;
	}
	public String getCol18() {
		return col18;
	}
	public void setCol18(String col18) {
		this.col18 = col18;
	}
	public String getCol19() {
		return col19;
	}
	public void setCol19(String col19) {
		this.col19 = col19;
	}

	/**
	 * 
	 * @param colnum
	 * @return 反射获取属性值
	 * 
	 * 
	 */
	public String getColvalue(String colnum){
		
		if(StringUtils.isEmpty(colnum))  return "";	
		 String firstLetter = colnum.substring(0, 1).toUpperCase();
		 String getter = "get"+ firstLetter+ colnum.substring(1);    
         try {
        	
			Method method = this.getClass().getMethod(getter, new Class[] {});
			String s=method.invoke(this, new Object[] {}).toString();
			return s;
		}catch(Exception e){
			return "";
		}
       
	}
	public ConsumerType getType() {
		return type;
	}
	public void setType(ConsumerType type) {
		this.type = type;
	}
	
	
}
