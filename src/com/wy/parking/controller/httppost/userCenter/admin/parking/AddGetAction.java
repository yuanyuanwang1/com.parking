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
public class AddGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(AddGetAction.class);

	@Override
	public String execute() {

		return SUCCESS;

	}
}
