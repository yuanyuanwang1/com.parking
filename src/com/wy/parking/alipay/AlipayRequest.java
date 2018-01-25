/**
 * 
 */
package com.wy.parking.alipay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 支付宝支付请求服务类
 * 
 * @author Bin
 * 
 */
public class AlipayRequest {

	private Logger logger = Logger.getLogger(AlipayRequest.class);

	public String partner = null;

	public String key = null;

	public String buildRequest(Map<String, String> requestParamTemp) {

		Map<String, String> requestParam = buildRequestPara(requestParamTemp);

		List<String> keys = new ArrayList<String>(requestParam.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"alipaySubmit\" name=\"alipaySubmit\" method=\""
				+ AlipayConfig.requestMethod
				+ "\" action=\""
				+ AlipayConfig.ALIPAY_GATEWAY
				+ "?_input_charset="
				+ AlipayConfig.inputCharset + "\"" + ">");

		for (String keyy : keys) {

			sbHtml.append("<input type=\"hidden\" name=\"" + keyy
					+ "\" value=\"" + requestParam.get(keyy) + "\" />");

		}

		sbHtml.append("<input type=\"submit\" value=\"\" style=\"display:none\" />正在跳转到支付宝收银台，请稍后……</form>");

		sbHtml.append("<script>document.forms['alipaySubmit'].submit();</script>");

		return sbHtml.toString();

	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private Map<String, String> buildRequestPara(
			Map<String, String> requestParamTemp) {

		// 除去数组中的空值和签名参数
		requestParamTemp = AlipayCore.paraFilter(requestParamTemp);

		// 生成签名结果
		String mysign = buildRequestMysign(requestParamTemp);

		// 签名结果与签名方式加入请求提交参数组中
		requestParamTemp.put(AlipayParameterName.sign, mysign);
		requestParamTemp.put(AlipayParameterName.signType,
				AlipayConfig.signType);

		return requestParamTemp;

	}

	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	private String buildRequestMysign(Map<String, String> requestParamTemp) {

		// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = AlipayCore.createLinkString(requestParamTemp);

		String mysign = "";

		if (AlipayConfig.signType.equals("MD5")) {

			mysign = AlipaySignMD5.sign(prestr, key, AlipayConfig.inputCharset);

		}

		return mysign;
	}

}
