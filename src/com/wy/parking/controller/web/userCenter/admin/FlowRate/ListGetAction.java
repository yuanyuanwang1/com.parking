/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.FlowRate;

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

	private String parkCode = null;

	private String CarNo = null;

	private int SearchPattern;// 查询模式，0：查询，1：获取数量，

	private int FromIndex;// 开始记录编号

	private int RecordCount;// 本次查询记录数量

	private String FromTime = null;// 入场时间段开始

	private String ToTime = null;// 入场时间段截止

	private Map<String, Object> resultMap = null;

	private String parkNos = null;// 子级停车场

	private String pname = null;// 主停车场名车

	private UserService userService = null;

	private String levelStatus = null;

	protected List<Map<String, Object>> levelList = null;

	public String getParkNos() {
		return parkNos;
	}

	public void setParkNos(String parkNos) {
		this.parkNos = parkNos;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public List<Map<String, Object>> getLevelList() {
		return levelList;
	}

	public void setLevelList(List<Map<String, Object>> levelList) {
		this.levelList = levelList;
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

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public String getCarNo() {
		return CarNo;
	}

	public void setCarNo(String carNo) {
		CarNo = carNo;
	}

	public int getFromIndex() {
		return FromIndex;
	}

	public void setFromIndex(int fromIndex) {
		FromIndex = fromIndex;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public int getSearchPattern() {
		return SearchPattern;
	}

	public void setSearchPattern(int searchPattern) {
		SearchPattern = searchPattern;
	}

	@Override
	public String execute() {

		int SearchedCount = 0;

		resultMap = new HashMap<String, Object>();

		list = new ArrayList<Map<String, Object>>();

		HttpSession session = request.getSession();

		parkCode = (String) session.getAttribute("pcode");
		
		if (StringUtils.isNotBlank(parkNos)) {
			parkCode = parkNos;

		}

		String params = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		if (StringUtils.isBlank(CarNo)) {

			// 查询列表
			SearchPattern = 1;

			FromIndex = 1;

			RecordCount = 30;

			params = "parkCode=" + parkCode;

		} else {

			SearchPattern = 0;

			params = "parkCode=" + parkCode + "&CarNo=" + CarNo;
		}

		if (StringUtils.isBlank(FromTime) || StringUtils.isBlank(ToTime)) {

			FromTime = format.format(new Date());

			ToTime = format.format(new Date());

			FromTime = FromTime + "%2000:00:00";

			ToTime = ToTime + "%2023:59:59";

			params += "&FromTime=" + FromTime + "&ToTime=" + ToTime;
		} else {

			FromTime = FromTime.replaceAll("\\s", "%20");

			ToTime = ToTime.replaceAll("\\s", "%20");

			params += "&FromTime=" + FromTime + "&ToTime=" + ToTime;
		}

		map = parkingService.FlowRate(params);

		FromTime = FromTime.replaceAll("%20", " ");

		ToTime = ToTime.replaceAll("%20", " ");

		JSONObject jsonObject = new JSONObject();

		try {

			if (map.get("ResultList") != null) {

				String ResultList = map.get("ResultList").toString();

				JSONArray json = JSONArray.fromObject(ResultList);

				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {

						JSONObject job = json.getJSONObject(i);

						resultMap.put("RecordNo", job.get("RecordNo"));

						resultMap.put("DateString", job.get("DateString"));

						resultMap.put("HourString", job.get("HourString"));

						resultMap.put("tCardCar1Num", job.get("tCardCar1Num"));

						resultMap.put("mCardCar1Num", job.get("mCardCar1Num"));

						resultMap.put("vCardCar1Num", job.get("vCardCar1Num"));

						resultMap.put("nCardCar1Num", job.get("nCardCar1Num"));

						resultMap.put("ManNum", job.get("ManNum"));

						list.add(resultMap);

						resultMap = new HashMap<String, Object>();
					}
				}
			}
			
			int ManNum=0;//定义一个变量
			int nCardCar1Num=0;//定义一个变量
			int vCardCar1Num=0;//定义一个变量
			int mCardCar1Num=0;//定义一个变量
			int tCardCar1Num=0;//定义一个变量
			if(list!=null && list.size()>0)
			{
				for(Map<String,Object> map:list){
					
					ManNum=ManNum+Integer.parseInt(map.get("ManNum").toString());
					
					nCardCar1Num=nCardCar1Num+Integer.parseInt(map.get("nCardCar1Num").toString());
					
					vCardCar1Num=vCardCar1Num+Integer.parseInt(map.get("vCardCar1Num").toString());
					
					mCardCar1Num=mCardCar1Num+Integer.parseInt(map.get("mCardCar1Num").toString());
					
					tCardCar1Num=tCardCar1Num+Integer.parseInt(map.get("tCardCar1Num").toString());
				}
			}

			
			resultMap.put("tCardCar1Num",tCardCar1Num);

			resultMap.put("mCardCar1Num",mCardCar1Num);

			resultMap.put("vCardCar1Num",vCardCar1Num);

			resultMap.put("nCardCar1Num",nCardCar1Num);
			
			resultMap.put("ManNum",ManNum);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		levelStatus = (String) session.getAttribute("levelStatus");

		String userId = (String) session.getAttribute("userId");

		CommonUser commonUser = userService.get(userId);

		Map<String, Object> mapZhu = new HashMap<String, Object>();

		mapZhu.put("pcode", (String) session.getAttribute("pcode"));
		mapZhu.put("pname", (String) session.getAttribute("pname"));

		if (StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus)) {

			levelList = parkingService.getLevelCustomer(commonUser.getPid());

		}

		if (StringUtils.isBlank(parkNos)) {
			parkNos = parkCode;

		}
		
		return SUCCESS;

	}
}
