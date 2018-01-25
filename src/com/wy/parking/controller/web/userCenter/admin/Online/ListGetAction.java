/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.Online;

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

/**
 * @author wy
 * 
 * 
 */
public class ListGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ListGetAction.class);

	private ParkingService parkingService = null;



	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {

		//查询同时在线车场
		
		map=parkingService.getparkInfos();
		
		
		if (map.get("ResultList") != null) {
			
			Map<String,Object> resultMap=new HashMap<String, Object>();
			
			list=new ArrayList<Map<String,Object>>();

			String ResultList = map.get("ResultList").toString();

			JSONArray json = JSONArray.fromObject(ResultList);

			if (json.size() > 0) {
				for (int i = 0; i < json.size(); i++) {

					JSONObject job = json.getJSONObject(i);

					resultMap.put("parkCode", job.get("parkCode"));

					resultMap.put("parkName", job.get("parkName"));

					resultMap.put("WorkerVersion", job.get("WorkerVersion"));

					resultMap.put("WorkerStartTime", job.get("WorkerStartTime"));

					resultMap.put("WorkerConnectTimes", job.get("WorkerConnectTimes"));

					resultMap.put("ConnectTime", job.get("ConnectTime"));

					resultMap.put("RequestCounter", job.get("RequestCounter"));

					resultMap.put("RequestFailCounter", job.get("RequestFailCounter"));

					resultMap.put("LastRequestFailMessage", job.get("LastRequestFailMessage"));

					list.add(resultMap);

					resultMap = new HashMap<String, Object>();
				}
			}
		}
		
		Integer iPageNum = getIPageNum();
		
		
		pageInfo = parkingService.getPage(list, iPageNum, 1);
		
		
		return SUCCESS;

	}


}
