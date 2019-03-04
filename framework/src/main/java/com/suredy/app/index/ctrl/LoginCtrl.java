package com.suredy.app.index.ctrl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseFlowCtrl;
import com.suredy.security.entity.LoginLogEntity;
import com.suredy.security.entity.UserTokenEntity;
import com.suredy.security.model.User;
import com.suredy.security.service.CipherService;
import com.suredy.security.service.LoginLogSrv;
import com.suredy.security.service.UserSrv;
import com.suredy.security.service.UserTokenSrv;
import com.suredy.tools.checkcode.SuredyLoginCheckImage;

import eabnp.basic.result.IResult;
import eabnp.eflow.client.impl.local.IEFlowClientRmi;
import eabnp.eflow.session.ESession;

@Controller
public class LoginCtrl extends BaseFlowCtrl {

	@Value("${SYS.SYSUN}")
	private String sysUn;
	
	@Autowired
	private SuredyLoginCheckImage suredyLoginCheckImage;

	@Autowired
	private UserSrv userSrv;
	
	@Autowired
	private UserTokenSrv tokenSrv;
	
	@Autowired
	private LoginLogSrv llSrv;

	//这里需要根据实际需要调整使用什么模式
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginIndex() {
		this.getSession();
		//return "login-big"; //用户数量较多时使用
		return "login"; //用户数量较少时使用
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		this.clearSession();
		return "redirect:login";
	}
	
	@RequestMapping(value="/mobile/login", method = RequestMethod.POST)
	@ResponseBody
	public Object mobileLogin(String userCode, String password){
		Map<String, String> data = new HashMap<String, String>();
		User user = this.userSrv.getUser(userCode);
		if (user == null) {
			data.put("authenticated", "0");
			data.put("reason", "帐号错误,请核对后重新输入");
			return data;
		} else if (!password.equals(user.getPassword())){
			data.put("authenticated", "0");
			data.put("reason", "密码错误,请核对后重新输入");
			return data;
		} else {
			UserTokenEntity token = this.tokenSrv.CreateOrUpdate(user.getId());
			this.setUser(user);
			this.getUser();
			data.put("authenticated", "1");
			data.put("token", token.getToken());
			return data;
		}
	}
	
	@RequestMapping(value="/mobile/loginByup")
	@ResponseBody
	public Object mobileLoginByup(String phone,String password){
		Map<String, String> data = new HashMap<String, String>();
		User user = this.userSrv.getUserByPhone(phone);
		if (user == null) {
			data.put("authenticated", "0");
			data.put("reason", "帐号错误,请核对后重新输入");
			return data;
		} else if (!password.equals(user.getPassword())){
			data.put("authenticated", "0");
			data.put("reason", "密码错误,请核对后重新输入");
			return data;
		} else {
			UserTokenEntity token = this.tokenSrv.CreateOrUpdate(user.getId());
			this.setUser(user);
			//this.getUser();
			data.put("authenticated", "1");
			
			data.put("token", token.getToken());
			return data;
		}
	}
	
	@RequestMapping(value = "/mobile/logout/{token}")
	@ResponseBody
	public Object mobileLogout(@PathVariable String token){
		Map<String, String> data = new HashMap<String, String>();
		if (StringUtils.isEmpty(token)) {
			data.put("result", "0");
			data.put("reason", "user token 为空,不能识别用户信息");
		}
		UserTokenEntity entity = null;
		try {
			String userId = CipherService.AESDecrypt(token);
			entity = this.tokenSrv.getByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (entity != null) {
			Calendar c = Calendar.getInstance();
			entity.setActiveTime(c.getTime());
			entity.setRefreshTime(c.getTime());
			entity.setIsActive(0);
			this.tokenSrv.update(entity);
			data.put("result", "1");
		} else {
			data.put("result", "0");
			data.put("reason", "user token 错误,不能识别用户信息");			
		}
		
		return data;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(String userCode, String password, String checkCode, Boolean startupFlow, RedirectAttributes attributes,HttpServletRequest eq ) {
		if (!this.isTheSameCheckCode(checkCode)) {
			attributes.addFlashAttribute("err_msg", "校验码错误");
			attributes.addFlashAttribute("uniqueCode", userCode);

			return "redirect:login";
		}

		if (StringUtils.isBlank(userCode) || StringUtils.isBlank(password)) {
			attributes.addFlashAttribute("err_msg", "请正确填写用户名与密码");
			attributes.addFlashAttribute("uniqueCode", userCode);

			return "redirect:login";
		}

		User user = this.userSrv.getUser(userCode);

		if (user == null || !password.equals(user.getPassword())) {
			attributes.addFlashAttribute("err_msg", "用户名与密码不匹配");
			attributes.addFlashAttribute("uniqueCode", userCode);

			return "redirect:login";
		}
		
		this.setUser(user);

		if (startupFlow) {
			IEFlowClientRmi client = getEFlowClient();

			if (client == null) {
				attributes.addFlashAttribute("err_msg", "登录失败！无法获取流程信息!");

				return "redirect:login";
			}

			try {
				IResult result = client.Exec("getSysCtrlInfo", new Object[] {user.getUserCode(),sysUn});

				if (result.isSuccess()) {
					setUser(user);
					setESession((ESession) result.getResultObject());
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		LoginLogEntity lLog = new LoginLogEntity();
		lLog.setLoginName(this.getUser().getFullName());
		lLog.setLoginIp(this.getIpAddress(eq));
		lLog.setLogindate(new Date());
		llSrv.save(lLog);
		return "redirect:index";
	}

	@RequestMapping("/check-img/{width}/{height}")
	public void checkImg(HttpServletResponse response, @PathVariable int width, @PathVariable int height) {
		try {
			String code = this.suredyLoginCheckImage.write(response.getOutputStream(), width, height);

			this.setSessionAttribute(Constants.SESSION_LOGIN_CHECK_CODE, code);
		} catch (IOException e) {
		}
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	  private  String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	  }

}
