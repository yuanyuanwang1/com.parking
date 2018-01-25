/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */
public class ParkModifyGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkModifyGetAction.class);

	private String pid = null;

	private ParkingOtherService parkingOtherService = null;

	private ParkingUser parkingUser = null;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ParkingOtherService getParkingOtherService() {
		return parkingOtherService;
	}

	public void setParkingOtherService(ParkingOtherService parkingOtherService) {
		this.parkingOtherService = parkingOtherService;
	}

	public ParkingUser getParkingUser() {
		return parkingUser;
	}

	public void setParkingUser(ParkingUser parkingUser) {
		this.parkingUser = parkingUser;
	}

	@Override
	public String execute() {

		if (StringUtils.isBlank(pid)) {

			return ERROR;

		}

		parkingUser = parkingOtherService.get(pid);

		return SUCCESS;

	}
}
