/**
 * 
 */
package com.wy.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * 此类经测试，在ORACLE中使用正常，在SQLSERVER中无法使用
 * 
 * @author Administrator
 * 
 */
public class DongrenHibernateTemplate extends HibernateTemplate {

	private Logger logger = Logger.getLogger(DongrenHibernateTemplate.class);

	public Integer findForCount(DetachedCriteria criteria) {

		List list = this.findByCriteria(criteria.setProjection(Projections
				.rowCount()));

		if (list == null || list.isEmpty()) {

			return 0;

		}

		Integer count = (Integer) list.get(0);

		return count;

	}

	public List findForPage(DetachedCriteria criteria, int firstResult,
			int maxResults) {

		List<?> list = this.findByCriteria(criteria.setProjection(null),
				firstResult, maxResults);

		return list;

	}

	public PageInfo getPage(DetachedCriteria criteria, int pageNum) {

		return getPage(criteria, pageNum, null);

	}

	public PageInfo getPage(DetachedCriteria criteria, int pageNum,
			Integer pageSize) {

		int totalRows = findForCount(criteria).intValue();

		PageInfo pageInfo = new PageInfo();

		pageInfo.setPageNum(pageNum);

		pageInfo.setTotalRows(totalRows);

		if (pageSize != null) {
			pageInfo.setPageSize(pageSize);
		}

		int firstResult = pageInfo.getFirstResult();

		int maxResults = pageInfo.getPageSize();

		List items = findForPage(criteria, firstResult, maxResults);

		pageInfo.setItems(items);

		return pageInfo;

	}

}
