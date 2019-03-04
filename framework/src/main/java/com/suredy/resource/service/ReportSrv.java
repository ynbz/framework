package com.suredy.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;


import com.suredy.core.mvc.model.Tree;
import com.suredy.core.mvc.model.TreeMenu;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;
import com.suredy.resource.entity.ReportEntity;
import com.suredy.resource.entity.ReportTypeEntity;
import com.suredy.resource.model.Report;

@Service("ReportSrv")
public class ReportSrv extends BaseSrvWithEntity<ReportEntity> {

	@Override
	public DetachedCriteria getDc(ReportEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}

		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.eq("name", t.getName()));
		}
		if (!StringUtils.isBlank(t.getFileId())) {
			dc.add(Restrictions.eq("fileId", t.getFileId()));
		}
		
		if (t.getTypes() != null && !t.getTypes().isEmpty()) {
			dc.add(Restrictions.in("type", t.getTypes()));
		}

		return dc;
	}

	
	public List<Report> getAll(List<ReportTypeEntity> types) {
		OrderEntity oe = new OrderEntity();
		oe.add("type", false);
		oe.add("sort", false);
		ReportEntity search = null;
		List<ReportEntity> pos = null;
		if (types == null || types.isEmpty()) {
			pos = this.readByEntity(null, oe);
		} else {
			search = new ReportEntity();
			search.setTypes(types);
			pos = this.readByEntity(search, oe);
		} 
		
		
		if (pos == null || pos.isEmpty()) {
			return null;
		} else {
			List<Report> ret = new ArrayList<Report>();
			for (ReportEntity po : pos) {
				ret.add(po.toVO());
			}
			return ret;
		}
	}
	
	public List<Report> getAll(Integer page, Integer size, List<ReportTypeEntity> types) {
		OrderEntity oe = new OrderEntity();
		oe.add("type", false);
		oe.add("sort", false);
		ReportEntity search = null;
		List<ReportEntity> pos = null;
		if (types == null || types.isEmpty()) {
			pos = this.readPageByEntity(null, page, size, oe);
		} else {
			search = new ReportEntity();
			search.setTypes(types);
			pos = this.readPageByEntity(search, page, size, oe);
		} 
		
		
		if (pos == null || pos.isEmpty()) {
			return null;
		} else {
			List<Report> ret = new ArrayList<Report>();
			for (ReportEntity po : pos) {
				ret.add(po.toVO());
			}
			return ret;
		}
	}

	
	public Integer Count(List<ReportTypeEntity> types) {
		int ret = 0;
		if (types == null || types.isEmpty()) {
			ret = this.getCountByEntity(null);
		} else {
			ReportEntity search = new ReportEntity();
			search.setTypes(types);
			ret = this.getCountByEntity(search);
		} 
		return ret;
	}
	



	public ReportEntity getByFileId(String fileId) {
		if (StringUtils.isBlank(fileId)) {
			return null;
		}
		ReportEntity search = new ReportEntity();
		search.setFileId(fileId);
		return this.readSingleByEntity(search);

	}
	

	
	@Transactional
	public List<Tree> getReportTypeTree(@Nullable String rootId, Boolean withReports){
		
		String ql = "SELECT T FROM ReportTypeEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<ReportTypeEntity> entities = (List<ReportTypeEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (ReportTypeEntity entity : entities) {
			Tree entityTree = buildTree(entity, withReports);
			if (entityTree != null) {
				ret.add(entityTree);
			}
		}
		
		return ret;
	}
	
	
	
	private Tree buildTree(ReportTypeEntity entity, Boolean withReports) {
		if (entity == null) {
			return null;
		}
		Tree node = new Tree();
		node.setText(entity.getName());
		node.setData(entity.toVO());
		
		
		if (withReports) {
			Set<ReportEntity> reports = entity.getAssociationReports();
			if (!reports.isEmpty() && reports.size() > 0) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					node.setChildren(new ArrayList<Tree>());
				}
				for (ReportEntity report : reports) {
					Tree rNode = new Tree();
					rNode.setText(report.getName());
					rNode.setData(report.toVO());
					node.getChildren().add(rNode);
				}
			}
		}
		
		if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				node.setChildren(new ArrayList<Tree>());
			}
			for (ReportTypeEntity typeEntity : entity.getChildren()) {
				Tree child = buildTree(typeEntity, withReports);

				if (child != null) {
					node.getChildren().add(child);
				}
			}
		}
		return node;
	}
	
	@Transactional
	public List<Tree> getReportTypeTree(@Nullable String rootId, List<String> resourceIds, Boolean withReports){
		String ql = "SELECT T FROM ReportTypeEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<ReportTypeEntity> entities = (List<ReportTypeEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (ReportTypeEntity entity : entities) {
			Tree tree = buildTree(entity, resourceIds, withReports);
			if (tree != null) {
				ret.add(tree);
			}
		}
		
		return ret;
	}
	
	
	private Tree buildTree(ReportTypeEntity entity, List<String> resouceIds, Boolean withReports) {
		if (entity == null) {
			return null;
		}
		Tree node = new Tree();
		node.setText(entity.getName());
		node.setData(entity.toVO());
		if (resouceIds.contains(entity.getResource().getId())) {
			node.setChecked(true);
		}
		if (withReports) {
			Set<ReportEntity> reports = entity.getAssociationReports();
			if (!reports.isEmpty() && reports.size() > 0) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					node.setChildren(new ArrayList<Tree>());
				}
				for (ReportEntity report : reports) {
					Tree rNode = new Tree();
					rNode.setText(report.getName());
					rNode.setData(report.toVO());
					if (resouceIds.contains(report.getResource().getId())) {
						rNode.setChecked(true);
					}
					node.getChildren().add(rNode);
				}
			}
		}	

		
		if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				node.setChildren(new ArrayList<Tree>());
			}

			for (ReportTypeEntity typeEntity : entity.getChildren()) {
				Tree cNode = buildTree(typeEntity, resouceIds, withReports);

				if (cNode != null) {
					node.getChildren().add(cNode);
				}
			}
		}
		return node;
	}
	
	
	@Transactional
	public List<TreeMenu> getReportTypeTree(String rootId, Set<String> allowedTypes){
		String ql = "SELECT T FROM ReportTypeEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<ReportTypeEntity> types = (List<ReportTypeEntity>) this.readByQL(ql);
		List<TreeMenu> ret = new ArrayList<TreeMenu>();
		for (ReportTypeEntity entity : types) {
			TreeMenu menuTree = buildTree(entity, allowedTypes);
			if (menuTree != null) {
				ret.add(menuTree);
			}
		}
		
		return ret;
	}
	
	private TreeMenu buildTree(ReportTypeEntity entity, Set<String> allowedTypes) {
		if (entity == null) {
			return null;
		}
		if ( allowedTypes.contains(entity.getResource().getId()) ) {					
			TreeMenu tree = new TreeMenu();
			tree.setText(entity.getName());
			//指定跳转到报表资源展示页面
			tree.setUri("report/list?typeId=" + entity.getId());
			if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
				tree.setChildren(new ArrayList<TreeMenu>());
	
				for (ReportTypeEntity child : entity.getChildren()) {
					TreeMenu cTree = buildTree(child, allowedTypes);
	
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
