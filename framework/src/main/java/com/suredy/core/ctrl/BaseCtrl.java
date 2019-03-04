package com.suredy.core.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.security.model.User;

public class BaseCtrl {
	


	protected final String CODE_ERR_MSG = "err_msg";


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	protected String getContextPath() {
		return this.getRequest().getContextPath();
	}

	protected HttpSession getSession() {
		return this.getRequest().getSession(true);
	}

	protected void setSessionAttribute(String name, Object value) {
		if (StringUtils.isBlank(name))
			throw new IllegalArgumentException("Invalid parameter String[key].");

		this.getSession().setAttribute(name, value);
	}

	protected Object getSessionAttribute(String name) {
		if (StringUtils.isBlank(name))
			throw new IllegalArgumentException("Invalid parameter String[key].");

		return this.getSession().getAttribute(name);
	}

	protected void removeSessionAttribute(String name) {
		if (StringUtils.isBlank(name))
			throw new IllegalArgumentException("Invalid parameter String[key].");

		this.getSession().removeAttribute(name);
	}

	protected Object getAndRemoveSessionAttribute(String name) {
		if (StringUtils.isBlank(name))
			throw new IllegalArgumentException("Invalid parameter String[key].");

		Object val = this.getSessionAttribute(name);

		this.removeSessionAttribute(name);

		return val;
	}

	protected void clearSession() {
		HttpSession session = this.getSession();

		session.invalidate();
	}

	protected boolean isTheSameCheckCode(String checkCode) {
		if (StringUtils.isBlank(checkCode))
			return false;

		String code = (String) this.getSessionAttribute(Constants.SESSION_LOGIN_CHECK_CODE);

		return checkCode.equalsIgnoreCase(code);
	}

	protected User getUser() {
		Object obj = this.getSessionAttribute(Constants.SESSION_LOGIN_USER);

		return (User) obj;
	}

	protected void setUser(User user) {
		if (user == null)
			return;

		this.setSessionAttribute(Constants.SESSION_LOGIN_USER, user);
	}
	
	

	/**
	 * Redirect to error page
	 * 
	 * @param errMsg
	 * @return
	 */
	protected ModelAndView gotoErrorPage(String errMsg) {
		ModelAndView view = new ModelAndView("error");

		view.addObject("msg", errMsg);

		return view;
	}

}
