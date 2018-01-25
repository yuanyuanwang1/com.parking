/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.wy.superClass.SuperAction;
/**
 * @author Bin
 * 
 */

public class ParkLoginGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkLoginGetAction.class);

	private String startDate = null;

	private String endDate = null;

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

	@Override
	public String execute() {
		
		return SUCCESS;
	}

}
