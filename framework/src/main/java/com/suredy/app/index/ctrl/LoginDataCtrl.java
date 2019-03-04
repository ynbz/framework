package com.suredy.app.index.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.GBKOrder;
import com.suredy.security.entity.OrgEntity;
import com.suredy.security.entity.UnitEntity;
import com.suredy.security.entity.UserEntity;
import com.suredy.security.service.OrgSrv;
import com.suredy.security.service.UnitSrv;
import com.suredy.security.service.UserSrv;

@Controller
@RequestMapping("/login-data")
@Transactional
public class LoginDataCtrl extends BaseCtrl {

	@Autowired
	private OrgSrv orgSrv;

	@Autowired
	private UnitSrv unitSrv;

	@Autowired
	UserSrv userSrv;

	/**
	 * 异步加载单位节点
	 * 
	 * @param loadUser 加载用户
	 * @param loginUserOnly 只显示登陆人员
	 * @param forConfig 显示配置相关信息（目前只显示序号）
	 * @return
	 */
	@RequestMapping("/asyn-org/{loadUser}/{loginUserOnly}")
	@ResponseBody
	public List<Tree> asynOrgNodes(@PathVariable boolean loadUser, @PathVariable boolean loginUserOnly, Boolean forConfig) {
		OrgEntity search = new OrgEntity();
		search.setBuildIn(0); // 非系统内建
		search.setAvailable(1); // 有效

		List<Tree> ret = new ArrayList<Tree>();

		// 使用默认排序查询
		List<OrgEntity> data = this.orgSrv.readByEntity(search);

		if (data == null || data.isEmpty())
			return ret;

		for (OrgEntity org : data) {
			Tree t = new Tree();

			t.setActive(null);
			t.setChecked(null);
			t.setChildren(new ArrayList<Tree>());
			t.setCollapse(data.size() > 1);
			t.setData(org.toVO());
			t.setIcon(null);
			t.setLoadDataFrom(this.getContextPath() + "/login-data/asyn-unit/" + (loadUser ? 1 : 0) + "/" + (loginUserOnly ? 1 : 0) + "?orgId={id}" + (Boolean.TRUE.equals(forConfig) ? "&forConfig=1" : ""));
			t.setText((Boolean.TRUE.equals(forConfig) ? "(" + org.getSort() + ")" : "") + org.getName());

			ret.add(t);
		}

		return ret;
	}

	/**
	 * 异步加载unit节点
	 * 
	 * @param loadUser 加载用户
	 * @param loginUserOnly 只显示登陆人员
	 * @param orgId 父节点ID。父节点是org时，需要传此值
	 * @param pId 父节点ID。父节点是unit时，需要传此值
	 * @param forConfig 显示配置相关信息（目前只显示序号）
	 * @return
	 */
	@RequestMapping("/asyn-unit/{loadUser}/{loginUserOnly}")
	@ResponseBody
	public List<Tree> asynUnitNodes(@PathVariable boolean loadUser, @PathVariable boolean loginUserOnly, String orgId, String pId, Boolean forConfig) {
		List<Tree> ret = new ArrayList<Tree>();

		String uri = this.getContextPath() + "/login-data/asyn-unit/" + (loadUser ? 1 : 0) + "/" + (loginUserOnly ? 1 : 0) + "?pId={id}";
		uri += Boolean.TRUE.equals(forConfig) ? "&forConfig=1" : "";

		List<Tree> unit = this.unitTreeOf(orgId, pId, uri, forConfig);

		if (unit != null)
			ret.addAll(unit);

		if (!loadUser || StringUtils.isBlank(pId))
			return ret;

		List<Tree> user = this.userTreeOf(pId, loginUserOnly, forConfig);

		if (user != null)
			ret.addAll(user);

		return ret;
	}

	/**
	 * 获取树子节点（unit）
	 * 
	 * @param orgId 父节点ID。父节点是org时，需要传此值
	 * @param pId 父节点ID。父节点是unit时，需要传此值
	 * @param uri 异步加载数据的URI模版
	 * @param forConfig 显示配置相关信息（目前只显示序号）
	 * @return
	 */
	private List<Tree> unitTreeOf(String orgId, String pId, String uri, Boolean forConfig) {
		UnitEntity search = new UnitEntity();
		search.setAvailable(1);

		DetachedCriteria dc = this.unitSrv.getDc(search);

		dc.addOrder(Order.asc("sort"));
		dc.addOrder(GBKOrder.asc("name"));

		if (!StringUtils.isBlank(orgId)) {
			dc.add(Restrictions.eq("org.id", orgId));
			dc.add(Restrictions.isNull("parent"));
		} else if (!StringUtils.isBlank(pId)) {
			dc.add(Restrictions.eq("parent.id", pId));
		}

		List<Tree> ret = new ArrayList<Tree>();

		@SuppressWarnings("unchecked")
		List<UnitEntity> unitData = (List<UnitEntity>) this.unitSrv.readByCriteria(dc);

		if (unitData != null) {
			for (UnitEntity unit : unitData) {
				Tree t = new Tree();

				t.setActive(null);
				t.setChecked(null);
				t.setChildren(new ArrayList<Tree>());
				t.setCollapse(true);
				t.setData(unit.toVO());
				t.setIcon(null);
				t.setLoadDataFrom(uri);
				t.setText((Boolean.TRUE.equals(forConfig) ? "(" + unit.getSort() + ")" : "") + unit.getName());

				ret.add(t);
			}
		}

		return ret;
	}

