/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wy.parking.model.OrderInfo;
import com.wy.parking.service.OrderInfoService;
import com.wy.parking.service.ParkingService;
import com.wy.superClass.SuperAction;
/**
 * @author wy
 * 
 */
public class AlipaySuccessGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(AlipaySuccessGetAction.class);
 
	private OrderInfoService orderInfoService = null;
	private String out_trade_no = null;
    private ParkingService parkingService=null;
    private String parkId=null;
    
	private String FreeTimeAfterCenterCharge=null;
	
	
    
	public String getFreeTimeAfterCenterCharge() {
		return FreeTimeAfterCenterCharge;
	}

	public void setFreeTimeAfterCenterCharge(String freeTimeAfterCenterCharge) {
		FreeTimeAfterCenterCharge = freeTimeAfterCenterCharge;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public OrderInfoService getOrderInfoService() {
		return orderInfoService;
	}

	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	 

	@Override
	public String execute() {
		
		System.out.println(out_trade_no);
				
		OrderInfo orderInfo = orderInfoService.getOnlyOrderInfoByOrderCode(out_trade_no);
		
		System.out.println("orderInfo"+orderInfo.getOrderStatus());
		
		parkId=orderInfo.getParkNo();
		
		if (orderInfo!=null && orderInfo.getOrderStatus().equals("payed")) {
			 
			//写回停车场出厂表
			System.out.println("写回停车场出厂表Success");
			//CheckOutCarByPlate		 
			
			String params="parkCode="+parkId+"&cardCode="+orderInfo.getCarNo()+"&inTime="+orderInfo.getInDateTime()+"&payTime="+orderInfo.getPayTime()+"&amount="+orderInfo.getCarFee();
			
			 Map<String,Object> mapPlate=new HashMap<String, Object>();
			
			 mapPlate= parkingService.CheckOutCarByPlate(params);
			 
			JSONObject obj=JSONObject.fromObject(mapPlate); 
			 
			if (obj.get("resultCode").toString().equals("0")){
				return ERROR;
			}
			if(obj.get("resultCode").toString().equals("1"))
			{
				orderInfo.setOrderStatus("exchange");//已付款可以出场
				
				//orderInfo.setWebServiceTime(new Date());
				
				orderInfoService.update(orderInfo);
			}
			System.out.println("停车场订单状态"+orderInfo.getOrderStatus());
			
			System.out.println("停车场结果"+obj);
		}
		
		map = orderInfoService.getMapByOrderCode(out_trade_no);
		
		System.out.println("map"+map);
		
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 这里的格式可以自己设置
		
//		 Date date = orderInfo.getWebServiceTime();
//		 
//		 System.out.println("调用出场接口时间："+ date);
//		 
//		 if(map!=null && map.get("FREETIMEAFTERCENTERCHARGE")!=null && date!=null){
//			 
//			 int freeTime=Integer.parseInt(map.get("FREETIMEAFTERCENTERCHARGE").toString());
//			 
//			 date.setMinutes(date.getMinutes()+freeTime);//给当前时间加免费时间分钟后的时间
//			 
//			 FreeTimeAfterCenterCharge=from.format(date);
//			 
//			 System.out.println("免费时间："+ map.get("FREETIMEAFTERCENTERCHARGE"));
//
//			 System.out.println("相加之后的时间："+FreeTimeAfterCenterCharge);
//		 }

		return INPUT;

	}

}
