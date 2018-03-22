/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 * 
 */
public class ParkOverTimePostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkOverTimePostAction.class);

	private String dateNow = null;

	private String levelStatus = null;

	private ParkingService parkingService = null;

	private ParkingOtherService parkingOtherService = null;

	private UserService userService = null;

	private Map<String, Object> resultMap = null;

	// 子级停车场字段
	private ParkingUser parkingUser = null;

	private String parkNos = null;

	private String FromTime = null;

	private String ToTime = null;

	private String CarNo = null;

	protected List<Map<String, Object>> whiteList = null;

	private Map<String, Object> whiteMap = null;

	protected List<Map<String, Object>> resultList = null;
	
	protected Map<String, Object> resultListMap = null;

	protected Map<String, Object> parkMap = null;
	
	
	protected List<Map<String, Object>> dataList = null;
	
	
	

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public Map<String, Object> getResultListMap() {
		return resultListMap;
	}

	public void setResultListMap(Map<String, Object> resultListMap) {
		this.resultListMap = resultListMap;
	}

	public Map<String, Object> getParkMap() {
		return parkMap;
	}

	public void setParkMap(Map<String, Object> parkMap) {
		this.parkMap = parkMap;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public List<Map<String, Object>> getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(List<Map<String, Object>> whiteList) {
		this.whiteList = whiteList;
	}

	public Map<String, Object> getWhiteMap() {
		return whiteMap;
	}

	public void setWhiteMap(Map<String, Object> whiteMap) {
		this.whiteMap = whiteMap;
	}

	public String getCarNo() {
		return CarNo;
	}

	public void setCarNo(String carNo) {
		CarNo = carNo;
	}

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

	public ParkingUser getParkingUser() {
		return parkingUser;
	}

	public void setParkingUser(ParkingUser parkingUser) {
		this.parkingUser = parkingUser;
	}

	public String getParkNos() {
		return parkNos;
	}

	public void setParkNos(String parkNos) {
		this.parkNos = parkNos;
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

		if (pid == null)

		{
			return ERROR;
		} else {
			parkingUser = parkingOtherService.get(pid);

		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String msg = " ";

		String code = "0";

		String params = null;

		if (StringUtils.isNotBlank(parkNos) && parkNos != "") {

			// 查询在场车辆信息

			params = "parkCode=" + parkNos;

			// 获取车场信息
			parkMap = parkingService.getParkNo(parkNos);
		}

		if (StringUtils.isNotBlank(FromTime) || StringUtils.isNotBlank(ToTime)) {

			// FromTime = format.format(new Date());
			//
			// ToTime = format.format(new Date());

			FromTime = FromTime + "%2000:00:00";

			ToTime = ToTime + "%2023:59:59";

			params += "&FromTime=" + FromTime + "&ToTime=" + ToTime;
		}

		map = parkingService.InCar(params);

		JSONObject jsonObject = new JSONObject();

		try {

			if (map.get("ResultList") != null
					&& map.get("ResultList").toString() != null) {

				String ResultList = map.get("ResultList").toString();

				resultMap = new HashMap<String, Object>();

				list = new ArrayList<Map<String, Object>>();

				JSONArray json = JSONArray.fromObject(ResultList);

				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {

						JSONObject job = json.getJSONObject(i);

						resultMap.put("RecordNo", job.get("RecordNo"));

						resultMap.put("ComputeNo", job.get("ComputeNo"));

						resultMap.put("CarNo", job.get("CarNo"));

						resultMap.put("CardType", job.get("CardType"));

						resultMap.put("ParkNo", job.get("ParkNo"));

						resultMap.put("CarType", job.get("CarType"));

						resultMap.put("MasterName", job.get("MasterName"));

						resultMap.put("MasterTel", job.get("MasterTel"));

						resultMap.put("MasterAddr", job.get("MasterAddr"));

						resultMap.put("ParkPosition", job.get("ParkPosition"));

						resultMap.put("InTrackName", job.get("InTrackName"));

						resultMap.put("InDateTime", job.get("InDateTime"));

						resultMap.put("InOperatorName",
								job.get("InOperatorName"));

						resultMap.put("CarFee", job.get("CarFee"));

						resultMap.put("PayAmount", job.get("PayAmount"));

						resultMap
								.put("CardPayAmount", job.get("CardPayAmount"));

						resultMap.put("PayDateTime", job.get("PayDateTime"));

						resultMap.put("ParkTime", job.get("ParkTime"));

						resultMap.put("Remark", job.get("Remark"));

						list.add(resultMap);

						resultMap = new HashMap<String, Object>();
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 查询白名单

		whiteMap = parkingService.ListCar(params);

		if (whiteMap.get("ResultList") != null) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			whiteList = new ArrayList<Map<String, Object>>();

			String ResultList = whiteMap.get("ResultList").toString();

			JSONArray json = JSONArray.fromObject(ResultList);

			if (json.size() > 0) {
				for (int i = 0; i < json.size(); i++) {

					JSONObject job = json.getJSONObject(i);

					resultMap.put("CarNo", job.get("CarNo"));

					resultMap.put("CardType", job.get("CardType"));

					resultMap.put("CardIndate", job.get("CardIndate"));

					resultMap.put("CarType", job.get("CarType"));

					resultMap.put("MasterName", job.get("MasterName"));

					resultMap.put("MasterTel", job.get("MasterTel"));

					resultMap.put("MasterAddr", job.get("MasterAddr"));

					resultMap.put("ParkPosition", job.get("ParkPosition"));

					resultMap.put("PayAmount", job.get("PayAmount"));

					resultMap.put("Remark", job.get("Remark"));

					whiteList.add(resultMap);

					resultMap = new HashMap<String, Object>();
				}
			}
		}

		resultList = new ArrayList<Map<String, Object>>();

		if (list != null && whiteList != null) {
			
			
			Map<String, Integer> mapAll = new HashMap<String, Integer>();

			for (Map<String, Object> map : list) {

				String key = (String) map.get("CarNo");

				if (mapAll.containsKey(key)) {

					Integer value = mapAll.get(key);

					mapAll.put(key, value + 1);

					continue;

				}

				mapAll.put(key, 1);

			}

			for (Map<String, Object> map : whiteList) {

				String key = (String) map.get("CarNo");

				if (mapAll.containsKey(key)) {

					Integer value = mapAll.get(key);

					mapAll.put(key, value + 1);

					continue;

				}

				mapAll.put(key, 1);

			}

			for (Map.Entry<String, Integer> entry : mapAll.entrySet()) {
				
				if(entry.getValue()>1)
				{
					String carNo=entry.getKey();
					
					//获取此车牌号的信息
					params+="&CarNo=" + carNo;
					
					for(Map<String,Object> map1:list)
					{
						if(carNo.equalsIgnoreCase(map1.get("CarNo").toString())){
							
							resultListMap=new HashMap<String, Object>();
							
							resultListMap.put("CarNo", map1.get("CarNo"));
							
							resultListMap.put("InDateTime", map1.get("InDateTime"));
							
							resultListMap.put("RecordNo", map1.get("RecordNo"));
							
							resultListMap.put("ComputeNo", map1.get("ComputeNo"));
							
							resultListMap.put("CardType", map1.get("CardType"));
							
							resultListMap.put("CarType", map1.get("CarType"));
							
							resultListMap.put("ParkNo", map1.get("ParkNo"));
							
							
							for(Map<String,Object> map2:whiteList)
							{
								if(carNo.equalsIgnoreCase(map2.get("CarNo").toString())){
																		
									resultListMap.put("CardIndate", map2.get("CardIndate"));
											
								}
							}
							
							
							resultList.add(resultListMap);
							
									
						}
					}
					
					System.out.println(resultList);
					
				}
			}

			dataList=new ArrayList<Map<String,Object>>();

			
			for (int i = 0; i < resultList.size(); i++) {

					Map<String, Object> map = new HashMap<String, Object>();

					String date = resultList.get(i).get("InDateTime").toString();

					String indate = null;

					try {

						Date indate1 = format.parse(whiteList.get(i)
								.get("CardIndate").toString());

						indate = format.format(indate1) + " 23:59:59";

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int va = date.compareTo(indate);

					if (va == 1) {
						// 进场时间大于预约时间
						

						map.put("RecordNo", resultList.get(i).get("RecordNo"));

						map.put("ComputeNo", resultList.get(i).get("ComputeNo"));

						map.put("CarNo", resultList.get(i).get("CarNo"));

						map.put("CardType", resultList.get(i).get("CardType"));

						map.put("ParkNo", resultList.get(i).get("ParkNo"));

						map.put("CarType", resultList.get(i).get("CarType"));

						map.put("InDateTime", resultList.get(i).get("InDateTime"));

						map.put("CardIndate", resultList.get(i)
								.get("CardIndate"));

						map.put("ParkName", parkMap.get("pname"));

						long overtime = overTime(resultList.get(i).get("InDateTime")
								.toString(), resultList.get(i).get("CardIndate")
								.toString());

						map.put("OverTime", overtime);

						dataList.add(map);

						map = new HashMap<String, Object>();
					}

				}

		}
		
		Integer iPageNum = getIPageNum();


		pageInfo = parkingService.getPage(dataList, iPageNum, 1);
	

		if (StringUtils.isNotBlank(FromTime) || StringUtils.isNotBlank(ToTime)) {

			FromTime = FromTime.replaceAll("%20", " ");

			ToTime = ToTime.replaceAll("%20", " ");

		}

		return SUCCESS;

	}

	public long overTime(String InDateTime, String CardIndate) {
		long overTime = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse(InDateTime);
			Date d2 = df.parse(CardIndate);
			System.out.println((d1.getTime() - d2.getTime()) / (1000 * 60*60*24));// 得到天数

			overTime = (d1.getTime() - d2.getTime()) / (1000 * 60*60*24);
		} catch (Exception e) {

		}
		return overTime;
	}

}
