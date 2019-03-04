/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月26日
 * @version 0.1
 */
package com.suredy.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.mvc.model.Tree;
import com.suredy.core.mvc.model.TreeMenu;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.flow.form.model.Form;
import com.suredy.resource.entity.MenuEntity;




/**
 * @author ZhangMaoren
 *
 */
@Service("MenuSrv")
public class MenuSrv extends BaseSrvWithEntity<MenuEntity> {

	public DetachedCriteria getDc(MenuEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getUrl())) {
			dc.add(Restrictions.eq("url", t.getUrl()));
		}
		if (!StringUtils.isBlank(t.getText())) {
			dc.add(Restrictions.like("text", t.getText(), MatchMode.ANYWHERE));
		}
	
		
		if (t.getParent() != null) {
			dc.add(Restrictions.eq("parent", t.getParent()));
		}

		return dc;
	}

	public MenuEntity getById(String id) {
		return this.get(id);
	}
	
	
	
	@Transactional
	public List<Tree> getMenuTree(@Nullable String rootId){
		String ql = "SELECT T FROM MenuEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<MenuEntity> menues = (List<MenuEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (MenuEntity menu : menues) {
			Tree menuTree = buildTree(menu);
			if (menuTree != null) {
				ret.add(menuTree);
			}
		}
		
		return ret;
	}
	
	
	
	private Tree buildTree(MenuEntity menu) {
		if (menu == null) {
			return null;
		}
		Tree tree = new Tree();
		tree.setCollapse(menu.getCollapse());
		tree.setActive(menu.getActive());
		tree.setIcon(menu.getIcon());
		tree.setText(menu.getText());
		tree.setData(menu.toVO());
		if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
			tree.setChildren(new ArrayList<Tree>());

			for (MenuEntity child : menu.getChildren()) {
				Tree cTree = buildTree(child);

				if (cTree != null) {
					tree.getChildren().add(cTree);
				}
			}
		}
		return tree;
	}
	
	@Transactional
	public List<Tree> getMenuTree(String rootId, List<String> menuIds){
		String ql = "SELECT T FROM MenuEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<MenuEntity> menues = (List<MenuEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (MenuEntity menu : menues) {
			Tree menuTree = buildTree(menu, menuIds);
			if (menuTree != null) {
				ret.add(menuTree);
			}
		}
		
		return ret;
	}
	
	
	private Tree buildTree(MenuEntity menu, List<String> menuIds) {
		if (menu == null) {
			return null;
		}
		Tree tree = new Tree();
		tree.setText(menu.getText());
		tree.setData(menu.toVO());
		if (menuIds.contains(menu.getResource().getId())) {
			tree.setChecked(true);
		}
		if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
			tree.setChildren(new ArrayList<Tree>());

			for (MenuEntity child : menu.getChildren()) {
				Tree cTree = buildTree(child, menuIds);

				if (cTree != null) {
					tree.getChildren().add(cTree);
				}
			}
		}
		return tree;
	}
	
	@Transactional
	public List<TreeMenu> getMenuTree(String rootId, Set<String> allowedMenues, Set<Form> dynamicForms){
		String ql = "SELECT T FROM MenuEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<MenuEntity> menues = (List<MenuEntity>) this.readByQL(ql);
		List<TreeMenu> ret = new ArrayList<TreeMenu>();
		for (MenuEntity menu : menues) {
			TreeMenu menuTree = buildTree(menu, allowedMenues, dynamicForms);
			if (menuTree != null) {
				ret.add(menuTree);
			}
		}
		
		return ret;
	}
	
	private TreeMenu buildTree(MenuEntity menu, Set<String> allowedMenues, Set<Form> dynamicForms) {
		if (menu == null) {
			return null;
		}
		if ( allowedMenues.contains(menu.getResource().getId()) ) {					
			TreeMenu tree = new TreeMenu();
			tree.setText(menu.getText());
			tree.setActive(menu.getActive());
			tree.setCollapse(menu.getCollapse());
			tree.setIcon(menu.getIcon());
			tree.setUri(menu.getUrl());
			if (dynamicForms != null && !dynamicForms.isEmpty()) {
				for (Form form : dynamicForms) {
					if (form.getMenuId().equals(menu.getId())) {
						if (tree.getChildren() == null) {
							tree.setChildren(new ArrayList<TreeMenu>());
						}
						TreeMenu cTree = new TreeMenu();
						cTree.setText(form.getName());
						cTree.setUri(form.getResourceUri());
						tree.getChildren().add(cTree);
					}
				}
			}
			
			if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
				if (tree.getChildren() == null) {
					tree.setChildren(new ArrayList<TreeMenu>());
				}
	
				for (MenuEntity child : menu.getChildren()) {
					TreeMenu cTree = buildTree(child, allowedMenues, dynamicForms);
	
					if (cTree != null) {
						tree.getChildren().add(cTree);
					}
				}
			}
			return tree;
		} 	
		return null;
	}
	

}
