package com.wy.model;

import com.wy.superClass.SuperModel;

public class AuditLog extends SuperModel {

	// Fields

	private String actionName = null;

	private String domainOld = null;

	private String domainNew = null;

	private String moduleName = null;

	private String moduleNameCn = null;

	private String actionPath = null;

	private String actionResult = null;

	private String exceptionInfo = null;

	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName
	 *            the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the domainOld
	 */
	public String getDomainOld() {
		return domainOld;
	}

	/**
	 * @param domainOld
	 *            the domainOld to set
	 */
	public void setDomainOld(String domainOld) {
		this.domainOld = domainOld;
	}

	/**
	 * @return the domainNew
	 */
	public String getDomainNew() {
		return domainNew;
	}

	/**
	 * @param domainNew
	 *            the domainNew to set
	 */
	public void setDomainNew(String domainNew) {
		this.domainNew = domainNew;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the moduleNameCn
	 */
	public String getModuleNameCn() {
		return moduleNameCn;
	}

	/**
	 * @param moduleNameCn
	 *            the moduleNameCn to set
	 */
	public void setModuleNameCn(String moduleNameCn) {
		this.moduleNameCn = moduleNameCn;
	}

	/**
	 * @return the actionPath
	 */
	public String getActionPath() {
		return actionPath;
	}

	/**
	 * @param actionPath
	 *            the actionPath to set
	 */
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	/**
	 * @return the actionResult
	 */
	public String getActionResult() {
		return actionResult;
	}

	/**
	 * @param actionResult
	 *            the actionResult to set
	 */
	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	/**
	 * @return the exceptionInfo
	 */
	public String getExceptionInfo() {
		return exceptionInfo;
	}

	/**
	 * @param exceptionInfo
	 *            the exceptionInfo to set
	 */
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

}
