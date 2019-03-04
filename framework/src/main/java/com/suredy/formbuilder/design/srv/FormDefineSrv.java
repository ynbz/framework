package com.suredy.formbuilder.design.srv;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.GBKOrder;
import com.suredy.formbuilder.design.model.FormDefine;

@Service
public class FormDefineSrv extends BaseSrvWithEntity<FormDefine> {

	public FormDefineSrv() {
		this.addDefOrder(GBKOrder.asc("name"));
		this.defDesc("enable");
		this.defAsc("version");
	}

	@Override
	public DetachedCriteria getDc(FormDefine t, String alias) {
		return this.putClause(null, t, alias);
	}

	@Override
	public DetachedCriteria putClause(DetachedCriteria dc, FormDefine t, String alias) {
		if (dc == null)
			dc = super.getDc(t, alias);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (t.getEnable() != null) {
			dc.add(Restrictions.eq("enable", t.getEnable()));
		}
		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.like("name", t.getName(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(t.getVersion())) {
			dc.add(Restrictions.eq("version", t.getVersion()));
		}
		if (t.getMinCreateTime() != null) {
			dc.add(Restrictions.ge("createTime", t.getMinCreateTime()));
		}
		if (t.getMaxCreateTime() != null) {
			dc.add(Restrictions.le("createTime", t.getMaxCreateTime()));
		}

		return dc;
	}

	public boolean exists(String name, String version, String... excludeId) {
		if (StringUtils.isBlank(name) || StringUtils.isBlank(version))
			return false;

		FormDefine search = new FormDefine();
		search.setVersion(version);

		DetachedCriteria dc = this.getDc(search);
		dc.add(Restrictions.eq("name", name));

		if (excludeId != null && excludeId.length > 0) {
			dc.add(Restrictions.not(Restrictions.in("id", excludeId)));
		}

		int count = this.getCountByCriteria(dc);

		return count > 0;
	}

}
