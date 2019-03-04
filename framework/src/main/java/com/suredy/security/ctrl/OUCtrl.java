/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年6月26日
 * @version 0.1
 */
package com.suredy.security.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.UnitEntity;
import com.suredy.security.model.Org;
import com.suredy.security.service.OrgSrv;
import com.suredy.security.service.UnitSrv;

/**
 * @author ZhangMaoren
 * 
 */
@Controller
@RequestMapping(value = "/config")
public class OUCtrl extends BaseCtrl {

	@Autowired
	private OrgSrv orgSrv;

	@Autowired
	private UnitSrv unitSrv;

	@RequestMapping("ou/tree")
	@ResponseBody
	public Object OUTree() {
		List<Tree> tree = new ArrayList<Tree>();
		List<Org> orgs = this.orgSrv.getAll();

		for (Org org : orgs) {
			Tree treeOrg = new Tree();
			treeOrg.setCollapse(false);
			treeOrg.setText(org.getName() + " [单位]");
			treeOrg.setData(org);
			List<Tree> units = this.unitSrv.getUnitTree(org.getId());
			if (units != null && !units.isEmpty()) {
				treeOrg.setChildren(new ArrayList<Tree>());
				treeOrg.getChildren().addAll(units);
			}
			tree.add(treeOrg);
		}

		return tree;
	}

	@RequestMapping("ou/tree/{allOrg}/{withUser}/{excludeNoUserNode}")
	@ResponseBody
	public Object OUTree(@PathVariable boolean allOrg, @PathVariable boolean withUser, @PathVariable boolean excludeNoUserNode) {
		String orgId = null;

		if (!allOrg)
			orgId = this.getUser().getOrgId();

		List<Tree> tree = this.orgSrv.getTree(orgId, withUser, excludeNoUserNode);

		return tree;
	}

	@RequestMapping(value = "ou/manager")
	public ModelAndView OUManager() {
		ModelAndView view = new ModelAndView("/config/security/ou-manager");
		return view;
	}

	@RequestMapping(value = "ou/org/form")
	public ModelAndView orgForm(String orgId) {
		ModelAndView view = new ModelAndView("/config/security/org-form");
		Org org = null;
		if (StringUtils.isEmpty(orgId)) {
			org = new Org();
		} else {
			org = this.orgSrv.getById(orgId);
		}
		view.addObject("org", org);
		
		return view;
	}


