package com.suredy.core.mvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 消息对象
 * 
 * @author VIVID.G
 * @since 2015-5-6
 * @version v0.1
 */
@JsonInclude(Include.NON_EMPTY)
public class MessageModel implements Serializable {

	private static final long serialVersionUID = -4513579455359138090L;

	private boolean success = true;
	private String msg;
	private Object data;

	public MessageModel() {}

	public MessageModel(boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static MessageModel createErrorMessage(String msg, Object data) {
		return new MessageModel(false, msg, data);
	}

	public static MessageModel createSuccessMessage(String msg, Object data) {
		return new MessageModel(true, msg, data);
	}

}
