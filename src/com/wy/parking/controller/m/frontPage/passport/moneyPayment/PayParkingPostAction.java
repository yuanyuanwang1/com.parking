/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.moneyPayment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;

import com.wy.parking.model.OrderInfo;
import com.wy.parking.service.MoneyPaymentService;
import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;

import wxpay.HttpConnect;
import wxpay.HttpResponse;


/**
 * @author Administrator
 * 
 */
public class PayParkingPostAction extends SuperAction {

	private String appId = null;

	private List<Map<String, Object>> lists = null;

	private Logger logger = Logger.getLogger(PayParkingPostAction.class);

	private MoneyPaymentService moneyPaymentService = null;
	
	private ParkingService parkingService=null;

	private String nonceStr = null;

	private String orderId = null;

	private OrderInfo orderInfo = null;

	private String packageValue = null;

	private String paySign = null;

	private String requestHtml = null;

	private String timeStamp = null;

	private String userAgentType = "notWx";

	@Override
	public String execute() {
		
		//根据订单id判断状态订单和来源
		
		System.out.println("停车场进去微信支付-使用");

		String code = request.getParameter("code");

		String pid = request.getParameter("pid");

		System.out.println("code" + code);

		System.out.println("pid" + pid);
		
		map = parkingService.getParkingOrder(pid);
		
		String appSerect=null;
		
		if(map!=null)
		{
			 Map<String, Object> mapPark=parkingService.getParkNo(map.get("parkNo").toString());
			 
			 if(mapPark!=null)
			 {
				 Map<String, Object> weixinInfo=moneyPaymentService.getWeixin(mapPark.get("pid").toString());
				 
				 if(weixinInfo!=null)
				 {
					  appId=weixinInfo.get("appId").toString();
						 
					  appSerect=weixinInfo.get("appSerect").toString();
				 }
			 }
			 

		}

		String openId = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appId + "&secret=" + appSerect
				+ "&code=" + code + "&grant_type=authorization_code";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);
		String tempValue = "";
		if (temp == null) {
			System.out.println("openId" + null);
		} else {
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				System.out.println(tempValue);
			}
			openId = jsonObj.getString("openid");
		}
		System.out.println("openId" + openId);
		
		System.out.println("map" + map);

		String userAgent = request.getHeader("user-agent");

		if (StringUtils.contains(userAgent, "MicroMessenger")) {

			userAgentType = "wx";

		}

		String remoteAddr = request.getRemoteAddr();

		System.out.println(remoteAddr);

		lists = parkingService.getWxPayHelpers(pid, remoteAddr, openId,
				request, response);

		appId = lists.get(0).get("appid").toString();
		timeStamp = lists.get(0).get("timestamp").toString();
		nonceStr = lists.get(0).get("nonceStr").toString();
		packageValue = lists.get(0).get("packages").toString();
		paySign = lists.get(0).get("sign").toString();

		System.out.println("appId" + appId + "timeStamp" + timeStamp
				+ "packageValue" + packageValue + "paySign" + paySign);

		return SUCCESS;

	}

	public String getAppId() {
		return appId;
	}

	@Override
	public List<Map<String, Object>> getList() {
		return list;
	}

	public Logger getLogger() {
		return logger;
	}

	public MoneyPaymentService getMoneyPaymentService() {
		return moneyPaymentService;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public String getPaySign() {
		return paySign;
	}

	public String getRequestHtml() {
		return requestHtml;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getUserAgentType() {
		return userAgentType;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setMoneyPaymentService(MoneyPaymentService moneyPaymentService) {
		this.moneyPaymentService = moneyPaymentService;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public void setRequestHtml(String requestHtml) {
		this.requestHtml = requestHtml;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setUserAgentType(String userAgentType) {
		this.userAgentType = userAgentType;
	}
	
     public String executeNative() {
		
		System.out.println("进去微信支付");
		
//		String code = request.getParameter("code");
//		
//		String pid = request.getParameter("pid");
//
//		System.out.println("code" + code);
//		
//		System.out.println("pid" + pid);
//		
//		String openId ="";
//		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WxpayConfig.appId+"&secret="+WxpayConfig.appSerect+"&code="+code+"&grant_type=authorization_code";
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
//		String tempValue="";
//		if( temp == null){
//			System.out.println("openId" + null);
//		}else
//		{
//			try {
//				tempValue = temp.getStringResult();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			JSONObject jsonObj = JSONObject.fromObject(tempValue);
//			if(jsonObj.containsKey("errcode")){
//				System.out.println(tempValue);
//			}
//			openId = jsonObj.getString("openid");
//		}
//		System.out.println("openId" + openId);
//			
//		map = orderInfoService.getMapForTourist(pid);
//
//		String userAgent = request.getHeader("user-agent");
//
//		if (StringUtils.contains(userAgent, "MicroMessenger")) {
//
//			userAgentType = "wx";
//
//		}
//
//		String remoteAddr = request.getRemoteAddr();
//
//		System.out.println(remoteAddr);
		
	//	lists = moneyPaymentService.getWxPayHelpersNative(pid, remoteAddr, openId,request, response);

		lists = moneyPaymentService.getWxPayHelpers(pid, request.getRemoteAddr(), null,request, response);
		
		appId = lists.get(0).get("appid").toString();
		timeStamp = lists.get(0).get("timestamp").toString();
		nonceStr = lists.get(0).get("nonceStr").toString();
		packageValue = lists.get(0).get("packages").toString();
		paySign = lists.get(0).get("sign").toString();

		System.out.println("appId" + appId + "timeStamp"+timeStamp +"packageValue"+packageValue + "paySign"+paySign);
//		// 判断当前浏览器类型，如果是在微信浏览器内，则不出现支付宝支付图标。如果是在其他浏览器内，则出现支付宝支付和微信支付图标。点击微信支付图标后，如在微信浏览器内，则直接出现支付画面，如果在其他浏览器内，则出现二维码，长按图标进行二维码识别来进入微信支付.

		return SUCCESS;

	}


}
