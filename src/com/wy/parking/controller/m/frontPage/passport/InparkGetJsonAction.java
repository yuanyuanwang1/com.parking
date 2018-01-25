/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;

public class InparkGetJsonAction extends SuperAction {

	private Logger logger = Logger.getLogger(InparkGetJsonAction.class);
	private String plateText = null;
	private String parkId = null;
	private ParkingService parkingService = null;

	private String FromTime = null;// 入场时间段开始

	private String ToTime = null;// 入场时间段截止

	private Map<String, Object> resultMap = null;

	private Map<String, Object> feeMap = null;

	public Map<String, Object> getFeeMap() {
		return feeMap;
	}

	public void setFeeMap(Map<String, Object> feeMap) {
		this.feeMap = feeMap;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
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

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {

		Map<String, Object> map = new HashMap<String, Object>();

		resultMap = new HashMap<String, Object>();

		String params = null;

		String FromTimeDefault = null;
		String ToTimeDefault = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		if (StringUtils.isBlank(plateText)) {

			params = "parkCode=" + parkId;

		} else {

			params = "parkCode=" + parkId+ "&CarNo=" + plateText;
		}

//		if (StringUtils.isBlank(FromTime) || StringUtils.isBlank(ToTime)) {
//
//			FromTime = format.format(new Date());
//
//			ToTime = format.format(new Date());
//
//			FromTime = FromTime + "%2000:00:00";
//
//			ToTime = ToTime + "%2023:59:59";
//
//			params += "&FromTime=" + FromTime + "&ToTime=" + ToTime;
//		} else {
//
//			FromTime = FromTime.replaceAll("\\s", "%20");
//
//			ToTime = ToTime.replaceAll("\\s", "%20");
//
//			params += "&FromTime=" + FromTime + "&ToTime=" + ToTime;
//		}

		map = parkingService.InCar(params);

		try {

			if (map.get("ResultList") != null
					&& map.get("ResultList").toString() != null) {

				String ResultList = map.get("ResultList").toString();

				JSONArray json = JSONArray.fromObject(ResultList);

				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {

						JSONObject job = json.getJSONObject(i);

						resultMap.put("RecordNo", job.get("RecordNo"));

						resultMap.put("ComputeNo", job.get("ComputeNo"));

						resultMap.put("CarNo", job.get("CarNo"));

						resultMap.put("CardType", job.get("CardType"));

						resultMap.put("ParkNo", job.get("ParkNo"));

						String CardAmount = fenToYuan(job.get("CardAmount"));

						resultMap.put("CardAmount", CardAmount);

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

					}

				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 调用计算费用接口
		
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
        String endTime=df.format(new Date());
        int carFee=0;
        //如果付款时间是空，不用付费
        
        if(resultMap.get("PayDateTime")!=null)
        {
    		
    		String feeParams="parkCode="+parkId+"&cardCode="+resultMap.get("CarNo").toString().trim()+"&startTime="
    		+resultMap.get("PayDateTime").toString()+"&endTime="+endTime;

    		feeMap=parkingService.getFee(feeParams);
    		
    		if(feeMap!=null && feeMap.get("data")!=null)
    		{
    			if(!"0".equalsIgnoreCase(feeMap.get("data").toString()))
    			{
    			carFee=Integer.parseInt(feeMap.get("data").toString());
    			
    			carFee=carFee/100;
    			
    			}
    		}
    		
    		System.out.println(feeMap);
        }


		JSONObject obj = new JSONObject();

		obj.put("appCode", "1");
		obj.put("plateText", plateText);
		obj.put("checkInTime", resultMap.get("InDateTime"));

			obj.put("costFee",carFee);

		obj.put("parkId", parkId);
		obj.put("sellType", resultMap.get("CarType"));
		obj.put("presellBeginTime", resultMap.get("InDateTime"));
		obj.put("presellEndTime", "2017-01-01 08:07");
		obj.put("FreeTimeAfterCenterCharge", "30");

		result = obj.toString();

		System.out.println("result" + result);

		return SUCCESS;

	}

	public String getPlateText() {
		return plateText;
	}

	public void setPlateText(String plateText) {
		this.plateText = plateText;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public static String fenToYuan(Object o) {
		if (o == null)
			return "0.00";
		String s = o.toString();
		int len = -1;
		StringBuilder sb = new StringBuilder();
		if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
			s = removeZero(s);
			if (s != null && s.trim().length() > 0
					&& !s.equalsIgnoreCase("null")) {
				len = s.length();
				int tmp = s.indexOf("-");
				if (tmp >= 0) {
					if (len == 2) {
						sb.append("-0.0").append(s.substring(1));
					} else if (len == 3) {
						sb.append("-0.").append(s.substring(1));
					} else {
						sb.append(s.substring(0, len - 2)).append(".")
								.append(s.substring(len - 2));
					}
				} else {
					if (len == 1) {
						sb.append("0.0").append(s);
					} else if (len == 2) {
						sb.append("0.").append(s);
					} else {
						sb.append(s.substring(0, len - 2)).append(".")
								.append(s.substring(len - 2));
					}
				}
			} else {
				sb.append("0.00");
			}
		} else {
			sb.append("0.00");
		}
		return sb.toString();
	}

	public static String removeZero(String str) {
		char ch;
		String result = "";
		if (str != null && str.trim().length() > 0
				&& !str.trim().equalsIgnoreCase("null")) {
			try {
				for (int i = 0; i < str.length(); i++) {
					ch = str.charAt(i);
					if (ch != '0') {
						result = str.substring(i);
						break;
					}
				}
			} catch (Exception e) {
				result = "";
			}
		} else {
			result = "";
		}
		return result;

	}

}
