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
import com.suredy.core.service.GBKOrder;
import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.UnitEntity;
import com.suredy.security.entity.UserEntity;
import com.suredy.security.model.Org;

@Service("OrgSrv")
public class OrgSrv extends BaseSrvWithEntity<OrgEntity> {

	public OrgSrv() {
		this.addAnDefOrder("sort", true);
		this.addDefOrder(GBKOrder.asc("name"));
	}

	@Override
	public DetachedCriteria getDc(OrgEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId()))
			dc.add(Restrictions.eq("id", t.getId()));

		if (!StringUtils.isBlank(t.getName()))
			dc.add(Restrictions.eq("name", t.getName()));

		if (!StringUtils.isBlank(t.getUniqueCode()))
			dc.add(Restrictions.eq("uniqueCode", t.getUniqueCode()));

		if (t.getBuildIn() != null)
			dc.add(Restrictions.eq("buildIn", t.getBuildIn()));

		if (t.getAvailable() != null)
			dc.add(Restrictions.eq("available", t.getAvailable()));

		return dc;
	}

	public List<Org> getAll() {
		String ql = "SELECT T FROM OrgEntity T WHERE T.buildIn=0";
		@SuppressWarnings("unchecked")
		List<OrgEntity> pos = (List<OrgEntity>) this.readByQL(ql);
		List<Org> ret = new ArrayList<Org>();
		for (OrgEntity po : pos) {
			ret.add(po.toVO());
		}
		return ret;
	}

	public Org getById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return this.get(id).toVO();
	}

	public Org getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		OrgEntity search = new OrgEntity();
		search.setName(name);
		OrgEntity ret = this.readSingleByEntity(search);
		if (ret == null) {
			return null;
		}
		return ret.toVO();
	}

	public Org getByCode(String uniqueCode) {
		if (StringUtils.isBlank(uniqueCode)) {
			return null;
		}
		OrgEntity search = new OrgEntity();
		search.setUniqueCode(uniqueCode);
		OrgEntity ret = this.readSingleByEntity(search);
		if (ret == null) {
			return null;
		}
		return ret.toVO();
	}

	/**
	 * 获取组织机构树
	 * 
	 * @param orgId 公司id，可以为空
	 * @param withUser 是否加载用户
	 * @param excludeNoUserNode 是否排除没有用户的部门
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Tree> getTree(String orgId, boolean withUser, boolean excludeNoUserNode) {
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
			Tree tree = this.makeTree(org, withUser, excludeNoUserNode);

			if (tree == null)
				continue;

			ret.add(tree);
		}

		return ret.isEmpty() ? null : ret;
	}

	private Tree makeTree(OrgEntity org, boolean withUser, boolean excludeNoUserNode) {
		if (org == null)
			return null;

		Tree node = new Tree();
		node.setCollapse(false); // 单位默认展开
		node.setText(org.getName());
		node.setData(org.toVO());
		node.setChildren(new ArrayList<Tree>());

		String ql = "SELECT T FROM UnitEntity T WHERE T.org='" + org.getId() + "' AND T.parent=null";
		ql += " ORDER BY T.sort, CONVERT_GBK(T.name)";

		@SuppressWarnings("unchecked")
		List<UnitEntity> data = (List<UnitEntity>) this.readByQL(ql);
		// 构建单位
		if (data != null && !data.isEmpty()) {
			for (UnitEntity unit : data) {
				Tree tmp = this.makeTree(unit, withUser, excludeNoUserNode);

				if (tmp == null)
					continue;

				node.getChildren().add(tmp);
			}
		}

		if (excludeNoUserNode && node.getChildren().isEmpty())
			return null;

		return node;
	}

	private Tree makeTree(UnitEntity unit, boolean withUser, boolean excludeNoUserNode) {
		if (unit == null)
			return null;

		// 排除没有用户的节点
		if (excludeNoUserNode) {
			boolean noChildrenUnit = unit.getChildren() == null || unit.getChildren().isEmpty();
			boolean noUser = unit.getAssociationUsers() == null || unit.getAssociationUsers().isEmpty();

			if (noChildrenUnit && noUser)
				return null;
		}

		Tree node = new Tree();
		node.setCollapse(true); // 部门默认折叠
		node.setText(unit.getName());
		node.setData(unit.toVO());
		node.setChildren(new ArrayList<Tree>());

		// 构建单位
		if (unit.getChildren() != null) {
			for (UnitEntity u : unit.getChildren()) {
				Tree tmp = this.makeTree(u, withUser, excludeNoUserNode);

				if (tmp == null)
					continue;

				node.getChildren().add(tmp);
			}
		}

		// 构建人员
		if (withUser && unit.getAssociationUsers() != null) {
			for (UserEntity user : unit.getAssociationUsers()) {
				Tree tmp = this.makeTree(user);

				if (tmp == null)
					continue;

				node.getChildren().add(tmp);
			}
		}

		return node;
	}

	private Tree makeTree(UserEntity user) {
		if (user == null)
			return null;
		if (user.getAvailable() == 0)
			return null;
		Tree node = new Tree();
		node.setText(user.getName());
		node.setData(user.toVO());

		return node;
	}

}
