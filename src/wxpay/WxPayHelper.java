package wxpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class WxPayHelper {
	public static String createLinkString(Map<String, String> params,
			boolean encode) {
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
		temp.append("&key=" + WxpayConfig.partnerKey);
		System.out.println(temp.toString());
		return MD5Util.MD5((temp.toString()));

	}

	private String AppId = "";
	private String AppKey = "";
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String PartnerKey = "";

	private String SignType = "sha1";

	private Boolean CheckCftParameters() {
		if (parameters.get("bank_type") == "" || parameters.get("body") == ""
				|| parameters.get("partner") == ""
				|| parameters.get("out_trade_no") == ""
				|| parameters.get("total_fee") == ""
				|| parameters.get("fee_type") == ""
				|| parameters.get("notify_url") == null
				|| parameters.get("spbill_create_ip") == ""
				|| parameters.get("input_charset") == "") {
			return false;
		}
		return true;
	}

	// ���app֧������json
	/*
	 * { "appid":"wwwwb4f85f3a797777", "traceid":"crestxu",
	 * "noncestr":"111112222233333", "package":
	 * "bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f
	 * %2f www .qq.com&out_trade_no=16642817866003386000&partner=1900000109&
	 * spbill_create_ip
	 * =127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9",
	 * "timestamp":1381405298,
	 * "app_signature":"53cca9d47b883bd4a5c85a9300df3da0cb48565c",
	 * "sign_method":"sha1" }
	 */
	public String CreateAppPackage(String traceid) throws SDKRuntimeException {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		if (CheckCftParameters() == false) {
			throw new SDKRuntimeException("���package����ȱʧ��");
		}
		nativeObj.put("appid", AppId);
		nativeObj.put("package", GetCftPackage());
		nativeObj.put("timestamp", Long.toString(new Date().getTime() / 1000));
		nativeObj.put("traceid", traceid);
		nativeObj.put("noncestr", CommonUtil.CreateNoncestr());
		nativeObj.put("app_signature", GetBizSign(nativeObj));
		nativeObj.put("sign_method", SignType);
		return JSONObject.fromObject(nativeObj).toString();
	}

	// ���jsapi֧������json
	/*
	 * "appId" : "wxf8b4f85f3a794e77", //���ں���ƣ����̻����� "timeStamp" :
	 * "189026618", //ʱ�����������ʹ����һ��ֵ "nonceStr" : "adssdasssd13d", //���
	 * "package" :
	 * "bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f
	 * %2fwww.qq.com&out_trade_no=16642817866003386000&partner=1900000109&
	 * spbill_create_i
	 * p=127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9",
	 * //��չ�ֶΣ����̻����� "signType" : "SHA1", //΢��ǩ��ʽ:sha1 "paySign" :
	 * "7717231c335a05165b1874658306fa431fe9a0de" //΢��ǩ��
	 */
	public String CreateBizPackage() throws SDKRuntimeException {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		if (CheckCftParameters() == false) {
			throw new SDKRuntimeException("预付订单生成失败");
		}
		nativeObj.put("appId", AppId);
		nativeObj.put("package", GetCftPackage());
		nativeObj.put("timestamp", Long.toString(new Date().getTime() / 1000));
		nativeObj.put("noncestr", CommonUtil.CreateNoncestr());
		nativeObj.put("paySign", GetBizSign(nativeObj));
		nativeObj.put("signType", SignType);
		return JSONObject.fromObject(nativeObj).toString();

	}

	// ���ԭ��֧������xml
	/*
	 * <xml> <AppId><![CDATA[wwwwb4f85f3a797777]]></AppId>
	 * <Package><![CDATA[a=1&url=http%3A%2F%2Fwww.qq.com]]></Package>
	 * <TimeStamp> 1369745073</TimeStamp>
	 * <NonceStr><![CDATA[iuytxA0cH6PyTAVISB28]]></NonceStr>
	 * <RetCode>0</RetCode> <RetErrMsg><![CDATA[ok]]></ RetErrMsg>
	 * <AppSignature><![CDATA[53cca9d47b883bd4a5c85a9300df3da0cb48565c]]>
	 * </AppSignature> <SignMethod><![CDATA[sha1]]></ SignMethod > </xml>
	 */
	public String CreateNativePackage(String retcode, String reterrmsg)
			throws SDKRuntimeException {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		if (CheckCftParameters() == false && retcode == "0") {
			throw new SDKRuntimeException("���package����ȱʧ��");
		}
		nativeObj.put("AppId", AppId);
		nativeObj.put("Package", GetCftPackage());
		nativeObj.put("TimeStamp", Long.toString(new Date().getTime() / 1000));
		nativeObj.put("RetCode", retcode);
		nativeObj.put("RetErrMsg", reterrmsg);
		nativeObj.put("NonceStr", CommonUtil.CreateNoncestr());
		nativeObj.put("AppSignature", GetBizSign(nativeObj));
		nativeObj.put("SignMethod", SignType);
		return CommonUtil.ArrayToXml(nativeObj);

	}

	// ���ԭ��֧��url
	/*
	 * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXXX&productid=XXXXXX&
	 * timestamp =XXXXXX&noncestr=XXXXXX
	 */
	public String CreateNativeUrl(String productid) throws SDKRuntimeException {
		String bizString = "";
		try {
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			nativeObj.put("appid", AppId);
			nativeObj.put("productid", URLEncoder.encode(productid, "utf-8"));
			nativeObj.put("timestamp",
					Long.toString(new Date().getTime() / 1000));
			nativeObj.put("noncestr", CommonUtil.CreateNoncestr());
			nativeObj.put("sign", GetBizSign(nativeObj));
			bizString = CommonUtil.FormatBizQueryParaMap(nativeObj, false);

		} catch (Exception e) {
			throw new SDKRuntimeException(e.getMessage());

		}
		return "weixin://wxpay/bizpayurl?" + bizString;
	}

	public String GetBizSign(HashMap<String, String> bizObj)
			throws SDKRuntimeException {
		HashMap<String, String> bizParameters = new HashMap<String, String>();

		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				bizObj.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			if (item.getKey() != "") {
				bizParameters.put(item.getKey().toLowerCase(), item.getValue());
			}
		}

		if (AppKey == "") {
			throw new SDKRuntimeException("APPKEYΪ�գ�");
		}
		bizParameters.put("appkey", AppKey);
		String bizString = CommonUtil.FormatBizQueryParaMap(bizParameters,
				false);
		// System.out.println(bizString);

		return SHA1Util.Sha1(bizString);

	}

	public String GetCftPackage() throws SDKRuntimeException {
		if ("" == PartnerKey) {
			throw new SDKRuntimeException("��Կ����Ϊ�գ�");
		}
		String unSignParaString = CommonUtil.FormatBizQueryParaMap(parameters,
				false);
		String paraString = CommonUtil.FormatBizQueryParaMap(parameters, true);
		return paraString + "&sign="
				+ MD5SignUtil.Sign(unSignParaString, PartnerKey);

	}

	public String GetParameter(String key) {
		return parameters.get(key);
	}

	public void SetAppId(String str) {
		AppId = str;
	}

	public void SetAppKey(String str) {
		AppKey = str;
	}

	public void SetParameter(String key, String value) {
		parameters.put(key, value);
	}

	public void SetPartnerKey(String str) {
		PartnerKey = str;
	}

	public void SetSignType(String str) {
		SignType = str;
	}

	// /**
	//
	// * 签名校验
	//
	// *
	//
	// * @return
	//
	// * @throws UnsupportedEncodingException
	//
	// */
	//
	// @SuppressWarnings("rawtypes")
	//
	// public static boolean verifyPaySign(SortedMap paras, String sign) throws
	// UnsupportedEncodingException {
	//
	// String paySign = Tools.createSign(paras);
	//
	// return StringUtil.isNotEmpty(paySign) ? paySign.equalsIgnoreCase(sign) :
	// false;
	//
	// }

}
