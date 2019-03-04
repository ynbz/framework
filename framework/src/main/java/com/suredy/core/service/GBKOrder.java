package com.suredy.core.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class GBKOrder extends Order {

	private static final long serialVersionUID = 1L;

	protected GBKOrder(String propertyName, boolean ascending) {
		super(propertyName, ascending);
	}

	@Override
	public String toString() {
		String sql = super.toString();

		return sql.replace(this.getPropertyName(), "convert(" + this.getPropertyName() + " using gbk)");
	}

	@Override
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) {
		String sql = super.toSqlString(criteria, criteriaQuery);

		final String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, this.getPropertyName());

		for (int i = 0; i < columns.length; i++) {
			sql = sql.replace(columns[i], "convert(" + columns[i] + " using gbk)");
		}

		return sql;
	}

	public static GBKOrder asc(String propertyName) {
		return new GBKOrder(propertyName, true);
	}

	public static GBKOrder desc(String propertyName) {
		return new GBKOrder(propertyName, false);
	}

}
