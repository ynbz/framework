package com.suredy.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.GBKOrder;
import com.suredy.core.service.OrderEntity;
import com.suredy.security.entity.PermissionEntity;
import com.suredy.security.entity.UnitEntity;
import com.suredy.security.entity.UserEntity;
import com.suredy.security.model.User;

@Service("UserSrv")
public class UserSrv extends BaseSrvWithEntity<UserEntity> {

	public UserSrv() {
		this.defAsc("sort");
		this.addDefOrder(GBKOrder.asc("name"));
	}

	@Override
	public DetachedCriteria getDc(UserEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eqOrIsNull("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getUniqueCode())) {
			dc.add(Restrictions.eq("uniqueCode", t.getUniqueCode()));
		}
		if (!StringUtils.isBlank(t.getPassword())) {
			dc.add(Restrictions.eq("password", t.getPassword()));
		}
		if (t.getAvailable() != null) {
			dc.add(Restrictions.eq("available", t.getAvailable()));
		}
		if (t.getUnit() != null) {
			dc.add(Restrictions.eq("unit", t.getUnit()));
		}
		if (t.getIsLongUser() != null) {
			dc.add(Restrictions.eq("isLongUser", t.getIsLongUser()));
		}
		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.like("name", t.getName(), MatchMode.ANYWHERE));
		}
		
		return dc;
	}

	public User getById(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		UserEntity po = this.get(userId);
		return po.toVO();
	}
	
	public User getUserByPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return null;
		}
		UserEntity search = new UserEntity();
		search.setUserphone(phone);
		UserEntity po = this.readSingleByEntity(search);
		if (po == null) {
			return null;
		}
		return po.toVO();
	}

	public List<User> getAll() {
		UserEntity search = new UserEntity();
		search.setAvailable(1);
		OrderEntity oe = new OrderEntity();
		oe.desc("unit");
		List<UserEntity> pos = this.readByEntity(search, oe);
		List<User> ret = new ArrayList<User>();
		for (UserEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}

	public List<User> getAll(Integer page, Integer size) {
		UserEntity search = new UserEntity();
		search.setAvailable(1);
		OrderEntity oe = new OrderEntity();
		oe.desc("sort");
		List<UserEntity> pos = this.readPageByEntity(search, page, size, oe);
		List<User> ret = new ArrayList<User>();
		for (UserEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}		
	
	@SuppressWarnings("unchecked")
	public List<User> getByFilter(Integer page, Integer size, String unitId, String keyword) {
		List<User> ret = new ArrayList<User>();
		UserEntity search = new UserEntity();
		search.setAvailable(1);
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("unit"));
		dc.addOrder(Order.asc("sort"));
		List<UserEntity> pos = new ArrayList<UserEntity>();
		if (!StringUtils.isEmpty(keyword)) {
			//OR
			dc.add(Restrictions.or(Restrictions.like("name", keyword,MatchMode.ANYWHERE).ignoreCase(), Restrictions.like("shortPinyin", keyword,MatchMode.ANYWHERE).ignoreCase()));
			//多条件OR
            //Disjunction disjunction = Restrictions.disjunction();  
            //disjunction.add(Restrictions.like("employeeId", keyword,MatchMode.ANYWHERE).ignoreCase());  
            //disjunction.add(Restrictions.like("userId", keyword, MatchMode.ANYWHERE).ignoreCase());  
            //disjunction.add(Restrictions.like("shortPinyin", keyword, MatchMode.ANYWHERE).ignoreCase());     
            //dc.add(disjunction);  
            pos =(List<UserEntity>) this.readPageByCriteria(dc, page, size);
		} else if (!StringUtils.isEmpty(unitId)) {			
			UnitEntity unit = new UnitEntity();
			unit.setId(unitId);
			dc.add(Restrictions.eq("unit", unit));
			pos =(List<UserEntity>) this.readPageByCriteria(dc, page, size);
		}
		
		for (UserEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
		
	}
		
	public Integer CountByFilter(String unitId, String keyword){
		UserEntity search = new UserEntity();
		search.setAvailable(1);
		DetachedCriteria dc = this.getDc(search);
		int ret = 0;
		if (!StringUtils.isEmpty(keyword)) {
			//OR
			dc.add(Restrictions.or(Restrictions.like("name", keyword,MatchMode.ANYWHERE).ignoreCase(), Restrictions.like("shortPinyin", keyword,MatchMode.ANYWHERE).ignoreCase()));
			//多条件OR
            //Disjunction disjunction = Restrictions.disjunction();  
            //disjunction.add(Restrictions.like("employeeId", keyword,MatchMode.ANYWHERE).ignoreCase());  
            //disjunction.add(Restrictions.like("userId", keyword, MatchMode.ANYWHERE).ignoreCase());  
            //disjunction.add(Restrictions.like("shortPinyin", keyword, MatchMode.ANYWHERE).ignoreCase());     
            //dc.add(disjunction);  
			ret = this.getCountByCriteria(dc);
			
		} else if (!StringUtils.isEmpty(unitId)) {
			UnitEntity unit = new UnitEntity();
			unit.setId(unitId);
			dc.add(Restrictions.eq("unit", unit));
			ret = this.getCountByCriteria(dc);
		}
		return ret;
	}

	public User getUser(String uniqueCode, String password) {
		if (StringUtils.isBlank(uniqueCode)) {
			return null;
		}
		UserEntity search = new UserEntity();
		search.setUniqueCode(uniqueCode);
		search.setPassword(password);

		UserEntity po = this.readSingleByEntity(search);
		if (po == null) {
			return null;
		}
		return po.toVO();
	}

	public User getUser(String uniqueCode) {
		if (StringUtils.isBlank(uniqueCode)) {
			return null;
		}
		UserEntity search = new UserEntity();
		search.setUniqueCode(uniqueCode);
		UserEntity po = this.readSingleByEntity(search);
		if (po == null) {
			return null;
		}
		return po.toVO();
	}


	public List<String> getPermissions(String userCode) {
		List<String> permissions = new ArrayList<String>();
		permissions.addAll(getRolePermissions(userCode));
		permissions.addAll(getUserPermissions(userCode));
		permissions.addAll(getBasicPermissions());
		return permissions;
	}

	public List<String> getRolePermissions(String userCode) {
		final String sql = "SELECT p.ID, p.ACTION, p.RESOURCEID " 
				+ "FROM T_E_YH u LEFT JOIN T_E_QZYH ur ON (u.ID_E_YH=ur.ID_E_YH) " 
				+ "LEFT JOIN T_E_QZ r ON (ur.ID_E_QZ=r.ID_E_QZ) " 
				+ "JOIN T_SECURITY_ROLE2PERMISSION rp ON (r.ID_E_QZ=rp.ROLEID) " 
				+ "JOIN T_SECURITY_PERMISSION p ON (rp.PERMISSIONID=p.ID) " 
				+ "WHERE u.WYMC=?";
		List<PermissionEntity> result = super.readWithClassBySQL(sql, PermissionEntity.class, userCode);
		List<String> ret = new ArrayList<String>();
		for (PermissionEntity p : result) {
			ret.add(p.getResource().getUri());
		}
		return ret;
	}

	public List<String> getUserPermissions(String userCode) {
		final String sql = "SELECT p.ID, p.ACTION, p.RESOURCEID  " 
				+ "FROM T_E_YH u LEFT JOIN T_SECURITY_USER2PERMISSION up ON (u.ID_E_YH=up.USERID) " 
				+ "JOIN T_SECURITY_PERMISSION p ON (up.PERMISSIONID=p.ID) " 
				+ "WHERE u.WYMC=?";
		List<PermissionEntity> result = super.readWithClassBySQL(sql, PermissionEntity.class, userCode);
		List<String> ret = new ArrayList<String>();
		for (PermissionEntity p : result) {
			ret.add(p.getResource().getUri());
		}
		return ret;
	}
	
	
	public List<String> getBasicPermissions() {
		final String sql = "SELECT p.ID, p.ACTION, p.RESOURCEID " 
				+ "FROM T_SECURITY_BASICPERMISSION b JOIN T_SECURITY_PERMISSION p ON (b.PERMISSIONID=p.ID)" ;
		List<PermissionEntity> result = super.readWithClassBySQL(sql, PermissionEntity.class);
		List<String> ret = new ArrayList<String>();
		for (PermissionEntity p : result) {
			ret.add(p.getResource().getUri());
		}
		return ret;
	}

	public int updateUserPswd(String id, String oldPswd, String newPswd) {
		String ql = "UPDATE UserEntity SET password = ? where id = ? and password = ?";

		int count = this.updateByQL(ql, newPswd, id, oldPswd);

		return count;
	}
}
