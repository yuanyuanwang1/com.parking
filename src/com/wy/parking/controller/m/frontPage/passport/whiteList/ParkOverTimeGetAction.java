/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;
import com.wy.util.DateUtil;

/**
 * @author wy
 * 
 * 
 */
public class ParkOverTimeGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkOverTimeGetAction.class);

	private String dateNow = null;

	private String levelStatus = null;

	private ParkingService parkingService = null;
	
	private ParkingOtherService parkingOtherService=null;

	private UserService userService = null;

	private Map<String, Object> resultMap = null;
	
	private String FromTime = null;

	private String ToTime = null;
	
	

	public String getFromTime() {
		return FromTime;
	}

	public void setFromTime(String fromTime) {
		FromTime = fromTime;
	}

	public String getToTime() {
		return ToTime;
	}

	public void setToTime(String toTime) {
		ToTime = toTime;
	}

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public String getDateNow() {
		return dateNow;
	}

	public void setDateNow(String dateNow) {
		this.dateNow = dateNow;
	}

	@Override
	public String execute() {
		
		
		HttpSession session = request.getSession();

		pid = (String) session.getAttribute("pid");
		
		if(pid==null)
			
		{
			return ERROR;
		}


		if (StringUtils.isBlank(FromTime)) {
			FromTime = DateUtil.getDate("yyyy-MM-dd");
		}

		if (StringUtils.isBlank(ToTime)) {
			ToTime = DateUtil.getDate("yyyy-MM-dd");
		}

		// 查询在线车场有多少

		map = parkingService.getparkInfos();

		if (map.get("ResultList") != null) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			list = new ArrayList<Map<String, Object>>();

			String ResultList = map.get("ResultList").toString();

			JSONArray json = JSONArray.fromObject(ResultList);

			if (json.size() > 0) {
				for (int i = 0; i < json.size(); i++) {

					JSONObject job = json.getJSONObject(i);

					resultMap.put("parkCode", job.get("parkCode"));
					
					Map<String,Object> maps=parkingOtherService.getParkNo(job.get("parkCode").toString());
					
					if(maps!=null)
					{
						resultMap.put("parkName",maps.get("pname"));
	
					}else
					{
						resultMap.put("parkName",job.get("parkCode"));
					}


					list.add(resultMap);

					resultMap = new HashMap<String, Object>();
				}
			}
		}

		return SUCCESS;

	}
}
