/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年4月29日
 * @version 0.1
 */
package com.suredy.resource.model;

/**
 * @author ZhangMaoren
 *
 */
public enum ResourceType {
	Menu(1), Segment(2), ReportType(3), ReportSource(4), FormType(5), FormSource(6);

	private final Integer type;

	public static ResourceType parse(Integer value) {
		ResourceType ret = null;
		switch (value) {
			case 1:
				ret = Menu;
				break;
			case 2:
				ret = Segment;
				break;
			case 3:
				ret = ReportType;
				break;
			case 4:
				ret = ReportSource;
				break;
			case 5:
				ret = FormType;
				break;
			case 6:
				ret = FormSource;
				break;	
			default:
				break;
		}
		return ret;
	}
	
	public Integer getType(){
		return type;
	}

	ResourceType(Integer value) {
		this.type = value;
	}

	public String getDescription() {
		String ret = null;
		switch (this.type) {

			case 1:
				ret = "菜单节点";
				break;
			case 2:
				ret = "单点控制";
				break;
			case 3:
				ret = "报表分类";
				break;
			case 4:
				ret = "报表资源";
			case 5:
				ret = "表单分类";
				break;
			case 6:
				ret = "表单资源";				
				break;
			default:
				break;
		}
		return ret;
	}
}
