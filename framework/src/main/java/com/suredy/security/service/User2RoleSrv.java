/**
 * Copyright (c) 2014-2015, Suredy technology Co., Ltd. All rights reserved.
 * @author ZhangMaoren 
 * @since 2015年6月19日
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
import com.suredy.security.entity.RoleEntity;
import com.suredy.security.entity.User2RoleEntity;
import com.suredy.security.entity.UserEntity;
import com.suredy.security.model.User2Role;


/**
 * @author ZhangMaoren
 *
 */
@Service("User2RoleSrv")
public class User2RoleSrv extends BaseSrvWithEntity<User2RoleEntity>{
	@Override
	public DetachedCriteria getDc(User2RoleEntity t) {
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
		if (t.getUser() != null) {
			dc.add(Restrictions.eq("user", t.getUser()));
		}
		

		return dc;
	}
	
	public List<User2Role> getAll() {
		List<User2RoleEntity> pos = this.readByEntity(null);
		List<User2Role> ret = new ArrayList<User2Role>();
		for (User2RoleEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}

	public User2Role getById(String id) {
		if (StringUtils.isBlank(id) ) {
			return null;
		}
		return this.get(id).toVO();
	}
	
	public List<User2RoleEntity> getByRole(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		List<User2RoleEntity> pos = new ArrayList<User2RoleEntity>();
		User2RoleEntity search = new User2RoleEntity();
		RoleEntity role = new RoleEntity();
		role.setId(roleId);
		search.setRole(role);
		pos = this.readByEntity(search);
		return pos;
	}
	
	public List<User2RoleEntity> getByUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		List<User2RoleEntity> pos = new ArrayList<User2RoleEntity>(); 
		User2RoleEntity search = new User2RoleEntity();
		UserEntity user = new UserEntity();
		user.setId(userId);
		search.setUser(user);
		pos = this.readByEntity(search);
		return pos;
	}

}
