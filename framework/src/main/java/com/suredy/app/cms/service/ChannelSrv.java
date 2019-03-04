/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2017年12月7日
 * @version 0.1
 */
package com.suredy.app.cms.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.cms.entity.Channel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;




/**
 * @author ZhangMaoren
 *
 */
@Service("ChannelSrv")
public class ChannelSrv extends BaseSrvWithEntity<Channel> {

	public DetachedCriteria getDc(Channel t) {
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


		return dc;
	}


	
	
	@Transactional
	public List<Tree> getChannelTree(@Nullable String rootId){
		String ql = "SELECT T FROM Channel T";

		if (StringUtils.isBlank(rootId)) {
			ql += " WHERE T.parent IS NULL";
		} else {
			ql += " WHERE T.parent = '" + rootId + "'";
		}
		ql += " order by sort";
		@SuppressWarnings("unchecked")
		List<Channel> channels = (List<Channel>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		for (Channel c : channels) {
			Tree tree = buildTree(c);
			if (tree != null) {
				ret.add(tree);
			}
		}
		
		return ret;
	}
	
	
	
	private Tree buildTree(Channel c) {
		if (c == null) {
			return null;
		}
		Tree tree = new Tree();
		tree.setCollapse(false); //收拢
		tree.setText(c.getName());
		tree.setData(c);
		if (c.getChildren() != null && !c.getChildren().isEmpty()) {
			tree.setChildren(new ArrayList<Tree>());

			for (Channel child : c.getChildren()) {
				Tree cTree = buildTree(child);

				if (cTree != null) {
					tree.getChildren().add(cTree);
				}
			}
		}
		return tree;
	}
	
	
	
	

}
