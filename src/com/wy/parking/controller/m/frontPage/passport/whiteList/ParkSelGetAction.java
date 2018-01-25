/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

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

import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;
import com.wy.util.DateUtil;

/**
 * @author Bin
 * 
 */

public class ParkSelGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkSelGetAction.class);

	private String startDate = null;

	private String endDate = null;

	private String pid = null;

	private ParkingService parkingService = null;

	private ParkingOtherService parkingOtherService = null;

	private String searchValue = null;

	protected List<Map<String, Object>> resultlist = null;

	public List<Map<String, Object>> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<Map<String, Object>> resultlist) {
		this.resultlist = resultlist;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
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

	@Override
	public String execute() {
		
		resultlist=new ArrayList<Map<String,Object>>();

		if (StringUtils.isBlank(startDate)) {
			startDate = DateUtil.getDate("yyyy-MM-dd");
		}

		if (StringUtils.isBlank(endDate)) {
			endDate = DateUtil.getDate("yyyy-MM-dd");
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

					resultMap.put("parkName", job.get("parkName"));

					list.add(resultMap);

					resultMap = new HashMap<String, Object>();
				}
			}
		}

		

		return SUCCESS;
	}

	
}
