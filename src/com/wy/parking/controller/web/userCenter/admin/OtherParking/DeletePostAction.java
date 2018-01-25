/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.OtherParking;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class DeletePostAction extends SuperAction {

	private Logger logger = Logger.getLogger(DeletePostAction.class);

	private ParkingService parkingService = null;

	private String pid = null;

	private CommonUser commonUser = null;

	private UserService userService = null;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CommonUser getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(CommonUser commonUser) {
		this.commonUser = commonUser;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String execute() {

		if (StringUtils.isBlank(pid)) {

			return ERROR;

		}

		commonUser =userService.get(pid);

		parkingService.delete(commonUser);

		return SUCCESS;

	}

}
