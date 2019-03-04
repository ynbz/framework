package com.suredy.security.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "TB_TEMPLATE_MANAGE")
public class TemplateManage extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3852549619347408150L;
	private String templateTitel;// 文件名称
	private String uploadName;// 上传人
	private Date uploadTime;// 上传时间
	private String templateUrlId;// 关联文件路径id

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "type_id")
	private TemplateType type;// 文件类型id

	@Transient
	private List<TemplateType> types;

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getTemplateTitel() {
		return templateTitel;
	}

	public void setTemplateTitel(String templateTitel) {
		this.templateTitel = templateTitel;
	}

	public String getTemplateUrlId() {
		return templateUrlId;
	}

	public void setTemplateUrlId(String templateUrlId) {
		this.templateUrlId = templateUrlId;
	}

	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public List<TemplateType> getTypes() {
		return types;
	}

	public void setTypes(List<TemplateType> types) {
		this.types = types;
	}

}
