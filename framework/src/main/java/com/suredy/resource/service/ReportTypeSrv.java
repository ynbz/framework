package com.suredy.resource.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.resource.entity.ReportTypeEntity;

@Service("ReportTypeSrv")
public class ReportTypeSrv extends BaseSrvWithEntity<ReportTypeEntity> {

	public DetachedCriteria getDc(ReportTypeEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
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
	public List<ReportTypeEntity> getReportTypes(@Nullable String typeId) {
		if (StringUtils.isEmpty(typeId)) {
			return null;
		} else {
			ReportTypeEntity search = new ReportTypeEntity();
			search.setId(typeId);
			ReportTypeEntity root = this.readSingleByEntity(search);
			List<ReportTypeEntity> data = getSubTypes(root, new ArrayList<ReportTypeEntity>());

			return data.isEmpty() ? null : data;
		}

	}

	private List<ReportTypeEntity> getSubTypes(ReportTypeEntity entity, List<ReportTypeEntity> data) {
		if (entity == null) {
			return null;
		}
		data.add(entity);
		List<ReportTypeEntity> children = entity.getChildren();
		if (children != null && !children.isEmpty()) {
			for (ReportTypeEntity child : children) {
				getSubTypes(child, data);
			}
		}
		return data;
	}

}
