/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月15日
 * @version 0.1
 */
package com.suredy.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.resource.entity.ResourceEntity;
import com.suredy.security.entity.PermissionEntity;
import com.suredy.security.model.Permission;



/**
 * @author ZhangMaoren
 *
 */
@Service("PermissionSrv")
public class PermissionSrv extends BaseSrvWithEntity<PermissionEntity> {
	@Override
	public DetachedCriteria getDc(PermissionEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		
		if (t.getResource() != null) {
			dc.add(Restrictions.eq("resource", t.getResource()));
		}
		
		
		if (t.getAction() != null) {
			dc.add(Restrictions.eq("action", t.getAction()));
		}

		return dc;
	}
	
	public List<Permission> getAll() {
		List<PermissionEntity> pos = this.readByEntity(null);
		
		List<Permission> ret = new ArrayList<Permission>();
		for (PermissionEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}
	
	
	public PermissionEntity GetOrCreate(String resourceId, Integer action ) {
		PermissionEntity search = new PermissionEntity();
		search.setAction(action);
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		search.setResource(resource);
		PermissionEntity p = this.readSingleByEntity(search);
		if (p == null) {
			this.save(search);
			p = search;
		} 
		return p;
	}
	
	public List<Permission> getById(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			return null;
		}
		PermissionEntity search = new PermissionEntity();
		ResourceEntity resource = new ResourceEntity();
		resource.setId(resourceId);
		search.setResource(resource);
		List<PermissionEntity> pos = this.readByEntity(search);
		List<Permission> ret = new ArrayList<Permission>(); 
		for (PermissionEntity po : pos) {
			ret.add(po.toVO());
		}
		
		return ret;
	}
	
	
}
