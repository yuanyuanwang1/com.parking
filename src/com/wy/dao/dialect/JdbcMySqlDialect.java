package com.wy.dao.dialect;

import org.apache.log4j.Logger;

public class JdbcMySqlDialect implements JdbcDialect {

	private Logger logger = Logger.getLogger(JdbcMySqlDialect.class);

	@Override
	public String getLimitString(String sql, int firstRow, int lastRow) {
		return new StringBuffer(sql.length() + 20).append(sql)
				.append(" limit ").append(0).append(", ").append(20).toString();
	}

	@Override
	public String getCountString(String sql) {
		return new StringBuffer("select count(*) from (").append(sql)
				.append(") aa").toString();
	}

}
