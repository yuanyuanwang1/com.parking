/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;

/**
 * @author jian198001 工作台
 * 
 */
public class IndexGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(IndexGetAction.class);
	private String parkId = null;
	private ParkingService parkingService = null;
	private String pname=null;
	
	

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {

		if (StringUtils.isNotBlank(parkId)) {

			map=parkingService.getParkNo(parkId);
			
			pname=map.get("pname").toString();
		}

		return SUCCESS;

	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

}
