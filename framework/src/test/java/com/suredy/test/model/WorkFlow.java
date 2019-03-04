package com.suredy.test.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.core.model.BaseFlowModel;

/**
 * @Title WorkFlow
 * @Package com.suredy.app.workflow.model
 * @Description 工作单实体
 * @author sdkj
 * @date 2017-02-06
 */
@Entity
@Table(name = "t_app_workflow")
public class WorkFlow extends BaseFlowModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8068561681220290223L;

	@Column(name = "title")
	private String title;// 标题

	@Column(name = "createName", length = 50)
	private String createName;// 派单人

	@Column(name = "executeName", length = 50)
	private String executeName;// 执行人

	@Column(name = "createNameId", length = 50)
	private String createNameId;// 派单人Id

	@Column(name = "executeNameId", length = 50)
	private String executeNameId;// 执行人Id

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;// 开始时间

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;// 结束时间

	@Column(name = "content", length = 10000)
	private String content;// 内容

	@Column(name = "statc", length = 100)
	private String flowState;// 流程状态

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getExecuteName() {
		return executeName;
	}

	public void setExecuteName(String executeName) {
		this.executeName = executeName;
	}

	public String getCreateNameId() {
		return createNameId;
	}

	public void setCreateNameId(String createNameId) {
		this.createNameId = createNameId;
	}

	public String getExecuteNameId() {
		return executeNameId;
	}

	public void setExecuteNameId(String executeNameId) {
		this.executeNameId = executeNameId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFlowState() {
		return flowState;
	}

	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}

}
