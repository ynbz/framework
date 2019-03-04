package com.suredy.app.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.app.task.model.SubjectType;
import com.suredy.app.task.model.TaskSubject;
import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "T_APP_WORKGROUP")
public class WorkGroupEntity extends BaseModel implements TaskSubject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3608263232853910831L;

	@Column(name = "NAME")
	private String name;// 班组名

	@Column(name = "MEMBERNAME")
	private String memberName;// 成员名称

	@Column(name = "MEMBERID")
	private String memberId;// 成员id，id用逗号连接

	@Column(name = "LEADERID")
	private String LeaderId;// 组长ID

	@Column(name = "CREATETIME")
	private Date createTime;// 创建时间

	@Column(name = "LEADERNAME")
	private String leaderName;// 组长名，多个用逗号连接
	

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the leaderId
	 */
	public String getLeaderId() {
		return LeaderId;
	}

	/**
	 * @param leaderId the leaderId to set
	 */
	public void setLeaderId(String leaderId) {
		LeaderId = leaderId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getType() {
		return SubjectType.WorkGroup.getType();
	}

	@Override
	public void setType(Integer type) {
		return;
		//do nothing
	}

}
