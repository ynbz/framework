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
import com.suredy.security.entity.UnitEntity;
import com.suredy.security.model.Unit;

@Service("UnitSrv")
public class UnitSrv extends BaseSrvWithEntity<UnitEntity> {

	public UnitSrv() {
		this.defAsc("sort");
		this.addDefOrder(GBKOrder.asc("name"));
	}

	@Override
	public DetachedCriteria getDc(UnitEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId()))
			dc.add(Restrictions.eq("id", t.getId()));

		if (!StringUtils.isBlank(t.getName()))
			dc.add(Restrictions.eq("name", t.getName()));

		if (!StringUtils.isBlank(t.getUniqueCode()))
			dc.add(Restrictions.eq("uniqueCode", t.getUniqueCode()));

		if (t.getAvailable() != null)
			dc.add(Restrictions.eq("available", t.getAvailable()));

		return dc;
	}
	
	public List<UnitEntity> getAll() {
		return this.readByEntity(null);
	}
	
	@Transactional
	public List<Tree> getUnitTree(String orgId){
		String ql = "SELECT T FROM UnitEntity T WHERE T.parent IS NULL AND T.org = '" + orgId + "' ORDER BY T.sort, CONVERT_GBK(T.name)";
		@SuppressWarnings("unchecked")
		List<UnitEntity> unitEntities = (List<UnitEntity>) this.readByQL(ql);
		List<Tree> ret = new ArrayList<Tree>();
		
		for (UnitEntity unitEntity : unitEntities) {
			Tree unitTree = buildTree(unitEntity);
			if (unitTree != null) {
				ret.add(unitTree);
			}
		}
		
		return ret;
	}
	
	private Tree buildTree(UnitEntity unitEntity) {
		if (unitEntity == null) {
			return null;
		}
		Tree tree = new Tree();
		tree.setText(unitEntity.getName() + " [部门]");
		tree.setData(unitEntity.toVO());
		
		if (unitEntity.getChildren() != null && !unitEntity.getChildren().isEmpty()) {
			tree.setChildren(new ArrayList<Tree>());

			for (UnitEntity child : unitEntity.getChildren()) {
				Tree cTree = buildTree(child);

				if (cTree != null) {
					tree.getChildren().add(cTree);
				}
			}
		}
		return tree;
	}

	public Unit getById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return this.get(id).toVO();
	}

	public Unit getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		UnitEntity search = new UnitEntity();
		search.setName(name);
		UnitEntity ret = this.readSingleByEntity(search);
		if (ret == null) {
			return null;
		}
		return ret.toVO();
	}
	
	public Unit getByCode(String uniqueCode) {
		if (StringUtils.isBlank(uniqueCode)) {
			return null;
		}
		UnitEntity search = new UnitEntity();
		search.setUniqueCode(uniqueCode);
		UnitEntity ret = this.readSingleByEntity(search);
		if (ret == null) {
			return null;
		}
		return ret.toVO();
	}
}
