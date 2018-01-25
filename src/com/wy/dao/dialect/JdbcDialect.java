package com.wy.dao.dialect;

public interface JdbcDialect {
	public String getLimitString(String sql, int firstRow, int lastRow);

	public String getCountString(String sql);
}
