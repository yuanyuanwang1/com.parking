/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.wy.superClass.SuperAction;
/**
 * @author Bin
 * 
 */

public class ParkPassWordGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkPassWordGetAction.class);


	@Override
	public String execute() {
		
		//修改密码
		
		HttpSession session = request.getSession();

		pid = (String) session.getAttribute("pid");

		if (StringUtils.isBlank(pid)) {

			return ERROR;
		}
		
		return SUCCESS;
	}

}
