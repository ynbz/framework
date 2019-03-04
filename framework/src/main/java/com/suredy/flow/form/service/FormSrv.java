package com.suredy.flow.form.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.Nullable;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.entity.FormTypeEntity;
import com.suredy.flow.form.model.Form;
import com.suredy.formbuilder.design.model.FormDefine;

@Service("FormSrv")
public class FormSrv extends BaseSrvWithEntity<FormEntity> {

	@Override
	public DetachedCriteria getDc(FormEntity t) {
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
		
		if (!StringUtils.isBlank(t.getFileType())) {
			dc.add(Restrictions.eq("fileType", t.getFileType()));
		}

		if (!StringUtils.isBlank(t.getFlowId())) {
			dc.add(Restrictions.eq("flowId", t.getFlowId()));
		}

		if (t.getTypes() != null && !t.getTypes().isEmpty()) {
			dc.add(Restrictions.in("type", t.getTypes()));
		}

		return dc;
	}
	
	public FormEntity getByFileType(String fileType){
		FormEntity search = new FormEntity();
		search.setFileType(fileType);
		search=this.readSingleByEntity(search);
		return search;
	}
	

	public List<Form> getAll() {
		OrderEntity oe = new OrderEntity();
		oe.add("type", false);
		oe.add("createTime", false);
		FormEntity search = new FormEntity();
		List<FormEntity> pos = this.readByEntity(search, oe);
		if (pos == null || pos.isEmpty()) {
			return null;
		} else {
			List<Form> ret = new ArrayList<Form>();
			for (FormEntity form : pos) {
				ret.add(form.toVO());
			}
			return ret;
		}
	}

	public Integer Count(List<FormTypeEntity> types) {
		int ret = 0;
		if (types == null || types.isEmpty()) {
			ret = this.getCountByEntity(null);
		} else {
			FormEntity search = new FormEntity();
			search.setTypes(types);
			ret = this.getCountByEntity(search);
		}
		return ret;
	}
	public List<Form> getAll(List<FormTypeEntity> types) {
		OrderEntity oe = new OrderEntity();
		oe.add("type", false);
		oe.add("createTime", false);
		FormEntity search = null;
		List<FormEntity> pos = null;
		if (types == null || types.isEmpty()) {
			pos = this.readByEntity(null, oe);
		} else {
			search = new FormEntity();
			search.setTypes(types);
			pos = this.readByEntity(search, oe);
		}

		if (pos == null || pos.isEmpty()) {
			return null;
		} else {
			List<Form> ret = new ArrayList<Form>();
			for (FormEntity form : pos) {
				ret.add(form.toVO());
			}
			return ret;
		}
	}

	public List<Form> getAll(Integer page, Integer size, List<FormTypeEntity> types) {
		OrderEntity oe = new OrderEntity();
		oe.add("type", false);
		oe.add("createTime", false);
		FormEntity search = null;
		List<FormEntity> pos = null;
		if (types == null || types.isEmpty()) {
			pos = this.readPageByEntity(null, page, size, oe);
		} else {
			search = new FormEntity();
			search.setTypes(types);
			pos = this.readPageByEntity(search, page, size, oe);
		}

		if (pos == null || pos.isEmpty()) {
			return null;
		} else {
			List<Form> ret = new ArrayList<Form>();
			for (FormEntity form : pos) {
				ret.add(form.toVO());
			}
			return ret;
		}
	}

	public List<FormEntity> getAll(String flowId) {
		FormEntity search = new FormEntity();

		if (StringUtils.isNotEmpty(flowId)) {
			search.setFlowId(flowId);
		}
		List<FormEntity> pos = this.readByEntity(search);

		return pos;
	}

	@Transactional
	public List<Tree> getFormTypeTree(@Nullable String rootId, Boolean withForms) {

		String ql = "SELECT T FROM FormTypeEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<FormTypeEntity> entities = (List<FormTypeEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (FormTypeEntity entity : entities) {
			Tree entityTree = buildTree(entity, withForms);
			if (entityTree != null) {
				ret.add(entityTree);
			}
		}

		return ret;
	}

	private Tree buildTree(FormTypeEntity entity, Boolean withForms) {
		if (entity == null) {
			return null;
		}
		Tree node = new Tree();
		node.setText(entity.getName());
		node.setData(entity.toVO());

		if (withForms) {
			List<FormEntity> forms = entity.getAssociationForms();
			if (!forms.isEmpty() && forms.size() > 0) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					node.setChildren(new ArrayList<Tree>());
				}
				for (FormEntity form : forms) {
					Tree rNode = new Tree();
					rNode.setText(form.getName());
					rNode.setData(form.toVO());
					node.getChildren().add(rNode);
				}
			}
		}

		if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
			if (node.getChildren() == null || node.getChildren().isEmpty()) {
				node.setChildren(new ArrayList<Tree>());
			}
			for (FormTypeEntity typeEntity : entity.getChildren()) {
				Tree child = buildTree(typeEntity, withForms);

				if (child != null) {
					node.getChildren().add(child);
				}
			}
		}
		return node;
	}


	@Transactional
	public List<Tree> getFormTypeTree(@Nullable String rootId, List<String> resourceIds, Boolean withForms) {
		String ql = "SELECT T FROM FormTypeEntity T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<FormTypeEntity> entities = (List<FormTypeEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (FormTypeEntity entity : entities) {
			Tree tree = buildTree(entity, resourceIds, withForms);
			if (tree != null) {
				ret.add(tree);
			}
		}

		return ret;
	}

	private Tree buildTree(FormTypeEntity entity, List<String> resouceIds, Boolean withForms) {
		if (entity == null) {
			return null;
		}
		Tree node = new Tree();
		node.setText(entity.getName());
		node.setData(entity.toVO());
		if (resouceIds.contains(entity.getResource().getId())) {
			node.setChecked(true);
		}
		if (withForms) {
			List<FormEntity> forms = entity.getAssociationForms();
			if (!forms.isEmpty() && forms.size() > 0) {
				if (node.getChildren() == null || node.getChildren().isEmpty()) {
					node.setChildren(new ArrayList<Tree>());
				}
				for (FormEntity form : forms) {
					Tree rNode = new Tree();
					rNode.setText(form.getName());
					rNode.setData(form.toVO());
					if (resouceIds.contains(form.getResource().getId())) {
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

			for (FormTypeEntity typeEntity : entity.getChildren()) {
				Tree cNode = buildTree(typeEntity, resouceIds, withForms);

				if (cNode != null) {
					node.getChildren().add(cNode);
				}
			}
		}
		return node;
	}


	@SuppressWarnings("unchecked")
	public List<Tree> getCustomFormTree() {
		List<Tree> ret = new ArrayList<Tree>();
		String ql = "SELECT T FROM FormDefine T where T.enable=1";
		List<FormDefine> fdfs = (List<FormDefine>) this.readByQL(ql);
		if (fdfs != null) {
			for (FormDefine t : fdfs) {
				Tree tree = new Tree();
				tree.setText(t.getName() + "(" + t.getVersion() + ")");
				tree.setData(t.getId());// 添加1来区分是表单类型
				ret.add(tree);
			}
		}
		return ret;
	}

}
