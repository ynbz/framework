package com.suredy.formbuilder.eav.srv;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.formbuilder.eav.model.FormAttributeValue;

/**
 * 表单属性值工具类
 * 
 * @author VIVID.G
 * @since 2017-3-2
 * @version v0.1
 */
@Service
public class FormAttributeValueSrv extends BaseSrvWithEntity<FormAttributeValue> {

	@Override
	public DetachedCriteria getDc(FormAttributeValue t, String alias) {
		return this.putClause(null, t, alias);
	}

	@Override
	public DetachedCriteria putClause(DetachedCriteria dc, FormAttributeValue t, String alias) {
		if (dc == null)
			dc = super.getDc(t, alias);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (t.getEntry() != null) {
			FormEntrySrv srv = ApplicationContextHelper.getBeanByType(FormEntrySrv.class);
			srv.putClause(dc.createCriteria("entry", JoinType.LEFT_OUTER_JOIN), t.getEntry());
		}
		if (t.getAttribute() != null) {
			FormAttributeSrv srv = ApplicationContextHelper.getBeanByType(FormAttributeSrv.class);
			srv.putClause(dc.createCriteria("attribute"), t.getAttribute());
		}

		return dc;
	}

}
