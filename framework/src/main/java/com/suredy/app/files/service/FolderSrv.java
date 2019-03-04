package com.suredy.app.files.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.files.entity.FolderEntity;
import com.suredy.core.service.BaseSrvWithEntity;

@Service("FolderSrv")
public class FolderSrv extends BaseSrvWithEntity<FolderEntity> {

	@Override
	public DetachedCriteria getDc(FolderEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (t.getIsPublic() != null) {
			dc.add(Restrictions.eq("isPublic", t.getIsPublic()));
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (t.getParent() != null) {
			dc.add(Restrictions.eq("parent", t.getParent()));
		}
		return dc;
	}
	



	// -----------------------------added by Zhangmaoren--------------------------
	@Transactional
	public List<FolderEntity> getFileTypes(@Nullable String folderId) {
		if (StringUtils.isEmpty(folderId)) {
			return null;
		} else {
			FolderEntity search = new FolderEntity();
			search.setId(folderId);
			FolderEntity root = this.readSingleByEntity(search);
			List<FolderEntity> data = getSubTypes(root, new ArrayList<FolderEntity>());

			return data.isEmpty() ? null : data;
		}

	}

	private List<FolderEntity> getSubTypes(FolderEntity entity, List<FolderEntity> data) {
		if (entity == null) {
			return null;
		}
		data.add(entity);
		List<FolderEntity> children = entity.getChildren();
		if (children != null && !children.isEmpty()) {
			for (FolderEntity child : children) {
				getSubTypes(child, data);
			}
		}
		return data;
	}

	// -----------------------------------end--------------------------------------

}
