package com.suredy.core.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public class OrderEntity {

	private final List<Order> ORDERS = new ArrayList<Order>();

	public OrderEntity asc(String field) {
		return this.add(field, true);
	}

	public OrderEntity desc(String field) {
		return this.add(field, false);
	}

	public OrderEntity add(String field, boolean asc) {
		if (StringUtils.isBlank(field))
			throw new RuntimeException("Invalid order field. It is blank.");

		Order order = null;

		if (asc)
			order = Order.asc(field);
		else
			order = Order.desc(field);

		return this.add(order);
	}

	public OrderEntity add(Order order) {
		if (order == null || StringUtils.isBlank(order.getPropertyName()))
			return this;

		for (Iterator<Order> it = this.ORDERS.iterator(); it.hasNext();) {
			Order o = it.next();
			if (order.getPropertyName().equals(o.getPropertyName())) {
				it.remove();
				break;
			}
		}

		this.ORDERS.add(order);

		return this;
	}

	public DetachedCriteria order(DetachedCriteria dc) {
		if (dc == null)
			throw new RuntimeException("Invalid DetachedCriteria. It is null.");

		for (Order o : this.ORDERS) {
			if (o == null)
				continue;

			dc.addOrder(o);
		}

		return dc;
	}

	/**
	 * 判断指定的字段是否为正序
	 * 
	 * @param field
	 * @return
	 */
	public Boolean isAsc(String field) {
		if (StringUtils.isBlank(field))
			return null;

		for (Order o : this.ORDERS) {
			if (!field.equals(o.getPropertyName()))
				continue;

			return o.isAscending();
		}

		return null;
	}

}
