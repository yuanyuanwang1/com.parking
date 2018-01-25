/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin.parking;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.model.CommonUser;
import com.wy.parking.service.ParkingService;
import com.wy.service.CommonService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 * 
 */
public class AddPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(AddPostAction.class);

	private ParkingService parkingService = null;

	private UserService userService = null;

	private String pcode = null;

	private String pname = null;

	private String username = null;

	private String password = null;

	private String descriptions = null;

	private String levelStatus = null;

	protected Map<String, Object> maps = null;

	public Map<String, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
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

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {

		if (StringUtils.isNotBlank(password)) {
			password = userService.passwordDigest(password);
		}

		String msg = "";

		String errcode = "";

		// 判断pcode是否存在

		if (StringUtils.isNotBlank(pcode)) {

			map = parkingService.getParkByCode(pcode,null);

			if (map != null) {
				msg = "车场编号重复,请确认!";
				errcode = "1";
			}
		}
		// 判断username 是否存在

		if (StringUtils.isNotBlank(username)) {

			maps = parkingService.getParkByUsername(username,null);

			if (maps != null){
				msg = "登录用户名重复,请确认!";
				errcode = "1";
			}
		}

		if (maps==null && map==null) {
			CommonUser commonUser = new CommonUser();

			commonUser.setCreateTime(new Date());

			commonUser.setPcode(pcode);

			commonUser.setPname(pname);

			commonUser.setDescriptions(descriptions);

			commonUser.setLevelStatus(levelStatus);

			commonUser.setUsername(username);
			
			commonUser.setPassword(password);
			
			commonUser.setStatus("1");

			String pidValue = userService.save(commonUser);
			
			CommonUser commonUserNew =userService.get(commonUser.getPid());
			
			commonUserNew.setCreateUserId(commonUserNew.getPid());
			
			userService.update(commonUserNew);

			if (StringUtils.isNotBlank(pidValue)) {
				msg = "添加车场成功!";
				errcode = "0";
			} else {
				msg = "添加车场失败!";
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
