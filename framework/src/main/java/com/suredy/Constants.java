package com.suredy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 常量类
 * 
 * @author VIVID.G
 * @since 2015-6-24
 * @version v0.1
 */
public class Constants {
	
	//default page size
	public final static int DEFAULT_PAGE_SIZE = 20; 

	// session key for login user
	public final static String SESSION_LOGIN_USER = "login_user";
	// session key for login check img
	public final static String SESSION_LOGIN_CHECK_CODE = "login_check_code";

	// super admin
	public final static String SYS_ADMIN_USER = "admin";
	public final static String SYS_ADMIN_PSWD = "123456";

	// flow
	public static final String FLOW_SESSION = "flow_session";
	
	public static final DateFormat dateTimeformater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
