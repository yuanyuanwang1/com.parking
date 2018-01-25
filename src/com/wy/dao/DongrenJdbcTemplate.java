package com.wy.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.wy.dao.dialect.JdbcDialect;

public class DongrenJdbcTemplate extends NamedParameterJdbcTemplate {

	private Logger logger = Logger.getLogger(DongrenJdbcTemplate.class);

	private String dialectName = null;

	private JdbcDialect jdbcDialect = null;

	public DongrenJdbcTemplate(DataSource dataSource, String dialectName) {
		super(dataSource);
		this.dialectName = dialectName;
		try {
			jdbcDialect = (JdbcDialect) classForName(dialectName).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public long findForCount(String sql,
			MapSqlParameterSource sqlParameterSource) {

		String countString = jdbcDialect.getCountString(sql);

		Long count = this.queryForLong(countString, sqlParameterSource);

		return count;

	}

	public List queryForPage(String sql,
			MapSqlParameterSource sqlParameterSource, int firstRow, int lastRow) {

		List<Map<String, Object>> list = this.queryForList(
				jdbcDialect.getLimitString(sql, firstRow, lastRow),
				sqlParameterSource);

		return list;

	}

	// 添加分页查询
	public PageInfo getPage(String sql,
			MapSqlParameterSource sqlParameterSource, int pageNum,
			Integer pageSize) {

		int totalRows = (int) findForCount(sql, sqlParameterSource);

		PageInfo pageInfo = new PageInfo();

		pageInfo.setPageNum(pageNum);

		pageInfo.setTotalRows(totalRows);

		if (pageSize != null) {
			pageInfo.setPageSize(pageSize);
		}

		int firstResult = pageInfo.getFirstResult();

		int maxResults = firstResult + pageInfo.getPageSize();

		List items = queryForPage(sql, sqlParameterSource, firstResult,
				maxResults);

		pageInfo.setItems(items);

		return pageInfo;

	}

	public PageInfo getPage(String sql, int pageNum) {

		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		return getPage(sql, paramMap, pageNum);

	}

	public PageInfo getPage(String sql, int pageNum, Integer pageSize) {

		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		return getPage(sql, paramMap, pageNum, pageSize);

	}

	public PageInfo getPage(String sql,
			MapSqlParameterSource sqlParameterSource, int pageNum) {

		return getPage(sql, sqlParameterSource, pageNum, null);

	}

	private static Class<?> classForName(String name)
			throws ClassNotFoundException {
		try {
			ClassLoader contextClassLoader = Thread.currentThread()
					.getContextClassLoader();
			if (contextClassLoader != null) {
				return contextClassLoader.loadClass(name);
			}
		} catch (Throwable t) {

			t.printStackTrace();

		}
		return Class.forName(name);
	}

	public String getDialectName() {
		return dialectName;
	}

	public void setDialectName(String dialectName) {
		this.dialectName = dialectName;
	}

	public Integer queryForInt(String sql) {

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

		return super.queryForInt(sql, sqlParameterSource);

	}

	public List<Map<String, Object>> queryForList(String sql) {

		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		return super.queryForList(sql, paramMap);

	}

	public Map<String, Object> queryForMap(String sql) {

		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		try {

			return super.queryForMap(sql, paramMap);

		} catch (EmptyResultDataAccessException e) {

			return null;

		}

	}

}
