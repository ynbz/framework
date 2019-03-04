package com.suredy.formbuilder.design.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * 表单域定义<br>
 * 
 * 只定义常用表单域，HTML5的新增表单域不在考虑之列
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
public enum FormItemType {

	INPUT_TEXT(true, "text"),

	INPUT_HIDDEN(true, "hidden"),

	INPUT_CHECKBOX(true, "checkbox"),

	INPUT_CHECKBOX_GROUP(true, "checkbox-group"),

	INPUT_RADIO(true, "radio"),

	INPUT_RADIO_GROUP(true, "radio-group"),

	INPUT_TEXT_DATE(true, "date"),

	INPUT_FILE(true, "file"),

	BUTTON(false, "button"),

	TEXTAREA(true, "textarea"),

	SELECT(true, "select");

	/* 是否为可以提交数据 */
	private boolean submitable;
	private String type;

	private FormItemType(boolean submitable, String type) {
		this.submitable = submitable;
		this.type = type;
	}

	public boolean isSubmitable() {
		return submitable;
	}

	public String getType() {
		return type;
	}

	public static FormItemType typeOf(String type) {
		if (StringUtils.isBlank(type))
			return null;

		type = type.trim().toLowerCase();

		for (FormItemType t : FormItemType.values()) {
			if (type.equals(t.getType()))
				return t;
		}

		return null;
	}

}
