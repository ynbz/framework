/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年6月15日
 * @version 0.1
 */
package com.suredy.resource.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.mvc.model.TreeMenu;
import com.suredy.flow.form.model.Form;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.resource.entity.MenuEntity;
import com.suredy.resource.entity.ResourceEntity;
import com.suredy.resource.entity.SegmentEntity;
import com.suredy.resource.model.ResourceType;
import com.suredy.resource.service.MenuSrv;
import com.suredy.resource.service.ReportSrv;
import com.suredy.resource.service.ResourceSrv;
import com.suredy.resource.service.SegmentSrv;
import com.suredy.security.entity.BasicPermissionEntity;
import com.suredy.security.entity.Role2PermissionEntity;
import com.suredy.security.entity.User2PermissionEntity;
import com.suredy.security.model.User;
import com.suredy.security.service.BasicPermissionSrv;
import com.suredy.security.service.Role2PermissionSrv;
import com.suredy.security.service.User2PermissionSrv;
import com.suredy.security.service.UserSrv;

/**
 * @author ZhangMaoren
 *
 */
@Controller
@RequestMapping(value = "/config")
public class ResourceCtrl extends BaseCtrl {

	private final static Logger log = LoggerFactory.getLogger(ResourceCtrl.class);

	@Autowired
	private MenuSrv menuSrv;

	@Autowired
	private SegmentSrv segmentSrv;

	@Autowired
	private ReportSrv reportSrv;
	
	@Autowired
	private FormSrv formSrv;
	

	@Autowired
	ResourceSrv resourceSrv;

	@Autowired
	private Role2PermissionSrv role2permissionSrv;

	@Autowired
	private User2PermissionSrv user2permissionSrv;

	@Autowired
	private UserSrv userSrv;

	@Autowired
	private BasicPermissionSrv basicPerSrv;

	@RequestMapping({"menu/user"})
	@ResponseBody
	public Object UserMenuTree(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		// not login
		if (user == null) {
			log.info("Session timeout or User didn't login.");
			return MessageModel.createErrorMessage("用户未登录或会话超时", null);
		}
		TreeMenu total = new TreeMenu();
		// 当前用户可访问的安全资源点
		List<String> userSecurityResources = this.userSrv.getPermissions(user.getUniqueCode());
		// Set<String> permissions = this.userSrv.getPermissions(user.getUniqueCode());
		
		
		
		//TODO 添加表单信息关联菜单
		// 全部报表表单相关的安全资源点
		List<Form> allformResources = this.formSrv.getAll();
		// 已授权用户访问的报表相关安全资源点
		Set<Form> dynamicForms = new HashSet<Form>();
		
		if (allformResources != null) {
			for (Form form : allformResources) {
				if (userSecurityResources.contains(form.getResourceUri())) {
					dynamicForms.add(form);
				}
			}
		}
		
		// 全部菜单相关的安全资源点
		List<ResourceEntity> menuResources = this.resourceSrv.getAll(ResourceType.Menu);
		// 已授权用户访问的菜单相关安全资源点
		Set<String> allowedMenues = new HashSet<String>();
		if (menuResources != null) {
			for (ResourceEntity menu : menuResources) {
				if (userSecurityResources.contains(menu.getUri())) {
					allowedMenues.add(menu.getId());
				}
			}
		}
		
		List<TreeMenu> menuTree = this.menuSrv.getMenuTree(null, allowedMenues, dynamicForms);
		if (menuTree != null && !menuTree.isEmpty()) {
			total.setChildren(new ArrayList<TreeMenu>());
			total.getChildren().addAll(menuTree);
		}
		

		// 全部报表分类相关的安全资源点
		List<ResourceEntity> reportTypeResources = this.resourceSrv.getAll(ResourceType.ReportType);
		// 已授权用户访问的报表相关安全资源点
		Set<String> allowedReportTypes = new HashSet<String>();
		if (reportTypeResources != null) {
			for (ResourceEntity type : reportTypeResources) {
				if (userSecurityResources.contains(type.getUri())) {
					allowedReportTypes.add(type.getId());
				}
			}
		}
		//报表资源树
		List<TreeMenu> reportTypeMenu = this.reportSrv.getReportTypeTree(null, allowedReportTypes);
		if (reportTypeMenu != null && !reportTypeMenu.isEmpty()) {
			if (total.getChildren() == null || total.getChildren().isEmpty()) {
				total.setChildren(new ArrayList<TreeMenu>());
			}
			TreeMenu typeTree = new TreeMenu();
			typeTree.setCollapse(true);
			typeTree.setText("报表资源");
			typeTree.setChildren(reportTypeMenu);
			total.getChildren().add(typeTree);
		}
		

		return total.getChildren();
	}

