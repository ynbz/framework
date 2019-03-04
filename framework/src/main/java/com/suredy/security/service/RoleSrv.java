package com.suredy.security.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.RoleEntity;
import com.suredy.security.model.Role;

@Service("RoleSrv")
public class RoleSrv extends BaseSrvWithEntity<RoleEntity> {
	@Override
	public DetachedCriteria getDc(RoleEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		
		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.eq("name", t.getName()));
		}
		
		if (!StringUtils.isBlank(t.getUniqueCode())) {
			dc.add(Restrictions.eq("uniqueCode", t.getUniqueCode()));
		}

		if (!StringUtils.isBlank(t.getCode())) {
			dc.add(Restrictions.eq("code", t.getCode()));
		}

		return dc;
	}
	
	/**
	 * 获取角色树
	 * 
	 * @param orgId 公司id，可以为空
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Tree> getTree(String orgId) {
		String ql = "SELECT T FROM OrgEntity T  WHERE T.buildIn=0 ";

		if (!StringUtils.isBlank(orgId)) {
			ql += " AND T.id = '" + orgId + "'";
		}
		ql += " ORDER BY T.sort, CONVERT_GBK(T.name)";
		List<OrgEntity> data = (List<OrgEntity>) this.readByQL(ql);

		if (data == null || data.isEmpty())
			return null;

		List<Tree> ret = new ArrayList<Tree>();

		for (OrgEntity org : data) {
			Tree tree = this.makeTree(org);

			if (tree == null)
				continue;

			ret.add(tree);
		}

		return ret.isEmpty() ? null : ret;
	}

	private Tree makeTree(OrgEntity org) {
		if (org == null)
			return null;

		Tree node = new Tree();
		node.setCollapse(false); // 单位默认展开
		node.setText(org.getName());
		node.setData(org.toVO());
		node.setChildren(new ArrayList<Tree>());

		String ql = "SELECT T FROM RoleEntity T WHERE T.buildIn=0 AND T.org='" + org.getId() + "'";
		ql += " ORDER BY T.sort, CONVERT_GBK(T.name)";

		@SuppressWarnings("unchecked")
		List<RoleEntity> data = (List<RoleEntity>) this.readByQL(ql);
		// 构建角色
		if (data != null && !data.isEmpty()) {
			for (RoleEntity role : data) {
				Tree tmp = new Tree();
				tmp.setText(role.getName());
				tmp.setData(role.toVO());
				node.getChildren().add(tmp);
			}
		}

		return node;
	}
	
	public Long Count() {
		String ql = "SELECT COUNT(*) FROM RoleEntity T WHERE T.buildIn=0";
		Object ret = this.readSingleByQL(ql);
		return (Long) ret;
	}
	
	public List<Role> getAll() {
		String ql = "SELECT T FROM RoleEntity T WHERE T.buildIn=0";
		@SuppressWarnings("unchecked")
		List<RoleEntity> pos = (List<RoleEntity>) this.readByQL(ql);
		List<Role> ret = new ArrayList<Role>();
		for (RoleEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}
	
	public List<Role> getAll(Integer page, Integer size) {
		String ql = "SELECT T FROM RoleEntity T WHERE T.buildIn=0";
		Object[] paras = null;
		@SuppressWarnings("unchecked")
		List<RoleEntity> pos = (List<RoleEntity>) this.readPageByQL(ql, page, size, paras);
		List<Role> ret = new ArrayList<Role>();
		for (RoleEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}

	public Role getById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return this.get(id).toVO();
	}

	public Role getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		RoleEntity search = new RoleEntity();
		search.setName(name);
		RoleEntity ret = this.readSingleByEntity(search);
		if (ret == null) {
			return null;
		}
		return ret.toVO();
	}
	
	public Role getByCode(String uniqueCode) {
		if (StringUtils.isBlank(uniqueCode)) {
			return null;
		}
		RoleEntity search = new RoleEntity();
		search.setUniqueCode(uniqueCode);
		RoleEntity ret = this.readSingleByEntity(search);
		if (ret == null) {
			return null;
		}
		return ret.toVO();
	}
}
