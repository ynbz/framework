package com.suredy.docOCX.eav;

import org.apache.commons.lang3.StringUtils;

public enum ButtonEnum {
	
	Save("保存"),

	StartHandDraw("领导圈阅"),
	
	ShowHandDrawDispBar("分层显示手写批注"),

	IsFullScreen("全屏/还原"),
	
	ShowDialog1("另存到本地"),

	ShowDialog2("页面设置"),
	
	ShowDialog3("打印"),

	InsertSeal("加盖印章"),
	
	AddHandSign("签字"),

	VerifySeal("验证印章"),
	
	ChangePsw("修改密码");
	
	private String val;

	private ButtonEnum(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
	
	public static String typeOf(String name) {
		if (StringUtils.isBlank(name))
			return null;

		for (ButtonEnum type : ButtonEnum.values()) {
			if (name.equals(type.name()))
				return type.val;
		}

		return null;
	}


}
