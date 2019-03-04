package com.suredy.app.equipasset.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * 巡检模型
 * 
 * @author VIVID.G
 * @since 2016-8-1
 * @version v0.1
 */
@Entity
@Table(name = "t_app_checkup_log")
public class CheckupLog extends BaseModel {

	public final static int[] STATUS = {0, 1, 2};

	private static final long serialVersionUID = 1L;

	/* 巡检状态。默认1。0=损坏，1=正常，2=异常 */
	@Column(nullable = false, columnDefinition = "int default 1")
	private Integer status;

	@Column(nullable = false)
	private Date time;

	@Column(nullable = false, length = 150)
	private String user;

	@Column(length = 300)
	private String place;

	@Column(name = "asset_id", nullable = false, length = 50)
	private String assetId;

	@Column(length = 3000)
	private String remark;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// 以下是业务方法
	@Transient
	@JsonIgnore
	public String getStatusString() {
		/* 巡检状态。默认1。0=损坏，1=正常，2=异常 */
		if (0 == this.getStatus())
			return "损坏";

		if (1 == this.getStatus())
			return "正常";

		if (2 == this.getStatus())
			return "异常";

		return "未知";
	}

}
