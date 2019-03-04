package com.suredy.formbuilder.eav.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.eav.model.EAVEntry;
import com.suredy.formbuilder.design.model.FormDefine;

/**
 * 表单实体
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
@Entity
@Table(name = "tb_form_entry")
public class FormEntry extends EAVEntry<FormAttributeValue> {

	private static final long serialVersionUID = 1L;

	/* 对应的表单定义对象 */
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "form_define_id")
	private FormDefine formDefine;

	public FormDefine getFormDefine() {
		return formDefine;
	}

	public void setFormDefine(FormDefine formDefine) {
		this.formDefine = formDefine;
	}

}
