/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月17日
 * @version 0.1
 */
package com.suredy.security.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.PermissionEntity;
import com.suredy.security.entity.Role2PermissionEntity;
import com.suredy.security.entity.RoleEntity;
import com.suredy.security.entity.UnitEntity;
import com.suredy.security.entity.User2PermissionEntity;
import com.suredy.security.entity.User2RoleEntity;
import com.suredy.security.entity.UserEntity;
import com.suredy.security.model.ResouceAction;
import com.suredy.security.model.Org;
import com.suredy.security.model.Role;
import com.suredy.security.model.User;
import com.suredy.security.model.User2Role;
import com.suredy.security.service.OrgSrv;
import com.suredy.security.service.PermissionSrv;
import com.suredy.security.service.Role2PermissionSrv;
import com.suredy.security.service.RoleSrv;
import com.suredy.security.service.UnitSrv;
import com.suredy.security.service.User2PermissionSrv;
import com.suredy.security.service.User2RoleSrv;
import com.suredy.security.service.UserSrv;


/**
 * @author ZhangMaoren
 *
 */
@Controller
@RequestMapping(value="/config")
public class URCtrl extends BaseCtrl{
	private final static Logger log = LoggerFactory.getLogger(URCtrl.class);

	@Value("${SYS.NAME}")
	private String sysName;
	
	@Autowired 
	private OrgSrv orgSrv;
		
	@Autowired
	private Role2PermissionSrv role2permissionSrv;
	
	@Autowired
	private User2RoleSrv user2roleSrv;
	
	@Autowired
	private User2PermissionSrv user2permissionSrv;

	@Autowired
	private UserSrv userSrv;
	
	@Autowired
	private RoleSrv roleSrv;
		
	@Autowired
	private UnitSrv unitSrv;
	
