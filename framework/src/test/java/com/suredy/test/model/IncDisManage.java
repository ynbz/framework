package com.suredy.test.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.core.model.BaseFlowModel;

/**
 * @Title IncDisManage
 * @Package com.suredy.test.model
 * @Description 收文实体类
 * @author zyh
 * @date 2017-03-09
 *
 */
@Entity
@Table(name = "T_T_INCDISM")
public class IncDisManage extends BaseFlowModel {

	private String title;// 收文题名

	private String idUnit;// 收文单位

	private String rootInUnit;// 来文单位

	private String originalNumber;// 原文文号

	private String incDisCode;// 收文编号

	private Date originalDate;// 原文日期

	private Date incDisDate;// 收文日期

	private Date limitDate;// 限办日期

	private String countCode;// 总序号

	private String sDepartment;// 主办部门

	private String coOrganizer;// 协办部门

	private String readUnit;// 阅读部门

	private String readDoUnit;// 阅办部门

	private String fileSecCla;// 文件密级

	private String secrecyDateLimit;// 保密期限

	private String degreeOfE;// 紧急程度

	private String pageNumber;// 页数

	private String comment;// 备注

	private String flowState;// 流程状态

	private String disPoth;// 发文路径

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdUnit() {
		return idUnit;
	}

	public void setIdUnit(String idUnit) {
		this.idUnit = idUnit;
	}

	public String getRootInUnit() {
		return rootInUnit;
	}

	public void setRootInUnit(String rootInUnit) {
		this.rootInUnit = rootInUnit;
	}

	public String getOriginalNumber() {
		return originalNumber;
	}

	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}

	public String getIncDisCode() {
		return incDisCode;
	}

	public void setIncDisCode(String incDisCode) {
		this.incDisCode = incDisCode;
	}

	public Date getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}

	public Date getIncDisDate() {
		return incDisDate;
	}

	public void setIncDisDate(Date incDisDate) {
		this.incDisDate = incDisDate;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public String getCountCode() {
		return countCode;
	}

	public void setCountCode(String countCode) {
		this.countCode = countCode;
	}

	public String getsDepartment() {
		return sDepartment;
	}

	public void setsDepartment(String sDepartment) {
		this.sDepartment = sDepartment;
	}

	public String getCoOrganizer() {
		return coOrganizer;
	}

	public void setCoOrganizer(String coOrganizer) {
		this.coOrganizer = coOrganizer;
	}

	public String getReadUnit() {
		return readUnit;
	}

	public void setReadUnit(String readUnit) {
		this.readUnit = readUnit;
	}

	public String getReadDoUnit() {
		return readDoUnit;
	}

	public void setReadDoUnit(String readDoUnit) {
		this.readDoUnit = readDoUnit;
	}

	public String getFileSecCla() {
		return fileSecCla;
	}

	public void setFileSecCla(String fileSecCla) {
		this.fileSecCla = fileSecCla;
	}

	public String getSecrecyDateLimit() {
		return secrecyDateLimit;
	}

	public void setSecrecyDateLimit(String secrecyDateLimit) {
		this.secrecyDateLimit = secrecyDateLimit;
	}

	public String getDegreeOfE() {
		return degreeOfE;
	}

	public void setDegreeOfE(String degreeOfE) {
		this.degreeOfE = degreeOfE;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
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
