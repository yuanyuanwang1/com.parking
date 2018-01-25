/**
 * 
 */
package com.wy.model;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public class AreaInfo {

	// 区域代码
	private String areaCode = null;

	// 区域名称
	private String areaName = null;

	// 区域类型：国家、省、市
	private String areaType = null;

	private String status = null;

	// 下级区域列表
	private List<AreaInfo> childArea = null;

	// 上级区域
	private AreaInfo parentArea = null;

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType
	 *            the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the childArea
	 */
	public List<AreaInfo> getChildArea() {
		return childArea;
	}

	/**
	 * @param childArea
	 *            the childArea to set
	 */
	public void setChildArea(List<AreaInfo> childArea) {
		this.childArea = childArea;
	}

	/**
	 * @return the parentArea
	 */
	public AreaInfo getParentArea() {
		return parentArea;
	}

	/**
	 * @param parentArea
	 *            the parentArea to set
	 */
	public void setParentArea(AreaInfo parentArea) {
		this.parentArea = parentArea;
	}

}
