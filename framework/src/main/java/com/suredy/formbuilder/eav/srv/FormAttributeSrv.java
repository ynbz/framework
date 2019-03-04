package com.suredy.formbuilder.eav.srv;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.formbuilder.eav.model.FormAttribute;

/**
 * 表单属性工具类
 * 
 * @author VIVID.G
 * @since 2017-3-2
 * @version v0.1
 */
@Service
public class FormAttributeSrv extends BaseSrvWithEntity<FormAttribute> {

	@Override
	public DetachedCriteria getDc(FormAttribute t, String alias) {
		return this.putClause(null, t, alias);
	}

	@Override
	public DetachedCriteria putClause(DetachedCriteria dc, FormAttribute t, String alias) {
		if (dc == null)
			dc = super.getDc(t, alias);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.eq("name", t.getName()));
		}
		if (t.getMetadata() != null) {
			FormAttributeMetadataSrv srv = ApplicationContextHelper.getBeanByType(FormAttributeMetadataSrv.class);
			srv.putClause(dc.createCriteria("metadata"), t.getMetadata());
		}
		if (t.getValue() != null) {
			FormAttributeValueSrv srv = ApplicationContextHelper.getBeanByType(FormAttributeValueSrv.class);
			srv.putClause(dc.createCriteria("value"), t.getValue());
		}

		return dc;
	}

}
