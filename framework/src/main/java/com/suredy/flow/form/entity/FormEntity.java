package com.suredy.flow.form.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;
import com.suredy.flow.form.model.Form;
import com.suredy.resource.entity.ResourceEntity;

@Entity
@Table(name = "T_APP_FORM")
public class FormEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3537630713505827607L;

	@Column(name = "NAME", nullable = false)
	private String name;// 表单名称

	@Column(name = "FILETYPE", nullable = false)
	private String fileType;// 对应流程中的fileTypeCode

	@Column(name = "ISTEMPLATE")
	private String isTemplate;// 是否需要模板 1：不需要 2：需要模板

	@Column(name = "TEMTYPEID")
	private String temTypeId;// 模板类型id

	@Column(name = "MENUID")
	private String menuId;// 菜单id

	@Column(name = "DOSTHPATH")
	private String doSthPath;// 待办路径

	@Column(name = "DRAFTPATH")
	private String draftPath;// 起草路径

	@Column(name = "LISTPATH")
	private String listPath;// 列表路径

	@Column(name = "FORMSEL")
	private Integer formSel;// 表单选择 1:固定表单 2：自定义表单

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "TYPE_ID")
	@JsonIgnore
	private FormTypeEntity type;

	@Column(name = "FLOW_ID")
	private String flowId;

	@Column(name = "DEFINE_ID")
	private String defineId;

	/* 创建日期 */
	@Column(name = "CREATETIME", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createTime;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESOURCEID")
	private ResourceEntity resource;

	@Transient
	private List<FormTypeEntity> types;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the isTemplate
	 */
	public String getIsTemplate() {
		return isTemplate;
	}

	/**
	 * @param isTemplate the isTemplate to set
	 */
	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}

	/**
	 * @return the temTypeId
	 */
	public String getTemTypeId() {
		return temTypeId;
	}

	/**
	 * @param temTypeId the temTypeId to set
	 */
	public void setTemTypeId(String temTypeId) {
		this.temTypeId = temTypeId;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the doSthPath
	 */
	public String getDoSthPath() {
		return doSthPath;
	}

	/**
	 * @param doSthPath the doSthPath to set
	 */
	public void setDoSthPath(String doSthPath) {
		this.doSthPath = doSthPath;
	}

	/**
	 * @return the draftPath
	 */
	public String getDraftPath() {
		return draftPath;
	}

	/**
	 * @param listPath the listPath to set
	 */
	public String getListPath() {
		return listPath;
	}

	/**
	 * @return the listPath
	 */
	public void setListPath(String listPath) {
		this.listPath = listPath;
	}

	/**
	 * @param draftPath the draftPath to set
	 */
	public void setDraftPath(String draftPath) {
		this.draftPath = draftPath;
	}

	/**
	 * @return the formSel
	 */
	public Integer getFormSel() {
		return formSel;
	}

	/**
	 * @param formSel the formSel to set
	 */
	public void setFormSel(Integer formSel) {
		this.formSel = formSel;
	}

	/**
	 * @return the type
	 */
	public FormTypeEntity getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(FormTypeEntity type) {
		this.type = type;
	}

	/**
	 * @return the flowId
	 */
	public String getFlowId() {
		return flowId;
	}

	/**
	 * @param flowId the flowId to set
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	/**
	 * @return the defineId
	 */
	public String getDefineId() {
		return defineId;
	}

	/**
	 * @param defineId the defineId to set
	 */
	public void setDefineId(String defineId) {
		this.defineId = defineId;
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
	 * @return the resource
	 */
	public ResourceEntity getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	/**
	 * @return the types
	 */
	public List<FormTypeEntity> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<FormTypeEntity> types) {
		this.types = types;
	}

	@Transient
	public Form toVO() {
		Form vo = new Form();
		vo.setCreateTime(createTime);
		vo.setDefineId(defineId);
		vo.setDoSthPath(doSthPath);
		vo.setDraftPath(draftPath);
		vo.setFileType(fileType);
		vo.setFlowId(flowId);
		vo.setFormSel(formSel);
		vo.setId(this.id);
		vo.setMenuId(menuId);
		vo.setName(name);
		vo.setResourceId(resource == null ? null : resource.getId());
		vo.setResourceUri(resource == null ? null : resource.getUri());
		vo.setTypeId(type == null ? null : type.getId());
		vo.setTypeName(type == null ? null : type.getName());
		return vo;
	}

}
