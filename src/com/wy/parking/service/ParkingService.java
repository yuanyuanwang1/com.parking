package com.wy.parking.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import wxpay.GetWxOrderno;
import wxpay.RequestHandler;
import wxpay.WxpayConfig;

import com.wy.dao.PageInfo;
import com.wy.model.CommonUser;
import com.wy.parking.model.AlipayInfo;
import com.wy.parking.model.OrderInfo;
import com.wy.parking.model.TenpayInfo;
import com.wy.superClass.SuperModel;
import com.wy.superClass.SuperService;
import com.wy.util.HttpRequestUtil;
import com.wy.util.HttpUtil;

/**
 * 
 * @author lv App信息服务类
 */
public class ParkingService extends SuperService {

	private OrderInfoService orderInfoService = null; // 订单服务类

	public String notifyParkingUrl = "http://www.sztigerwong.com/com.wy.parking/action/tenpay/tenpayNotifyDataParkingProcess.action";

	public OrderInfoService getOrderInfoService() {
		return orderInfoService;
	}

	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	public AlipayInfo getAlipayInfo(String pid) {

		AlipayInfo user = hibernateTemplate.get(AlipayInfo.class, pid);

		return user;

	}

	public TenpayInfo getTenpayInfo(String pid) {

		TenpayInfo user = hibernateTemplate.get(TenpayInfo.class, pid);

		return user;

	}

	// 4) 车辆充值（只用于储值车）

