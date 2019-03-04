package com.suredy.app.equipasset.model;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.app.type.model.Type;
import com.suredy.core.model.BaseModel;

/**
 * 
 * @author lhl
 * 设备物品资产表	
 *
 */
@Entity
@Table(name="t_app_equipasset")
public class EquipAsset extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3592418946130967428L;

	//资产ID
	@Myannotation(description="资产ID")
	@Column(length = 50)
	private String assetId;
	
	//购买日期
	@Myannotation(description="购买日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "buyDate")
	private Date buyDate;
	
	//资产类型
	@Myannotation(description="资产类型")
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="assetType")
	private Type type;	
	
	//单价
	@Myannotation(description="单价")
	@Column(length = 20)
	private String unitcose;
	
	
	//序列号
	@Myannotation(description="序列号")
	@Column(length = 50)
	private String serialNum;
	
	//RFID
	@Myannotation(description="RFID")
	@Column(length = 50)
	private String rfid;
	
	//责任人
	@Myannotation(description="责任人")
	@Column(length = 50)
	private String responsible;
	
	//责任人id
	@Myannotation(description="责任人id")
	@Column(length = 50)
	private String responsibleId;
	
	//责任人电话
	@Myannotation(description="责任人电话")
	@Column(length = 50)
	private String responsiblePhone;
	
	//使用人联系电话
	@Myannotation(description="使用人联系电话")
	@Column(length = 50)
	private String userPhone;
	
	//状态
	@Myannotation(description="状态")
	@Column(length = 20)
	private String  status;
	
	//领用日期
	@Myannotation(description="领用日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "receiveDate")
	private Date receiveDate;
	
	//设备型号
	@Myannotation(description="设备型号")
	@Column(length = 50)
	private String equipModel;
	
	//生产厂商
	@Myannotation(description="生产厂商")
	@Column(length = 100)
	private String vendor;
	
	//供应商
	@Myannotation(description="供应商")
	@Column(length = 100)
	private String supplier;
	
	//备注
	@Myannotation(description="备注")
	@Column(length = 255)
	private String comm;
	
	//数据可用标识 （1：可用,0:待确认）
	@Myannotation(description="数据可用标识")
	@Column(length = 4)
	private Integer identifying;
	
	//责任单位
	@Myannotation(description="责任单位")
	@Column(length = 100)
	private String userUnit;
	
	//运维（责任）单位id
	@Myannotation(description="运维（责任）单位id")
	@Column(length = 100)
	private String userUnitId;
	
	//使用人id
	@Myannotation(description="使用人id")
	@Column(length = 100)
	private String userID;
	
	//使用人名称
	@Myannotation(description="使用人名称")
	@Column(length = 100)
	private String userName;
	
	//使用人冗余字段
	@Myannotation(description="使用人")
	@Column(length = 50)
	private String userNameeed;
	
	// 使用范围:0、合同制员工;1、部门公用;2、其他用工人员（手写使用人 ）
	@Myannotation(description="使用范围")
	@Column(length = 4)
	private Integer isPublic;
	
	//使用地点
	@Myannotation(description="使用地点")
	@Column(length = 100)
	private String userPlace;
	
	//导入人员id
	@Myannotation(description="导入人员id")
	@Column(length = 32)
	private String upUserId;
	
	//新旧设备（1：新设备，0：旧设备）
	@Myannotation(description="新旧设备")
	@Column(length = 11)
	private Integer isNewEquip;
	
	//设备是借用还是领用（1：借用，0：领用）
	@Myannotation(description="设备是借用还是领用")
	@Column(length = 11)
	private Integer isBorrow;
	
	//合同号
	@Myannotation(description="合同号")
	@Column(length = 100)
	private String contractNumber;
	
	//项目名
	@Myannotation(description="项目名")
	@Column(length = 100)
	private String projectName;
	
	//费用来源
	@Myannotation(description="费用来源")
	@Column(length = 100)
	private String costSource;
	
	//财务资产编号
	@Myannotation(description="财务资产编号")
	@Column(length = 100)
	private String financeAssetNumber;
	
	
	//数据导入是否完整性（1：完整，2：不完整）
	@Myannotation(description="数据导入是否完整性")
	@Column(length = 11)
	private Integer isFull;
	
	//数据不完整项
	@Myannotation(description="数据不完整项")
	@Column(length = 1000)
	private String noFullCause;
	
	//修改日期
	@Myannotation(description="修改日期")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "updateEq")
	private Date updateEq;

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
	
	@Transient
	private String propertyid;
	@Transient
	private String typeName;
	@Transient
	private String typeID;
	@Transient
	private String statusName;
	
	
	public String getUnitcose() {
		return unitcose;
	}
	public void setUnitcose(String unitcose) {
		this.unitcose = unitcose;
	}
	
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	
	
	
	
	public String getAssetId() {
		return assetId;
	}
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public Date getBuyDate() {
		return buyDate;
	}
	
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	public String getSerialNum() {
		return serialNum;
	}
	
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	public String getUserPhone() {
		return userPhone;
	}
	
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	public String getEquipModel() {
		return equipModel;
	}
	
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
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
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	
	
	
	public Integer getIdentifying() {
		return identifying;
	}
	
	public void setIdentifying(Integer identifying) {
		this.identifying = identifying;
	}
	
	public String getUserUnit() {
		return userUnit;
	}
	
	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}

	public String getResponsibleId() {
		return responsibleId;
	}
	
	public void setResponsibleId(String responsibleId) {
		this.responsibleId = responsibleId;
	}
	
	public String getUserUnitId() {
		return userUnitId;
	}
	
	public void setUserUnitId(String userUnitId) {
		this.userUnitId = userUnitId;
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
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
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
	
	//通过属性名称给属性赋值
	public void setColValue(String col, String value) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(col)){
			String firstLetter = col.substring(0, 1).toUpperCase();
			String getter = "set"+ firstLetter+ col.substring(1);   
			try {
				Method method = null;
				if(col.equals("isFull")||col.equals("isBorrow")||col.equals("isNewEquip")||col.equals("isPublic")||col.equals("identifying")){
					method = this.getClass().getDeclaredMethod(getter, Integer.class);
				}else if(col.equals("buyDate")||col.equals("receiveDate")||col.equals("putDate")||col.equals("stopRepair")||col.equals("updateEq")){
					method = this.getClass().getDeclaredMethod(getter, Date.class);
				}else if(col.equals("type")){
					method = this.getClass().getDeclaredMethod(getter, Type.class);
				}else{
					method = this.getClass().getDeclaredMethod(getter, String.class);
				}
				method.invoke(this, value);			
			}catch(Exception e){
				
			}
		}
		 
	}
	
	public String getPropertyid() {
		return propertyid;
	}
	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}
	public String getCol19() {
		return col19;
	}
	public void setCol19(String col19) {
		this.col19 = col19;
	}
	
	public String getTypeName() {
		return this.type.getTypeName();
	}
	
	
	
	public String getTypeID() {
		return this.type.getId();
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getIsPublic() {
		return isPublic;
	}
	
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	
	public String getUserPlace() {
		return userPlace;
	}
	
	public void setUserPlace(String userPlace) {
		this.userPlace = userPlace;
	}
	
	public String getUpUserId() {
		return upUserId;
	}
	
	public void setUpUserId(String upUserId) {
		this.upUserId = upUserId;
	}
	
	public String getUserNameeed() {
		return userNameeed;
	}
	
	public void setUserNameeed(String userNameeed) {
		this.userNameeed = userNameeed;
	}
	
	public String getResponsiblePhone() {
		return responsiblePhone;
	}
	
	public void setResponsiblePhone(String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}
	
	public Integer getIsNewEquip() {
		return isNewEquip;
	}
	
	public void setIsNewEquip(Integer isNewEquip) {
		this.isNewEquip = isNewEquip;
	}
	
	public Integer getIsBorrow() {
		return isBorrow;
	}
	
	public void setIsBorrow(Integer isBorrow) {
		this.isBorrow = isBorrow;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public String getContractNumber() {
		return contractNumber;
	}
	
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCostSource() {
		return costSource;
	}
	
	public void setCostSource(String costSource) {
		this.costSource = costSource;
	}
	
	public String getFinanceAssetNumber() {
		return financeAssetNumber;
	}
	
	public void setFinanceAssetNumber(String financeAssetNumber) {
		this.financeAssetNumber = financeAssetNumber;
	}
	
	public Integer getIsFull() {
		return isFull;
	}
	
	public void setIsFull(Integer isFull) {
		this.isFull = isFull;
	}
	
	public String getNoFullCause() {
		return noFullCause;
	}
	
	public void setNoFullCause(String noFullCause) {
		this.noFullCause = noFullCause;
	}
	
	public Date getUpdateEq() {
		return updateEq;
	}
	
	public void setUpdateEq(Date updateEq) {
		this.updateEq = updateEq;
	}
	
}
