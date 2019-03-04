package com.suredy.test.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseFlowModel;

/**
 * @Title DisManage
 * @Package com.suredy.test.model
 * @Description 发文实体类
 * @author zyh
 * @date 2017-03-09
 *
 */
@Entity
@Table(name = "T_T_DISM")
public class DisManage extends BaseFlowModel {

	private String title;// 发文题名

	private String asUnit;// 主送单位

	private String ctUnit;// 抄送单位

	private String sDepartment;// 主办部门

	private String disUnit;// 发文单位

	private String issuePerson;// 签发人

	private Date issueDate;// 签发时间

	private String drafter;// 拟稿人

	private Date draftDate;// 拟稿时间

	private String phone;// 联系电话

	private String disType;// 发文类型

	private String disWrodSize;// 发文字号

	private String degreeOfE;// 紧急程度

	private String printNumber;// 印发数量

	private String subjectTerm;// 主题词

	private String comment;// 备注

	private String flowState;// 流程状态

	private String disPoth;// 发文路径

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAsUnit() {
		return asUnit;
	}

	public void setAsUnit(String asUnit) {
		this.asUnit = asUnit;
	}

	public String getCtUnit() {
		return ctUnit;
	}

	public void setCtUnit(String ctUnit) {
		this.ctUnit = ctUnit;
	}

	public String getsDepartment() {
		return sDepartment;
	}

	public void setsDepartment(String sDepartment) {
		this.sDepartment = sDepartment;
	}

	public String getDisUnit() {
		return disUnit;
	}

	public void setDisUnit(String disUnit) {
		this.disUnit = disUnit;
	}

	public String getIssuePerson() {
		return issuePerson;
	}

	public void setIssuePerson(String issuePerson) {
		this.issuePerson = issuePerson;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getDrafter() {
		return drafter;
	}

	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	public Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDisType() {
		return disType;
	}

	public void setDisType(String disType) {
		this.disType = disType;
	}

	public String getDisWrodSize() {
		return disWrodSize;
	}

	public void setDisWrodSize(String disWrodSize) {
		this.disWrodSize = disWrodSize;
	}

	public String getDegreeOfE() {
		return degreeOfE;
	}

	public void setDegreeOfE(String degreeOfE) {
		this.degreeOfE = degreeOfE;
	}

	public String getPrintNumber() {
		return printNumber;
	}

	public void setPrintNumber(String printNumber) {
		this.printNumber = printNumber;
	}

	public String getSubjectTerm() {
		return subjectTerm;
	}

	public void setSubjectTerm(String subjectTerm) {
		this.subjectTerm = subjectTerm;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFlowState() {
		return flowState;
	}

	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}

	public String getDisPoth() {
		return disPoth;
	}

	public void setDisPoth(String disPoth) {
		this.disPoth = disPoth;
	}

}
