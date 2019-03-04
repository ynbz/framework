package com.suredy.core.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * 基础dao类
 * 
 * @author VIVID.G
 * @since 2015-3-31
 * @version v0.1
 */
@Repository("com.suredy.core.dao.BaseDao")
public class BaseDao extends HibernateDaoSupport {

	private final int DEF_PAGE_SIZE = 25;

	@Autowired
	public void setBusinessSessionFacotry(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public <T> T persist(T t) {
		getHibernateTemplate().persist(t);
		return t;
	}

	public <T> T delete(T t) {
		getHibernateTemplate().delete(t);
		return t;
	}

	public <T> T merge(T t) {
		return getHibernateTemplate().merge(t);
	}

	public <T> T saveOrUpdate(T t) {
		getHibernateTemplate().saveOrUpdate(t);
		return t;
	}

	public <T> T get(Class<T> clazz, String id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public <T> T get(Class<T> clazz, Long id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public <T> T get(Class<T> clazz, Integer id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public List<?> readByCriteria(DetachedCriteria dc) {
		if (dc == null) {
			return null;
		}
		return getHibernateTemplate().findByCriteria(dc);
	}

	public List<?> readPageByCriteria(DetachedCriteria dc, int page, int pageSize) {
		if (dc == null)
			return null;

		if (page < 1)
			page = 1;

		if (pageSize < 1)
			pageSize = 25;

		int start = (page - 1) * pageSize;

		return getHibernateTemplate().findByCriteria(dc, start, pageSize);
	}

	public Object readSingleByCriteria(DetachedCriteria dc) {
		if (dc == null) {
			return null;
		}
		List<?> lst = this.readPageByCriteria(dc, 1, 1);

		if ((lst == null) || lst.isEmpty())
			return null;

		return lst.get(0);
	}

	public int getCountByCriteria(DetachedCriteria dc) {
		if (dc == null)
			return -1;

		dc.setProjection(Projections.rowCount());

		List<?> lst = readPageByCriteria(dc, 1, 1);

		if ((lst == null) || (lst.isEmpty()))
			return 0;

		Long count = (Long) lst.get(0);

		return count == null ? 0 : count.intValue();
	}

	public int insertByQL(String ql, Object... values) {
		return this.executeUpdateByQL(ql, values);
	}

	public int deleteByQL(String ql, Object... values) {
		return this.executeUpdateByQL(ql, values);
	}

	public int updateByQL(String ql, Object... values) {
		return this.executeUpdateByQL(ql, values);
	}

	public List<?> readByQL(String ql, Object... values) {
		if (StringUtils.isBlank(ql))
			return null;

		return this.getHibernateTemplate().find(ql, values);
	}

	public List<?> readPageByQL(final String ql, final int page, final int pageSize, final Object... values) {
		if (StringUtils.isBlank(ql))
			return null;

		return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {

			@Override
			public List<?> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(ql);

				int p = page < 1 ? 1 : page;
				int ps = pageSize < 1 ? 25 : pageSize;

				query.setFirstResult((p - 1) * ps);
				query.setMaxResults(ps);

				for (int i = 0; values != null && i < values.length; i++) {
					query.setParameter(i, values[i]);
				}

				return query.list();
			}
		});
	}

	public Object readSingleByQL(String ql, Object... values) {
		List<?> data = this.readPageByQL(ql, 1, 1, values);

		if (data == null || data.isEmpty())
			return null;

		return data.get(0);
	}

	public int getCountByQL(String ql, Object... values) {
		if (StringUtils.isBlank(ql))
			return -1;

		Long count = (Long) this.readSingleByQL(ql, values);

		if (count == null)
			return 0;

		return count.intValue();
	}

	public int insertBySQL(final String sql, final Object... values) {
		return this.executeUpdateBySQL(sql, values);
	}

	public int deleteBySQL(String sql, Object... values) {
		return this.executeUpdateBySQL(sql, values);
	}

	public int updateBySQL(String sql, Object... values) {
		return this.executeUpdateBySQL(sql, values);
	}

	public List<?> readBySQL(final String sql, final Object... values) {
		return this.readWithClassBySQL(sql, null, values);
	}

	public <T> List<T> readWithClassBySQL(final String sql, final Class<T> clazz, final Object... values) {
		if (StringUtils.isBlank(sql))
			return null;

		return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);

				if (clazz != null)
					query.addEntity(clazz);

				for (int i = 0; values != null && i < values.length; i++) {
					query.setParameter(i, values[i]);
				}

				return query.list();
			}
		});
	}

	public List<?> readPageBySQL(final String sql, final int page, final int pageSize, final Object... values) {
		return this.readPageWithClassBySQL(sql, null, page, pageSize, values);
	}

	public <T> List<T> readPageWithClassBySQL(final String sql, final Class<T> clazz, final int page, final int pageSize, final Object... values) {
		if (StringUtils.isBlank(sql))
			return null;

		return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);

				if (clazz != null)
					query.addEntity(clazz);

				int p = page < 1 ? 1 : page;
				int ps = pageSize < 1 ? DEF_PAGE_SIZE : pageSize;

				query.setFirstResult((p - 1) * ps);
				query.setMaxResults(ps);

				for (int i = 0; values != null && i < values.length; i++) {
					query.setParameter(i, values[i]);
				}

				return query.list();
			}
		});
	}

	public Object readSingleBySQL(final String sql, final Object... values) {
		if (StringUtils.isBlank(sql))
			return null;

		List<?> data = this.readPageBySQL(sql, 1, 1, values);

		if (data == null || data.isEmpty())
			return null;

		return data.get(0);
	}

	public <T> T readSingleWithClassBySQL(final String sql, final Class<T> clazz, final Object... values) {
		if (StringUtils.isBlank(sql))
			return null;

		List<T> data = this.readPageWithClassBySQL(sql, clazz, 1, 1, values);

		if (data == null || data.isEmpty())
			return null;

		return data.get(0);
	}

	public int getCountBySQL(final String sql, final Object... values) {
		Object data = this.readSingleBySQL(sql, values);

		if (data == null)
			return 0;

		return ((BigInteger) data).intValue();
	}

	private int executeUpdateByQL(String ql, Object... values) {
		if (StringUtils.isBlank(ql))
			return -1;

		int count = this.getHibernateTemplate().bulkUpdate(ql, values);

		return count;
	}

	private int executeUpdateBySQL(final String sql, final Object... values) {
		if (StringUtils.isBlank(sql))
			return -1;

		return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {

			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = session.createSQLQuery(sql);

				for (int i = 0; values != null && i < values.length; i++) {
					query.setParameter(i, values[i]);
				}

				return query.executeUpdate();
			}
		});
	}

}
