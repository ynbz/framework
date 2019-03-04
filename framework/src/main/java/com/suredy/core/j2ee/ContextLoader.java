package com.suredy.core.j2ee;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.StringUtils;

/**
 * context-param loader
 * 
 * @author VIVID.G
 * @since 2015-6-24
 * @version v0.1
 */
@WebListener
public class ContextLoader implements ServletContextListener {

	private static ServletContext SERVLET_CONTEXT;

	public void contextDestroyed(ServletContextEvent event) {}

	public void contextInitialized(ServletContextEvent event) {
		SERVLET_CONTEXT = event.getServletContext();
	}

	public static String _string(String key) {
		if (StringUtils.isBlank(key))
			return null;

		return SERVLET_CONTEXT.getInitParameter(key);
	}

	public static Integer _integer(String key) {
		String string = _string(key);

		if (StringUtils.isBlank(string))
			return null;

		return Integer.parseInt(string);
	}

	public static Float _float(String key) {
		String string = _string(key);

		if (StringUtils.isBlank(string))
			return null;

		return Float.parseFloat(string);
	}

	public static Boolean _boolean(String key) {
		String string = _string(key);

		if (StringUtils.isBlank(string))
			return null;

		return Boolean.parseBoolean(string);
	}

}
