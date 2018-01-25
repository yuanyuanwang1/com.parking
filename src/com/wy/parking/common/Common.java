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
public class Common extends SuperAction {

	private UserService userService = null;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String userNameUnique() {

		String userName = request.getParameter("username");

		if (StringUtils.isBlank(userName)) {
			this.printAjaxToPage("false");
			return SUCCESS;
		}

		boolean result = userService.verifyUsernameUniqueAll(userName);

		this.printAjaxToPage(Boolean.toString(result));

		return SUCCESS;

	}

}
