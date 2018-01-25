/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.DigestUtils;
import com.wy.parking.service.ParkingOtherService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */

public class ParkLoginPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkLoginPostAction.class);

	private String username = null;

	private String password = null;

	private ParkingOtherService parkingOtherService = null;

	private String result = null;

	private String errorInfo = null;

	private String pid = null;
	
	private String roleId=null;
	
	
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}


	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String execute() {

		// 停车场组长进行登录，进行补录

		JSONObject jsonObject = new JSONObject();

		if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {

//			password = passwordDigest(password);
			
			list=parkingOtherService.getSystemAccount(username, password);

			if(list != null && list.size()>0)
			{
				pid=list.get(0).get("pid").toString();
				
				roleId=list.get(0).get("role_id").toString();
			}

			
			if (pid== null) {
				errorInfo = "用户名或密码不正确！！";
				try {
					jsonObject.put("loginResult", "loginError");
					result = jsonObject.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
			try {
				jsonObject.put("loginResult", "loginOK");
				result = jsonObject.toString();
				session.put("pid", pid);
				session.put("roleId", roleId);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		} else {
			errorInfo = "用户名或密码不能为空！！";
		}

		return SUCCESS;
	}

	public static String passwordDigest(String password) {
		return DigestUtils.md5DigestAsHex((password).getBytes());
	}

}
