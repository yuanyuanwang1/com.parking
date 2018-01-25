/**
 * 
 */
package com.wy.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 
 */
public class PageInfo {

	private Logger logger = Logger.getLogger(PageInfo.class);

	public static final int DEFAULT_PAGESIZE = 20;
	/** 当前页码 */
	private int pageNum = 1;
	/** 每页记录数 */
	private int pageSize = 0;
	/** 总记录数 */
	private int totalRows = 0;
	/** 当前页记录集 */
	private List<?> list = null;

	/** 获取记录集 */
	public List<?> getItems() {
		return list;
	}

	/** 设置记录集 */
	public void setItems(List<?> items) {
		this.list = items;
	}

	/** 获取下一页页码 */
	public int getNextPageNum() {
		return getTotalPages() <= getPageNum() ? 0 : getPageNum() + 1;
	}

	/** 获取最后一页页码 */
	public int getLastPageNum() {
		return getTotalPages();
	}

	/** 获取当前页页码 */
	public int getPageNum() {
		if (pageNum <= 0) {
			return 1;
		}
		// 总页数
		int totalPages = getTotalPages();

		// 如果当前页不是最大页数，则返回当前页，否则返回总页数
		return totalPages < pageNum ? totalPages : pageNum;
	}

	/**
	 * * 获取当前页首条记录顺序数 *
	 * 
	 * @return int 记录序号，如果为 -N, 表示没有记录
	 * */
	public int getFirstResult() {
		return (getPageNum() - 1) * getPageSize();
	}

	/** 获取首页页码 */
	public int getFirstPageNum() {
		return 1;
	}

	/** 设置当前页页码 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/** 获取每页记录数 */
	public int getPageSize() {
		// 如果没有设置每页记录数，则记录数取默认值
		return pageSize == 0 ? DEFAULT_PAGESIZE : pageSize;
	}

	/** 设置每页记录数 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 获取前一页页码 */
	public int getPrePageNum() {
		return pageNum > 1 ? pageNum - 1 : 0;
	}

	/** 获取总页数 */
	public int getTotalPages() {
		int totalPages = getTotalRows() / getPageSize();
		if (getTotalRows() % getPageSize() > 0) {
			totalPages = totalPages + 1;
		}
		return totalPages;
	}

	/** 获取总记录数 */
	public int getTotalRows() {
		return totalRows;
	}

	/** 设置总记录数 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/** 获取当前页记录数 */
	public int getPageRows() {
		int pageSize = getPageSize();
		if (getTotalPages() <= getPageNum()) {
			// 最后一页
			return getTotalRows() % pageSize;
		}
		return pageSize;
	}

	public static String changeQueryString(String queryString, String match) {

		if (queryString == null || "".equals(queryString.trim())) {

			return "";

		}

		int semicolon = queryString.indexOf(match);

		if (semicolon >= 0) {

			String rest = queryString.substring(semicolon + match.length());

			int semicolon2 = rest.indexOf('&');

			if (semicolon2 >= 0) {

				rest = rest.substring(semicolon2 + 1);

			} else {

				rest = "";

			}

			queryString = queryString.substring(0, semicolon) + rest;

		}

		if (queryString == null || "".equals(queryString.trim())) {

			return "";

		}

		if ((queryString.length() - 1) == queryString.lastIndexOf("&")) {

			queryString = queryString.substring(0, (queryString.length() - 1));

		}

		return queryString;

	}

	public static String changeQueryString(String match, String pageURI,
			String queryString) {

		queryString = changeQueryString(queryString, match);

		if (queryString == null || "".equals(queryString.trim())) {

			pageURI = pageURI + "?" + match;

		} else {

			pageURI = pageURI + "?" + queryString + "&" + match;

		}

		return pageURI;

	}

	public static PageInfo getPage(List list, int pageNum, Integer pageSize) {

		if (list == null || list.isEmpty()) {

			return null;

		}

		PageInfo pageInfo = new PageInfo();

		pageInfo.setPageNum(pageNum);

		pageInfo.setTotalRows(list.size());

		if (pageSize != null) {
			pageInfo.setPageSize(0);
		}

		int firstResult = pageInfo.getFirstResult();

		int maxResults = firstResult + pageInfo.getPageSize();

		List items = new ArrayList();

		for (int i = 0; i < maxResults; i++) {

			if (i < firstResult) {

				continue;

			}

			Object item = list.get(i);

			items.add(item);

		}

		pageInfo.setItems(items);

		return pageInfo;

	}

}
