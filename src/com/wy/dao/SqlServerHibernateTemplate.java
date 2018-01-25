/**
 * 
 */
package com.wy.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * 此类经测试，在SQLSERVER中使用正常
 * 
 * @author Administrator
 * 
 */
public class SqlServerHibernateTemplate extends HibernateTemplate {

	private Logger logger = Logger.getLogger(SqlServerHibernateTemplate.class);

	public static String hql = null;

	public static int firstResult = 0;

	public static int maxResults = 0;

	public Long findForCount(String hql) {

		List list = this.find(hql);

		if (list == null || list.isEmpty()) {

			return new Long(0);

		}

		Long count = (Long) list.get(0);

		return count;

	}

	public List findForPage(String hql, int firstResult, int maxResults) {

		this.hql = hql;

		this.firstResult = firstResult;

		this.maxResults = maxResults;

		List<?> list = this.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.createQuery(SqlServerHibernateTemplate.hql);
				query.setMaxResults(SqlServerHibernateTemplate.maxResults); // 每页最多显示几条
				query.setFirstResult(SqlServerHibernateTemplate.firstResult);// 每页从第几条记录开始
				List list = query.list();
				return list;
			}
		});

		return list;

	}

	public PageInfo getPage(String hql, String hqlCount, int pageNum) {

		return getPage(hql, hqlCount, pageNum, null);

	}

	public PageInfo getPage(String hql, String hqlCount, int pageNum,
			Integer pageSize) {

		int totalRows = findForCount(hqlCount).intValue();

		PageInfo pageInfo = new PageInfo();

		pageInfo.setPageNum(pageNum);

		pageInfo.setTotalRows(totalRows);

		if (pageSize != null) {
			pageInfo.setPageSize(pageSize);
		}

		int firstResult = pageInfo.getFirstResult();

		int maxResults = pageInfo.getPageSize();

		if (totalRows < 1) {

			return pageInfo;

		}

		List items = findForPage(hql, firstResult, maxResults);

		pageInfo.setItems(items);

		return pageInfo;

	}

}
