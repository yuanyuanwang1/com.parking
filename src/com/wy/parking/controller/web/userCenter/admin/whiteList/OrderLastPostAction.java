/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.whiteList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 * 
 */
public class OrderLastPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(OrderLastPostAction.class);

	private ParkingService parkingService = null;

	private String parkCode = null;

	private String cardCode = null;

	private String CardIndate = null;

	private String PayAmount = null;

	private String Remark = null;

	// 子级停车场字段

	private String parkNos = null;
	
	private Map<String, Object> resultMap = null;
	
	

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

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardIndate() {
		return CardIndate;
	}

	public void setCardIndate(String cardIndate) {
		CardIndate = cardIndate;
	}

	public String getPayAmount() {
		return PayAmount;
	}

	public void setPayAmount(String payAmount) {
		PayAmount = payAmount;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getParkNos() {
		return parkNos;
	}

	public void setParkNos(String parkNos) {
		this.parkNos = parkNos;
	}

	@Override
	public String execute() {

		// 下发预约信息接口，作为白名单接口

		HttpSession session = request.getSession();


		parkCode = (String) session.getAttribute("pcode");
		

		String msg = " ";

		String code="0";
		
//		if(StringUtils.isNotBlank(parkCode) && !"1".equalsIgnoreCase(parkCode))
//		{
//		
//		
//		
//
//		String params = "parkCode=" + parkCode + "&CarNo=" + cardCode
//				+ "&CardIndate=" + CardIndate + "&PayAmount=" + PayAmount
//				+ "&Remark=" + Remark;
//
//		// List<NameValuePair> params = new ArrayList<NameValuePair>();
//		//
//		// params.add(new BasicNameValuePair("parkCode",parkCode));
//		// params.add(new BasicNameValuePair("cardCode",cardCode));
//		// params.add(new BasicNameValuePair("startTime",startTime));
//		// params.add(new BasicNameValuePair("endTime",endTime));
//		// params.add(new BasicNameValuePair("flag",flag));
//
//		map = parkingService.modifyPresellTime(params);
//
//		if (!"0".equalsIgnoreCase(map.get("resultCode").toString())) {
//			String parkName = parkingService.getParkNo(parkCode).get("pname")
//					.toString();
//
//			msg = msg + parkName + map.get("message").toString();
//
//			code = "-1";
//		} else {
//			code = "0";
//		}
//		}

		String OldCardIndate=null;//旧截止有效期
		
		resultMap = new HashMap<String, Object>();
		
		if (StringUtils.isNotBlank(parkNos) && parkNos != "") {
			// 给子级停车场车辆延期
			String[] sourceStrArray = parkNos.split(",");

			for (int i = 0; i < sourceStrArray.length; i++) {

				map = new HashMap<String, Object>();

				String parkNos = sourceStrArray[i].trim();
				
				
				//获取车辆的旧截止有效期
				
				String params = "parkCode=" + parkNos + "&CarNo=" + cardCode;
				
				Map<String, Object> searchCard=parkingService.SearchCard(params);
				
				if(searchCard!=null && searchCard.get("resultCode")!=null && "0".equalsIgnoreCase(searchCard.get("resultCode").toString()))
				{
							
							if (searchCard.get("ResultList") != null
									&& searchCard.get("ResultList").toString() != null) {

								String ResultList = searchCard.get("ResultList").toString();

								JSONArray json = JSONArray.fromObject(ResultList);

								
								resultMap=new HashMap<String, Object>();
								
								if (json.size() > 0) {
									for (int z = 0; z< json.size(); z++) {

										net.sf.json.JSONObject job = json.getJSONObject(z);

										resultMap.put("CarNo", job.get("CarNo"));

										resultMap.put("CardType", job.get("CardType"));

										resultMap.put("CardIndate", job.get("CardIndate"));

										resultMap.put("CardAmount", job.get("CardAmount"));
										
										resultMap.put("CarType", job.get("CarType"));

										resultMap.put("CarStyle", job.get("CarStyle"));

										resultMap.put("CarColor", job.get("CarColor"));

										resultMap.put("MasterName", job.get("MasterName"));
										

										resultMap.put("MasterID", job.get("MasterID"));
										
										resultMap.put("MasterTel", job.get("MasterTel"));

										resultMap.put("MasterAddr", job.get("MasterAddr"));

										resultMap.put("ParkNo", job.get("ParkNo"));

										resultMap.put("ParkPosition", job.get("ParkPosition"));
										

										resultMap.put("Remark", job.get("Remark"));

										resultMap.put("MakeDateTime", job.get("MakeDateTime"));

										resultMap.put("Enable", job.get("Enable"));

									}
								}
							}
				}
				
				if(resultMap.get("CardIndate")!=null)
				{
				OldCardIndate=resultMap.get("CardIndate").toString();
				
				

				// 给子级停车场增加白名单

				String paramLevel = "parkCode=" + parkNos + "&CarNo=" + cardCode
						+ "&NewCardIndate=" + CardIndate + "&PayAmount=" + PayAmount
						+ "&Remark=" + Remark;

				map = parkingService.modifyPresellTime(paramLevel);

				if (!"0".equalsIgnoreCase(map.get("resultCode").toString())) {
					String parkName = parkingService.getParkNo(parkNos)
							.get("pname").toString();

					msg = msg + parkName + map.get("message").toString() + "  ";

					code = "-1";

					continue;
				}

				map = new HashMap<String, Object>();
				
				}else{
					String parkName = parkingService.getParkNo(parkNos)
							.get("pname").toString();
					
					msg = "获取不到"+parkName+"车辆的旧截止有效期";

					code = "-1";
					
					continue;
				}

			}

		}else
		{
			msg = "没有选择车场";

			code = "-1";
		}

		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put("resultCode", msg);

			jsonObject.put("message", code);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = jsonObject.toString();

		return SUCCESS;

	}

}