	@RequestMapping("ou/org/save")
	@ResponseBody
	public Object orgSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String sort = request.getParameter("sort");
		String description = request.getParameter("description");

		
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足, 代码和名称必须填写", null);
		}
		if (StringUtils.isEmpty(id)) { //创建
			OrgEntity org = new OrgEntity();
			org.setAlias(StringUtils.isEmpty(alias) ? null : alias);
			org.setAvailable(1);
			org.setBuildIn(0);
			org.setCode(code);
			org.setDescription(StringUtils.isEmpty(description) ? null : description);
			org.setFullName(name);
			org.setName(name);
			org.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			org.setUniqueCode("O=" + code);
			this.orgSrv.save(org);
		} else {
			OrgEntity org = this.orgSrv.get(id);
			if (org == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的单位信息", null);
			}
			org.setAlias(StringUtils.isEmpty(alias) ? null : alias);
			org.setAvailable(1);
			org.setBuildIn(0);
			org.setCode(code);
			org.setDescription(StringUtils.isEmpty(description) ? null : description);
			org.setFullName(name);
			org.setName(name);
			org.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			org.setUniqueCode("O=" + code);

			this.orgSrv.update(org);
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping("ou/org/delete")
	@ResponseBody
	public Object doDeleteOrg(String nodeId) {
		if (StringUtils.isEmpty(nodeId)) {
			return MessageModel.createErrorMessage("参数错误, 节点ID不能为空", null);
		}
		OrgEntity org = this.orgSrv.get(nodeId);
		if (org == null) {
			return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的菜单信息", null);
		}

		this.orgSrv.delete(org);

		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping(value = "ou/unit/form")
	public ModelAndView unitForm(String unitId) {
		ModelAndView view = new ModelAndView("/config/security/unit-form");
		UnitEntity unit = null;
		if (StringUtils.isEmpty(unitId)) {
			unit = new UnitEntity();
		} else {
			unit = this.unitSrv.get(unitId);
			UnitEntity parent = unit.getParent();
			OrgEntity org = unit.getOrg();
			if (parent == null) {
				view.addObject("parentName", org.getName());
				view.addObject("parentId", org.getId());
				view.addObject("parentType", "org");
			} else {
				view.addObject("parentName", parent.getName());
				view.addObject("parentId", parent.getId());
				view.addObject("parentType", "unit");
			}
		}
		view.addObject("unit", unit.toVO());
		
		return view;
	}

	@RequestMapping("ou/unit/save")
	@ResponseBody
	public Object unitSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String sort = request.getParameter("sort");
		String description = request.getParameter("description");
		String parentId = request.getParameter("parentId");
		String parentType = request.getParameter("parentType");
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足, 代码和名称必须填写", null);
		}
		OrgEntity org = null;
		UnitEntity parent = null;

		if(StringUtils.isEmpty(id)) { //创建
			UnitEntity unit = new UnitEntity();
			unit.setAlias(StringUtils.isEmpty(alias) ? null : alias);
			unit.setAvailable(1);
			unit.setCode(code);
			unit.setDescription(StringUtils.isEmpty(description) ? null : description);
			unit.setFullName(name);
			unit.setName(name);
			if ("org".equalsIgnoreCase(parentType)) {
				org = this.orgSrv.get(parentId);
				unit.setParent(null);
				unit.setUniqueCode("OU=" + code + "/" + org.getUniqueCode());
				unit.setFullName(name + "/" + org.getFullName());
			} else {
				parent = this.unitSrv.get(parentId);
				unit.setParent(parent);
				org = parent.getOrg();
				unit.setUniqueCode("OU=" + code + "/" + parent.getUniqueCode());
				unit.setFullName(name + "/" + parent.getFullName());
			}
			unit.setOrg(org);
			unit.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			this.unitSrv.save(unit);
		} else { //修改
			UnitEntity unit = this.unitSrv.get(id);
			if (unit == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的部门信息", null);
			}

			unit.setAlias(StringUtils.isEmpty(alias) ? null : alias);
			unit.setAvailable(1);
			unit.setCode(code);
			unit.setDescription(StringUtils.isEmpty(description) ? null : description);
			unit.setFullName(name);
			unit.setName(name);
			if ("org".equalsIgnoreCase(parentType)) {
				org = this.orgSrv.get(parentId);
				unit.setParent(null);
				unit.setOrg(org);
				unit.setUniqueCode("OU=" + code + "/" + org.getUniqueCode());
				unit.setFullName(name + "/" + org.getFullName());
			} else {
				if (!id.equals(parentId)) {
					parent = this.unitSrv.get(parentId);
					unit.setParent(parent);
					org = parent.getOrg();
					unit.setOrg(org);
					unit.setUniqueCode("OU=" + code + "/" + parent.getUniqueCode());
					unit.setFullName(name + "/" + parent.getFullName());
				}

			}

			unit.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));

			this.unitSrv.update(unit);
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	

	@RequestMapping("ou/unit/delete")
	@ResponseBody
	public Object doDeleteUnit(String nodeId) {
		if (StringUtils.isEmpty(nodeId)) {
			return MessageModel.createErrorMessage("参数错误, 节点ID不能为空", null);
		}
		UnitEntity unit = this.unitSrv.get(nodeId);
		if (unit == null) {
			return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的菜单信息", null);
		}

		this.unitSrv.delete(unit);

		return MessageModel.createSuccessMessage(null, null);
	}

}
