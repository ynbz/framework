package com.suredy.app.files.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.files.entity.FolderPermissionEntity;
import com.suredy.core.service.BaseSrvWithEntity;

@Service("FolderPermissionSrv")
public class FolderPermissionSrv extends BaseSrvWithEntity<FolderPermissionEntity> {

	@Override
	public DetachedCriteria getDc(FolderPermissionEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}

		if (t.getFolder() != null) {
			dc.add(Restrictions.eq("folder", t.getFolder()));
		}

		if (t.getSubject() != null) {
			dc.add(Restrictions.eq("subject", t.getSubject()));
		}

		if (t.getSubjectType() != null) {
			dc.add(Restrictions.eq("subjectType", t.getSubjectType()));
		}
		return dc;
	}
}
