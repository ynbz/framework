package com.suredy.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.suredy.core.model.Dictionary;

/**
 * 基础service类
 * 
 * @author VIVID.G
 * @since 2015-4-1
 * @version v0.1
 */
@Service
public class DictionarySrv extends BaseSrvWithEntity<Dictionary> {

	private final static Logger log = LoggerFactory.getLogger(DictionarySrv.class);

	public DictionarySrv() {
		this.addAnDefOrder("system", true);
		this.addAnDefOrder("sort", true);
	}

	@Override
	public DetachedCriteria getDc(Dictionary t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getGroupCode())) {
			dc.add(Restrictions.eq("groupCode", t.getGroupCode()));
		}
		if (!StringUtils.isBlank(t.getVal())) {
			dc.add(Restrictions.eqOrIsNull("val", t.getVal()));
		}
		if (t.getSystem() != null) {
			dc.add(Restrictions.eqOrIsNull("system", t.getSystem()));
		}

		return dc;
	}

	@Transactional
	public boolean add(String groupCode, String val, boolean system) {
		if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(val))
			throw new IllegalArgumentException("Invalid parameter String[groupCode] or String[val]. It is blank.");

		Dictionary dic = new Dictionary();
		dic.setGroupCode(groupCode);
		dic.setVal(val);
		dic.setSystem(system);

		try {
			dic = this.save(dic);

			return dic != null;
		} catch (Exception e) {
			log.error("Add new dictionary item error.", e);
		}

		return false;
	}

	@Transactional
	public boolean addIfNotExists(String dicGroup, String[] values, boolean system) {
		if (StringUtils.isBlank(dicGroup) || ArrayUtils.isEmpty(values))
			return false;

		for (int i = 0; i < values.length; i++) {
			String val = values[i];

			Dictionary dic = new Dictionary();

			dic.setGroupCode(dicGroup);
			dic.setVal(val);

			Dictionary exists = this.readSingleByEntity(dic);

			if (exists == null) {
				dic.setSystem(system);
				dic.setSort(i);

				this.save(dic);
			} else if (!exists.getSystem().equals(system)) {
				dic.setSystem(system);

				this.update(dic);
			}
		}

		return true;
	}

	@Transactional
	public boolean addCustomerDic(String groupCode, String val) {
		return this.add(groupCode, val, false);
	}

	@Transactional
	public boolean delete(String id) {
		String ql = "DELETE FROM Dictionary WHERE id = ?";

		int count = this.deleteByQL(ql, id);

		return count == 1;
	}

	@Transactional
	public boolean delete(String[] ids) {
		if (ids == null || ids.length <= 0)
			return true;

		String ql = "DELETE FROM Dictionary WHERE id in ";

		String tmp = "";
		for (int i = 0; i < ids.length; i++) {
			if (!StringUtils.isBlank(tmp))
				tmp += ",";

			tmp += "?";
		}

		ql += "(" + tmp + ")";

		int count = this.deleteByQL(ql, (Object[]) ids);

		return count <= ids.length;
	}

	public List<Dictionary> getDics(String groupCode, Boolean system) {
		if (StringUtils.isBlank(groupCode))
			return null;

		Dictionary search = new Dictionary();
		search.setGroupCode(groupCode);
		search.setSystem(system);

		List<Dictionary> dics = this.readByEntity(search);

		return dics;
	}

	public List<String> getVals(String groupCode, Boolean system) {
		List<Dictionary> dics = this.getDics(groupCode, system);

		if (dics == null)
			return null;

		List<String> ret = new ArrayList<String>();

		for (Dictionary dic : dics) {
			ret.add(dic.getVal());
		}

		return ret.isEmpty() ? null : ret;
	}

}