	@RequestMapping("menu/tree")
	@ResponseBody
	public Object ManagerMenuTree() {
		List<Tree> tree = this.menuSrv.getMenuTree(null);
		return tree == null ? null : tree;
	}

	@RequestMapping(value = "menu/manager")
	public ModelAndView MenuManager() {
		ModelAndView view = new ModelAndView("/config/resource/menu-manager");
		return view;
	}

	@RequestMapping(value = "menu/edit")
	public ModelAndView editMenu(String id, String parentId) {
		ModelAndView view = new ModelAndView("/config/resource/menu-form");
		MenuEntity data;
		if (StringUtils.isEmpty(id)) {
			data = new MenuEntity();
			data.setCollapse(false); // 默认收拢
			data.setActive(false);
			data.setParent(new MenuEntity());
			data.getParent().setId(parentId);
		} else {
			data = this.menuSrv.get(id);
		}
		view.addObject("data", data);
		return view;
	}

	@RequestMapping("menu/save")
	@ResponseBody
	public Object updateMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		String parent = request.getParameter("parent");
		String text = request.getParameter("text");
		String url = request.getParameter("url");
		String sort = request.getParameter("sort");
		String icon = request.getParameter("icon");
		String active = request.getParameter("active");
		String collapse = request.getParameter("collapse");
		if (StringUtils.isEmpty(text)) {
			log.info("Parameter String[text] is blank.");
			return MessageModel.createErrorMessage("参数不足, 菜单名称必须填写", null);
		}
		MenuEntity menu = null;
		if (StringUtils.isEmpty(id)) {
			Date date = new Date();
			ResourceEntity resource = new ResourceEntity();
			resource.setCreateTime(date);
			resource.setLastModifiedTime(date);
			resource.setName(text);
			resource.setType(ResourceType.Menu.getType());
			resource.setUri(UUID.randomUUID().toString());
			menu = new MenuEntity();
			menu.setUrl(StringUtils.isEmpty(url) ? null : url);
			menu.setText(text);
			menu.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			menu.setIcon(StringUtils.isEmpty(icon) ? null : icon);
			menu.setActive(Boolean.valueOf(active));
			menu.setCollapse(Boolean.valueOf(collapse));
			menu.setResource(resource);
			if (!StringUtils.isEmpty(parent)) {
				menu.setParent(new MenuEntity());
				menu.getParent().setId(parent);
			}
			this.menuSrv.save(menu);
		} else {
			menu = this.menuSrv.get(id);
			if (menu == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的菜单信息", null);
			}

			menu.setUrl(StringUtils.isEmpty(url) ? null : url);
			menu.setText(text);
			menu.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			menu.setIcon(StringUtils.isEmpty(icon) ? null : icon);
			menu.setActive(Boolean.valueOf(active));
			menu.setCollapse(Boolean.valueOf(collapse));

			if (!StringUtils.isEmpty(parent)) {
				if (!parent.equals(id)) {
					menu.setParent(new MenuEntity());
					menu.getParent().setId(parent);
				}
			} else {
				menu.setParent(null);
			}
			ResourceEntity resource = menu.getResource();
			resource.setName(text);
			if (!StringUtils.isEmpty(url)) {
				resource.setUri(url);
			}
			menu.setResource(resource);
			this.menuSrv.update(menu);
		}
		return MessageModel.createSuccessMessage(null, menu.getId());
	}

	@Transactional
	@RequestMapping("menu/delete")
	@ResponseBody
	public Object MenuDelete(String menuId) {
		if (StringUtils.isEmpty(menuId)) {
			return MessageModel.createErrorMessage("无效的菜单ID！", null);
		}

		MenuEntity menu = this.menuSrv.get(menuId);

		if (menu == null) {
			return MessageModel.createErrorMessage("未找到与ID对应的菜单信息！", null);
		}
		MenuEntity parent = menu.getParent();
		if (parent != null) {
			parent.getChildren().remove(menu);
			menu.setParent(null);
		}

		try {
			this.menuSrv.delete(menu);
		} catch (Exception e) {
			return MessageModel.createErrorMessage("删除不成功,菜单信息映射错误！", null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}

	@RequestMapping({"segment/user"})
	@ResponseBody
	public Object UserSegments(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		// not login
		if (user == null) {
			log.info("Session timeout or User didn't login.");
			return MessageModel.createErrorMessage("用户未登录或会话超时", null);
		}
		// 当前用户可访问的安全资源点
		List<String> userSecurityResources = this.userSrv.getPermissions(user.getUniqueCode());
		// TODO 读取数据库内的资源配置或者从Session读取，二选一
		// Set<String> permissions = this.userSrv.getPermissions(user.getUniqueCode());
		// 全部页面片段相关的安全资源点
		List<ResourceEntity> segments = this.resourceSrv.getAll(ResourceType.Segment);
		// 已授权用户访问的菜单相关安全资源点
		List<String> allowedResources = new ArrayList<String>();
		for (ResourceEntity segment : segments) {
			if (userSecurityResources.contains(segment.getUri())) {
				allowedResources.add(segment.getUri());
			}
		}
		return allowedResources;
	}

	@RequestMapping(value = "segment/manager")
	public ModelAndView segmentManager() {
		ModelAndView view = new ModelAndView("/config/resource/segment-manager");
		List<SegmentEntity> data = this.segmentSrv.getAll();
		view.addObject("data", data);
		return view;
	}

	@RequestMapping(value = "segment/form")
	public ModelAndView editSegment(String id) {
		ModelAndView view = new ModelAndView("/config/resource/segment-form");
		SegmentEntity data;
		if (StringUtils.isEmpty(id)) {
			data = new SegmentEntity();
		} else {
			data = this.segmentSrv.get(id);
		}
		view.addObject("data", data);
		return view;
	}

	@RequestMapping("segment/save")
	@ResponseBody
	public Object segmentUpdate(HttpServletRequest request) {
		String name = request.getParameter("name");
		String uri = request.getParameter("uri");
		String sort = request.getParameter("sort");
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(uri)) {
			log.info("Parameter String[name|uri] is blank.");
			return MessageModel.createErrorMessage("参数不足, 名称和资源标识必须填写", null);
		}

		Date date = new Date();

		if (StringUtils.isEmpty(id)) {
			ResourceEntity resource = new ResourceEntity();
			resource.setCreateTime(date);
			resource.setLastModifiedTime(date);
			resource.setName(name);
			resource.setType(ResourceType.Segment.getType());
			resource.setUri(uri);
			SegmentEntity entity = new SegmentEntity();
			entity.setName(name);
			entity.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			entity.setUri(uri);
			entity.setResource(resource);
			this.segmentSrv.save(entity);
		} else {
			SegmentEntity entity = this.segmentSrv.get(id);
			entity.setName(name);
			entity.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			entity.setUri(uri);
			ResourceEntity resource = entity.getResource();
			resource.setName(name);
			resource.setUri(uri);
			entity.setResource(resource);
			this.segmentSrv.update(entity);
		}
		return MessageModel.createSuccessMessage(null, null);
	}

	@RequestMapping("segment/delete")
	@ResponseBody
	public Object doDelete(String id) {
		if (StringUtils.isEmpty(id)) {
			return MessageModel.createErrorMessage("无效的资源ID！", null);
		}

		SegmentEntity entity = this.segmentSrv.get(id);

		if (entity == null) {
			return MessageModel.createErrorMessage("未找到与ID对应的资源信息！", null);
		}

		try {
			this.segmentSrv.delete(entity);
		} catch (Exception e) {
			return MessageModel.createErrorMessage("删除不成功,控制资源信息映射错误！", null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}

	@RequestMapping("resource/user")
	@ResponseBody
	public Object ResourceUser(String userId, Integer type) {
		Tree resourceTree = new Tree();
		resourceTree.setText("请选择...");
		resourceTree.setCollapse(false);
		resourceTree.setChildren(new ArrayList<Tree>());

		List<User2PermissionEntity> userPermissions = this.user2permissionSrv.getByUser(userId);
		List<String> resources = new ArrayList<String>();
		for (User2PermissionEntity u2p : userPermissions) {
			resources.add(u2p.getPermission().getResource().getId());
		}
		
		switch (type) {
			case 1:
				List<Tree> menuTree = this.menuSrv.getMenuTree(null, resources);
				if (menuTree != null) {
					resourceTree.getChildren().addAll(menuTree);
				}
				break;
			case 2:
				List<Tree> segmentTree = new ArrayList<Tree>();
				List<SegmentEntity> segmentsData = this.segmentSrv.getAll();
				if (segmentsData != null) {
					for (SegmentEntity segment : segmentsData) {
						Tree temp = new Tree();
						temp.setText(segment.getName());
						temp.setData(segment.toVO());
						if (resources.contains(segment.getResource().getId())) {
							temp.setChecked(true);
						}
						segmentTree.add(temp);
					}
				}
				resourceTree.getChildren().addAll(segmentTree);
				break;
			case 3:
				// 报表分类带报表信息，报表文件本身视为资源
				List<Tree> reportTypeTree = this.reportSrv.getReportTypeTree(null, resources, true);
				if (reportTypeTree != null) {
					resourceTree.getChildren().addAll(reportTypeTree);
				}
				break;
			case 4:
				// 表单分类带报表信息，报表文件本身视为资源
				List<Tree> formTypeTree = this.formSrv.getFormTypeTree(null, resources, true);
				if (formTypeTree != null) {
					resourceTree.getChildren().addAll(formTypeTree);
				}
				break;
			default:
				break;
		}

		return resourceTree;		


	}


	@RequestMapping("resource/role")
	@ResponseBody
	public Object ResourceRole(String roleId, Integer type) {
		Tree resourceTree = new Tree();
		resourceTree.setText("请选择...");
		resourceTree.setCollapse(false);
		resourceTree.setChildren(new ArrayList<Tree>());

		List<Role2PermissionEntity> rolePermissions = this.role2permissionSrv.getByRole(roleId);
		List<String> resources = new ArrayList<String>();
		for (Role2PermissionEntity r2p : rolePermissions) {
			resources.add(r2p.getPermission().getResource().getId());
		}

		switch (type) {
			case 1:
				List<Tree> menuTree = this.menuSrv.getMenuTree(null, resources);
				if (menuTree != null) {
					resourceTree.getChildren().addAll(menuTree);
				}
				break;
			case 2:
				List<Tree> segmentTree = new ArrayList<Tree>();
				List<SegmentEntity> segmentsData = this.segmentSrv.getAll();
				if (segmentsData != null) {
					for (SegmentEntity segment : segmentsData) {
						Tree temp = new Tree();
						temp.setText(segment.getName());
						temp.setData(segment.toVO());
						if (resources.contains(segment.getResource().getId())) {
							temp.setChecked(true);
						}
						segmentTree.add(temp);
					}
				}
				resourceTree.getChildren().addAll(segmentTree);
				break;
			case 3:
				// 报表分类带报表信息，报表文件本身视为资源
				List<Tree> reportTypeTree = this.reportSrv.getReportTypeTree(null, resources, true);
				if (reportTypeTree != null) {
					resourceTree.getChildren().addAll(reportTypeTree);
				}
				break;
			case 4:
				// 表单分类带报表信息，表单文件本身视为资源
				List<Tree> formTypeTree = this.formSrv.getFormTypeTree(null, resources, true);
				if (formTypeTree != null) {
					resourceTree.getChildren().addAll(formTypeTree);
				}
				break;
			default:
				break;
		}

		return resourceTree;
	}

	@RequestMapping("resource/basic")
	@ResponseBody
	public Object ResourceBasic(Integer type) {
		Tree resourceTree = new Tree();
		resourceTree.setText("请选择...");
		resourceTree.setCollapse(false);
		resourceTree.setChildren(new ArrayList<Tree>());

		List<BasicPermissionEntity> baspers = this.basicPerSrv.getAll();
		List<String> resources = new ArrayList<String>();
		if (baspers != null && !baspers.isEmpty()) {
			for (BasicPermissionEntity p : baspers) {
				resources.add(p.getPermission().getResource().getId());
			}
		}

		switch (type) {
			case 1:
				List<Tree> menuTree = this.menuSrv.getMenuTree(null, resources);
				if (menuTree != null) {
					resourceTree.getChildren().addAll(menuTree);
				}
				break;
			case 2:
				List<Tree> segmentTree = new ArrayList<Tree>();
				List<SegmentEntity> segmentsData = this.segmentSrv.getAll();
				if (segmentsData != null) {
					for (SegmentEntity segment : segmentsData) {
						Tree temp = new Tree();
						temp.setText(segment.getName());
						temp.setData(segment.toVO());
						if (resources.contains(segment.getResource().getId())) {
							temp.setChecked(true);
						}
						segmentTree.add(temp);
					}
				}
				resourceTree.getChildren().addAll(segmentTree);
				break;
			case 3:
				// 报表分类带报表信息，报表文件本身视为资源
				List<Tree> reportTypeTree = this.reportSrv.getReportTypeTree(null, resources, true);
				if (reportTypeTree != null) {
					resourceTree.getChildren().addAll(reportTypeTree);
				}
				break;
			case 4:
				// 表单分类带表单信息，表单文件本身视为资源
				List<Tree> formTypeTree = this.formSrv.getFormTypeTree(null, resources, true);
				if (formTypeTree != null) {
					resourceTree.getChildren().addAll(formTypeTree);
				}
				break;
			default:
				break;
		}

		return resourceTree;
	

	}



}
