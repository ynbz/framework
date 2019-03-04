package com.suredy.core.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.suredy.core.dao.BaseDao;

/**
 * 基础service类
 * 
 * @author VIVID.G
 * @since 2015-4-1
 * @version v0.1
 */
@Service("com.suredy.core.service.BaseSrv")
public class BaseSrv {

	@Autowired
	@Qualifier("com.suredy.core.dao.BaseDao")
	protected BaseDao dao;

	public <T extends BaseDao> void setDao(T t) {
		this.dao = t;
	}

	@Transactional
	public <T> T save(T t) {
		return this.dao.persist(t);
	}

	@Transactional
	public <T> T delete(T t) {
		return this.dao.delete(t);
	}

	@Transactional
	public <T> T update(T t) {
		return this.dao.merge(t);
	}

	@Transactional
	public <T> T saveOrUpdate(T t) {
		return this.dao.saveOrUpdate(t);
	}

	public <T> T get(Class<T> clazz, String id) {
		return this.dao.get(clazz, id);
	}

	public <T> T get(Class<T> clazz, Long id) {
		return this.dao.get(clazz, id);
	}

	public <T> T get(Class<T> clazz, Integer id) {
		return this.dao.get(clazz, id);
	}

	public List<?> readByCriteria(DetachedCriteria dc) {
		return this.dao.readByCriteria(dc);
	}

	public List<?> readPageByCriteria(DetachedCriteria dc, int page, int pageSize) {
		return this.dao.readPageByCriteria(dc, page, pageSize);
	}

	public Object readSingleByCriteria(DetachedCriteria dc) {
		return this.dao.readSingleByCriteria(dc);
	}

	public int getCountByCriteria(DetachedCriteria dc) {
		return this.dao.getCountByCriteria(dc);
	}

	@Transactional
	public int insertByQL(String ql, Object... values) {
		return this.dao.insertByQL(ql, values);
	}

	@Transactional
	public int deleteByQL(String ql, Object... values) {
		return this.dao.deleteByQL(ql, values);
	}

	@Transactional
	public int updateByQL(String ql, Object... values) {
		return this.dao.updateByQL(ql, values);
	}

	public List<?> readByQL(String ql, Object... values) {
		return this.dao.readByQL(ql, values);
	}

	public List<?> readPageByQL(String ql, int page, int pageSize, Object... values) {
		return this.dao.readPageByQL(ql, page, pageSize, values);
	}

	public Object readSingleByQL(String ql, Object... values) {
		return this.dao.readSingleByQL(ql, values);
	}

	public int getCountByQL(String ql, Object... values) {
		return this.dao.getCountByQL(ql, values);
	}

	@Transactional
	public int insertBySQL(String sql, Object... values) {
		return this.dao.insertBySQL(sql, values);
	}

	@Transactional
	public int deleteBySQL(String sql, Object... values) {
		return this.dao.deleteBySQL(sql, values);
	}

	@Transactional
	public int updateBySQL(String sql, Object... values) {
		return this.dao.updateBySQL(sql, values);
	}

	public List<?> readBySQL(String sql, Object... values) {
		return this.dao.readBySQL(sql, values);
	}

	public <T> List<T> readWithClassBySQL(String sql, Class<T> clazz, Object... values) {
		return this.dao.readWithClassBySQL(sql, clazz, values);
	}

	public List<?> readPageBySQL(String sql, int page, int pageSize, Object... values) {
		return this.dao.readPageBySQL(sql, page, pageSize, values);
	}

	public <T> List<T> readPageWithClassBySQL(String sql, Class<T> clazz, int page, int pageSize, Object... values) {
		return this.dao.readPageWithClassBySQL(sql, clazz, page, pageSize, values);
	}

	public Object readSingleBySQL(String sql, Object... values) {
		return this.dao.readSingleBySQL(sql, values);
	}

	public <T> T readSingleWithClassBySQL(String sql, Class<T> clazz, Object... values) {
		return this.dao.readSingleWithClassBySQL(sql, clazz, values);
	}

	public int getCountBySQL(String sql, Object... values) {
		return this.dao.getCountBySQL(sql, values);
	}

}
