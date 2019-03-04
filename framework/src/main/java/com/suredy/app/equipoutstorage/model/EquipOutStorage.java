package com.suredy.app.equipoutstorage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.suredy.core.model.BaseModel;

/**
 * 
 * @author lhl
 * 设备物品资产表	
 *
 */
@Entity
@Table(name="t_app_outstorage")
public class EquipOutStorage extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 142112305860560933L;
	@Column(length = 32)
	private String assetId; //资产id
	private Date receiveDate; //领用日期
	private Date beakTime;//实际归还日期
	@Column(length = 50)
	private String recipients;//责任人人
	private String recipientsId;//责任人id
	private String userId;//使用人id
	private String userName;//使用人
	private String userDepartId;//使用部门id
	private String userDepartName;//使用部门
	private String deadline;//使用期限
	private Date promiseTime;//应归还日期
	private String comm;//备注
	private String equipStatus;//设备状态（待出库 3 已出库 4）
	private Integer  backStatus;//设备归还状态 0 为归还，1归还
	private String isBorrow;//设备是领用还是借用

	private String isApplyfor;//修改标识，设备是修改状态还是申请状态（1：申请，2：人工修改）
	private String updateperpeoid;//修改人id
	private String userPalce;//使用地点
	private Date updatetime;//数据修改时间
	
	
	@Transient
	private String equipModel;//设备型号
	@Transient
	private String typeName;//设备类型
	
	private Date dataCreateTime = new Date();//数据创建日期
	
	public String getAssetId() {
		return assetId;
	}
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public Date getReceiveDate() {
		return receiveDate;
	}
	
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	public Date getBeakTime() {
		return beakTime;
	}
	
	public void setBeakTime(Date beakTime) {
		this.beakTime = beakTime;
	}
	
	public String getRecipients() {
		return recipients;
	}
	
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	
	public String getRecipientsId() {
		return recipientsId;
	}
	
	public void setRecipientsId(String recipientsId) {
		this.recipientsId = recipientsId;
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
	
	public String getUserDepartId() {
		return userDepartId;
	}
	
	public void setUserDepartId(String userDepartId) {
		this.userDepartId = userDepartId;
	}
	
	public String getUserDepartName() {
		return userDepartName;
	}
	
	public void setUserDepartName(String userDepartName) {
		this.userDepartName = userDepartName;
	}
	
	public String getDeadline() {
		return deadline;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public Date getPromiseTime() {
		return promiseTime;
	}
	
	public void setPromiseTime(Date promiseTime) {
		this.promiseTime = promiseTime;
	}
	
	public String getComm() {
		return comm;
	}
	
	public void setComm(String comm) {
		this.comm = comm;
	}

	
	public String getEquipStatus() {
		return equipStatus;
	}

	
	public void setEquipStatus(String equipStatus) {
		this.equipStatus = equipStatus;
	}

	
	public String getEquipModel() {
		return equipModel;
	}

	
	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}

	
	public String getTypeName() {
		return typeName;
	}

	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public Integer getBackStatus() {
		return backStatus;
	}

	
	public void setBackStatus(Integer backStatus) {
		this.backStatus = backStatus;
	}

	
	public String getIsBorrow() {
		return isBorrow;
	}

	public void setIsBorrow(String isBorrow) {
		this.isBorrow = isBorrow;
	}

	public String getIsApplyfor() {
		return isApplyfor;
	}

	public void setIsApplyfor(String isApplyfor) {
		this.isApplyfor = isApplyfor;
	}

	public String getUpdateperpeoid() {
		return updateperpeoid;
	}

	public void setUpdateperpeoid(String updateperpeoid) {
		this.updateperpeoid = updateperpeoid;
	}

	public String getUserPalce() {
		return userPalce;
	}

	public void setUserPalce(String userPalce) {
		this.userPalce = userPalce;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getDataCreateTime() {
		return dataCreateTime;
	}
	
	public void setDataCreateTime(Date dataCreateTime) {
		this.dataCreateTime = dataCreateTime;
	}

}
