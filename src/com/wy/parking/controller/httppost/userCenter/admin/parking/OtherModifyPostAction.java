/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin.parking;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.model.CommonUser;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class OtherModifyPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(OtherModifyPostAction.class);

	private String pid = null;

	private ParkingService parkingService = null;

	private UserService userService = null;

	private String pcode = null;

	private String pname = null;

	private String username = null;

	private String password = null;

	private String descriptions = null;

	private String levelStatus = null;

	protected Map<String, Object> maps = null;

	private CommonUser commonUser = null;

	private String createUserId = null;

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

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

	public Map<String, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {

		if (StringUtils.isBlank(pid)) {

			return ERROR;

		}

		String msg = "";

		String errcode = "";

		commonUser = userService.get(pid);

		// 判断pcode是否存在

		if (StringUtils.isNotBlank(pcode)) {

			map = parkingService.getParkByCode(pcode,commonUser.getPid());

			if (map != null) {
				msg = "车场编号重复,请确认!";
				errcode = "1";
			}
		}
		// 判断username 是否存在

		if (StringUtils.isNotBlank(username)) {

			maps = parkingService.getParkByUsername(username,commonUser.getPid());

			if (maps != null) {
				msg = "登录用户名重复,请确认!";
				errcode = "1";
			}
		}

		if (maps == null && map == null) {

			commonUser.setCreateTime(new Date());

			commonUser.setPcode(pcode);

			commonUser.setPname(pname);

			commonUser.setDescriptions(descriptions);

			commonUser.setUsername(username);

			commonUser.setStatus("0");

			String pidValue = userService.update(commonUser);

			if (StringUtils.isNotBlank(pidValue)) {
				msg = "修改车场信息成功!";
				errcode = "0";
			} else {
				msg = "修改车场信息失败!";
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
