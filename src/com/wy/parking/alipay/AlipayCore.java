/**
 * 
 */
package com.wy.parking.alipay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Bin
 * 
 * @category 支付宝支付业务处理核心类
 * 
 */
public class AlipayCore {

	private Logger logger = Logger.getLogger(AlipayCore.class);

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		// 如果输入参数为空，则返回空结果
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		// 遍历MAP中每个参数
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);

			// 如果参数为空或者是空字符串或者为“sign”或"sign_type"，则不放入结果字符串中，否则放入结果字符串

			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		// 将结果字符串返回
		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		// 将输入参数的MAP中的KEY放到数组中
		List<String> keys = new ArrayList<String>(params.keySet());

		// 利用数组的排序功能对MAP中的KEY进行排序
		Collections.sort(keys);

		String prestr = "";

		// 遍历排序后的数组
		for (int i = 0; i < keys.size(); i++) {
			// 依次取出数组中的元素
			String key = keys.get(i);

			// 根据KEY取出MAP中的VALUE
			String value = params.get(key);

			if (i == keys.size() - 1) {

				// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;

			} else {

				prestr = prestr + key + "=" + value + "&";

			}
		}

		return prestr;
	}

}
