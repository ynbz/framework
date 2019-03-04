package com.suredy.core.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

/**
 * 通过对象操作的基础service
 * 
 * @author VIVID.G
 * @since 2015-4-1
 * @version v0.1
 */
@SuppressWarnings("unchecked")
@Service("com.suredy.core.service.BaseSrvWithEntity")
public abstract class BaseSrvWithEntity<T> extends BaseSrv {

	private Class<T> clazz;

	private final String DEF_ENTITY_ALIAS = "T";

	protected boolean USE_DEF_ORDER = true;

	protected final OrderEntity DEF_ORDERS = new OrderEntity();

	public BaseSrvWithEntity() {
		super();

		Class<?> clazz = this.getClass();

		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();

			if (!(t instanceof ParameterizedType))
				clazz = clazz.getSuperclass();

			Type[] ts = ((ParameterizedType) t).getActualTypeArguments();

			if (ts == null || ts.length <= 0)
				throw new RuntimeException("Can not get a default class.");

			if (ts[0] instanceof Class) {
				this.clazz = (Class<T>) ts[0];
				break;
			}
		}
	}

	public DetachedCriteria getDc() {
		return this.getDc(null);
	}

	public DetachedCriteria getDc(T t) {
		return this.getDc(t, null);
	}

	public DetachedCriteria getDc(T t, String alias) {
		if (this.clazz == null)
			throw new RuntimeException("Can not get a default class type.");

		alias = StringUtils.isBlank(alias) ? this.DEF_ENTITY_ALIAS : alias;

		DetachedCriteria dc = null;

		if (t != null)
			dc = DetachedCriteria.forClass(t.getClass(), alias);
		else
			dc = DetachedCriteria.forClass(this.clazz, alias);

		return dc;
	}

	public DetachedCriteria putClause(DetachedCriteria dc, T t) {
		return this.putClause(dc, t, null);
	}

	public DetachedCriteria putClause(DetachedCriteria dc, T t, String alias) {
		throw new RuntimeException("You should override this method<public DetachedCriteria putClause(DetachedCriteria, T, String)>.");
	}

	public void defAsc(String field) {
		this.addAnDefOrder(field, true);
	}

	public void defDesc(String field) {
		this.addAnDefOrder(field, false);
	}

	public void addAnDefOrder(String field, boolean asc) {
		this.DEF_ORDERS.add(field, asc);
	}

	public void addDefOrder(Order order) {
		this.DEF_ORDERS.add(order);
	}

	public T get(String id) {
		return super.get(this.clazz, id);
	}

	public T get(Long id) {
		return super.get(this.clazz, id);
	}

	public T get(Integer id) {
		return super.get(this.clazz, id);
	}

	public List<T> readByEntity(T t) {
		return this.readByEntity(t, null);
	}

	public List<T> readPageByEntity(T t, int page, int pageSize) {
		return (List<T>) this.readPageByEntity(t, page, pageSize, null);
	}

	public T readSingleByEntity(T t) {
		return (T) this.readSingleByEntity(t, null);
	}

	public List<T> readByEntity(T t, OrderEntity oe) {
		return (List<T>) this.readByCriteria(this.getDcForSearch(t, oe));
	}

	public List<T> readPageByEntity(T t, int page, int pageSize, OrderEntity oe) {
		return (List<T>) this.readPageByCriteria(this.getDcForSearch(t, oe), page, pageSize);
	}

	public T readSingleByEntity(T t, OrderEntity oe) {
		return (T) this.readSingleByCriteria(this.getDcForSearch(t, oe));
	}

	public int getCountByEntity(T t) {
		return this.getCountByCriteria(this.getDcForSearch(t, null));
	}

	public OrderEntity getDefOrders() {
		return this.DEF_ORDERS;
	}

	private DetachedCriteria getDcForSearch(T t, OrderEntity oe) {
		DetachedCriteria dc = this.getDc(t);

		if (dc == null)
			throw new RuntimeException("Can not get a DetachedCriteria object.");

		if (oe == null && this.USE_DEF_ORDER)
			oe = this.DEF_ORDERS;

		// 排序
		if (oe != null)
			oe.order(dc);

		return dc;
	}

}
