package com.suredy.core.mvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.security.entity.UserTokenEntity;
import com.suredy.security.service.CipherService;
import com.suredy.security.service.UserTokenSrv;

/**
 * token拦截器，只要请求中含有token参数，则进行拦截
 * 
 * @author VIVID.G
 * @since 2016-8-2
 * @version v0.1
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	// token参数名
	private final String TOKEN_PARAM_NAME = "token";

	// 移动端应用前缀
	private final String PREFIX_URL_OF_MOBILE = "/mobile/";

	private final ObjectMapper om = new ObjectMapper();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// if static resource, let it go.
		if (ResourceHttpRequestHandler.class.isInstance(handler))
			return true;

		String uri = request.getRequestURI();

		uri = uri.replace(request.getContextPath(), "");

		boolean isMobile = uri.startsWith(this.PREFIX_URL_OF_MOBILE);

		// 这里的修改是为了防止解析输入流
		// 所以token必需是在URL上面作为Query String进行传递。
		// eg: http://host:port/server/method?token=xxxxx
		// String token = request.getParameter(this.TOKEN_PARAM_NAME); // 这个是之前的方法
		String token = this.getTokenInQueryString(request);

		// 非移动端，并且没有token直接返回
		if (!isMobile && token == null)
			return true;

		UserTokenEntity tokenEntity = null;
		UserTokenSrv tokenSrv = ApplicationContextHelper.getBeanByType(UserTokenSrv.class);

		if (!StringUtils.isBlank(token))
			try {
				String userId = CipherService.AESDecrypt(token);

				tokenEntity = tokenSrv.getByUserId(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}

		Map<String, Object> data = new HashMap<String, Object>();

		// token返回数据的标识符
		data.put("type", "token");

		// token无效
		if (tokenEntity == null) {
			data.put("authenticated", "0");
			data.put("reason", "登录信息无效！");

			this.sendMessage(request, response, isMobile, data);

			return false;
		}

		Calendar c = Calendar.getInstance();

		// token过期
		if (tokenEntity.getActiveTime().before(c.getTime())) {
			tokenEntity.setIsActive(0);
			tokenSrv.update(tokenEntity);
			data.put("authenticated", "0");
			data.put("reason", "长时间未登录，请重新登录！");

			this.sendMessage(request, response, isMobile, data);

			return false;
		}

		// 刷新token时间
		tokenEntity.setRefreshTime(c.getTime());
		c.add(Calendar.MINUTE, UserTokenSrv.DEFAULT_ACTIVE_TIME);
		tokenEntity.setActiveTime(c.getTime());
		tokenEntity.setIsActive(1);

		tokenSrv.update(tokenEntity);

		return true;
	}

	private void sendMessage(HttpServletRequest request, HttpServletResponse response, boolean isMobile, Object data) throws IOException {
		if (isMobile) {
			response.setContentType("application/json;charset=UTF-8");

			this.om.writeValue(response.getWriter(), data);
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	/**
	 * 从query中解析token，避免解析整个request。<br>
	 * 此方法是为了避免上传文件的时候自动解析大文件输入流
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getTokenInQueryString(HttpServletRequest request) throws UnsupportedEncodingException {
		if (request == null)
			return null;

		String qs = request.getQueryString();

		if (StringUtils.isBlank(qs))
			return null;

		qs = URLDecoder.decode(qs, "UTF-8");

		String[] tmp = qs.split("&");

		for (String t : tmp) {
			// 非token
			if (!t.toLowerCase().trim().startsWith(this.TOKEN_PARAM_NAME))
				continue;

			String[] tt = t.split("\\=");

			if (tt.length != 2)
				continue;

			return tt[1];
		}

		return null;
	}

}
