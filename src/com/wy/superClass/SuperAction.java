/**
 * 
 */
package com.wy.superClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import com.wy.dao.PageInfo;
import com.wy.model.AuditLog;
import com.wy.service.LogService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * 
 */
public abstract class SuperAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, SessionAware {

	private Logger logger = Logger.getLogger(SuperAction.class);

	protected HttpServletRequest request = null;

	protected HttpServletResponse response = null;

	protected Map session = null;

	protected PageInfo pageInfo = null;

	protected String pageNum = null;

	protected String searchValue = null;

	protected String pid = null;

	protected Map<String, Object> map = null;

	protected List<Map<String, Object>> list = null;

	protected String result = null;

	protected String errorInfo = null;

	protected String errorCode = null;

	protected AuditLog auditLog = new AuditLog();

	protected LogService logService = null;

	protected String dateNow = null;
	

	public String getDateNow() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		dateNow = sdf.format(date);

		return dateNow;
	}

	public void setDateNow(String dateNow) {
		this.dateNow = dateNow;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the session
	 */
	public Map getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(Map session) {
		this.session = session;
	}

	/**
	 * @return the pageInfo
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            the pageInfo to set
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * @return the pageNum
	 */
	public String getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue
	 *            the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the map
	 */
	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * @return the list
	 */
	public List<Map<String, Object>> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the errorInfo
	 */
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * @param errorInfo
	 *            the errorInfo to set
	 */
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}

	/**
	 * @param logService
	 *            the logService to set
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse
	 * (javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse response) {

		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {

		this.request = request;
	}

	public String getUserId() {

		
	if (session == null || session.isEmpty()) {

	 return null;

	}

    return (String) session.get("userId");
		 
	}
	


	public Integer getIPageNum() {

		if (StringUtils.isBlank(pageNum)) {
			pageNum = "1";
		}

		return Integer.valueOf(pageNum);

	}

	public void setResult(JSONObject jsonObject) {
		result = jsonObject.toString();
	}

	/**
	 * @return the auditLog
	 */
	public AuditLog getAuditLog() {

		auditLog.setActionPath(request.getServletPath() + "?"
				+ request.getQueryString());

		return auditLog;

	}

	/**
	 * @param auditLog
	 *            the auditLog to set
	 */
	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}

	@Override
	public String execute() {

		return SUCCESS;

	}

	public static String getRandomFileName() {

		SimpleDateFormat simpleDateFormat;

		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		Date date = new Date();

		String str = simpleDateFormat.format(date);

		Random random = new Random();

		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

		return rannum + str;// 当前时间
	}

	protected void printAjaxToPage(String ajaxValue) {

		PrintWriter out = null;

		try {

			out = response.getWriter();

			out.write(ajaxValue);
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
