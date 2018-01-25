/**
 * 
 */
package com.wy.parking.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import wxpay.GetWxOrderno;
import wxpay.RequestHandler;
import wxpay.WxPayHelper;

import com.alipay.AlipayParameterName;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.alipay.util.UtilDate;
import com.wy.parking.model.OrderInfo;
import com.wy.parking.model.PayInfoAlipay;
import com.wy.parking.model.PayInfoWx;
import com.wy.superClass.SuperService;
import com.wy.util.DateUtil;
import com.wy.util.MathUtil;
/**
 * @author wy
 * 
 */
public class MoneyPaymentService extends SuperService {

	// 付款方式 session 变量名
	public final static String MONEY_PAYMENT_TYPE_SESSION_NAME = "moneyPaymentType";

//	private String callBackUrl = "http://www.sztigerwong.com/com.wy.parking/action/web/frontPage/passport/AlipaySuccessGet.action";
	private Logger logger = Logger.getLogger(MoneyPaymentService.class);

	//操作中断返回地址
	
//	private String merchantUrl = "http://www.sztigerwong.com/com.wy.parking/action/m/frontPage/passport/IndexGet.action";

	// 依赖服务

//	private String notifyUrl = "http://www.sztigerwong.com/com.wy.parking/action/web/frontPage/passport/moneyPayment/wapAlipayNotifyDataProcessGet.action";

	private OrderInfoService orderInfoService = null;

	private ParkingService parkingService = null;

	private WxpayService wxpayService = null;

	private PayInfoWxService payInfoWxService = null;

    

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	private String buildRequest(String notifyUrl,String callBackUrl,String merchantUrl,String partner, String sellerEmail,
			String alipay_key, String out_trade_no, String subject,
			String total_fee) {

		// 支付宝网关地址
		String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

		// //////////////////////////////////调用授权接口alipay.wap.trade.create.direct获取授权码token//////////////////////////////////////

		// 返回格式
		String format = "xml";
		// 必填，不需要修改

		// 返回格式
		String v = "2.0";
		// 必填，不需要修改

		// req_data详细信息

		String req_id = UtilDate.getOrderNum();

		// 请求业务参数详细
		String req_dataToken = "<direct_trade_create_req><notify_url>"
				+ notifyUrl + "</notify_url><call_back_url>" + callBackUrl
				+ "</call_back_url><seller_account_name>" + sellerEmail
				+ "</seller_account_name><out_trade_no>" + out_trade_no
				+ "</out_trade_no><subject>" + subject
				+ "</subject><total_fee>" + total_fee + "</total_fee><merchant_url>"
				+ merchantUrl + "</merchant_url></direct_trade_create_req>";
		// 必填

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", partner);
		sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
		sParaTempToken.put("sec_id", AlipayConfig.sign_type);
		sParaTempToken.put("format", format);
		sParaTempToken.put("v", v);
		sParaTempToken.put("req_id", req_id);
		sParaTempToken.put("req_data", req_dataToken);

		// 获取token
		String request_token = null;
		try {

			// 建立请求
			String sHtmlTextToken = AlipaySubmit.buildRequest(
					ALIPAY_GATEWAY_NEW, "", "", sParaTempToken, alipay_key);
			// URLDECODE返回的信息
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,
					AlipayConfig.input_charset);
			request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// out.println(request_token);

		// //////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////

		// 业务详细
		String req_data = "<auth_and_execute_req><request_token>"
				+ request_token + "</request_token></auth_and_execute_req>";
		// 必填

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("sec_id", AlipayConfig.sign_type);
		sParaTemp.put("format", format);
		sParaTemp.put("v", v);
		sParaTemp.put("req_data", req_data);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW,
				sParaTemp, "get", "确认", alipay_key);

