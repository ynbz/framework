/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @author ZhangMaoren
 * @since 2015年4月13日
 * @version 0.1
 */
package com.suredy.resource.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;
import com.suredy.resource.entity.ResourceEntity;
import com.suredy.resource.model.ResourceType;

/**
 * @author ZhangMaoren
 *
 */
@Service("ResourceSrv")
public class ResourceSrv extends BaseSrvWithEntity<ResourceEntity> {
	@Override
	public DetachedCriteria getDc(ResourceEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		
		if (!StringUtils.isBlank(t.getUri())) {
			dc.add(Restrictions.eq("uri", t.getUri()));
		}
		
		if (t.getType() != null) {
			dc.add(Restrictions.eq("type", t.getType()));
		}

		return dc;
	}
	
	public List<ResourceEntity> getAll() {
		OrderEntity oe = new OrderEntity();
		oe.desc("createTime");
		List<ResourceEntity> pos = this.readByEntity(null, oe);
		if (pos == null || pos.isEmpty()) {
			return null;
		}
		return pos;
	}
	
	public List<ResourceEntity> getAll(ResourceType type) {
		OrderEntity oe = new OrderEntity();
		oe.desc("createTime");
		ResourceEntity search = new ResourceEntity();
		search.setType(type.getType());
		List<ResourceEntity> pos = this.readByEntity(search, oe);
		if (pos == null || pos.isEmpty()) {
			return null;
		}
		return pos;
	}

	public ResourceEntity getById(String resourceId) {
		return this.get(resourceId);
	}

	public ResourceEntity getByUrl(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		ResourceEntity search = new ResourceEntity();
		search.setUri(url);
		return this.readSingleByEntity(search);

	}


}
