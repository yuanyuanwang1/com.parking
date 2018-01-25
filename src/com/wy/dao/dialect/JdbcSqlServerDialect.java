package com.wy.dao.dialect;

import org.apache.log4j.Logger;

public class JdbcSqlServerDialect implements JdbcDialect {

	private Logger logger = Logger.getLogger(JdbcSqlServerDialect.class);

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
				.append(" with t_rowtable as ( select row_number() over(order by create_time desc) as row_number,row_.* from ( "
						+ sql
						+ " ) row_) select * from t_rowtable where row_number> "
						+ firstRow + " and row_number < " + lastRow + " ");

		if (isForUpdate) {
			pagingSelect.append(" ");
			pagingSelect.append(forUpdateClause);
		}

		return pagingSelect.toString();
	}

	@Override
	public String getCountString(String sql) {
		String countString = new StringBuffer(" select count(*) from (")
				.append(sql).append(") tmp1 ").toString();

		logger.debug("countSql:" + countString);

		return countString;

	}

}
