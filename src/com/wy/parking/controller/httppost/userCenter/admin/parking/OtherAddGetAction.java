/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin.parking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 * 
 */
public class OtherAddGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(OtherAddGetAction.class);

	private CommonUser commonUser = null;

	private UserService userService = null;

	public CommonUser getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(CommonUser commonUser) {
		this.commonUser = commonUser;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute() {
		
		if (StringUtils.isBlank(pid)) {

			return ERROR;

		}

		commonUser = userService.get(pid);

		return SUCCESS;

	}
}
