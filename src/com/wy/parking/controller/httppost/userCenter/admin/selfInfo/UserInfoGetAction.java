/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin.selfInfo;

import java.util.List;

import org.apache.log4j.Logger;

import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author Administrator
 * 
 */
public class UserInfoGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(UserInfoGetAction.class);

	private String roleId = null;

	private UserService userService = null;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute() {


		return SUCCESS;

	}

}
