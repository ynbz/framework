package com.suredy.app.project.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.suredy.core.model.BaseModel;

/**
 * @Title ProScheduleEntity
 * @Package com.suredy.app.project.entity
 * @Description 生产计划表管理实体类
 * @author zyh
 * @date 2017-04-07
 *
 */
@Entity
@Table(name = "t_app_pro_schedule")
public class ProScheduleEntity extends BaseModel {

	private static final long serialVersionUID = 6132889049469101674L;

	private Date periodStart;//周期开始时间

	private Date periodEnd;//周期结束时间

	private Integer number;//产量

	private String unit;//单位

	private Date createDate;//创建时间

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "pro_id")
	private ProjectEntity project;

	public Date getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}

	public Date getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

}
