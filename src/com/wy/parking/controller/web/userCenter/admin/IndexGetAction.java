/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.parking.service.ParkingService;
import com.wy.dao.PageInfo;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class IndexGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(IndexGetAction.class);

	private UserService userService = null;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute() {
		
		
		map = userService.getMapOne("common_user", getUserId());
		
		return SUCCESS;

	}
}
