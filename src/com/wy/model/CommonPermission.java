package com.wy.model;

import java.util.Set;

import com.wy.superClass.SuperModel;

public class CommonPermission extends SuperModel {

	// Fields

	private String permissionName = null;

	private String parentId = null;

	private Set<String> part = null;

	/**
	 * @return the permissionName
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * @param permissionName
	 *            the permissionName to set
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the part
	 */
	public Set<String> getPart() {
		return part;
	}

	/**
	 * @param part
	 *            the part to set
	 */
	public void setPart(Set<String> part) {
		this.part = part;
	}

}
