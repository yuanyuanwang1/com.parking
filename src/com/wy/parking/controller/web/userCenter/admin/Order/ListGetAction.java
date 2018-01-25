/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.wy.util.DateUtil;

/**
 * @author wy
 * 
 * 
 */
public class ListGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ListGetAction.class);

	private ParkingService parkingService = null;

	private String parkId=null;
	
	private String CarNo=null;
	
	private String startDate=null;
	
	private String endDate=null;
	
	private String levelStatus=null;
	
	private UserService userService=null;
	
	protected List<Map<String, Object>> levelList = null;
	
	
	private String startDate1=null;
	
	private String endDate1=null;
	
	

	public String getStartDate1() {
		return startDate1;
	}

	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}

	public String getEndDate1() {
		return endDate1;
	}

	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getLevelList() {
		return levelList;
	}

	public void setLevelList(List<Map<String, Object>> levelList) {
		this.levelList = levelList;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getCarNo() {
		return CarNo;
	}

	public void setCarNo(String carNo) {
		CarNo = carNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {
	
		if(StringUtils.isBlank(startDate))
		{
			startDate = DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
			
			startDate1 = DateUtil.getDate("yyyy-MM-dd");
			
			startDate = startDate1 + " 00:00:00";

		}

		if (StringUtils.isBlank(endDate)) {
			endDate =DateUtil.getDate("yyyy-MM-dd HH:mm:ss");
			
			endDate1 =DateUtil.getDate("yyyy-MM-dd");
			
			endDate = endDate1 + " 23:59:59";
		}
		
		HttpSession session = request.getSession();

		parkId = (String) session.getAttribute("pcode");
		
		//订单列表
		

		
		list=parkingService.getAdminInfo(getIPageNum(),parkId, CarNo, startDate, endDate);
		
		pageInfo = parkingService.getPage(list, getIPageNum(), 1);
		
		levelStatus = (String) session.getAttribute("levelStatus");
		
		String userId = (String) session.getAttribute("userId");

		CommonUser commonUser = userService.get(userId);

		if (StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus)) {

			levelList = parkingService.getLevelCustomer(commonUser.getPid());

		}
		
		return SUCCESS;

	}


}
