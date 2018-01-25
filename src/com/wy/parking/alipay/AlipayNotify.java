/**
 * 
 */
package com.wy.parking.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 支付宝支付回调处理类
 * 
 * @author Bin
 * 
 */
public class AlipayNotify {

	private Logger logger = Logger.getLogger(AlipayNotify.class);

	public String partner = null;

	public String key = null;

	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	public boolean verify(Map<String, String> params) {

		// 判断responsetTxt是否为true，isSign是否为true
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "true";

		logger.info("notifyId:" + params.get(AlipayParameterName.notifyId));

		if (params.get(AlipayParameterName.notifyId) != null) {
			String notify_id = params.get(AlipayParameterName.notifyId);
			responseTxt = verifyResponse(partner, notify_id);
		}

		String sign = "";
		if (params.get(AlipayParameterName.sign) != null) {
			sign = params.get(AlipayParameterName.sign);
		}

		boolean isSign = getSignVeryfy(params, sign);

		if (isSign && responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 */
	private boolean getSignVeryfy(Map<String, String> Params, String sign) {

		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = AlipayCore.paraFilter(Params);

		// 获取待签名字符串
		String preSignStr = AlipayCore.createLinkString(sParaNew);

		// 获得签名验证结果
		boolean isSign = false;
		if (AlipayConfig.signType.equals("MD5")) {
			isSign = AlipaySignMD5.verify(preSignStr, sign, key,
					AlipayConfig.inputCharset);
		}

		return isSign;

	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String verifyResponse(String partner, String notify_id) {

		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

		String veryfy_url = AlipayConfig.HTTPS_VERIFY_URL + "partner="
				+ partner + "&notify_id=" + notify_id;

		return checkUrl(veryfy_url);

	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String checkUrl(String urlvalue) {

		String inputLine = "";

		try {

			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			inputLine = in.readLine().toString();

		} catch (Throwable e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;

	}
}
