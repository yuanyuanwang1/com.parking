package com.wy.filter;

import it.sauronsoftware.base64.Base64;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author jian198001
 * 
 */
public class AuthenticationFilter implements Filter {

	private Logger logger = Logger.getLogger(AuthenticationFilter.class);

	private String loginURI = "/action/web/frontPage/passport/LoginGet.action";

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String requestUrl = getReturnUrl(request);

		HttpSession session = request.getSession();

		if (session == null) {

			response.sendRedirect(request.getContextPath() + loginURI
					+ "?returnUrl=" + requestUrl);

			logger.warn("动作：用户打开用户中心页面；状态：失败；原因：未授权访问，subject未登陆");

			return;

		}

		String userId = (String) session.getAttribute("userId");

		if (StringUtils.isBlank(userId)) {

			response.sendRedirect(request.getContextPath() + loginURI
					+ "?returnUrl=" + requestUrl);

			logger.warn("动作：用户打开用户中心页面；状态：失败；原因：未授权访问，subject未登陆");

			return;

		}

		String menuId = request.getParameter("menuId");

		if (StringUtils.isNotBlank(menuId)) {

			session.setAttribute("menuId", menuId);

		}

		filterChain.doFilter(request, response);

	}

	/**
	 * 获取上一次请求request信息
	 * 
	 * @param request
	 *            请求信息
	 * @return Base64位编码后的request信息
	 */
	public String getReturnUrl(HttpServletRequest request) {

		String requestUrl = request.getRequestURL().toString();

		String requestParam = null;

		Map requestMap = request.getParameterMap();

		for (Iterator iter = requestMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestMap.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			requestParam = (requestParam == null) ? name + "=" + valueStr
					: requestParam + "&" + name + "=" + valueStr;
		}

		requestUrl = requestUrl + "?" + requestParam;

		requestUrl = Base64.encode(requestUrl);

		return requestUrl;

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