	@Autowired
	private PermissionSrv permissionSrv;
	
	
	/**
	 * get user list
	 * 人员少
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "userfew/list")
	public ModelAndView getUsers() {
		ModelAndView view = new ModelAndView("/config/security/userfew-list");
		
		return view;
	}
	
	/**
	 * get user list
	 * 人员多
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "usermuch/list")
	public ModelAndView getUsers(String page, String size, String deptId, String keyword) {
		ModelAndView view = new ModelAndView("/config/security/usermuch-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isEmpty(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isEmpty(size)) {
			pageSize = Integer.parseInt(size);
		}
		Integer count = this.userSrv.CountByFilter(deptId, keyword);
		List<User> data = this.userSrv.getByFilter(pageIndex, pageSize,deptId, keyword);
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	
	@RequestMapping(value = "user/form")
	public ModelAndView userForm(String userId) {		
		ModelAndView view = new ModelAndView("/config/security/user-form");
		User user = null;
		if (StringUtils.isEmpty(userId)) {
			user = new User();
		} else {
			user = this.userSrv.getById(userId);
		}
		view.addObject("user", user);
		return view;
		
	}
	
	@Transactional
	@RequestMapping("user/pinyin")
	@ResponseBody
	public Object userPinyin(HttpServletRequest request) {
		UserEntity search = new UserEntity();
		List<UserEntity> users = this.userSrv.readByEntity(search);
		if (!users.isEmpty()) {
			for (UserEntity user : users) {
				String name = user.getName();

				String py = null;
				try {
					py = PinyinHelper.getShortPinyin(name);
				} catch (PinyinException e) {
					e.printStackTrace();
				}
				user.setShortPinyin(py==null ? null : py);
				this.userSrv.update(user);
			}
		}
		

		return MessageModel.createSuccessMessage(null, null);
		
	}
	
	@RequestMapping("user/save")
	@ResponseBody
	public Object userSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		String unitId = request.getParameter("unitId");
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String title = request.getParameter("title");
		String email = request.getParameter("email");
		String sort = request.getParameter("sort");
		String shortPinyin = request.getParameter("shortPinyin");
		String description = request.getParameter("description");	
		Integer isEmployee=Integer.parseInt(request.getParameter("isEmployee"));	
		Integer isLongUser=Integer.parseInt(request.getParameter("isLongUser"));
		String phone = request.getParameter("userphone");
		if ( StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足: 用户ID,用户姓名必须提供", null);
		}

		
		UnitEntity unit = this.unitSrv.get(unitId);
		OrgEntity org = unit.getOrg();
		String code = generateCode(unitId, name);
		if (StringUtils.isEmpty(id)) {
			UserEntity user = new UserEntity();
			user.setActionType(0);
			user.setActUserId(null);
			user.setAlias(StringUtils.isEmpty(alias) ? null : alias);
			user.setAvailable(1);
			user.setCode(code);
			user.setDescription(StringUtils.isEmpty(description) ? null : description);
			user.setDominoName(null);
			user.setEmail(StringUtils.isEmpty(email) ? null : email);
			user.setIsMailUser(0);
			user.setName(name);
			String py = null;
			try {
				py = PinyinHelper.getShortPinyin(name);
			} catch (PinyinException e) {
				e.printStackTrace();
			}
			if(!StringUtils.isBlank(shortPinyin)){
				user.setShortPinyin(shortPinyin);
			}else{
				user.setShortPinyin(py==null ? null : py);
			}
			user.setPassword("1");
			user.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			user.setTitle(StringUtils.isEmpty(title) ? null : title);
			user.setUniqueCode("U=" + code + "/" + org.getUniqueCode());
			user.setFullName(name + "/" + unit.getFullName());
			user.setUnit(unit);
			user.setUnitUC(unit.getUniqueCode());
			user.setOrg(org);
			user.setIsEmployee(isEmployee);
			user.setIsLongUser(isLongUser);
			user.setUserphone(phone);
			this.userSrv.save(user);
		} else {
			UserEntity user = this.userSrv.get(id);
			if (user == null) {
				return MessageModel.createErrorMessage("未找到与ID['"+ id +"']对应的用户信息", null);
			}
			user.setAlias(StringUtils.isEmpty(alias) ? null : alias);
			user.setDescription(StringUtils.isEmpty(description) ? null : description);
			user.setEmail(StringUtils.isEmpty(email) ? null : email);
			user.setName(name);
			user.setShortPinyin(shortPinyin);
			String py = null;
			try {
				py = PinyinHelper.getShortPinyin(name);
			} catch (PinyinException e) {
				e.printStackTrace();
			}
			if(!StringUtils.isBlank(shortPinyin)){
				user.setShortPinyin(shortPinyin);
			}else{
				user.setShortPinyin(py==null ? null : py);
			}
			user.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			user.setTitle(StringUtils.isEmpty(title) ? null : title);
			//user.setCode(code); can't change user's Code and WYMC
			unit = this.unitSrv.get(unitId);
			org = unit.getOrg();
			user.setFullName(name + "/" + unit.getFullName());
			user.setUnit(unit);
			user.setUnitUC(unit.getUniqueCode());
			user.setIsEmployee(isEmployee);
			user.setIsLongUser(isLongUser);
			user.setOrg(org);
			user.setUserphone(phone);
			
			this.userSrv.update(user);			
		}

		return MessageModel.createSuccessMessage(null, null);
		
	}
	
	private String generateCode(String unit, String name) {
		long sn = System.currentTimeMillis() + name.hashCode() + unit.hashCode();
		String temp = "" + sn;
		return "A" + temp.substring(5);
	}
	
	@RequestMapping("user/delete")
	@ResponseBody
	public Object doEidtDelete(String userId) {	
		if (StringUtils.isEmpty(userId)) {
			return MessageModel.createErrorMessage("用户ID必须提供", null);
		}
		UserEntity user = this.userSrv.get(userId);
		if (user == null) {
			return MessageModel.createErrorMessage("未找到与ID['"+ userId +"']对应的用户信息", null);
		}
		
		this.userSrv.delete(user);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping(value = "user/permission")
	public ModelAndView User2Permission(String userId) {
		ModelAndView view = new ModelAndView("/config/security/user2permission");
		User user = this.userSrv.getById(userId);
		view.addObject("user", user);
		return view;

	}
	
	@Transactional
	@RequestMapping("user/permission-save")
	@ResponseBody
	public Object User2ResourceSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			log.error("Parameter String[id] is blank.");
			return MessageModel.createErrorMessage("用户ID必须提供", null);
		}
		
		//删除全部role2permission引用
		List<User2PermissionEntity> userPermissions = this.user2permissionSrv.getByUser(id);
		for (User2PermissionEntity rp : userPermissions) {
			this.user2permissionSrv.delete(rp);
		}
		
		for (ResouceAction resouceAction : ResouceAction.values()) {			
			//新的user2permission引用
			String data =  request.getParameter("resources");
			if (!StringUtils.isEmpty(data)) {
				String[] resourceIds = data.split("-");
				if (resourceIds != null && resourceIds.length > 0) {
					for (String resourceId : resourceIds) {
						PermissionEntity permission = this.permissionSrv.GetOrCreate(resourceId,  resouceAction.getAction());
						User2PermissionEntity up = new User2PermissionEntity();
						up.setPermission(permission);
						UserEntity user = new UserEntity();
						user.setId(id);
						up.setUser(user);
						this.user2permissionSrv.save(up);
					} 
				}
			}
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping(value = "user/role")
	public ModelAndView User2Role(String userId) {
		ModelAndView view = new ModelAndView("config/security/user2role");
		User user = this.userSrv.getById(userId);
		List<Role> roles = this.roleSrv.getAll();
		
		List<User2RoleEntity> pos = this.user2roleSrv.getByUser(userId);
		List<User2Role> relations = new ArrayList<User2Role>();
		for (User2RoleEntity po : pos) {
			relations.add(po.toVO());
		}
		view.addObject("user", user);
		view.addObject("relations", relations);
		view.addObject("roles", roles);
		return view;
	}

	@Transactional
	@RequestMapping("user/role-save")
	@ResponseBody
	public Object User2RoleSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			log.error("Parameter String[id] is blank.");
			return MessageModel.createErrorMessage("用户ID必须提供", null);
		}
		// 删除全部user2role引用
		List<User2RoleEntity> relations = this.user2roleSrv.getByUser(id);
		for (User2RoleEntity ur : relations) {
			this.user2roleSrv.delete(ur);
		}
		// 加入新的user2role引用
		String[] roleIds = request.getParameterValues("roleId");
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				User2RoleEntity ur = new User2RoleEntity();
				UserEntity user = new UserEntity();
				user.setId(id);
				ur.setUser(user);
				RoleEntity role = new RoleEntity();
				role.setId(roleId);
				ur.setRole(role);
				this.user2roleSrv.save(ur);
			}
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * get role list
	 * 
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "role/list")
	public ModelAndView getRoles(String page, String size) {
		ModelAndView view = new ModelAndView("/config/security/role-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		Long count = this.roleSrv.Count();
		List<Role> data = this.roleSrv.getAll(pageIndex, pageSize);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping(value = "role/form")
	public ModelAndView roleForm(String roleId) {
		ModelAndView view = new ModelAndView("/config/security/role-form");
		List<Org> orgs = this.orgSrv.getAll();
		Role role = null;
		if (StringUtils.isEmpty(roleId)) {
			role = new Role();
		} else {
			role = this.roleSrv.getById(roleId);
		}
		view.addObject("orgs", orgs);
		view.addObject("role", role);
		return view;
	}
	
	@RequestMapping("role/save")
	@ResponseBody
	public Object roleSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		String orgId = request.getParameter("org");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String sort = request.getParameter("sort");
		String description = request.getParameter("description");
		
		if (StringUtils.isBlank(orgId) || StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
			return MessageModel.createErrorMessage("参数不足, 所属单位,角色代码,角色名称必须提供", null);
		}
		String app = "";
		if (StringUtils.isNotEmpty(sysName)) {
			app = "/APP=" + sysName;
		}
		OrgEntity org = this.orgSrv.get(orgId);
		if (StringUtils.isEmpty(id)) {
			RoleEntity role = new RoleEntity();
			role.setAlias(StringUtils.isBlank(alias) ? null : alias);
			role.setAvailable(1);
			role.setBuildIn(0);
			role.setCode(code);
			role.setDescription(StringUtils.isBlank(description) ? null : description);
			role.setIsMailGroup(0);
			role.setName(name);
			role.setNotUserGroup(0);
			role.setOrg(org);
			role.setSort(StringUtils.isBlank(sort) ? null : Integer.parseInt(sort));
			role.setUniqueCode("G=" + code + app + "/" + org.getUniqueCode());
			this.roleSrv.save(role);
		} else {
			RoleEntity role = this.roleSrv.get(id);
			role.setAlias(StringUtils.isBlank(alias) ? null : alias);
			role.setCode(code);
			role.setDescription(StringUtils.isBlank(description) ? null : description);
			role.setName(name);
			role.setOrg(org);
			role.setSort(StringUtils.isBlank(sort) ? null : Integer.parseInt(sort));
			role.setUniqueCode("G=" + code + app + "/" + org.getUniqueCode());
			this.roleSrv.update(role);
			
		}
		
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	
	@RequestMapping("role/delete")
	@ResponseBody
	public Object doEditDelete(String roleId) {	
		if (StringUtils.isBlank(roleId)) {
			log.error("Parameter String[roleId] is blank.");
			return MessageModel.createErrorMessage("角色ID必须提供", null);
		}
		RoleEntity role = this.roleSrv.get(roleId);
		if (role == null) {
			return MessageModel.createErrorMessage("未找到与ID['"+ roleId +"']对应的角色信息", null);
		}
		
		this.roleSrv.delete(role);
		return MessageModel.createSuccessMessage(null, null);
	}

	@RequestMapping(value = "role/user")
	public ModelAndView Role2User(String roleId) {
		ModelAndView view = new ModelAndView("/config/security/role2user");
		Role role = this.roleSrv.getById(roleId);
		//List<User> users = this.userSrv.getAll();
		List<User2RoleEntity> pos = this.user2roleSrv.getByRole(roleId);
		List<User2Role> relations = new ArrayList<User2Role>();
		for (User2RoleEntity po : pos) {
			relations.add(po.toVO());
		}
		view.addObject("role", role);
		view.addObject("relations", relations);
		//view.addObject("users", users);
		return view;
	}

	@Transactional
	@RequestMapping("role/user-save")
	@ResponseBody
	public Object Role2UserSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			log.error("Parameter String[id] is blank.");
			return MessageModel.createErrorMessage("角色ID必须提供", null);
		}
		// 删除全部user2role引用
		List<User2RoleEntity> relations = this.user2roleSrv.getByRole(id);
		for (User2RoleEntity ur : relations) {
			this.user2roleSrv.delete(ur);
		}
		// 加入新的user2role引用
		String[] userIds = request.getParameterValues("userId");
		if (userIds != null && userIds.length > 0) {
			for (String userId : userIds) {
				User2RoleEntity ur = new User2RoleEntity();
				RoleEntity role = new RoleEntity();
				role.setId(id);
				UserEntity user = new UserEntity();
				user.setId(userId);
				ur.setRole(role);
				ur.setUser(user);
				this.user2roleSrv.save(ur);
			}
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping(value = "role/permission")
	public ModelAndView Role2Permission(String roleId) {
		ModelAndView view = new ModelAndView("config/security/role2permission");
		Role role = this.roleSrv.getById(roleId);
		view.addObject("role", role);
		return view;
	}
	
	@Transactional
	@RequestMapping("role/permission-save")
	@ResponseBody
	public Object Role2PermissionSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			log.error("Parameter String[id] is blank.");
			return MessageModel.createErrorMessage("角色ID必须提供", null);
		}
		
		//删除全部role2permission引用
		List<Role2PermissionEntity> rolePermissions = this.role2permissionSrv.getByRole(id);
		for (Role2PermissionEntity rp : rolePermissions) {
			this.role2permissionSrv.delete(rp);
		}
		
		for (ResouceAction resouceAction : ResouceAction.values()) {			
			//新的role2permission引用
			String data =  request.getParameter("resources");
			if (!StringUtils.isBlank(data)) {
				String[] resourceIds = data.split("-");
				if (resourceIds != null && resourceIds.length > 0) {
					for (String resourceId : resourceIds) {
						PermissionEntity permission = this.permissionSrv.GetOrCreate(resourceId,  resouceAction.getAction());
						Role2PermissionEntity rp = new Role2PermissionEntity();
						rp.setPermission(permission);
						RoleEntity role = new RoleEntity();
						role.setId(id);
						rp.setRole(role);
						this.role2permissionSrv.save(rp);
					} 
				}
			}
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping("role/tree")
	@ResponseBody
	public Object RoleTree() {
		String orgId = null;
		List<Tree> tree = this.roleSrv.getTree(orgId);
		return tree;
	}
}
