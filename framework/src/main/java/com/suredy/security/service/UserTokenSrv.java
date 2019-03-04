package com.suredy.security.service;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;
import com.suredy.security.entity.UserTokenEntity;

@Service("UserTokenSrv")
public class UserTokenSrv extends BaseSrvWithEntity<UserTokenEntity> {

	public static Integer DEFAULT_ACTIVE_TIME = 30; // 单位分钟

	@Override
	public DetachedCriteria getDc(UserTokenEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eqOrIsNull("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getUserCode())) {
			dc.add(Restrictions.eq("userCode", t.getUserCode()));
		}
		if (!StringUtils.isBlank(t.getToken())) {
			dc.add(Restrictions.eq("token", t.getToken()));
		}

		if (t.getIsActive() != null) {
			dc.add(Restrictions.eq("isActive", t.getIsActive()));
		}
		return dc;
	}

	public UserTokenEntity getByUserId(String userCode) {
		if (StringUtils.isBlank(userCode)) {
			return null;
		}
		UserTokenEntity search = new UserTokenEntity();
		search.setUserCode(userCode);
		UserTokenEntity po = this.readSingleByEntity(search);
		return po;
	}
	

	public List<UserTokenEntity> getAll() {
		OrderEntity oe = new OrderEntity();
		oe.desc("refreshTime");
		List<UserTokenEntity> ret = this.readByEntity(null, oe);
		return ret;
	}

	public Integer Count() {
		return this.getCountByEntity(null);
	}

	public List<UserTokenEntity> getAll(Integer page, Integer size) {
		OrderEntity oe = new OrderEntity();
		oe.desc("refreshTime");
		List<UserTokenEntity> ret =  this.readPageByEntity(null, page, size, oe);
		return ret;
	}

	private String generate(String userId) {
		String token = null;
		try {
			token = CipherService.AESEncrypt(userId);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	@Transactional
	public UserTokenEntity CreateOrUpdate(String userCode) {
		if (StringUtils.isBlank(userCode)) {
			return null;
		}
		String token = generate(userCode);
		UserTokenEntity entity = this.getByUserId(userCode);
		Calendar calendar = Calendar.getInstance();
		
		if (entity == null) {
			entity = new UserTokenEntity();
			entity.setCreateTime(calendar.getTime());
			entity.setIsActive(1);
			entity.setRefreshTime(calendar.getTime());
			entity.setToken(token);
			entity.setUserCode(userCode);
			calendar.add(Calendar.MINUTE, DEFAULT_ACTIVE_TIME);
			entity.setActiveTime(calendar.getTime());
			this.save(entity);
		} else {
			entity.setIsActive(1);
			entity.setCreateTime(calendar.getTime());
			entity.setRefreshTime(calendar.getTime());
			entity.setToken(token);
			calendar.add(Calendar.MINUTE, DEFAULT_ACTIVE_TIME);
			entity.setActiveTime(calendar.getTime());
			this.update(entity);
		}
		return entity;
	}

}
