package wxpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/*
 '微信支付服务器签名支付请求请求类
 '============================================================================
 'api说明：
 'init(app_id, app_secret, partner_key, app_key);
 '初始化函数，默认给一些参数赋值，如cmdno,date等。
 'setKey(key_)'设置商户密钥
 'getLasterrCode(),获取最后错误号
 'GetToken();获取Token
 'getTokenReal();Token过期后实时获取Token
 'createMd5Sign(signParams);生成Md5签名
 'genPackage(packageParams);获取package包
 'createSHA1Sign(signParams);创建签名SHA1
 'sendPrepay(packageParams);提交预支付
 'getDebugInfo(),获取debug信息
 '============================================================================
 '*/
public class RequestHandler {
	/** 商户参数 */
	private String appid;
	private String appkey;
	private String appsecret;
	private String charset;
	/** debug信息 */
	private String debugInfo;
	/** 预支付网关url地址 */
	private String gateUrl;
	private String key;
	private String last_errcode;
	/** 查询支付通知网关URL */
	private String notifyUrl;
	/** 请求的参数 */
	private SortedMap parameters;
	private String partnerkey;
	private HttpServletRequest request;
	private HttpServletResponse response;

	/** Token */
	private String Token;

	/** Token获取网关地址地址 */
	private String tokenUrl;

	/**
	 * 初始构造函数。
	 * 
	 * @return
	 */
	public RequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		this.last_errcode = "0";
		this.request = request;
		this.response = response;
		// this.charset = "GBK";
		this.charset = "UTF-8";
		this.parameters = new TreeMap();
		// 验证notify支付订单网关
		notifyUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";

	}

	/**
	 * 创建package签名
	 */
	public boolean createMd5Sign(String signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,
				this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:"
				+ tenpaySign);

		return tenpaySign.equals(sign);
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign(Map<String, String> parameters, boolean encode,String partnerKey) {
		// StringBuffer sb = new StringBuffer();
		// Set es = packageParams.entrySet();
		// Iterator it = es.iterator();
		// while (it.hasNext()) {
		// Map.Entry entry = (Map.Entry) it.next();
		// String k = (String) entry.getKey();
		// String v = (String) entry.getValue();
		// if (null != v && !"".equals(v) && !"sign".equals(k)
		// && !"key".equals(k)) {
		// sb.append(k + "=" + v + "&");
		// }
		// }
		// // sb.append("key=" + this.getKey());
		// sb.append("key=" + WxpayConfig.partnerKey);
		// System.out.println("md5 sb:" + sb);
		// String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
		// .toUpperCase();
		// System.out.println("packge签名:" + sign);
		// return sign;
		Set<String> keysSet = parameters.keySet();
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
			Object value = parameters.get(key);
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
		// return MD5Util.MD5((temp.toString()));
		return MD5Util.MD5Encode(temp.toString(), this.charset);

	}
	public String createSignOther(Map<String, String> parameters, boolean encode,String partnerKey) {
		// StringBuffer sb = new StringBuffer();
		// Set es = packageParams.entrySet();
		// Iterator it = es.iterator();
		// while (it.hasNext()) {
		// Map.Entry entry = (Map.Entry) it.next();
		// String k = (String) entry.getKey();
		// String v = (String) entry.getValue();
		// if (null != v && !"".equals(v) && !"sign".equals(k)
		// && !"key".equals(k)) {
		// sb.append(k + "=" + v + "&");
		// }
		// }
		// // sb.append("key=" + this.getKey());
		// sb.append("key=" + WxpayConfig.partnerKey);
		// System.out.println("md5 sb:" + sb);
		// String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
		// .toUpperCase();
		// System.out.println("packge签名:" + sign);
		// return sign;
		Set<String> keysSet = parameters.keySet();
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
			Object value = parameters.get(key);
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
		temp.append("&key=" +partnerKey);
		System.out.println(temp.toString());
		// return MD5Util.MD5((temp.toString()));
		return MD5Util.MD5Encode(temp.toString(), this.charset);

	}
	// 获取package的签名包
	public String genPackage(Map<String, String> parameters) {
		// String sign = createSign(packageParams);
		//
		// StringBuffer sb = new StringBuffer();
		// Set es = packageParams.entrySet();
		// Iterator it = es.iterator();
		// while (it.hasNext()) {
		// Map.Entry entry = (Map.Entry) it.next();
		// String k = (String) entry.getKey();
		// String v = (String) entry.getValue();
		// sb.append(k + "=" + UrlEncode(v) + "&");
		// }
		//
		// // 去掉最后一个&
		// String packageValue = sb.append("sign=" + sign).toString();
		// // System.out.println("UrlEncode后 packageValue=" + packageValue);
		// return packageValue;
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
					|| "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		try {
			// return new String(sb.toString().getBytes(), "ISO8859-1");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	/**
	 * 获取入口地址,不包含参数值
	 */
	public String getGateUrl() {
		return gateUrl;
	}

	// 设置密钥

	public String getKey() {
		return key;
	}

	/**
	 * 获取最后错误号
	 */
	public String getLasterrCode() {
		return last_errcode;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	public void init() {
	}

	/**
	 * 初始化函数。
	 */
	public void init(String app_id, String app_secret, String partner_key) {
		this.last_errcode = "0";
		this.Token = "token_";
		this.debugInfo = "";
		this.appid = app_id;
		this.partnerkey = partner_key;
		this.appsecret = app_secret;
		// this.key = partner_key;
	}

	// 输出XML
	public String parseXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {

				sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	// 设置微信密钥
	public void setAppKey(String key) {
		this.appkey = key;
	}

	/**
	 * 设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	public void setKey(String key) {
		this.partnerkey = key;
	}

	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}

	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}

}
