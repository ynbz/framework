package com.suredy.formbuilder.eav.srv;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.formbuilder.design.srv.FormDefineSrv;
import com.suredy.formbuilder.eav.model.FormEntry;

/**
 * 表单实体工具类
 * 
 * @author VIVID.G
 * @since 2017-3-2
 * @version v0.1
 */
@Service
public class FormEntrySrv extends BaseSrvWithEntity<FormEntry> {

	@Override
	public DetachedCriteria getDc(FormEntry t, String alias) {
		return this.putClause(null, t, alias);
	}

	@Override
	public DetachedCriteria putClause(DetachedCriteria dc, FormEntry t, String alias) {
		if (dc == null)
			dc = super.getDc(t, alias);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (t.getFormDefine() != null) {
			FormDefineSrv srv = ApplicationContextHelper.getBeanByType(FormDefineSrv.class);
			srv.putClause(dc.createCriteria("formDefine", JoinType.LEFT_OUTER_JOIN), t.getFormDefine());
		}
		if (t.getValues() != null && !t.getValues().isEmpty() && t.getValues().get(0) != null) {
			FormAttributeValueSrv srv = ApplicationContextHelper.getBeanByType(FormAttributeValueSrv.class);
			DetachedCriteria sub = srv.getDc(t.getValues().get(0));
			sub.setProjection(Projections.property("entry.id"));

			dc.add(Subqueries.propertyIn("values", sub));
		}

		return dc;
	}

}