	/**
	 * 获取树子节点（user）
	 * 
	 * @param unitId
	 * @param loginUserOnly
	 * @param forConfig
	 * @return
	 */
	private List<Tree> userTreeOf(String unitId, boolean loginUserOnly, Boolean forConfig) {
		UserEntity search = new UserEntity();
		search.setAvailable(1);
		search.setUnit(new UnitEntity());
		search.getUnit().setId(unitId);
		search.setIsLongUser(loginUserOnly ? 1 : null);

		List<Tree> ret = new ArrayList<Tree>();

		List<UserEntity> userData = this.userSrv.readByEntity(search);

		if (userData != null) {
			for (UserEntity user : userData) {
				Tree t = new Tree();

				t.setActive(null);
				t.setChecked(null);
				t.setChildren(null);
				t.setCollapse(null);
				t.setData(user.toVO());
				t.setIcon(null);
				t.setLoadDataFrom(null);
				t.setText((Boolean.TRUE.equals(forConfig) ? "(" + user.getSort() + ")" : "") + user.getName());

				ret.add(t);
			}
		}

		return ret;
	}

	// ============================== 下面是老方法

	@RequestMapping("/org-tree")
	@ResponseBody
	public Object loginTree() {
		List<Tree> tree = this.getLoginOrgTree();

		return tree;
	}

	@SuppressWarnings("unchecked")
	public List<Tree> getLoginOrgTree() {
		String ql = "SELECT T FROM OrgEntity T  WHERE T.buildIn=0 ORDER BY T.sort, T.name";

		List<OrgEntity> data = (List<OrgEntity>) this.orgSrv.readByQL(ql);

		if (data == null || data.isEmpty())
			return null;

		List<Tree> ret = new ArrayList<Tree>();

		for (OrgEntity org : data) {
			Tree tree = this.makeTreeOfOrg(org, true, true);

			if (tree == null)
				continue;

			ret.add(tree);
		}

		return ret.isEmpty() ? null : ret;
	}

	// make org
	@SuppressWarnings("unchecked")
	private Tree makeTreeOfOrg(OrgEntity org, boolean excludeEmptyNode, boolean isLonginUserOnly) {
		if (org == null)
			return null;

		Tree node = new Tree();
		node.setCollapse(false); // 单位默认展开
		node.setText(org.getName());
		node.setData(org.toVO());
		node.setChildren(new ArrayList<Tree>());

		String ql = "SELECT T FROM UnitEntity T WHERE T.parent=null AND T.org.id=? ORDER BY T.sort, T.name";

		List<UnitEntity> data = (List<UnitEntity>) this.orgSrv.readByQL(ql, org.getId());

		if (data == null || data.isEmpty())
			return node;

		// 构建单位
		for (UnitEntity unit : data) {
			Tree tmp = this.makeTreeOfUnit(unit, excludeEmptyNode, isLonginUserOnly);

			if (tmp == null)
				continue;

			node.getChildren().add(tmp);
		}

		// 排除空节点
		if (excludeEmptyNode && node.getChildren().isEmpty())
			return null;

		return node;
	}

	// make unit
	private Tree makeTreeOfUnit(UnitEntity unit, boolean excludeEmptyNode, boolean isLonginUserOnly) {
		if (unit == null)
			return null;

		Tree node = new Tree();
		node.setText(unit.getName());
		node.setData(unit.toVO());
		node.setCollapse(true); // 部门默认折叠
		node.setChildren(new ArrayList<Tree>());
		// 构建单位
		if (unit.getChildren() != null) {

			for (UnitEntity u : unit.getChildren()) {
				Tree tmp = this.makeTreeOfUnit(u, excludeEmptyNode, isLonginUserOnly);

				if (tmp == null)
					continue;

				node.getChildren().add(tmp);
			}
		}

		return node;
	}

	@RequestMapping("/user-tree")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Object userTree(String unit) {
		if (StringUtils.isEmpty(unit)) {
			return null;
		}
		String ql = "SELECT T FROM UserEntity T WHERE T.unit.id=? ORDER BY T.sort, T.name";
		List<UserEntity> data = (List<UserEntity>) this.userSrv.readByQL(ql, unit);
		if (data == null || data.isEmpty()) {
			return null;
		} else {
			List<Tree> users = new ArrayList<Tree>();

			for (UserEntity user : data) {
				if (user.getAvailable() == 1) {
					Tree tmp = new Tree();
					tmp.setText(user.getName());
					tmp.setData(user.toVO());

					users.add(tmp);
				}
			}
			return users;

		}
	}

}
