/**
 * 
 */
package com.wy.parking.controller.httppost.userCenter.admin;

import org.apache.log4j.Logger;

import com.wy.model.CommonUser;
import com.wy.service.CommonService;
import com.wy.superClass.SuperAction;
import com.wy.util.ExceptionUtil;

/**
 * @author Administrator
 * 
 */
public class LogoutGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(LogoutGetAction.class);

	@Override
	public String execute() {

		try {
			
		  String sessionId = request.getSession().getId();
		
			session.clear();
			
			sessionId=null;


		} catch (Throwable e) {

			logger.error(ExceptionUtil.getException(e));

		}

		return SUCCESS;

	}

}
