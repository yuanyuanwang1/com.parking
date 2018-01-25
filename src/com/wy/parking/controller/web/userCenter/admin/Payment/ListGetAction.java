/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.Payment;

import java.util.ArrayList;
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
	
	private String parkCode =null;
	
	
	

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {
		
		//获取所有的车场信息根据登录信息获取
		

		HttpSession session = request.getSession();

		parkCode = (String) session.getAttribute("pcode");
		
		
		if("1".equals(parkCode))
		{
			pageInfo=parkingService.getCarInfoList(getIPageNum(),searchValue,getUserId());
		}else
		{
			pageInfo=parkingService.getListBySelf(getIPageNum(),searchValue,parkCode);	
		}
		
		return SUCCESS;

	}
}
