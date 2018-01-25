/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.ChargeRecord;

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
	
	private String OperatorName=null;
	
	

	public String getOperatorName() {
		return OperatorName;
	}

	public void setOperatorName(String operatorName) {
		OperatorName = operatorName;
	}

	public List<Map<String, Object>> getLevelList() {
		return levelList;
	}

	public void setLevelList(List<Map<String, Object>> levelList) {
		this.levelList = levelList;
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
		
		if(StringUtils.isBlank(OperatorName))
		{
			OperatorName="";
		}

		resultMap = new HashMap<String, Object>();

		list = new ArrayList<Map<String, Object>>();

		HttpSession session = request.getSession();

		parkCode = (String) session.getAttribute("pcode");

		if (StringUtils.isNotBlank(parkNos)) {
			parkCode = parkNos;

		}

		String params = null;

		String FromTimeDefault = null;
		String ToTimeDefault = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		if (StringUtils.isBlank(CarNo)) {

			// 查询列表
			SearchPattern = 1;

			FromIndex = 1;

			RecordCount = 30;

			params = "parkCode=" + parkCode + "&OperatorName=" + OperatorName;

		} else {

			SearchPattern = 0;

			params = "parkCode=" + parkCode + "&OperatorName=" + OperatorName + "&FromTime="
					+ FromTime + "&ToTime=" + ToTime;
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

		map = parkingService.ChargeRecord(params);

		FromTime = FromTime.replaceAll("%20", " ");

		ToTime = ToTime.replaceAll("%20", " ");

		JSONObject jsonObject = new JSONObject();

		try {

			if (map.get("ResultList") != null
					&& map.get("ResultList").toString() != null) {

				String ResultList = map.get("ResultList").toString();

				JSONArray json = JSONArray.fromObject(ResultList);

				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {

						JSONObject job = json.getJSONObject(i);

						resultMap.put("RecordNo", job.get("RecordNo"));

						resultMap.put("OperatorName", job.get("OperatorName"));

						resultMap.put("DateString", job.get("DateString"));

						resultMap.put("HourString", job.get("HourString"));

						resultMap.put("IssueAmt", job.get("IssueAmt"));

						resultMap.put("DelayAmt", job.get("DelayAmt"));

						resultMap.put("AddAmt", job.get("AddAmt"));

						resultMap.put("CarFeeAmt", job.get("CarFeeAmt"));

						resultMap.put("CardFeePay", job.get("CardFeePay"));

						resultMap.put("CarFeePay", job.get("CarFeePay"));

					
						list.add(resultMap);

						resultMap = new HashMap<String, Object>();
					}

					Integer iPageNum = getIPageNum();

					if (map.get("SearchedCount") != null && json.size() > 0) {

						SearchedCount = Integer.parseInt(map.get(
								"SearchedCount").toString());

						pageInfo = parkingService.getPage(list, iPageNum, 1);
					}
				}
			}

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
				&& "1".equalsIgnoreCase(levelStatus) && !"admin".equals(commonUser.getUsername())) {

			levelList = parkingService.getLevelCustomer(commonUser.getPid());

		}else if(StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus) && "admin".equals(commonUser.getUsername()))
		{
			levelList = parkingService.getLevelCustomer();
		}

		if (StringUtils.isBlank(parkNos)) {
			parkNos = parkCode;

		}

		return SUCCESS;

	}

	/**
	 * 
	 * 功能描述：金额字符串转换：单位分转成单元
	 * 
	 * @param str
	 *            传入需要转换的金额字符串
	 * @return 转换后的金额字符串
	 */
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

	/**
	 * 
	 * 功能描述：去除字符串首部为"0"字符
	 * 
	 * @param str
	 *            传入需要转换的字符串
	 * @return 转换后的字符串
	 */
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
