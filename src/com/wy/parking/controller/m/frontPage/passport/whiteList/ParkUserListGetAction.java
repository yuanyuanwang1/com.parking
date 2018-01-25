/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.wy.parking.service.ParkingOtherService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */

public class ParkUserListGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkUserListGetAction.class);

	private ParkingOtherService parkingOtherService = null;

	private List<Map<String, Object>> dataList = null;
	
	private String searchValue=null;
	
	

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}


	@Override
	public String execute() {
		
		dataList=parkingOtherService.getUserList(searchValue);

		return SUCCESS;
	}

}
