package com.suredy.app.files.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.files.entity.FolderLogEntity;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

@Service("FolderLogSrv")
public class FolderLogSrv extends BaseSrvWithEntity<FolderLogEntity> {
	@Override
	public DetachedCriteria getDc(FolderLogEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (t.getFolderId() != null) {
			dc.add(Restrictions.eq("folderId", t.getFolderId()));
		}
		return dc;
	}
	
	public List<FolderLogEntity> getLogList(String folderId) {
		if (StringUtils.isEmpty(folderId)) {
			return null;
		} else {
			FolderLogEntity search = new FolderLogEntity();
			search.setFolderId(folderId);
			OrderEntity oe = new OrderEntity();
			oe.add("logDate", false);
			List<FolderLogEntity> data = this.readByEntity(search, oe);
			return data;
		}
	}
	
}
