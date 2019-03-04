/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月23日
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
import com.suredy.security.entity.UserEntity;
import com.suredy.security.entity.User2PermissionEntity;
import com.suredy.security.model.User2Permission;


/**
 * @author ZhangMaoren
 *
 */
@Service("User2PermissionSrv")
public class User2PermissionSrv extends BaseSrvWithEntity<User2PermissionEntity>{
	@Override
	public DetachedCriteria getDc(User2PermissionEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		
		if (t.getPermission() != null) {
			dc.add(Restrictions.eq("permission", t.getPermission()));
		}
		if (t.getUser() != null) {
			dc.add(Restrictions.eq("user", t.getUser()));
		}

		return dc;
	}
	
	public List<User2Permission> getAll() {
		List<User2PermissionEntity> pos = this.readByEntity(null);
		List<User2Permission> ret = new ArrayList<User2Permission>();
		for (User2PermissionEntity po :pos) {
			ret.add(po.toVO());
			
		}
		return ret;
	}

	public User2Permission getById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return this.get(id).toVO();
	}
	
	public List<User2Permission> getByPermission(String permissionId) {
		
		if (StringUtils.isBlank(permissionId)) {
			return null;
		}
		List<User2PermissionEntity> pos = new ArrayList<User2PermissionEntity>(); 
		User2PermissionEntity search = new User2PermissionEntity();
		PermissionEntity permission = new PermissionEntity();
		permission.setId(permissionId);
		search.setPermission(permission);
		pos = this.readByEntity(search);
		List<User2Permission> ret = new ArrayList<User2Permission>();
		for (User2PermissionEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}
	
	public List<User2PermissionEntity> getByUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
	
		User2PermissionEntity search = new User2PermissionEntity();
		UserEntity user = new UserEntity();
		user.setId(userId);
		search.setUser(user);
		List<User2PermissionEntity> pos = this.readByEntity(search);	
		return pos;
	}

}
