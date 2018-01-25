/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.whiteList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

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
public class OrderChargePostAction extends SuperAction {

	private Logger logger = Logger.getLogger(OrderChargePostAction.class);

	private ParkingService parkingService = null;

	private String parkCode = null;

	private String cardCode = null;

	private String CardAmount = null;

	private String PayAmount = null;

	private String Remark = null;

	// 子级停车场字段

	private String parkNos = null;

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


	public String getCardAmount() {
		return CardAmount;
	}

	public void setCardAmount(String cardAmount) {
		CardAmount = cardAmount;
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
//		//CardAmount转为分
//			
//			
//			BigDecimal amount=new BigDecimal(CardAmount);
//			
//			BigDecimal num = new BigDecimal("100");
//			
//			amount=amount.multiply(num);
//			
//			System.out.println("停车场费用CardAmount"+amount);
//
//		String params = "parkCode=" + parkCode + "&CarNo=" + cardCode
//				+ "&CardAmount=" + amount + "&PayAmount=" + PayAmount
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
//		map = parkingService.modifyPresellFee(params);
//
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
//
//		}
		
		if (StringUtils.isNotBlank(parkNos) && parkNos != "") {
			// 给子级停车场车辆延期
			String[] sourceStrArray = parkNos.split(",");

			for (int i = 0; i < sourceStrArray.length; i++) {

				map = new HashMap<String, Object>();

				String parkNos = sourceStrArray[i].trim();

				// 给子级停车场增加白名单
				
				
				//CardAmount转为分
					
					
					BigDecimal amount=new BigDecimal(CardAmount);
					
					BigDecimal num = new BigDecimal("100");
					
					amount=amount.multiply(num);
					
					System.out.println("停车场费用CardAmount"+amount);

				String paramLevel = "parkCode=" + parkNos + "&CarNo=" + cardCode
						+ "&CardAmount=" + amount + "&PayAmount=" + PayAmount
						+ "&Remark=" + Remark;

				map = parkingService.modifyPresellFee(paramLevel);

				if (!"0".equalsIgnoreCase(map.get("resultCode").toString())) {
					String parkName = parkingService.getParkNo(parkNos)
							.get("pname").toString();

					msg = msg + parkName + map.get("message").toString() + "  ";

					code = "-1";

					continue;
				}

				map = new HashMap<String, Object>();

			}

		}

		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put("resultCode", map.get("resultCode").toString());

			jsonObject.put("message", map.get("message").toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = jsonObject.toString();

		return SUCCESS;

	}

}
