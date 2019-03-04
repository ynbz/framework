/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月17日
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
import com.suredy.security.entity.PermissionEntity;
import com.suredy.security.entity.Role2PermissionEntity;
import com.suredy.security.entity.RoleEntity;
import com.suredy.security.model.Role2Permission;


/**
 * @author ZhangMaoren
 *
 */
@Service("Role2PermissionSrv")
public class Role2PermissionSrv extends BaseSrvWithEntity<Role2PermissionEntity>{
	@Override
	public DetachedCriteria getDc(Role2PermissionEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		
		if (t.getRole() != null) {
			dc.add(Restrictions.eq("role", t.getRole()));
		}
		
		if (t.getPermission() != null) {
			dc.add(Restrictions.eq("permission", t.getPermission()));
		}

		return dc;
	}
	
	public List<Role2Permission> getAll() {
		List<Role2PermissionEntity> pos = this.readByEntity(null);
		List<Role2Permission> ret = new ArrayList<Role2Permission>();
		for (Role2PermissionEntity po : pos ) {
			ret.add(po.toVO());
		}
		return ret;
	}

	public Role2Permission getById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return this.get(id).toVO();
	}
	
	public List<Role2PermissionEntity> getByRole(String roleId) {
		 
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		List<Role2PermissionEntity> pos = new ArrayList<Role2PermissionEntity>();
		Role2PermissionEntity search = new Role2PermissionEntity();
		RoleEntity role = new RoleEntity();
		role.setId(roleId);
		search.setRole(role);
		pos = this.readByEntity(search);
		
		return pos;
	}
	
	public List<Role2PermissionEntity> getByPermission(String permissionId) {
		 
		if (StringUtils.isBlank(permissionId)) {
			return null;
		}
		List<Role2PermissionEntity> pos = new ArrayList<Role2PermissionEntity>();
		Role2PermissionEntity search = new Role2PermissionEntity();
		PermissionEntity permission = new PermissionEntity();
		permission.setId(permissionId);
		search.setPermission(permission);
		pos = this.readByEntity(search);
		
		return pos;
	}

}
