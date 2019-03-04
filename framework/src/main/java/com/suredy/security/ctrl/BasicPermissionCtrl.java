package com.suredy.security.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.core.mvc.model.MessageModel;
import com.suredy.security.entity.BasicPermissionEntity;
import com.suredy.security.entity.PermissionEntity;
import com.suredy.security.model.ResouceAction;
import com.suredy.security.service.BasicPermissionSrv;
import com.suredy.security.service.PermissionSrv;

/**
 * @author SDKJ
 *
 */
@Controller
@RequestMapping(value="/config")
public class BasicPermissionCtrl {
	
	@Autowired
	private BasicPermissionSrv basicPerSrv;
	

	@Autowired
	private PermissionSrv permissionSrv;
	
	@RequestMapping(value = "basic/permission")
	public ModelAndView Role2Permission(String roleId) {
		ModelAndView view = new ModelAndView("config/security/basicPermission");
		return view;
	}
	
	@Transactional
	@RequestMapping("basic/permission-save")
	@ResponseBody
	public Object basicPermissionSave(HttpServletRequest request) {
		
		//删除全部BasicPermission引用
		List<BasicPermissionEntity> baspers = this.basicPerSrv.getAll();
		if(baspers!=null){
			for (BasicPermissionEntity bp : baspers) {
				this.basicPerSrv.delete(bp);
			}
		}
		
		for (ResouceAction resouceAction : ResouceAction.values()) {			
			//新的BasicPermission引用
			String data =  request.getParameter("resources");
			if (!StringUtils.isBlank(data)) {
				String[] resourceIds = data.split("-");
				if (resourceIds != null && resourceIds.length > 0) {
					for (String resourceId : resourceIds) {
						PermissionEntity permission = this.permissionSrv.GetOrCreate(resourceId,  resouceAction.getAction());
						BasicPermissionEntity bp = new BasicPermissionEntity();
						bp.setPermission(permission);
						this.basicPerSrv.save(bp);
					} 
				}
			}
		}

		return MessageModel.createSuccessMessage(null, null);
	}

}
