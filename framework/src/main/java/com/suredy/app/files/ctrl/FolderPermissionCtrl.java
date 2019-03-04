/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @since 2017年4月26日
 * @version 1.0
 */
package com.suredy.app.files.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.app.files.entity.FolderEntity;
import com.suredy.app.files.entity.FolderPermissionEntity;
import com.suredy.app.files.service.FolderPermissionSrv;
import com.suredy.app.files.service.FolderSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;

/**
 * @author ZhangMaoren
 *
 *         2017年4月26日
 */
@Controller
@RequestMapping({"/files"})
public class FolderPermissionCtrl extends BaseCtrl {

	@Autowired
	FolderSrv folderSrv;

	@Autowired
	FolderPermissionSrv permissionSrv;

	@RequestMapping(value = "/folder-permission")
	public ModelAndView folderPermission(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/app/files/folder-permission");

		String typeId = request.getParameter("typeId");
		FolderEntity folder = this.folderSrv.get(typeId);
		if (folder.getIsPublic() == 0) {
			FolderPermissionEntity search = new FolderPermissionEntity();
			search.setFolder(folder);
			List<FolderPermissionEntity> permissions = this.permissionSrv.readByEntity(search);;
			if (permissions != null && !permissions.isEmpty()) {
				List<String> unitPermissions = new ArrayList<String>(); // 3
				List<String> rolePermissions = new ArrayList<String>(); // 2
				for (FolderPermissionEntity p : permissions) {
					if (p.getSubjectType() == 3) {
						unitPermissions.add(p.getSubject()); // unit ids
					} else if (p.getSubjectType() == 2) {
						rolePermissions.add(p.getSubject()); // role ids
					}
				}
				if (!rolePermissions.isEmpty()) {
					view.addObject("rolePermissions", StringUtils.join(rolePermissions.toArray(), ","));
				}
				if (!unitPermissions.isEmpty()) {
					view.addObject("unitPermissions", StringUtils.join(unitPermissions.toArray(), ","));
				}
			}
		}
		view.addObject("folder", folder);
		return view;
	}

	@Transactional
	@RequestMapping({"/permission-save"})
	@ResponseBody
	public Object savePermission(HttpServletRequest request) {
		String id = request.getParameter("id");
		String unitPermissions = request.getParameter("unitPermissions"); // 3
		String rolePermissions = request.getParameter("rolePermissions"); // 2

		if (StringUtils.isEmpty(id)) {
			return MessageModel.createErrorMessage("参数不足, 分类ID信息不能为空", null);
		}
		FolderEntity folder = this.folderSrv.get(id); 
		if (folder == null) {
			return MessageModel.createErrorMessage("参数错误, 未找到与ID["+id+"]对应的分类信息", null);
		}
		FolderPermissionEntity search = new FolderPermissionEntity();
		search.setFolder(folder);
		List<FolderPermissionEntity> permissions = this.permissionSrv.readByEntity(search);
		if (permissions != null && !permissions.isEmpty()) {
			for (FolderPermissionEntity temp : permissions){
				this.permissionSrv.delete(temp);
			}
			permissions.clear();
		}
		
		String[] units = unitPermissions.split(",");
		String[] roles = rolePermissions.split(",");
		Date lastModified = new Date();
		if (units.length > 0) {
			for (String unitId : units) {
				if (unitId.trim().length() > 0) {
					FolderPermissionEntity temp = new FolderPermissionEntity();
					temp.setFolder(folder);
					temp.setInherited(0);
					temp.setLastModified(lastModified);
					temp.setSubject(unitId);
					temp.setSubjectType(3);
					this.permissionSrv.save(temp);
				}
			}
		}
		if (roles.length > 0) {
			for (String roleId : roles) {
				if (roleId.trim().length() > 0) {
					FolderPermissionEntity temp = new FolderPermissionEntity();
					temp.setFolder(folder);
					temp.setInherited(0);
					temp.setLastModified(lastModified);
					temp.setSubject(roleId);
					temp.setSubjectType(2);
					this.permissionSrv.save(temp);
				}
			}
		}
		return MessageModel.createSuccessMessage(null, null);
	}

}
