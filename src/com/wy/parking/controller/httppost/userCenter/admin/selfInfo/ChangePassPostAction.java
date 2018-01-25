/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin.selfInfo;

import org.apache.log4j.Logger;
import net.sf.json.JSONObject;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author Administrator
 * 
 */
public class ChangePassPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ChangePassPostAction.class);

	private String newPassword = null;

	private String oldPassword = null;

	private UserService userService = null;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute() {

		JSONObject jsonObject = new JSONObject();

		if (!userService.validationPassword(getUserId(), oldPassword)) {

			jsonObject.put("status", "oldPasswordError");
			result = jsonObject.toString();
			return SUCCESS;
		}

		userService.editPassword(getUserId(), newPassword);
		jsonObject.put("status", "changePassOK");
		result = jsonObject.toString();
		return SUCCESS;

	}

}
