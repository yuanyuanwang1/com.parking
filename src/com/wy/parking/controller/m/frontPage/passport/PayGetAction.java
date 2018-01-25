/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;

import wxpay.SDKRuntimeException;
import wxpay.WxPayHelper;

import com.wy.parking.service.MoneyPaymentService;
import com.wy.parking.service.OrderInfoService;
import com.wy.superClass.SuperAction;

/**
 * @author jian198001 工作台
 * 
 */
public class PayGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(PayGetAction.class);

	private String requestHtml = null;

	private String userAgentType = "notWx";

	private MoneyPaymentService moneyPaymentService = null;
	
	private OrderInfoService orderInfoService=null;

	private String tenpayUrl = null;
	
	private String FreeTimeAfterCenterCharge=null;
	
	private String parkId=null;
	
	

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getFreeTimeAfterCenterCharge() {
		return FreeTimeAfterCenterCharge;
	}

	public void setFreeTimeAfterCenterCharge(String freeTimeAfterCenterCharge) {
		FreeTimeAfterCenterCharge = freeTimeAfterCenterCharge;
	}

	public OrderInfoService getOrderInfoService() {
		return orderInfoService;
	}

	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	public String getTenpayUrl() {
		return tenpayUrl;
	}

	public void setTenpayUrl(String tenpayUrl) {
		this.tenpayUrl = tenpayUrl;
	}

	public String getRequestHtml() {
		return requestHtml;
	}

	public void setRequestHtml(String requestHtml) {
		this.requestHtml = requestHtml;
	}

	public String getUserAgentType() {
		return userAgentType;
	}

	public void setUserAgentType(String userAgentType) {
		this.userAgentType = userAgentType;
	}

	public MoneyPaymentService getMoneyPaymentService() {
		return moneyPaymentService;
	}

	public void setMoneyPaymentService(MoneyPaymentService moneyPaymentService) {
		this.moneyPaymentService = moneyPaymentService;
	}

	@Override
	public String execute() {

		String userAgent = request.getHeader("user-agent");

		if (StringUtils.contains(userAgent, "MicroMessenger")) {

			userAgentType = "wx";

		}

		String remoteAddr = request.getRemoteAddr();

		WxPayHelper wxPayHelper = moneyPaymentService.getWxPayHelper(pid,remoteAddr);
		
		map=orderInfoService.getMapOne("order_info", pid);
		
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 这里的格式可以自己设置
		
		 Date date = new Date();
		 
		 if(map!=null && map.get("FREETIMEAFTERCENTERCHARGE")!=null){
			 
			 int freeTime=Integer.parseInt(map.get("FREETIMEAFTERCENTERCHARGE").toString());
			 
			 date.setMinutes(date.getMinutes()+freeTime);//给当前时间加免费时间分钟后的时间
			 
			 FreeTimeAfterCenterCharge=from.format(date);
			 
			 System.out.println("免费时间："+ map.get("FREETIMEAFTERCENTERCHARGE"));

			 System.out.println("相加之后的时间："+FreeTimeAfterCenterCharge);
		 }

		try {
			requestHtml = wxPayHelper.CreateBizPackage();
		} catch (SDKRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("parkId"+parkId);

		// 判断当前浏览器类型，如果是在微信浏览器内，则不出现支付宝支付图标。如果是在其他浏览器内，则出现支付宝支付和微信支付图标。点击微信支付图标后，如在微信浏览器内，则直接出现支付画面，如果在其他浏览器内，则出现二维码，长按图标进行二维码识别来进入微信支付.
		return SUCCESS;

	}
	
}
