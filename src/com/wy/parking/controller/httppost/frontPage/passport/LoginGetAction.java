/**
 * 
 */
package com.wy.parking.controller.httppost.frontPage.passport;

import org.apache.log4j.Logger;

import com.wy.superClass.SuperAction;

/**
 * @author Administrator
 * 
 */
public class LoginGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(LoginGetAction.class);

	private String returnUrl = null;

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Override
	public String execute() {

		return SUCCESS;

	}

}
