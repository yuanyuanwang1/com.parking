/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.Payment;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */
public class ModifyTenpayGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ModifyTenpayGetAction.class);

	private String pid = null;

	private ParkingService parkingService = null;

	private CommonUser commonUser = null;

	private UserService userService = null;

	public CommonUser getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(CommonUser commonUser) {
		this.commonUser = commonUser;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@Override
	public String execute() {

		if (StringUtils.isBlank(pid)) {

			return ERROR;

		}

		commonUser = userService.get(pid);
		
		//根据车场pid查询微信信息
		
		map=parkingService.getTenpayListById(pid);

		return SUCCESS;

	}
}
