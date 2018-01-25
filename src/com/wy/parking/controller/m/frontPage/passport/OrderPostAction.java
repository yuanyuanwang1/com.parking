/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wy.parking.model.OrderInfo;
import com.wy.parking.service.MoneyPaymentService;
import com.wy.parking.service.OrderInfoService;
import com.wy.parking.service.ParkingService;

import com.wy.superClass.SuperAction;
/**
 * @author Administrator
 * 
 */
public class OrderPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(OrderPostAction.class);

	private String orderId = null;

	private OrderInfo orderInfo = null;

	private OrderInfoService orderInfoService = null;
	
	private MoneyPaymentService moneyPaymentService = null;

	private String tenpayUrl = null;

	private String parkId = null;

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	private ParkingService parkingService = null;

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
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

	@Override
	public String execute() {

		// 创建停车场订单
		
//		String params="parkCode=1&cardCode=甘A00115&startTime=2015-12-08%2017:28:26.000&endTime=2015-12-08%2014:16:30.000";
//
//		// 查询接口匹配收费金额，不相等按照最后查询为主
//	    map= parkingService.GetGarageCarInfoByPlate(params);

//		System.out.println(orderInfo.getCarFee());

//		Double cost = Double.valueOf(map.get("data").toString());
//
//		if (BigDecimal.valueOf(cost).compareTo(orderInfo.getCarFee()) == 1) {
//			System.out.println("实际金额较大");
//			orderInfo.setCarFee(BigDecimal.valueOf(cost));
//		}

		orderId = orderInfoService.saveParkingOrder(orderInfo);

		parkId = orderInfo.getParkNo();

		System.out.println(orderId + ":" + orderInfo.getCarFee());
		
		System.out.println("pid" + pid);
		
		//根据pid获取微信信息
		
		//获取车场信息
		
		 String appId=null;
		 
		 String redirectUrl=null;
		
		Map<String, Object> mapPark=parkingService.getParkNo(orderInfo.getParkNo());
		
		System.out.println("mapPark"+mapPark);
 
		 if(mapPark!=null)
		 {
			 Map<String, Object> weixinInfo=parkingService.getWeixin(mapPark.get("pid").toString());
			 
			 if(weixinInfo!=null)
			 {
				  appId=weixinInfo.get("appId").toString();
					 
				  redirectUrl=weixinInfo.get("redirect_url").toString();
			 }
			 
				System.out.println("appId"+appId+"redirectUrl"+redirectUrl);

		 }
		 

		tenpayUrl = tenpay(appId,redirectUrl);
		
		System.out.println(tenpayUrl);

		return SUCCESS;

	}

	public String tenpay(String appId,String redirectUrl) {

		String backUri = redirectUrl + "?pid="
				+ orderInfo.getPid();

		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid="
				+ appId
				+ "&redirect_uri="
				+ backUri
				+ "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";

		return url;
	}

}
