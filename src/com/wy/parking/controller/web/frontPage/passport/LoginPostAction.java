/**
 * 
 */
package com.wy.parking.controller.web.frontPage.passport;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.model.CommonUser;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author Administrator
 * 
 */
public class LoginPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(LoginPostAction.class);

	private String username = null;

	private String password = null;

	private String returnUrl = null;

	private UserService userService = null;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute() {

		JSONObject jsonObject = new JSONObject();

		if (StringUtils.isBlank(username)) {

			errorInfo = "用户名为空，登录失败";

			logger.warn("动作：用户登录；状态：失败；用户名：" + username + "；原因：用户名为空，登录失败");

			try {
				jsonObject.put("errorInfo", errorInfo);
				jsonObject.put("loginResult", "loginError");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			result = jsonObject.toString();

			return SUCCESS;

		}

		if (StringUtils.isBlank(password)) {

			errorInfo = "密码为空，登录失败";

			logger.warn("动作：用户登录；状态：失败；用户名：" + username + "；原因：密码为空，登录失败");

			try {
				jsonObject.put("errorInfo", errorInfo);
				jsonObject.put("loginResult", "loginError");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			result = jsonObject.toString();

			return SUCCESS;

		}

		password = userService.passwordDigest(password);

		try {
			// 4、登录，即身份验证
			String userId = userService.login(username, password);

			if (StringUtils.isBlank(userId)) {

				errorInfo = "用户名或密码错误，登录失败";

				logger.warn("动作：用户登录；状态：失败；用户名：" + username + "；原因：用户名或密码错误");

				try {
					jsonObject.put("loginResult", "loginError");

					jsonObject.put("errorInfo", "用户名或密码错误");
				} catch (JSONException je) {
					// TODO Auto-generated catch block
					je.printStackTrace();
				}

				result = jsonObject.toString();

				return SUCCESS;

			}

			session.put("userId", userId);
			
			CommonUser commonUser = userService.get(userId);

			session.put("username", commonUser.getUsername());

			session.put("pname", commonUser.getPname());

			session.put("pcode", commonUser.getPcode());
			
			session.put("levelStatus", commonUser.getLevelStatus());

			session.put("indexCode", "1");

		} catch (Throwable e) {

			errorInfo = "用户名或密码错误，登录失败";

			logger.warn("动作：用户登录；状态：失败；用户名：" + username + "；原因：用户名或密码错误");

			try {
				jsonObject.put("loginResult", "loginError");

				jsonObject.put("errorInfo", "用户名或密码错误");
			} catch (JSONException je) {
				// TODO Auto-generated catch block
				je.printStackTrace();
			}

			result = jsonObject.toString();

			return SUCCESS;

			// 5、身份验证失败
		}

		if (!StringUtils.isBlank(returnUrl)) {

			returnUrl = it.sauronsoftware.base64.Base64.decode(returnUrl);

			logger.info("动作：用户登录；状态：成功；用户名：" + username + "；跳转页面地址" + returnUrl);

			return "redirectUrl";
		}

	
		try {
			jsonObject.put("loginResult", "loginOK");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = jsonObject.toString();
		

		return SUCCESS;

	}
}
