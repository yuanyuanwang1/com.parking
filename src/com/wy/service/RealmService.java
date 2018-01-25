/**
 * 
 */
package com.wy.service;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.ApplicationContext;

import com.wy.util.SpringInit;

/**
 * @author Administrator
 * 
 */
public class RealmService extends AuthorizingRealm {

	private UserService userService = null;

	private ApplicationContext applicationContext = null;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		String userId = (String) principals.getPrimaryPrincipal();

		getContext();

		return userService.getAuthorizationInfo(userId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.Realm#getName()
	 */
	@Override
	public String getName() {
		return "realmService";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.Realm#supports(org.apache.shiro.authc.
	 * AuthenticationToken)
	 */
	@Override
	public boolean supports(AuthenticationToken token) {

		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());

		getContext();

		String userId = userService.login(username, password);

		if (StringUtils.isBlank(userId)) {

			throw new IncorrectCredentialsException();

		}

		return new SimpleAuthenticationInfo(userId, password, getName());

	}

	private void getContext() {

		if (applicationContext == null) {
			applicationContext = SpringInit.getApplicationContext();
		}

		userService = (UserService) applicationContext.getBean("userService");

	}

}
