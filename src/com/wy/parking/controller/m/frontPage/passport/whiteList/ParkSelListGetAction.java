/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.parking.model.WhiteList;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;
import com.wy.util.DateUtil;

/**
 * @author Bin
 * 
 */

public class ParkSelListGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkSelListGetAction.class);

	private String parkCode = null;

	private String pid = null;

	private ParkingService parkingService = null;

	private ParkingOtherService parkingOtherService = null;

	private String searchValue = null;

	protected List<Map<String, Object>> resultlist = null;

	private Map<String, Object> resultMap = null;
	
	private String startDate=null;
	
	private String endDate=null;
	
	

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

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

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

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	@Override
	public String execute() {

		// 进行白名单查询

		resultMap = new HashMap<String, Object>();

		list=new ArrayList<Map<String,Object>>();
		
		String params = null;

		if (StringUtils.isNotBlank(parkCode)) {

			params = "parkCode=" + parkCode;

		}
		map = parkingService.ListCar(params);

		JSONObject jsonObject = new JSONObject();

		try {

			if (map.get("ResultList") != null) {
				
				
				//进行数据库保存操作，先删除parkCode的白名单
				parkingOtherService.deleteByCode(parkCode);

				String ResultList = map.get("ResultList").toString();

				JSONArray json = JSONArray.fromObject(ResultList);

				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {

						JSONObject job = json.getJSONObject(i);

						resultMap.put("CarNo", job.get("CarNo"));

						resultMap.put("CardType", job.get("CardType"));
						
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
						
						SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
						
						try {
							
							java.util.Date date=sdf.parse(job.get("CardIndate").toString());
							
							resultMap.put("CardIndate", sdf1.format(date));
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String CardAmount = fenToYuan(job.get("CardAmount"));

						resultMap.put("CardAmount", CardAmount);

						resultMap.put("CarType", job.get("CarType"));

						resultMap.put("MasterName", job.get("MasterName"));

						resultMap.put("MasterTel", job.get("MasterTel"));

						resultMap.put("MasterAddr", job.get("MasterAddr"));

						resultMap.put("ParkPosition", job.get("ParkPosition"));

						resultMap.put("PayAmount", job.get("PayAmount"));
						
						if(job.get("Remark")!=null)
						{
							//根據备注查询下发白名单人员
							Map<String,Object> mapUser=parkingOtherService.getMapOnePname(job.get("Remark").toString());
							
							if(mapUser!=null)
							{
							
							resultMap.put("Remark", mapUser.get("pname"));
							}
						}

						list.add(resultMap);


						//进行数据库保存操作
						
						WhiteList whiteList=new WhiteList();
						
						whiteList.setCardAmount(CardAmount);
						
						whiteList.setCarNo(job.get("CarNo").toString());
						
						try {
							whiteList.setCardIndate(sdf1.parse(resultMap.get("CardIndate").toString()));
														
							if(resultMap.get("CardType")!=null)
							{
								whiteList.setCardType(resultMap.get("CardType").toString());
							}
							if(resultMap.get("CarType")!=null)
							{
								whiteList.setCarType(resultMap.get("CarType").toString());
							}
							if(resultMap.get("MasterName")!=null)
							{
								whiteList.setMasterName(resultMap.get("MasterName").toString());
							}
							if(resultMap.get("MasterTel")!=null)
							{
								whiteList.setMasterTel(resultMap.get("MasterTel").toString());
							}
							if(resultMap.get("MasterAddr")!=null)
							{
								whiteList.setMasterAddr(resultMap.get("MasterAddr").toString());
							}
							if(resultMap.get("ParkPosition")!=null)
							{
								whiteList.setParkPosition(resultMap.get("ParkPosition").toString());
							}
							if(resultMap.get("PayAmount")!=null)
							{
								whiteList.setPayAmount(resultMap.get("PayAmount").toString());
							}

							if(resultMap.get("Remark")!=null)
							{
								whiteList.setRemark(resultMap.get("Remark").toString());
							}
							
							whiteList.setParkCode(parkCode);
							
							whiteList.setStatus("1");
					
							parkingOtherService.save(whiteList);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						resultMap = new HashMap<String, Object>();
					}
				}
			}
			
			//模糊查询
			//list=likeString(list, searchValue);
			
			list=parkingOtherService.whiteList(searchValue, startDate, endDate, parkCode);
			
			Integer iPageNum = getIPageNum();

			if (map.get("SearchedCount") != null) {

				pageInfo = parkingService.getPage(list, iPageNum, 1);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	// 模糊查询
	public List<Map<String,Object>> likeString(List<Map<String,Object>> list,String searchValue) {
		
	   	 List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		
		 Pattern pattern = Pattern.compile(searchValue);
		 
		for (int i = 0; i < list.size(); i++) {
			
			if(list.get(i).get("CarNo")!=null)
			{
				
				Matcher matcher = pattern.matcher((list.get(i)).get("CarNo").toString());
				
				if(matcher.find()){
					
					resultList.add(list.get(i));
			      }
				
			}
			
			if(list.get(i).get("Remark")!=null)
			{
				
				Matcher matcher = pattern.matcher((list.get(i)).get("Remark").toString());
				
				if(matcher.find()){
					
					resultList.add(list.get(i));
			      }
				
			}

		}
		return resultList;

	}
	

}
