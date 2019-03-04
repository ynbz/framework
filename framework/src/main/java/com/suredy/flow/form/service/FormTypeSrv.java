package com.suredy.flow.form.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.sun.istack.internal.Nullable;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.flow.form.entity.FormTypeEntity;

@Service("FormTypeSrv")
public class FormTypeSrv extends BaseSrvWithEntity<FormTypeEntity> {
	
	@Override
	public DetachedCriteria getDc(FormTypeEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.like("name", t.getName(), MatchMode.ANYWHERE));
		}

		if (t.getParent() != null) {
			dc.add(Restrictions.eq("parent", t.getParent()));
		}

		return dc;
	}
	
	@Transactional
	public List<FormTypeEntity> getFormTypes(@Nullable String typeId) {
		if (StringUtils.isEmpty(typeId)) {
			return null;
		} else {
			FormTypeEntity search = new FormTypeEntity();
			search.setId(typeId);
			FormTypeEntity root = this.readSingleByEntity(search);
			List<FormTypeEntity> data = getSubTypes(root, new ArrayList<FormTypeEntity>());

			return data.isEmpty() ? null : data;
		}

	}

	private List<FormTypeEntity> getSubTypes(FormTypeEntity entity, List<FormTypeEntity> data) {
		if (entity == null) {
			return null;
		}
		data.add(entity);
		List<FormTypeEntity> children = entity.getChildren();
		if (children != null && !children.isEmpty()) {
			for (FormTypeEntity child : children) {
				getSubTypes(child, data);
			}
		}
		return data;
	}


}
