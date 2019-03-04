package com.suredy.formbuilder.eav.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * eav用到的数据类型
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
public enum DataType {

	INTEGER("int"),

	FLOAT("float"),

	DOUBLE("double"),

	STRING("string"),

	BOOLEAN("bool"),

	DATETIME("date");

	private String val;

	private DataType(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	public static DataType typeOf(String val) {
		if (StringUtils.isBlank(val))
			return null;

		for (DataType type : DataType.values()) {
			if (val.equals(type.getVal()))
				return type;
		}

		return null;
	}

}
