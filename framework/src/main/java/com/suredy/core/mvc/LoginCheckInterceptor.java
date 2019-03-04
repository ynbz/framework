package com.suredy.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.suredy.Constants;
import com.suredy.security.model.User;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// if static resource, let it go.
		if (ResourceHttpRequestHandler.class.isInstance(handler))
			return true;

		Object user = request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);

		// not login
		// 用户数量少用/login,用户数量多用/login-big
		if (!User.class.isInstance(user)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		return true;
	}

}
