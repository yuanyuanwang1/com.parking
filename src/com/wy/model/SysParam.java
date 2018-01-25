package com.wy.model;

import java.util.Date;

/**
 * SysParam entity. @author MyEclipse Persistence Tools
 */

public class SysParam implements java.io.Serializable {

	// Fields

	private String pid = null;

	private String paramName = null;

	private String paramCode = null;

	private String paramValue = null;

	private String createUserId = null;

	private Date createTime = null;

	private Date lastUpTime = null;
	private String status = null;

	// Constructors

	/** default constructor */
	public SysParam() {
	}

	// Property accessors

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastUpTime
	 */
	public Date getLastUpTime() {
		return lastUpTime;
	}

	/**
	 * @param lastUpTime
	 *            the lastUpTime to set
	 */
	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
