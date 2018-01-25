/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.model.CommonUser;
import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class ParkModifyPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkModifyPostAction.class);

	private String pid = null;

	private ParkingOtherService parkingOtherService = null;

	private String pname = null;

	private String pcode = null;

	private String password = null;

	private String descriptions = null;

	private ParkingUser parkingUser = null;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public ParkingUser getParkingUser() {
		return parkingUser;
	}

	public void setParkingUser(ParkingUser parkingUser) {
		this.parkingUser = parkingUser;
	}

	@Override
	public String execute() {
		
		String msg = "";

		String errcode = "";


		if (StringUtils.isBlank(pid)) {

			msg = "主键为空!";
			errcode = "1";
			
			return SUCCESS;

		}

		parkingUser = parkingOtherService.get(pid);

		// 判断pcode是否存在

		if (StringUtils.isNotBlank(pcode)) {

			map = parkingOtherService.getParkByCode(pcode, pid);

			if (map != null) {
				msg = "账号重复,请确认!";
				errcode = "1";
			}
		}

		if (map == null) {

			parkingUser.setLastUpTime(new Date());

			parkingUser.setPname(pname);

			parkingUser.setDescriptions(descriptions);

			parkingUser.setPcode(pcode);

			parkingUser.setPassword(password);

			String pidValue = parkingOtherService.update(parkingUser);

			if (StringUtils.isNotBlank(pidValue)) {
				msg = "修改账号信息成功!";
				errcode = "0";
			} else {
				msg = "修改账号信息失败!";
				errcode = "1";
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
