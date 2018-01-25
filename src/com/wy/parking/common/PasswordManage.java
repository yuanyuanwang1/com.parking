/**
 * 
 */
package com.wy.parking.common;

import org.apache.commons.lang.StringUtils;

import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */
public class PasswordManage extends SuperAction {

	private UserService userService = null;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String validationPassword() {

		String pid = request.getParameter("pid");
		String password = request.getParameter("oldPassword");

		if (StringUtils.isBlank(pid) || StringUtils.isBlank(password)) {
			this.printAjaxToPage("false");
			return SUCCESS;
		}

		if (!userService.validationPassword(pid, password)) {
			this.printAjaxToPage("false");
			return SUCCESS;
		}

		this.printAjaxToPage("true");
		return SUCCESS;

	}

}
