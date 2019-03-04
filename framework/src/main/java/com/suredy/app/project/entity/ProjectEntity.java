package com.suredy.app.project.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.suredy.core.model.BaseFlowModel;

/**
 * @Title ProjectEntity
 * @Package com.suredy.app.project.entity
 * @Description 生产计划管理实体类
 * @author zyh
 * @date 2017-04-07
 *
 */
@Entity
@Table(name = "T_app_project")
public class ProjectEntity extends BaseFlowModel {

	private static final long serialVersionUID = 9120022274411530897L;

	private String title;// 计划名称

	private String proUnit;// 计划部门

	private String proPerson;// 计划人

	private Date issueDate;// 发布时间

	private String comment;// 备注

	private String flowState;// 流程状态

	@OneToMany(mappedBy = "project", cascade = {CascadeType.ALL},  targetEntity = ProScheduleEntity.class)
	@OrderBy("createDate")
	@Fetch(FetchMode.SUBSELECT)
	private Set<ProScheduleEntity> schedules;// 计划表

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProUnit() {
		return proUnit;
	}

	public void setProUnit(String proUnit) {
		this.proUnit = proUnit;
	}

	public String getProPerson() {
		return proPerson;
	}

	public void setProPerson(String proPerson) {
		this.proPerson = proPerson;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<ProScheduleEntity> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<ProScheduleEntity> schedules) {
		this.schedules = schedules;
	}

	public String getFlowState() {
		return flowState;
	}

	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}

}
