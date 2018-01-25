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

import com.wy.model.CommonUser;
import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */

public class ParkAddPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkAddPostAction.class);

	private ParkingOtherService parkingOtherService = null;

	private ParkingUser parkingUser = null;

	private String pname = null;

	private String pcode = null;

	private String password = null;

	private String descriptions = null;

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

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	@Override
	public String execute() {

		// 新增用户
		
		//校验用户名是否重复
		String msg = "";

		String errcode = "";

		if (StringUtils.isNotBlank(pcode)) {

			map = parkingOtherService.getParkByCode(pcode, null);

			if (map != null) {
				msg = "账号重复,请确认!";
				errcode = "1";
			}
		}
		
		if(map==null)
		{
			//进行插入
			ParkingUser parkingUser = new ParkingUser();

			parkingUser.setCreateTime(new Date());
			
			parkingUser.setLastUpTime(new Date());

			parkingUser.setPname(pname);

			parkingUser.setDescriptions(descriptions);

			parkingUser.setPcode(pcode);

			parkingUser.setPassword(password);

			parkingUser.setStatus("1");
		
			parkingUser.setRoleId("1");
			
			String pidValue = parkingOtherService.save(parkingUser);

			if (StringUtils.isNotBlank(pidValue)) {
				msg = "添加账号成功!";
				errcode = "0";
			}else
			{
				msg = "添加账号失败!";
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