		return sHtmlText;

	}

	public String buildReuqest(String pid) {

		Map<String, Object> paymentInfo = getPaymentInfo(pid);
		
		System.out.println("paymentInfo"+paymentInfo);
		
		//根据停车场编号找到唯一的支付宝信息
		
		String parkNo=paymentInfo.get("parkNo").toString();
		
		System.out.println("parkNo"+parkNo);

		
		//根据parkNo获取CommonUser信息
		
		Map<String,Object> parkMap=parkingService.getParkNo(parkNo);
		
		
		System.out.println("parkMap"+parkMap);

		Map<String, Object> secnicInfo = getScenicInfo(parkMap.get("pid").toString());
		
		System.out.println("secnicInfo"+secnicInfo);

		String partner = (String) secnicInfo.get("alipay_partner");
		String sellerEmail = (String) secnicInfo.get("pay_id");
		String out_trade_no = (String) paymentInfo.get("order_code");
		
		String callBackUrl = (String) secnicInfo.get("call_Back_Url");
		String merchantUrl = (String) secnicInfo.get("merchant_Url");
		
		merchantUrl=merchantUrl+"?parkCode="+parkNo;
		
		String notifyUrl = (String) secnicInfo.get("notify_Url");
		
		
		String parkingInfo=" ";
		
		if(paymentInfo.get("carNo")!=null){
			
			parkingInfo=paymentInfo.get("carNo").toString();
		}
		
		String subject = "订单" +parkingInfo;
		
		BigDecimal totalPrices = new  BigDecimal(paymentInfo.get("carfee").toString());

		String alipay_key = (String) secnicInfo.get("alipay_key");

		String total_fee = "0";

		if (totalPrices != null) {

			total_fee = totalPrices.toString();

		}

		return buildRequest(notifyUrl,callBackUrl,merchantUrl,partner, sellerEmail, alipay_key, out_trade_no,
				subject, total_fee);

	}

	public String deliverNotify(String accessToken, String appid,
			String openid, String transid, String outTradeNo,
			String appSignature) {

		String url = "https://api.weixin.qq.com/pay/delivernotify?access_token="
				+ accessToken;

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("appid", appid);

			jsonObject.put("openid", openid);

			jsonObject.put("transid", transid);

			jsonObject.put("out_trade_no", outTradeNo);

			jsonObject.put("deliver_timestamp",
					System.currentTimeMillis() / 1000);

			jsonObject.put("deliver_status", "1");

			jsonObject.put("deliver_msg", "ok");

			jsonObject.put("app_signature", appSignature);

			jsonObject.put("sign_method", "sha1");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost method = new HttpPost(url);
		StringEntity entity = null;
		entity = new StringEntity(jsonObject.toString(), "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		method.setEntity(entity);

		HttpResponse httpResponse = null;
		String resData = null;
		JSONObject resJson = null;
		String code = null;
		try {
			httpResponse = httpClient.execute(method);
			resData = EntityUtils.toString(httpResponse.getEntity());
			resJson = new JSONObject(resData);
			code = resJson.get("errcode").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resData;

	}

	private String genNonceStr() {
		Random random = new Random();

		return getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
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

	public OrderInfoService getOrderInfoService() {
		return orderInfoService;
	}

	public PayInfoWxService getPayInfoWxService() {
		return payInfoWxService;
	}

	private Map<String, Object> getPaymentInfo(String pid) {

		String sql = " select order_code,carfee,carNo,parkNo from order_info where pid = '"
				+ pid + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	
	private Map<String, Object> getparkNo(String orderCode) {

		String sql = " select order_code,carfee,carNo,parkNo from order_info where order_code = '"
				+ orderCode + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	private Map<String, Object> getScenicInfo(String pid) {

		String sql = " select * from alipay_info t where t.common_pid='"+pid+"'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	
	public Map<String, Object> getWeixin(String pid) {

		String sql = " select * from tenpay_info t where t.common_pid='"+pid+"'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public WxPayHelper getWxPayHelper(String pid, String remoteAddr) {

		Map<String, Object> paymentInfo = getPaymentInfo(pid);

		String out_trade_no = (String) paymentInfo.get("order_code");
		
		String subject = "订单：" + out_trade_no;
		
		BigDecimal totalPrices=new BigDecimal(paymentInfo.get("carfee").toString());  
		
//		BigDecimal totalPrices = (BigDecimal) paymentInfo.get("carfee");

		BigDecimal totalFee = new BigDecimal(100).multiply(totalPrices);
		
          	//根据停车场编号找到唯一的微信信息
		
		String parkNo=paymentInfo.get("parkNo").toString();
		
		System.out.println("parkNo"+parkNo);

		
		//根据parkNo获取CommonUser信息
		
		Map<String,Object> parkMap=parkingService.getParkNo(parkNo);
		
		
		System.out.println("parkMap"+parkMap);
		
		
	     Map<String, Object> weixinInfo=getWeixin(parkMap.get("pid").toString());
	     
	     System.out.println("weixinInfo"+weixinInfo);
		 
		 String appId=weixinInfo.get("appId").toString();
		 
		 String mchId=weixinInfo.get("mch_id").toString();
		 
		 String notifyUrl=weixinInfo.get("notifyUrl").toString();
		 
		 String appSerect=weixinInfo.get("appSerect").toString();
		 
		 String partnerKey=weixinInfo.get("partnerKey").toString();
		 
		 String signType=weixinInfo.get("signType").toString(); 
		 
		 String bankType=weixinInfo.get("bankType").toString(); 
		 
		 String partner=weixinInfo.get("partner").toString();  
		 
		 String feeType=weixinInfo.get("feeType").toString();  
		 
		 String inputCharset=weixinInfo.get("inputCharset").toString();  

		Map<String, String> para = new HashMap<String, String>();

		WxPayHelper wxPayHelper = new WxPayHelper();
		wxPayHelper.SetAppId(appId);
		wxPayHelper.SetAppKey(partnerKey);
		wxPayHelper.SetPartnerKey(partnerKey);
		wxPayHelper.SetSignType(signType);

		wxPayHelper.SetParameter("bank_type",bankType);
		wxPayHelper.SetParameter("body", subject);
		wxPayHelper.SetParameter("partner",partner);
		wxPayHelper.SetParameter("out_trade_no", out_trade_no);
		wxPayHelper.SetParameter("total_fee", totalFee.toString());
		wxPayHelper.SetParameter("fee_type",feeType);
		wxPayHelper.SetParameter("notify_url",notifyUrl);
		wxPayHelper.SetParameter("spbill_create_ip", remoteAddr);
		wxPayHelper.SetParameter("input_charset",inputCharset);

		return wxPayHelper;

	}

	public List<Map<String, Object>> getWxPayHelpers(String pid,
			String remoteAddr, String openid, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("进下单-----");
		
		Map<String, Object> paymentInfo = getPaymentInfo(pid);
		
		System.out.println("paymentInfo"+paymentInfo);
		
		//根据停车场编号找到唯一的支付宝信息
		
		String parkNo=paymentInfo.get("parkNo").toString();
		
		System.out.println("parkNo"+parkNo);

		
		//根据parkNo获取CommonUser信息
		
		Map<String,Object> parkMap=parkingService.getParkNo(parkNo);
		
		
		System.out.println("parkMap"+parkMap);

		Map<String, Object> secnicInfo = getScenicInfo(parkMap.get("pid").toString());
		
		System.out.println("secnicInfo"+secnicInfo);

		String out_trade_no = (String) paymentInfo.get("order_code");
		
		String parkingInfo=" ";
		
		if(paymentInfo.get("carNo")!=null){
			
			parkingInfo=paymentInfo.get("carNo").toString();
		}
		
		String body = "订单" +parkingInfo;
		System.out.println("openid" + openid);
		
		BigDecimal totalPrices = new  BigDecimal(paymentInfo.get("carfee").toString());
		
		System.out.println(totalPrices);

		BigDecimal totalFees = new BigDecimal(100).multiply(totalPrices);

		System.out.println(totalFees);

		int totalFee = totalFees.intValue();

		System.out.println(totalFees);

		String totalPrice = String.valueOf(totalFee);
		
		 Map<String, Object> weixinInfo=getWeixin(parkMap.get("pid").toString());
		 
		 String appId=weixinInfo.get("appId").toString();
		 
		 String mchId=weixinInfo.get("mch_id").toString();
		 
		 String notifyUrl=weixinInfo.get("notifyUrl").toString();
		 
		 String appSerect=weixinInfo.get("appSerect").toString();
		 
		 String partnerKey=weixinInfo.get("partnerKey").toString();
		 

		Map<String, String> para = new HashMap<String, String>();
		para.put("appid", appId);
		para.put("mch_id",mchId);
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
		reqHandler.init(appId, appSerect,partnerKey);

		String sign = reqHandler.createSignOther(para, false,partnerKey);

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
		finalpackage.put("key",partnerKey);

		String finalsign = reqHandler.createSign(finalpackage, false,partnerKey);

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

	public WxpayService getWxpayService() {
		return wxpayService;
	}

	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	public void setPayInfoWxService(PayInfoWxService payInfoWxService) {
		this.payInfoWxService = payInfoWxService;
	}

	public void setWxpayService(WxpayService wxpayService) {
		this.wxpayService = wxpayService;
	}

	// 支付宝异步链接地址
	public String wapAlipayNotifyProcess(Map requestParams) {

		Map<String, String> params = new HashMap<String, String>();

		logger.info("wapAlipayNotifyProcess go");

		if (requestParams == null || requestParams.isEmpty()) {

			return "fail";

		}

		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		logger.info("准备验证");

		// RSA签名解密
		if (com.alipay.config.AlipayConfig.sign_type.equals("0001")) {
			try {
				params = com.alipay.util.AlipayNotify.decrypt(params);

			} catch (Exception e) {

				logger.info("验证失败");
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return "fail";
			}
		}
		
		System.out.println("params"+params);
 
		// XML解析notify_data数据
		Document doc_notify_data = null;
		try {
			doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "fail";

		}
		
		System.out.println("doc_notify_data"+doc_notify_data);

		// 商户订单号
		String out_trade_no = doc_notify_data.selectSingleNode(
				"//notify/out_trade_no").getText();
		
		System.out.println("out_trade_no"+out_trade_no);

		// 支付宝交易号
		String trade_no = doc_notify_data.selectSingleNode("//notify/trade_no")
				.getText();
		
		System.out.println("trade_no"+trade_no);

		// 交易状态
		String trade_status = doc_notify_data.selectSingleNode(
				"//notify/trade_status").getText();
		
		
		Map<String,Object> parkNo=getparkNo(out_trade_no);
		
		System.out.println("parkNo"+parkNo);
		
		//根据parkNo获取CommonUser信息
		
		Map<String,Object> parkMap=parkingService.getParkNo(parkNo.get("parkNo").toString());

		Map<String, Object> secnicInfo = getScenicInfo(parkMap.get("pid").toString());
		
		System.out.println("secnicInfo"+secnicInfo);

		String sql = " select * from alipay_info where common_pid='"+secnicInfo.get("common_pid")+"'";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();

		Map map = dongrenJdbcTemplate.queryForMap(sql, paramMap);
		
		System.out.println("mapResult"+map);
		
		System.out.println("验证"+map.get("pay_id"));
		
		logger.info("验证"+map.get("pay_id"));
		
		System.out.println("验证"+map.get("pay_id"));

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		String partner = (String) map.get("pay_id");

		String key = (String) map.get("alipay_key");

		try {
			if (com.alipay.util.AlipayNotify.verifyNotify(params, partner, key)) {

				return "fail";

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "fail";

		}

		// 验证成功
		// ////////////////////////////////////////////////////////////////////////////////////////
		// 请在这里加上商户的业务逻辑程序代码

		// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

		if (trade_status.equals("TRADE_FINISHED")) {

			logger.info("to--3months trade-finished by wangyuan" + "order流水號:"
					+ AlipayParameterName.outTradeNO);

			return "success";

		}

		if (!trade_status.equals("TRADE_SUCCESS")) {
			// 判断该笔订单是否在商户网站中已经做过处理
			// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
			// 如果有做过处理，不执行商户的业务程序

			// 注意：
			// 该种交易状态只在两种情况下出现
			// 1、开通了普通即时到账，买家付款成功后。
			// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。

			return "fail";

		}

		paymentSuccess(doc_notify_data);

		return "success"; // 请不要修改或删除

		// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

		// ////////////////////////////////////////////////////////////////////////////////////////

	}

	private String paymentSuccess(Document docNotifyData) {
		// TODO Auto-generated method stub
		// 商户订单号
		String outTradeNo = docNotifyData.selectSingleNode("//notify/out_trade_no").getText();

		// 判断是否已经处理过该条订单
		PayInfoAlipay payInfo = payInfoWxService.getPayInfoByOutTradeNo(outTradeNo);

		logger.info("payInfo - " + payInfo);

		// 将支付宝返回的数据集中的数据设置到payInfo中去
		payInfo = getPayInfoByDirectPay(docNotifyData, payInfo);

		payInfo.setRefundStatus("unrefund");

		payInfoWxService.save(payInfo);
		
		logger.info("payInfo - save = success");
		
		//更新订单状态

		boolean status=updateOrderStatus(outTradeNo, "payed");
		
		System.out.println("-------------------------order_info----------------"+status);
		
		OrderInfo orderInfo = orderInfoService.getOnlyOrderInfoByOrderCode(outTradeNo);
		
		if (orderInfo!=null && orderInfo.getOrderStatus().equals("payed")) {
		
		//写回停车场出厂表
		System.out.println("写回停车场出厂表");
		//CheckOutCarByPlate		 
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		
		java.util.Date date=new java.util.Date();  
		
		String payTime=sdf.format(date);  
		
		System.out.println("payTime"+payTime);
		
		try {
			orderInfo.setPayTime(sdf.parse(payTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderInfoService.update(orderInfo);
		
		
		BigDecimal amount=orderInfo.getCarFee();
		
		BigDecimal num = new BigDecimal("100");
		
		amount=amount.multiply(num);
		
		System.out.println("停车场费用"+amount);
		
		String params="parkCode="+orderInfo.getParkNo()+"&cardCode="+orderInfo.getCarNo()+"&inTime="+orderInfo.getInDateTime()+"&payTime="+payTime+"&amount="+amount;
		
		System.out.println("停车场params"+params);
				
		Map<String,Object> obj = parkingService.CheckOutCarByPlate(params);
		
		System.out.println(obj);
		
		if (obj.get("resultCode").toString().equals("0")){
			
			orderInfo.setOrderStatus("exchange");//已付款可以出场
			
			orderInfoService.update(orderInfo);
		}
		
		System.out.println("停车场订单状态"+orderInfo.getOrderStatus());
		
		System.out.println("停车场结果"+obj);
//		
    	}

		logger.info("paymentSuccess -- updateOrderStatus -  success");
		
		return outTradeNo;
	}

	
	public PayInfoAlipay getPayInfoByDirectPay(Document docNotifyData, PayInfoAlipay payInfo) {

		if (payInfo == null) {
			payInfo = new PayInfoAlipay();
			payInfo.setCreateTime(new Date());
			payInfo.setStatus("1");
		}

		// 商户订单号
		String out_trade_no = docNotifyData.selectSingleNode(
				"//notify/out_trade_no").getText();

		// 支付宝交易号
		String trade_no = docNotifyData.selectSingleNode("//notify/trade_no")
				.getText();

		// 交易状态
		String trade_status = docNotifyData.selectSingleNode(
				"//notify/trade_status").getText();

		String subject = docNotifyData.selectSingleNode("//notify/subject")
				.getText();

		String gmtCreate = docNotifyData
				.selectSingleNode("//notify/gmt_create").getText();

		String gmtPayment = docNotifyData.selectSingleNode(
				"//notify/gmt_payment").getText();

		String gmtClose = null;
		//
		// gmtClose = docNotifyData.selectSingleNode("//notify/gmt_close")
		// .getText();
		//

		String refundStatus = null;
		//
		// docNotifyData.selectSingleNode(
		// "//notify/refund_status").getText();

		String gmtRefund = null;

		// docNotifyData
		// .selectSingleNode("//notify/gmt_refund").getText();

		String sellerEmail = docNotifyData.selectSingleNode(
				"//notify/seller_email").getText();

		String sellerId = docNotifyData.selectSingleNode("//notify/seller_id")
				.getText();

		String buyerEmail = docNotifyData.selectSingleNode(
				"//notify/buyer_email").getText();

		String buyerId = docNotifyData.selectSingleNode("//notify/buyer_id")
				.getText();

		String totalFee = docNotifyData.selectSingleNode("//notify/total_fee")
				.getText();

		payInfo.setOutTradeNo(out_trade_no);
		payInfo.setSubject(subject);
		payInfo.setPaymentType("alipay");
		payInfo.setPrices(Double.valueOf(totalFee));
		payInfo.setTradeNo(trade_no);
		payInfo.setTradeStatus(trade_status);

		payInfo.setGmtCreate(parseDate(gmtCreate));
		payInfo.setGmtPayment(parseDate(gmtPayment));
		payInfo.setGmtClose(parseDate(gmtClose));
		payInfo.setRefundStatus(refundStatus);
		payInfo.setGmtRefund(parseDate(gmtRefund));

		payInfo.setSellerEmail(sellerEmail);
		payInfo.setSellerId(sellerId);
		payInfo.setBuyerEmail(buyerEmail);
		payInfo.setBuyerId(buyerId);

		return payInfo;

	}
	
	public static Date parseDate(String dateStr) {

		if (StringUtils.isBlank(dateStr)) {

			return null;

		}

		if (StringUtils.length(dateStr) == 10) {

			return DateUtil.parseDate(dateStr);

		}

		return DateUtil.parseTime(dateStr);

	}
	
	public Boolean updateOrderStatus(String orderCode, String orderStatus) {

		if (StringUtils.isBlank(orderCode) || StringUtils.isBlank(orderStatus)) {
			return false;
		}
		
		Map<String, Object> map=orderInfoService.getOrderInfoId(orderCode);
		
		String pid=map.get("pid").toString();
		
		System.out.println("pid"+pid);

		OrderInfo orderInfo = orderInfoService.getOrderInfo(pid);
		
		System.out.println("orderInfo"+orderInfo.getOrderStatus());
		
		if (orderInfo == null) {
			return false;
		}


		orderInfo.setOrderStatus(orderStatus);

		orderInfoService.update(orderInfo);
		
		System.out.println("orderInfo"+orderInfo.getOrderStatus());

		return true;

	}
}
