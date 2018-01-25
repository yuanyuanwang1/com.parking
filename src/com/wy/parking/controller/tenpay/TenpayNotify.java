package com.wy.parking.controller.tenpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import wxpay.MD5Util;

/**
 * 微信支付回调处理类
 * 
 * @author jian198001
 * 
 */

public class TenpayNotify {

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	public static String appId = "wx66ca424a230b9140";

	public static String paySignKey = "Wangshuanghukuangxiaoyi200804211";

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params,
			boolean encode,String partnerKey) {

		// // 将输入参数的MAP中的KEY放到数组中
		// List<String> keys = new ArrayList<String>(params.keySet());
		//
		// // 利用数组的排序功能对MAP中的KEY进行排序
		// Collections.sort(keys);
		//
		// String prestr = "";
		//
		// // 遍历排序后的数组
		// for (int i = 0; i < keys.size(); i++) {
		// // 依次取出数组中的元素
		// String key = keys.get(i);
		//
		// // 根据KEY取出MAP中的VALUE
		// String value = params.get(key);
		//
		// if (i == keys.size() - 1) {
		//
		// // 拼接时，不包括最后一个&字符
		// prestr = prestr + key + "=" + value;
		//
		// } else {
		//
		// prestr = prestr + key + "=" + value + "&";
		//
		// }
		// }
		//
		// return prestr;
		// }

		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {

			String keyStr = (String) key;

			if (StringUtils.isBlank(keyStr)) {

				continue;

			}

			if (StringUtils.equals("sign", keyStr)) {

				continue;

			}
			if (StringUtils.equals("key", keyStr)) {

				continue;

			}

			if (first) {

				first = false;

			} else {

				temp.append("&");

			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";

			valueString = value.toString();

			if (encode) {

				try {
					temp.append(URLEncoder.encode(valueString, "UTF-8"));

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				temp.append(valueString);
			}

		}
		temp.append("&key=" + partnerKey);
		System.out.println(temp.toString());
		return MD5Util.MD5((temp.toString()));

	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	/**
	 * 支付签名
	 * 
	 * @param timestamp
	 * @param noncestr
	 * @param packages
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getPackage(String timestamp, String noncestr,
			String packages,String partnerKey) {

		// Map<String, String> result = new HashMap<String, String>();
		//
		// // 如果输入参数为空，则返回空结果
		// if (sArray == null || sArray.size() <= 0) {
		// return result;
		// }
		//
		// // 遍历MAP中每个参数
		// for (String key : sArray.keySet()) {
		// String value = sArray.get(key);
		//
		// // 如果参数为空或者是空字符串或者为“sign”或"sign_type"，则不放入结果字符串中，否则放入结果字符串
		//
		// if (value == null || value.equals("")
		// || key.equalsIgnoreCase("sign")
		// || key.equalsIgnoreCase("sign_type")) {
		// continue;
		// }
		// result.put(key, value);
		// }
		//
		// // 将结果字符串返回
		// return result;
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("appid", appId);
		paras.put("timestamp", timestamp);
		paras.put("noncestr", noncestr);
		paras.put("package", packages);
		paras.put("appkey", partnerKey);
		// appid、timestamp、noncestr、package 以及 appkey。
		String string1 = createLinkString(paras, false,partnerKey);
		String paySign = DigestUtils.shaHex(string1);
		return paySign;

	}

	/**
	 * 支付回调校验签名
	 * 
	 * @param productid
	 * @param timestamp
	 * @param noncestr
	 * @param openid
	 * @param issubscribe
	 * @param appsignature
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean verifySign(Map<String, String> Params,
			String appsignature,String partnerKey) throws UnsupportedEncodingException {

		System.out.println(Params);
		// 拼接后字符串
		String string1 = createLinkString(Params, false,partnerKey);
		System.out.println(string1);
		String paySign = string1.toUpperCase();
		System.out.println(string1);
		// String paySign = DigestUtils.shaHex(string1);
		System.out.println(paySign);
		appsignature = appsignature.toUpperCase();
		return paySign.equalsIgnoreCase(appsignature);

	}

	public String key = null;

	private Logger logger = Logger.getLogger(TenpayNotify.class);

	public String partner = null;

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

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 */
	private boolean getSignVeryfy(Map<String, String> Params, String sign,String partnerKey) {

		// 获取待签名方式
		System.out.println("获取待签名方式" + Params + "sign:" + sign);
		// 获得签名验证结果
		boolean isSign = false;
		try {
			System.out.println("MD5" + isSign);
			isSign = verifySign(Params, sign,partnerKey);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		System.out.println("MD5" + isSign);
		System.out.println(isSign);
		return isSign;

	}

	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	public boolean verify(Map<String, String> params,String partnerKey) {

		// 判断responsetTxt是否为true，isSign是否为true
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "true";

		//
		// if (params.get(TenpayParameterName.notifyId) != null) {
		// String notify_id = params.get(TenpayParameterName.notifyId);
		// responseTxt = verifyResponse(partner, notify_id);
		// System.out.println(notify_id);
		// }
		System.out.println("SIGN验证" + params);
		String sign = "";
		if (params.get(TenpayParameterName.sign) != null) {
			sign = params.get(TenpayParameterName.sign);
		}
		System.out.println("SIGN验证2" + sign);

		boolean isSign = getSignVeryfy(params, sign,partnerKey);

		System.out.println(isSign);

		if (isSign && responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
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

		String veryfy_url = TenpayConfig.HTTPS_VERIFY_URL + "partner="
				+ partner + "&notify_id=" + notify_id;

		return checkUrl(veryfy_url);

	}

}
