/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.whiteList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ImportGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(ImportGetAction.class);

	private ParkingService parkingService = null;

	private String parkCode = null;

	private String CarNo = null;

	private UserService userService = null;

	private String levelStatus = null;

	protected List<Map<String, Object>> levelList = null;


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public List<Map<String, Object>> getLevelList() {
		return levelList;
	}

	public void setLevelList(List<Map<String, Object>> levelList) {
		this.levelList = levelList;
	}

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

	public String getCarNo() {
		return CarNo;
	}

	public void setCarNo(String carNo) {
		CarNo = carNo;
	}

	@Override
	public String execute() {

		HttpSession session = request.getSession();

		parkCode = (String) session.getAttribute("pcode");
		
		levelStatus = (String) session.getAttribute("levelStatus");

		String userId = (String) session.getAttribute("userId");

		CommonUser commonUser = userService.get(userId);
		

//		if (StringUtils.isNotBlank(levelStatus)
//				&& "1".equalsIgnoreCase(levelStatus)) {
//
//			levelList = parkingService.getLevelCustomer(commonUser.getPid());
//		}
		
		if (StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus) && !"admin".equals(commonUser.getUsername())) {

			levelList = parkingService.getLevelCustomer(commonUser.getPid());

		}else if(StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus) && "admin".equals(commonUser.getUsername()))
		{
			levelList = parkingService.getLevelCustomer();
		}

		return SUCCESS;

	}

}
