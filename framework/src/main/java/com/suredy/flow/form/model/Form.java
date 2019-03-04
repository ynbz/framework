/**
 * 
 */
package com.suredy.flow.form.model;

import java.io.Serializable;
import java.util.Date;

import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.entity.FormTypeEntity;
import com.suredy.resource.entity.ResourceEntity;

/**
 * @author ZhangMaoren
 *
 */
public class Form implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7067098883053644401L;

	private String id; // ID

	private String name;// 表单名称

	private String fileType;// 对应流程中的fileTypeCode

	private String menuId;// 菜单id

	private String doSthPath;// 待办路径

	private String draftPath;// 起草路径

	private Integer formSel;// 表单选择 1:固定表单 2：自定义表单

	private String typeId; // 表单类型ID

	private String typeName; // 表单类型名称

	private String flowId; // 流程ID

	private String defineId; // 自定义表单ID

	private Date createTime; // 创建日期

	private String resourceId;

	private String resourceUri;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceUri
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * @param resourceUri the resourceUri to set
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}
	
	public FormEntity toPO() {
		FormEntity po = new FormEntity();
		if (id != null) {
			po.setId(id);
		}
		po.setCreateTime(createTime);
		po.setDefineId(defineId);
		po.setDoSthPath(doSthPath);
		po.setDraftPath(draftPath);
		po.setFileType(fileType);
		po.setFlowId(flowId);
		po.setFormSel(formSel);
		po.setMenuId(menuId);
		po.setName(name);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		po.setResource(resource);

		if (typeId != null) {
			FormTypeEntity pm = new FormTypeEntity();
			pm.setId(typeId);
			po.setType(pm);
		}
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Form other = (Form) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (name != null ? !name.equals(other.name) : other.name != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (name != null ? name.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Form[" + "id=" + id + ", name='" + name + "\']";
	}

}