	public Map<String, Object> modifyPresellFee(String params) {

		String url = "http://39.108.4.1:8090/park/modifyPresellFee";

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 3) 车辆延期（可以往前延期也可以往后延期，只用于月租车和贵宾车）
	public Map<String, Object> modifyPresellTime(String params) {

		String url = "http://39.108.4.1:8090/park/modifyPresellTime";

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 下发预约信息接口
	public Map<String, Object> parkOrder(String params) {

		String url = "http://39.108.4.1:8090/park/order";

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 发行车辆查询
	public Map<String, Object> ListCar(String params) {

		String url = "http://39.108.4.1:8090/park/searchPresell";

		params = params.replaceAll(" ", "%20");

		System.out.println(params);

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		return map;
	}

	// 白名单录入
	public Map<String, Object> AddCar(String params) {

		String url = "http://39.108.4.1:8090/park/addPresell";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 白名单删除
	public Map<String, Object> DeleteCar(String params) {

		String url = "http://39.108.4.1:8090/park/deletePresell";

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 在场车辆查询
	public Map<String, Object> InCar(String params) {

		String url = "http://39.108.4.1:8090/park/searchInCar";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 出厂车辆查询
	public Map<String, Object> OutCar(String params) {

		String url = "http://39.108.4.1:8090/park/searchOutCar";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 8) 交接班记录
	public Map<String, Object> WorkShiftRecord(String params) {

		String url = "http://39.108.4.1:8090/park/searchWorkShift";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 计算停车费用接口
	public Map<String, Object> getFee(String params) {

		String url = "http://39.108.4.1:8090/park/getFee";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			// map.put("inTime", jsStr.getString("inTime"));

			map.put("data", jsStr.getString("data"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 8)9) 收费记录
	public Map<String, Object> ChargeRecord(String params) {

		String url = "http://39.108.4.1:8090/park/searchChargeRecord";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 车流量查询
	public Map<String, Object> FlowRate(String params) {

		String url = "http://39.108.4.1:8090/park/searchCarFlowRate";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	public Map<String, Object> GetGarageCarInfoByPlate(String params) {
		// TODO Auto-generated method stub
		String url = "http://39.108.4.1:8090/park/getFee";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("data", jsStr.getString("data"));

			// map.put("inTime", jsStr.getString("inTime"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	public List<Map<String, Object>> getWxPayHelpers(String pid,
			String remoteAddr, String openid, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("进下单-----");

		Map<String, Object> paymentInfo = getPaymentInfo(pid);

		System.out.println("paymentInfo" + paymentInfo);

		// 根据停车场编号找到唯一的支付宝信息

		String parkNo = paymentInfo.get("parkNo").toString();

		System.out.println("parkNo" + parkNo);

		// 根据parkNo获取CommonUser信息

		Map<String, Object> parkMap = getParkNo(parkNo);

		System.out.println("parkMap" + parkMap);

		Map<String, Object> secnicInfo = getScenicInfo(parkMap.get("pid")
				.toString());

		System.out.println("secnicInfo" + secnicInfo);

		String out_trade_no = (String) paymentInfo.get("order_code");

		String parkingInfo = " ";

		if (paymentInfo.get("carNo") != null) {

			parkingInfo = paymentInfo.get("carNo").toString();
		}

		String body = "订单" + parkingInfo;
		System.out.println("openid" + openid);

		BigDecimal totalPrices = new BigDecimal(paymentInfo.get("carfee")
				.toString());

		System.out.println(totalPrices);

		BigDecimal totalFees = new BigDecimal(100).multiply(totalPrices);

		System.out.println(totalFees);

		int totalFee = totalFees.intValue();

		System.out.println(totalFees);

		String totalPrice = String.valueOf(totalFee);

		Map<String, Object> weixinInfo = getWeixin(parkMap.get("pid")
				.toString());

		String appId = weixinInfo.get("appId").toString();

		String mchId = weixinInfo.get("mch_id").toString();

		String notifyUrl = weixinInfo.get("notifyUrl").toString();

		String appSerect = weixinInfo.get("appSerect").toString();

		String partnerKey = weixinInfo.get("partnerKey").toString();

		Map<String, String> para = new HashMap<String, String>();
		para.put("appid", appId);
		para.put("mch_id", mchId);
		para.put("nonce_str", genNonceStr());
		para.put("body", body);
		para.put("out_trade_no", out_trade_no);// 商户订单号要唯一
		para.put("total_fee", totalPrice);
		para.put("spbill_create_ip", remoteAddr);
		para.put("notify_url", notifyUrl);// 支付成功后回调的action
		para.put("trade_type", "JSAPI");
		para.put("openid", openid);

		RequestHandler reqHandler = new RequestHandler(request, response);
		System.out.println("reqHandler-----" + reqHandler);
		reqHandler.init(appId, appSerect, partnerKey);

		String sign = reqHandler.createSign(para, false, partnerKey);

		System.out.println("sign-----" + sign);

		para.put("sign", sign);

		// String xml = "<xml>" + "<appid>" + WxpayConfig.appId + "</appid>"
		// + "<mch_id>" + WxpayConfig.MCH_ID + "</mch_id>" + "<nonce_str>"
		// + genNonceStr() + "</nonce_str>"
		// + "<body>text</body>"
		// + "<out_trade_no>"
		// + out_trade_no
		// + "</out_trade_no>"
		// +"<total_fee>" + "1" + "</total_fee>" + "<spbill_create_ip>"
		// + remoteAddr + "</spbill_create_ip>" + "<notify_url>"
		// + WxpayConfig.notifyUrl + "</notify_url>" + "<trade_type>"
		// + "JSAPI" + "</trade_type>" + "<openid>" + openid + "</openid>"
		// + "<sign>"+ sign + "</sign>" + "</xml>";

		// String xml = "<xml>" + "<appid>" + WxpayConfig.appId + "</appid>"
		// + "<mch_id>" + WxpayConfig.MCH_ID + "</mch_id>" + "<nonce_str>"
		// + genNonceStr() + "</nonce_str>"
		// + "<body>"+"text"+"</body>"
		// + "<out_trade_no>"
		// + out_trade_no
		// + "</out_trade_no>"
		// +"<total_fee>" + "1" + "</total_fee>" + "<spbill_create_ip>"
		// + remoteAddr + "</spbill_create_ip>" + "<notify_url>"
		// + WxpayConfig.notifyUrl + "</notify_url>" + "<trade_type>"
		// + "JSAPI" + "</trade_type>" + "<openid>"
		// +"o8DdnuIkgtLx4t9RmJTTS_L0yp38</openid>"
		// + "<sign>"+ sign + "</sign>" + "</xml>";
		// System.out.println(xml);

		String allParameters = "";
		try {
			allParameters = reqHandler.genPackage(para);
			System.out.println("allParameters-------" + allParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Map<String, Object> dataMap2 = new HashMap<String, Object>();
		String prepay_id = "";
		try {
			new GetWxOrderno();
			prepay_id = GetWxOrderno.getPayNo(createOrderURL, allParameters);
			System.out.println(prepay_id);
			if (prepay_id.equals("")) {
				System.out.println("统一支付接口获取预支付订单出错");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		Map<String, String> finalpackage = new HashMap<String, String>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String appid2 = appId;
		String timestamp = String.valueOf(genTimeStamp());
		String nonceStr2 = genNonceStr();
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr2);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		finalpackage.put("key", partnerKey);

		String finalsign = reqHandler.createSign(finalpackage, false,
				partnerKey);

		System.out.println("finalsign" + finalsign);

		map.put("appid", appid2);
		map.put("timestamp", timestamp);
		map.put("nonceStr", nonceStr2);
		map.put("packages", packages);
		map.put("sign", finalsign);
		dataList.add(map);
		System.out.println(dataList);
		System.out.println("map" + map);
		// response.sendRedirect("/weChatpay/pay.jsp?appid="+appid2+"&timeStamp="+timestamp+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
		return dataList;
	}

	public Map<String, Object> getParkingOrder(String pid) {

		String sql = " SELECT * from order_info where pid='" + pid + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	private Map<String, Object> getScenicInfo(String pid) {

		String sql = " select * from alipay_info t where t.common_pid='" + pid
				+ "'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	private String genNonceStr() {
		Random random = new Random();

		return getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	public String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	public Map<String, Object> getParkNo(String parkNo) {

		String sql = " SELECT * from common_user where pcode='" + parkNo
				+ "' and status='1'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	private Map<String, Object> getPaymentInfo(String pid) {

		String sql = " select order_code,carfee,carNo,parkNo from order_info where pid = '"
				+ pid + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public List<Map<String, Object>> getLevelCustomer(String pid) {

		String sql = null;

		if (StringUtils.isNotBlank(pid)) {
			// TODO Auto-generated method stub
			sql = " SELECT * from common_user where create_user_id='" + pid
					+ "' and status!='-1' and pcode!='1'";

		}

		return dongrenJdbcTemplate.queryForList(sql);

	}

	public List<Map<String, Object>> getLevelCustomer() {

		String sql = null;
		// TODO Auto-generated method stub
		sql = " SELECT * from common_user where  status!='-1'";

		return dongrenJdbcTemplate.queryForList(sql);

	}

	public PageInfo getCarInfoList(int pageNum, String searchValue,
			String createUserId) {

		// TODO Auto-generated method stub
		String sql = " SELECT * from common_user where status='1' and level_status!='admin'";

		if (StringUtils.isNotBlank(createUserId)) {
			sql += " and create_user_id='" + createUserId + "'";
		}

		if (StringUtils.isNotBlank(searchValue)) {
			sql += " and (pname like '%" + searchValue
					+ "%' or  username like '%" + searchValue
					+ "%' or  descriptions like '%" + searchValue + "%')";
		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public PageInfo getListBySelf(int pageNum, String searchValue, String pcode) {

		// TODO Auto-generated method stub
		String sql = " SELECT * from common_user where status='1' and level_status!='admin'";

		if (StringUtils.isNotBlank(pcode)) {
			sql += " and pcode='" + pcode + "'";
		}

		if (StringUtils.isNotBlank(searchValue)) {
			sql += " and (pname like '%" + searchValue
					+ "%' or  username like '%" + searchValue
					+ "%' or  descriptions like '%" + searchValue + "%')";
		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public PageInfo getList(int pageNum, String searchValue) {

		// TODO Auto-generated method stub
		String sql = " SELECT * from common_user where status='1' and level_status!='admin'";

		if (StringUtils.isNotBlank(searchValue)) {
			sql += " and (pname like '%" + searchValue
					+ "%' or  username like '%" + searchValue
					+ "%' or  descriptions like '%" + searchValue + "%')";
		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public PageInfo getAlipayList(int pageNum, String searchValue) {

		// TODO Auto-generated method stub
		String sql = " SELECT t.*,c.pname,c.username,c.pcode from alipay_info t ,common_user c where t.common_pid=c.pid";

		if (StringUtils.isNotBlank(searchValue)) {
			sql += " and (c.pname like '%" + searchValue
					+ "%' or  c.username like '%" + searchValue
					+ "%' or  c.descriptions like '%" + searchValue + "%')";
		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public PageInfo getTenpayList(int pageNum, String searchValue) {

		// TODO Auto-generated method stub
		String sql = " SELECT t.*,c.pname,c.username,c.pcode from tenpay_info t ,common_user c where t.common_pid=c.pid";

		if (StringUtils.isNotBlank(searchValue)) {
			sql += " and (c.pname like '%" + searchValue
					+ "%' or  c.username like '%" + searchValue
					+ "%' or  c.descriptions like '%" + searchValue + "%')";
		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public PageInfo getList(int pageNum, String searchValue, String pid) {

		// TODO Auto-generated method stub
		String sql = " SELECT * from common_user where level_status!='admin' and status='0'";

		if (StringUtils.isNotBlank(pid)) {
			sql += " and create_user_id='" + pid + "'";
		}

		if (StringUtils.isNotBlank(searchValue)) {
			sql += " and (pname like '%" + searchValue
					+ "%' or  username like '%" + searchValue
					+ "%' or  descriptions like '%" + searchValue + "%')";
		}

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public Map<String, Object> getParkByCode(String pcode, String pid) {

		String sql = " SELECT * from common_user where pcode='" + pcode
				+ "' and status!='-1'";

		if (StringUtils.isNotBlank(pid)) {
			sql += " and pid!='" + pid + "'";
		}

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public Map<String, Object> getParkByUsername(String username, String pid) {

		String sql = " SELECT * from common_user where username='" + username
				+ "' and status!='-1'";

		if (StringUtils.isNotBlank(pid)) {
			sql += " and pid!='" + pid + "'";
		}

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	// 下发扣费成功记录接口
	public Map<String, Object> CheckOutCarByPlate(String params) {

		String url = "http://39.108.4.1:8090/park/charge";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	public Map<String, Object> getparkInfos() {
		// TODO Auto-generated method stub
		String url = "http://39.108.4.1:8090/park/getparkinfos";

		String httpResponse = HttpUtil.httpGet(url);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	public Map<String, Object> getAlipayListById(String pid) {

		// TODO Auto-generated method stub
		String sql = " SELECT t.*,c.pname,c.username,c.pcode from alipay_info t ,common_user c where t.common_pid=c.pid";

		if (StringUtils.isNotBlank(pid)) {

			sql += " and t.common_pid='" + pid + "'";

		}

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public Map<String, Object> getWeixin(String pid) {

		String sql = " select * from tenpay_info t where t.common_pid='" + pid
				+ "'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public Map<String, Object> getTenpayListById(String pid) {

		// TODO Auto-generated method stub
		String sql = " SELECT t.*,c.pname,c.username,c.pcode from tenpay_info t ,common_user c where t.common_pid=c.pid";

		if (StringUtils.isNotBlank(pid)) {

			sql += " and t.common_pid='" + pid + "'";

		}

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	// 查询中央收费信息
	public List<Map<String, Object>> getAdminInfo(int pageNum, String jqId,
			String carText, String startDate, String endDate) {

		String sql = "select t.*,c.pname from order_info t,common_user c where c.pcode=t.parkNo and t.ParkNo='"
				+ jqId + "'";

		if (StringUtils.isNotBlank(carText)) {

			sql += " and CarNo like '%" + carText + "%'";

		}
		sql += " and t.create_time between '" + startDate + "' and '" + endDate
				+ "' order by t.create_time desc";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		List<Map<String, Object>> list = null;

		System.out.println("sql" + sql);

		return dongrenJdbcTemplate.queryForList(sql);
	}

	public Map<String, Object> ParkInformation(String params) {
		// TODO Auto-generated method stub
		String url = "http://39.108.4.1:8090/park/searchParkInformation";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

	// 12) 车基本档案表Card 查询

	public Map<String, Object> SearchCard(String params) {
		// TODO Auto-generated method stub
		String url = "http://39.108.4.1:8090/park/searchCard";

		params = params.replaceAll(" ", "%20");

		String httpResponse = HttpUtil.httpGet(url + "?" + params);

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isBlank(httpResponse)) {
			JSONObject jsStr = JSONObject.fromObject(httpResponse); // 将字符串{“id”：1}

			String resultCode = jsStr.getString("resultCode");// 获取resultCode的值

			String message = jsStr.getString("message");// 获取resultCode的值

			map.put("resultCode", resultCode);

			map.put("message", message);

			map.put("SearchedCount", jsStr.getString("SearchedCount"));

			map.put("ResultList", jsStr.getString("ResultList"));

		} else {

			map.put("resultCode", "-1");

			map.put("message", "数据处理失败，请重试");
		}

		System.out.println(map);

		return map;
	}

}
