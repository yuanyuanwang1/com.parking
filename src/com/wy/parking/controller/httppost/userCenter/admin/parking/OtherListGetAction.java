/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin.parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

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
public class OtherListGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(OtherListGetAction.class);

	private ParkingService parkingService = null;

	private String pid = null;

	private CommonUser commonUser = null;
	
	private UserService userService=null;
	
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CommonUser getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(CommonUser commonUser) {
		this.commonUser = commonUser;
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

		// 获取车场的其他车场信息
		
		commonUser=userService.get(pid);
		
		String pid=null;
		
		if(commonUser!=null)
		{
			pid=commonUser.getPid();
		}

		pageInfo = parkingService.getList(getIPageNum(), searchValue,pid);

		return SUCCESS;

	}
}
