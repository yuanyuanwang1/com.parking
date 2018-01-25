/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.whiteList;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.parking.model.ParkingUser;
import com.wy.parking.service.ParkingOtherService;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class ParkDeletePostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ParkDeletePostAction.class);

	private ParkingOtherService parkingOtherService = null;

	private String pid = null;

	private ParkingUser parkingUser = null;

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

		parkingOtherService.delete(parkingUser);

		return SUCCESS;

	}

}
