package com.suredy.security.ctrl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.security.entity.UserTokenEntity;
import com.suredy.security.service.CipherService;
import com.suredy.security.service.UserSrv;
import com.suredy.security.service.UserTokenSrv;

@Controller
@RequestMapping(value = "/mobile")
public class MobileCtrl extends BaseCtrl{
	@Autowired
	private UserSrv userSrv;

	@Autowired
	private UserTokenSrv tokenSrv;
	
	
	@RequestMapping(value = "authenticate/{token}" )
	@ResponseBody
	public Object authenticate(@PathVariable String token) {
		Map<String, String> data = new HashMap<String, String>();
		if (StringUtils.isEmpty(token)) {
			data.put("authenticated", "0");
			data.put("reason", "token can't be null or empty.");
			return data;
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
			if (entity.getActiveTime().before(c.getTime())) {
				entity.setIsActive(0);
				this.tokenSrv.update(entity);
				data.put("authenticated", "0");
				data.put("reason", "Expired token.");
			} else {
				entity.setRefreshTime(c.getTime());
				c.add(Calendar.MINUTE, UserTokenSrv.DEFAULT_ACTIVE_TIME);
				entity.setActiveTime(c.getTime());
				entity.setIsActive(1);
				this.tokenSrv.update(entity);
				data.put("authenticated", "1");
				data.put("token", entity.getToken());
			}
		} else {
			data.put("authenticated", "0");
			data.put("reason", "Invalid token.");
		}
		return data;
	}
	
	@RequestMapping(value = "authorizate/{token}")
	@ResponseBody
	public Object authorizate(@PathVariable String token){
		UserTokenEntity entity = null;
		try {
			String userId = CipherService.AESDecrypt(token);
			entity = this.tokenSrv.getByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (entity != null) {
			String userCode = entity.getUserCode();
			List<String> data = this.userSrv.getPermissions(userCode);
			return data;
		}
		return null;
	}
	

	@RequestMapping(value = "notification/{token}")
	@ResponseBody
	public Object notification(@PathVariable String token) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (StringUtils.isEmpty(token)) {
			data.put("authenticated", "0");
			data.put("reason", "token can't be null or empty.");
			return data;
		}
		UserTokenEntity tokenEntity = null;
		try {
			String userId = CipherService.AESDecrypt(token);
			tokenEntity = this.tokenSrv.getByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tokenEntity != null) {
			Calendar c = Calendar.getInstance();
			if (tokenEntity.getActiveTime().before(c.getTime())) {
				tokenEntity.setIsActive(0);
				this.tokenSrv.update(tokenEntity);
				data.put("authenticated", "0");
				data.put("reason", "Expired token.");
			} else {
				tokenEntity.setRefreshTime(c.getTime());
				c.add(Calendar.MINUTE, UserTokenSrv.DEFAULT_ACTIVE_TIME);
				tokenEntity.setActiveTime(c.getTime());
				tokenEntity.setIsActive(1);
				this.tokenSrv.update(tokenEntity);
				data.put("authenticated", "1");
				data.put("token", token);

				//TODO add business notification messages

//				if (!msgs.isEmpty()) {
//					data.put("messages", msgs);
//				}

			}
		} else {
			data.put("authenticated", "0");
			data.put("reason", "Invalid token.");
		}
		return data;
	}
	
	@RequestMapping(value = "main")
	public ModelAndView mainPage() {
		ModelAndView view = new ModelAndView("/mobile/main");		
		return view;
	}
}
