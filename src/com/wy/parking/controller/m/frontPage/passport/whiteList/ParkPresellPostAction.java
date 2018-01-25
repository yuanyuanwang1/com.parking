/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 * 
 */
public class ParkPresellPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkPresellPostAction.class);

	private ParkingOtherService parkingOtherService = null;
	
	private ParkingService parkingService = null;

	private String parkCode = null;

	private String CarNo = null;

	private String CardType = null;

	private String CardIndate = null;

	private String CardAmount = null;

	private String CarType = null;

	private String MasterName = null;

	private String MasterTel = null;

	private String MasterAddr = null;

	private String ParkPosition = null;

	private String PayAmount = null;

	private String Remark = null;

	// 子级停车场字段
	private ParkingUser parkingUser=null;
	
	
	

	public ParkingUser getParkingUser() {
		return parkingUser;
	}

	public void setParkingUser(ParkingUser parkingUser) {
		this.parkingUser = parkingUser;
	}

	private String parkNos = null;

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getParkNos() {
		return parkNos;
	}

	public void setParkNos(String parkNos) {
		this.parkNos = parkNos;
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

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getCardIndate() {
		return CardIndate;
	}

	public void setCardIndate(String cardIndate) {
		CardIndate = cardIndate;
	}

	public String getCardAmount() {
		return CardAmount;
	}

	public void setCardAmount(String cardAmount) {
		CardAmount = cardAmount;
	}

	public String getCarType() {
		return CarType;
	}

	public void setCarType(String carType) {
		CarType = carType;
	}

	public String getMasterName() {
		return MasterName;
	}

	public void setMasterName(String masterName) {
		MasterName = masterName;
	}

	public String getMasterTel() {
		return MasterTel;
	}

	public void setMasterTel(String masterTel) {
		MasterTel = masterTel;
	}

	public String getMasterAddr() {
		return MasterAddr;
	}

	public void setMasterAddr(String masterAddr) {
		MasterAddr = masterAddr;
	}

	public String getParkPosition() {
		return ParkPosition;
	}

	public void setParkPosition(String parkPosition) {
		ParkPosition = parkPosition;
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


	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	@Override
	public String execute() {

		// 白名单接口

		HttpSession session = request.getSession();

		pid = (String) session.getAttribute("pid");
		

		if(pid==null)
			
		{
			return ERROR;
		}else
		{
			parkingUser=parkingOtherService.get(pid);
			
		}
		
		String msg=" ";
		
		String code="0";
				
		if(StringUtils.isNotBlank(parkNos) && parkNos!=""){
		//给子级停车场增加白名单	
		String[] sourceStrArray = parkNos.split(",");
		   
	    for (int i = 0; i < sourceStrArray.length; i++) {
	    	
	    	map=new HashMap<String, Object>();

	         String parkNos=sourceStrArray[i].trim();
	        	
	        	//给子级停车场增加白名单
	        			
	 		String params = "parkCode=" + parkNos + "&CarNo=" + CarNo
					+ "&CardType=" + CardType + "&CardIndate=" + CardIndate
					+ "&CarType=" + CarType
					+ "&MasterName=" + MasterName + "&MasterAddr=" + MasterAddr
					+ "&Remark=" + parkingUser.getPname();
	 		
		map = parkingService.AddCar(params);
		
		if(!"0".equalsIgnoreCase(map.get("resultCode").toString()))
		{
			String parkName=parkingService.getParkNo(parkNos).get("pname").toString();
			
			msg=msg+parkName+map.get("message").toString()+"  ";
			
			code="-1";
			
			continue;
		}else
		{
			String parkName=parkingService.getParkNo(parkNos).get("pname").toString();
			
			msg=msg+parkName+map.get("message").toString()+"  ";
			
			code="0";
		}

		map=new HashMap<String, Object>();

	 }
	    
}
	        

		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put("resultCode", code);

			jsonObject.put("message", msg);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = jsonObject.toString();

		return SUCCESS;

	}

	public String formatAmtY2F(String amtY) {
		if (amtY == null || "".equals(amtY.trim()) || "0".equals(amtY))
			return "0";
		if (amtY.indexOf(",") != -1) {
			amtY = amtY.replace(",", "");
		}

		amtY = new DecimalFormat("0.00").format(new BigDecimal(amtY));
		int index = amtY.indexOf(".");
		int len = amtY.length();
		StringBuffer amtF = new StringBuffer();
		if (index == -1) {
			amtF.append(amtY).append("00");
		} else if ((len - index) == 1) {
			amtF.append(Long.parseLong(amtY.replace(".", ""))).append("00");
		} else if ((len - index) == 2) {
			amtF.append(Long.parseLong(amtY.replace(".", ""))).append("0");
		} else {
			amtF.append(Long.parseLong(amtY.replace(".", "")));
		}
		return amtF.toString();
	}

}
