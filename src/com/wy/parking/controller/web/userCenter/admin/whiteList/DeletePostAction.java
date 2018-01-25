/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.whiteList;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class DeletePostAction extends SuperAction {

	private Logger logger = Logger.getLogger(DeletePostAction.class);

	private ParkingService parkingService = null;

	private String parkCode = null;

	private String CarNo = null;

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

//		HttpSession session = request.getSession();
//
//		parkCode = (String) session.getAttribute("pcode");
//		
//		
		CarNo=CarNo.trim();
		
		String params = "parkCode=" + parkCode + "&CarNo=" + CarNo;
		
		System.out.println(params);

		parkingService.DeleteCar(params);
		
		
		return SUCCESS;

	}

}
