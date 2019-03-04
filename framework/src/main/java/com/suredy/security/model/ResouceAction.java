/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年4月27日
 * @version 0.1
 */
package com.suredy.security.model;

/**
 * @author ZhangMaoren
 *
 */
public enum ResouceAction {
	Denied(0), Access(1);// , Modification(2), Grant(3);

	private final Integer action;

	public static ResouceAction parse(Integer value) {
		ResouceAction ret = null;
		switch (value) {
			case 0:
				ret = Denied;
				break;
			case 1:
				ret = Access;
				break;
			// case 2:
			// ret = Modification;
			// break;
			// case 3:
			// ret = Grant;
			// break;
			default:
				break;
		}
		return ret;
	}

	ResouceAction(Integer value) {
		this.action = value;
	}

	public Integer getAction() {
		return action;
	}

	public String getDescription() {
		String ret = null;
		switch (this.action) {
			case 0:
				ret = "拒绝";
				break;
			case 1:
				ret = "访问";
				break;
			// case 2:
			// ret = "写入/修改";
			// break;
			// case 3:
			// ret = "完全控制";
			// break;

			default:
				break;
		}
		return ret;
	}
}
