package com.suredy.formbuilder.eav.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.eav.model.EAVAttribute;

/**
 * 表单属性实体
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
@Entity
@Table(name = "tb_form_attribute")
public class FormAttribute extends EAVAttribute<FormAttributeMetadata, FormAttributeValue> {

	private static final long serialVersionUID = 1L;

}
