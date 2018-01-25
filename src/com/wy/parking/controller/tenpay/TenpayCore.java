/**
 * 
 */
package com.wy.parking.controller.tenpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

/**
 * @author Wy
 * 
 * @category 支付业务处理核心类
 * 
 */
public class TenpayCore {

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
			boolean encode) {

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

			if (first) {

				first = false;

			} else {

				temp.append("&");

			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {

				valueString = value.toString();

			}
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
		System.out.println(temp.toString());
		return temp.toString();

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
			String packages) {

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
		paras.put("appkey", paySignKey);
		// appid、timestamp、noncestr、package 以及 appkey。
		String string1 = createLinkString(paras, false);
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
	public static boolean verifySign(String productid, long timestamp,
			String noncestr, String openid, int issubscribe, String appsignature)
			throws UnsupportedEncodingException {

		Map<String, String> paras = new HashMap<String, String>();
		paras.put("appid", appId);
		paras.put("appkey", paySignKey);
		paras.put("productid", productid);
		paras.put("timestamp", String.valueOf(timestamp));
		paras.put("noncestr", noncestr);
		paras.put("openid", openid);
		paras.put("issubscribe", String.valueOf(issubscribe));
		// appid、appkey、productid、timestamp、noncestr、openid、issubscribe
		String string1 = createLinkString(paras, false);
		System.out.println(string1);
		String paySign = DigestUtils.md5Hex(string1);
		System.out.println(paySign);
		return paySign.equalsIgnoreCase(appsignature);

	}

	private Logger logger = Logger.getLogger(TenpayCore.class);
}
