/**
 * 
 */
package com.wy.superClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wy.dao.DongrenJdbcTemplate;
import com.wy.dao.PageInfo;
import com.wy.dao.SqlServerHibernateTemplate;

/**
 * @author Administrator
 * 
 */
public abstract class SuperService {

	public final static String TABLE_STATUS_VALID = "1";
	public final static String TABLE_STATUS_INVALID = "0";
	public final static String TABLE_STATUS_DELETED = "-1";

	protected HibernateTemplate hibernateTemplate = null;

	protected DongrenJdbcTemplate dongrenJdbcTemplate = null;

	protected DataSource dataSource = null;
	
	protected SqlServerHibernateTemplate sqlServerHibernateTemplate=null;
	
	

	public SqlServerHibernateTemplate getSqlServerHibernateTemplate() {
		return sqlServerHibernateTemplate;
	}

	public void setSqlServerHibernateTemplate(
			SqlServerHibernateTemplate sqlServerHibernateTemplate) {
		this.sqlServerHibernateTemplate = sqlServerHibernateTemplate;
	}

	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate
	 *            the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * @return the dongrenJdbcTemplate
	 */
	public DongrenJdbcTemplate getDongrenJdbcTemplate() {
		return dongrenJdbcTemplate;
	}

	/**
	 * @param dongrenJdbcTemplate
	 *            the dongrenJdbcTemplate to set
	 */
	public void setDongrenJdbcTemplate(DongrenJdbcTemplate dongrenJdbcTemplate) {
		this.dongrenJdbcTemplate = dongrenJdbcTemplate;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public PageInfo getPage(String tableName, int pageNum, String searchValue) {

		String sql = " select * from " + tableName + " t where t.status = '"
				+ TABLE_STATUS_VALID + "' ";

		if (StringUtils.isNotBlank(searchValue)) {

			sql = sql + " and (pname like '%" + searchValue
					+ "%' or pcode like '%" + searchValue + "%') ";

		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public List<Map<String, Object>> list(String tableName) {

		String sql = " select * from " + tableName + " t where t.status = '"
				+ TABLE_STATUS_VALID
				+ "' order by order_num, create_time desc ";

		return dongrenJdbcTemplate.queryForList(sql);

	}

	public String save(SuperModel object) {

		if (object == null) {
			return null;
		}

		hibernateTemplate.save(object);

		return object.getPid();
	}

	public String update(SuperModel object) {

		if (object == null) {
			return null;
		}

		hibernateTemplate.update(object);

		return object.getPid();

	}

	public String delete(SuperModel object) {

		if (object == null) {
			return null;
		}
		
		object.setStatus(TABLE_STATUS_DELETED);

		hibernateTemplate.update(object);

		return object.getPid();

	}

	public Map<String, Object> getMapOne(String tableName, String pid) {

		String sql = " select t.* from " + tableName + " t where t.pid = '"
				+ pid + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public Object getScalarFunction(String scalarFunctionStr) {

		String sql = " select " + scalarFunctionStr + " ";

		Map<String, Object> map = dongrenJdbcTemplate.queryForMap(sql);

		if (map == null || map.isEmpty()) {

			return null;

		}

		Collection collection = map.values();

		if (collection == null || collection.isEmpty()) {

			return null;

		}

		Object[] objectArray = collection.toArray();

		if (objectArray == null || objectArray.length < 1) {

			return null;

		}

		return objectArray[0];

	}

	public Integer unique(String tableName, String columnName,
			String columnValue) {

		return unique(tableName, columnName, columnValue, null);

	}

	public Integer unique(String tableName, String columnName,
			String columnValue, String pid) {

		String sql = " select count(0) from " + tableName + " where status = '"
				+ this.TABLE_STATUS_VALID + "' and " + columnName + " = '"
				+ columnName + "' ";

		if (StringUtils.isNotBlank(pid)) {

			sql = sql + " and pid != '" + pid + "' ";

		}

		return dongrenJdbcTemplate.queryForInt(sql);

	}
	
	public PageInfo getPage(List list, int pageNum, Integer pageSize) {

		if (list == null || list.isEmpty()) {

			return null;

		}

		PageInfo pageInfo = new PageInfo();

		pageInfo.setPageNum(pageNum);
		
		pageInfo.setTotalRows(list.size());

//		if (pageSize != null) {
//			pageInfo.setPageSize(pageSize);
//		}

		Integer firstResult = pageInfo.getFirstResult();

		Integer maxResults = firstResult + pageInfo.getPageSize();

		List items = new ArrayList();

		for (Integer i = 0; i < maxResults; i++) {

			if (i < firstResult) {

				continue;

			}

			if (i >= list.size()) {

				break;

			}

			Object item = list.get(i);

			items.add(item);

		}

		pageInfo.setItems(items);

		return pageInfo;
	}

}
