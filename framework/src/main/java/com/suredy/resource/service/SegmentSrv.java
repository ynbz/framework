/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年9月21日
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
import com.suredy.resource.entity.SegmentEntity;


/**
 * @author ZhangMaoren
 *
 */
@Service("SegmentSrv")
public class SegmentSrv extends BaseSrvWithEntity<SegmentEntity> {

	public DetachedCriteria getDc(SegmentEntity t) {
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

		return dc;
	}
	
	public List<SegmentEntity> getAll() {
		OrderEntity oe = new OrderEntity();
		oe.desc("sort");
		List<SegmentEntity> pos = this.readByEntity(null, oe);
		if (pos == null || pos.isEmpty()) {
			return null;
		}
		return pos;
	}

	public SegmentEntity getById(String segementId) {
		return this.get(segementId);
	}
	
	public SegmentEntity getByUri(String uri) {
		SegmentEntity search = new SegmentEntity();
		search.setUri(uri);
		SegmentEntity entity = this.readSingleByEntity(search);
		return entity;
	}

}
