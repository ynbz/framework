package com.suredy.formbuilder.eav.srv;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.formbuilder.eav.model.FormAttributeMetadata;

/**
 * 表单属性元信息工具类
 * 
 * @author VIVID.G
 * @since 2017-3-2
 * @version v0.1
 */
@Service
public class FormAttributeMetadataSrv extends BaseSrvWithEntity<FormAttributeMetadata> {

	@Override
	public DetachedCriteria getDc(FormAttributeMetadata t, String alias) {
		return this.putClause(null, t, alias);
	}

	@Override
	public DetachedCriteria putClause(DetachedCriteria dc, FormAttributeMetadata t, String alias) {
		if (dc == null)
			dc = super.getDc(t, alias);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (t.getDataType() != null) {
			dc.add(Restrictions.eq("dataType", t.getDataType()));
		}
		if (t.getRequired() != null) {
			dc.add(Restrictions.eq("required", t.getRequired()));
		}
		if (t.getSearchable() != null) {
			dc.add(Restrictions.eq("searchable", t.getSearchable()));
		}
		if (t.getAttribute() != null) {
			FormAttributeSrv srv = ApplicationContextHelper.getBeanByType(FormAttributeSrv.class);
			srv.putClause(dc.createCriteria("attribute"), t.getAttribute(), alias);
		}

		return dc;
	}

}
