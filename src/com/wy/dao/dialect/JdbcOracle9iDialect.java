package com.wy.dao.dialect;

import org.apache.log4j.Logger;

public class JdbcOracle9iDialect implements JdbcDialect {

	private Logger logger = Logger.getLogger(JdbcOracle9iDialect.class);

	@Override
	public String getLimitString(String sql, int firstRow, int lastRow) {
		sql = sql.trim();
		String forUpdateClause = null;
		boolean isForUpdate = false;
		final int forUpdateIndex = sql.toLowerCase().lastIndexOf("for update");
		if (forUpdateIndex > -1) {
			// save 'for update ...' and then remove it
			forUpdateClause = sql.substring(forUpdateIndex);
			sql = sql.substring(0, forUpdateIndex - 1);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
				.append("select * from ( select row_.*, rownum rownum_ from ( ")
				.append(sql).append(" ) row_ where rownum <= ").append(lastRow)
				.append(") where rownum_ > ").append(firstRow);

		if (isForUpdate) {
			pagingSelect.append(" ");
			pagingSelect.append(forUpdateClause);
		}

		return pagingSelect.toString();
	}

	@Override
	public String getCountString(String sql) {
		String countString = new StringBuffer(" select count(*) from (")
				.append(sql).append(")").toString();

		logger.debug("countSql:" + countString);

		return countString;

	}

}
