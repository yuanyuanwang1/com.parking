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
import com.wy.util.DateUtil;

/**
 * @author Bin
 * 
 */

public class ParkIndexGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkIndexGetAction.class);

	private String roleId = null;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String execute() {

		HttpSession session = request.getSession();

		pid = (String) session.getAttribute("pid");
		
		roleId = (String) session.getAttribute("roleId");

		if (StringUtils.isBlank(pid)) {

			return ERROR;
		}

		return SUCCESS;
	}

}
