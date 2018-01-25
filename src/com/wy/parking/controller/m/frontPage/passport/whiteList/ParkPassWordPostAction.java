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
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */

public class ParkPassWordPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkPassWordPostAction.class);

	private ParkingOtherService parkingOtherService = null;

	private String password = null;

	private String newPasssword = null;
	
	private ParkingUser parkingUser=null;
	
	

	public ParkingUser getParkingUser() {
		return parkingUser;
	}

	public void setParkingUser(ParkingUser parkingUser) {
		this.parkingUser = parkingUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPasssword() {
		return newPasssword;
	}

	public void setNewPasssword(String newPasssword) {
		this.newPasssword = newPasssword;
	}

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	@Override
	public String execute() {

		// 修改密码

		String msg = "";

		String errcode = "";

		HttpSession session = request.getSession();

		pid = (String) session.getAttribute("pid");

		if (StringUtils.isBlank(pid)) {

			msg = "主键为空!";
			errcode = "1";
		}

		if (StringUtils.isNotBlank(pid)) {
			
			parkingUser=parkingOtherService.get(pid);
			
			if(parkingUser!=null)
			{
				if(!password.equalsIgnoreCase(parkingUser.getPassword()))
				{
					msg = "原密码不正确";
					errcode = "1";
				}else
				{
					parkingUser.setPassword(newPasssword);
					
					parkingOtherService.update(parkingUser);
					
					msg = "密码修改成功";
					errcode = "0";
				}
			}

		}
		
		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put("resultCode", errcode);

			jsonObject.put("message", msg);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = jsonObject.toString();

		return SUCCESS;
	}

}
