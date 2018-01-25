/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.whiteList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
 * 
 */
public class OrderLastGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(OrderLastGetAction.class);

	private String dateNow = null;

	private String levelStatus = null;

	private ParkingService parkingService = null;

	private UserService userService = null;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public String getDateNow() {
		return dateNow;
	}

	public void setDateNow(String dateNow) {
		this.dateNow = dateNow;
	}

	@Override
	public String execute() {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		dateNow = format.format(new Date());

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) + 1;
		int month = cal.get(Calendar.MONTH) + 1;
		cal.add(Calendar.MONTH, 1);
		// Date date = cal.getTime(); //结果
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = cal.getTime();

		dateNow = sdf.format(date);

		System.out.println(sdf.format(date));

		// 判断是否包含子级停车场
		HttpSession session = request.getSession();

		levelStatus = (String) session.getAttribute("levelStatus");

		String userId = (String) session.getAttribute("userId");

		CommonUser commonUser = userService.get(userId);
		
		System.out.println("commonUser"+commonUser);


		if (StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus) && !"admin".equals(commonUser.getUsername())) {

			list = parkingService.getLevelCustomer(commonUser.getPid());

		}else if(StringUtils.isNotBlank(levelStatus)
				&& "1".equalsIgnoreCase(levelStatus) && "admin".equals(commonUser.getUsername()))
		{
			list = parkingService.getLevelCustomer();
		}
		
		
		System.out.println("list"+list);

		return SUCCESS;

	}
}
