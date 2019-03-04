package com.suredy.security.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.security.model.TemplateType;

@Service
public class TemplateTypeSrv extends BaseSrvWithEntity<TemplateType>{

	@Override
	public DetachedCriteria getDc(TemplateType t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (t.getTypeName() != null) {
			dc.add(Restrictions.eqOrIsNull("TypeName", t.getTypeName()));
		}
		return dc;		
	}
	
	public List<Tree> getTemplateTypeTree(){
		List<Tree> ret = new ArrayList<Tree>();
		String ql = "SELECT T FROM TemplateType T where T.parent is null";
		@SuppressWarnings("unchecked")
		List<TemplateType> TemplateType=(List<TemplateType>)this.readByQL(ql);
		if(TemplateType!=null){
			for(TemplateType t:TemplateType){
				ret.add(createTree(t));
			}
		}
		return ret;
	}
	
	public Tree createTree(TemplateType TemplateType){
		Tree tree = new Tree();
		tree.setText(TemplateType.getTypeName());
		tree.setData(TemplateType.getId());
		String ql = "SELECT T FROM TemplateType T where T.parent.id=?";
		@SuppressWarnings("unchecked")
		List<TemplateType> list = (List<TemplateType>)this.readByQL(ql, TemplateType.getId());
		for(TemplateType t:list){
			if(tree.getChildren()==null||tree.getChildren().isEmpty())
				tree.setChildren(new ArrayList<Tree>());
			tree.getChildren().add(createTree(t));
		}
		return tree;
	}
	
	
	//-----------------------------added by Zhangmaoren--------------------------
	@Transactional
	public List<TemplateType> getFileTypes(@Nullable String typeId) {
		if (StringUtils.isEmpty(typeId)) {
			return null;
		} else {
			TemplateType search = new TemplateType();
			search.setId(typeId);
			TemplateType root = this.readSingleByEntity(search);
			List<TemplateType> data = getSubTypes(root, new ArrayList<TemplateType>());

			return data!=null &&!data.isEmpty()? data : null;
		}

	}

	private List<TemplateType> getSubTypes(TemplateType entity, List<TemplateType> data) {
		if (entity == null) {
			return null;
		}
		data.add(entity);
		List<TemplateType> children = entity.getChildrenItems();
		if (children != null && !children.isEmpty()) {
			for (TemplateType child : children) {
				getSubTypes(child, data);
			}
		}
		return data;
	}
	
	//-----------------------------------end--------------------------------------
	
}
