package com.suredy.formbuilder.eav.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.suredy.eav.model.EAVAttributeMetadata;

/**
 * 表单属性元数据
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
@Entity
@Table(name = "tb_form_attribute_metadata")
public class FormAttributeMetadata extends EAVAttributeMetadata<FormAttribute> {

	private static final long serialVersionUID = 1L;

}
