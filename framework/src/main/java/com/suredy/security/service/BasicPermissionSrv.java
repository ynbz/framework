package com.suredy.security.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.security.entity.BasicPermissionEntity;


/**
 * @author SDKJ
 *
 */
@Service("BasicPermissionSrv")
public class BasicPermissionSrv extends BaseSrvWithEntity<BasicPermissionEntity>{
	@Override
	public DetachedCriteria getDc(BasicPermissionEntity t) {
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

		return dc;
	}
	
	public List<BasicPermissionEntity> getAll(){
		List<BasicPermissionEntity> pos = this.readByEntity(null);
		
		return pos!=null?pos:null;
	}

}
