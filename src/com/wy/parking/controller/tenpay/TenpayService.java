/**
 * 
 */
package com.wy.parking.controller.tenpay;

import java.math.BigDecimal;
import java.security.MessageDigest;
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
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wy.parking.model.MoneyPaymentType;
import com.wy.parking.model.OrderInfo;
import com.wy.parking.model.PayInfoWx;
import com.wy.parking.service.OrderInfoService;
import com.wy.parking.service.ParkingService;
import com.wy.parking.service.PayInfoWxService;
import com.wy.superClass.SuperService;

import wxpay.GetWxOrderno;
import wxpay.RequestHandler;

/**
 * 支付口统一调用服务类
 * 
 * @author jian198001
 * 
 */
public class TenpayService extends SuperService {

	// 付款方式 session 变量名
	public final static String MONEY_PAYMENT_TYPE_SESSION_NAME = "moneyPaymentType";
	
	public String notifyParkingUrl = "http://www.shg.com.cn/com.chentuo.shgpw/action/tenpay/tenpayNotifyDataParkingProcess.action";
	
	public static String getMoneyPaymentType(String paymentTypeCode) {

		if (StringUtils.isBlank(paymentTypeCode)) {
			return "支付方式不存在";
		}

		if (MoneyPaymentType.ALIPAY.equals(paymentTypeCode)) {
			return "支付宝";
		}

		if (MoneyPaymentType.BANK.equals(paymentTypeCode)) {
			return "合作银行";
		}

		if (MoneyPaymentType.EXCHANGEMACHINE.equals(paymentTypeCode)) {
			return "换票机";
		}

		if (MoneyPaymentType.TICKETWINDOW.equals(paymentTypeCode)) {
			return "售票点";
		}

		return "未知的支付方式";

	}

	private Logger logger = Logger.getLogger(TenpayService.class);

	// 依赖服务
	private OrderInfoService orderInfoService = null; // 订单服务类

	private PayInfoWxService payInfoWxService = null; // 消费信息服务类

	private TenpayNotify tenpayNotify = null;
	
	private ParkingService parkingService = null; // 消费信息服务类

	/**
	 * @param notifyParamsTemp
	 * @return
	 */
	public String alipayNotifyProcess(Map<String, String> notifyParamsTemp) {

		Map<String, String> resultMap = new HashMap();

		if (notifyParamsTemp == null) {
			return "fail";
		}
		System.out.println(notifyParamsTemp);
		// 处理返回的参数集
		// Map<String, String> notifyParam =
		// notifyParamProcess(notifyParamsTemp);
		Map<String, String> notifyParam = notifyParamsTemp;
		
		String outTrade = notifyParam.get(TenpayParameterName.outTradeNO);

		logger.info("verifyNotify - begin");
		
		String partnerKey = null;
		
		if (outTrade != null) {
			
			System.out.println(outTrade);

			String pid=payInfoWxService.getOrderInfoPid(outTrade);
			
			System.out.println("pid"+pid);

			OrderInfo orderInfoVerfy = orderInfoService.getOrderInfo(pid);

			System.out.println("OrderParkingInfo" + orderInfoVerfy);
			
			Map<String,Object> parkMap=payInfoWxService.getParkNo(orderInfoVerfy.getParkNo());
			
			if(parkMap!=null && parkMap.get("pid")!=null)
			{
			
			 Map<String, Object> weixinInfo=payInfoWxService.getWeixin(parkMap.get("pid").toString());
			 
			 
			 partnerKey=weixinInfo.get("partnerKey").toString();
			 
			}
			

		}

		// 验证返回数据是否合法
		Boolean verifyResult = verifyNotifyByDirectPay(notifyParam,partnerKey);

		System.out.println(verifyResult);
		logger.info("verifyResult -" + verifyResult);
		// 当验证结果不合法返回 failed8
		if (verifyResult == null || !verifyResult) {
			return "fail";
		}
		logger.info("verifyNotify - success");

		logger.info("wanbin - DirectPay - TenpayProcessType.DirectPay.equals(notifyType) - yes");


		String outTradeNo = notifyParam.get(TenpayParameterName.outTradeNO);
		if (outTradeNo != null) {
			System.out.println(outTradeNo);

			System.out.println(orderInfoService);
			
			System.out.println("payInfoWxService"+payInfoWxService);
			
			String pid=payInfoWxService.getOrderInfoPid(outTradeNo);
			
			System.out.println("pid"+pid);

			OrderInfo orderInfo = orderInfoService.getOrderInfo(pid);

			System.out.println("OrderParkingInfo" + orderInfo);

			try {

				paymentSuccess(notifyParam);

			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}
			return "success";
		}
		return "fail";

	}

	public OrderInfoService getOrderInfoService() {
		return orderInfoService;
	}

	/**
	 * 将即时到付返回的结果设置到PayInfo中去
	 * 
	 * @param notifyParams
	 *            返回的结果集
	 * @param payInfo
	 *            PayInfo对象
	 * @return PayInfo对象
	 */
	public PayInfoWx getPayInfoByDirectPay(Map<String, String> notifyParams, PayInfoWx payInfo) {

		System.out.println(payInfo);

		System.out.println("notifyParams" + notifyParams);

		if (payInfo == null) {
			payInfo = new PayInfoWx();
			payInfo.setCreateTime(new Date());
			payInfo.setLastUpTime(new Date());
			payInfo.setStatus("1");
		}
		System.out.println(payInfo);
		System.out.println(notifyParams.get(TenpayParameterName.outTradeNO));
		System.out.println(notifyParams.get(TenpayParameterName.bankType));
		System.out.println(Integer.parseInt((notifyParams.get(TenpayParameterName.totalFee))));
		System.out.println(notifyParams.get(TenpayParameterName.transactionId));
		System.out.println(notifyParams.get(TenpayParameterName.timeEnd));
		String timeEnd = notifyParams.get(TenpayParameterName.timeEnd);


		System.out.println("timeEnd" + timeEnd);

		payInfo.setSubject("订单编号:" + notifyParams.get(TenpayParameterName.outTradeNO));
		payInfo.setPaymentType("tenpay");
		payInfo.setTimeEnd(notifyParams.get(TenpayParameterName.timeEnd));
		payInfo.setOutTradeNo(notifyParams.get(TenpayParameterName.outTradeNO));
		payInfo.setBankType((notifyParams.get(TenpayParameterName.bankType)));
		payInfo.setTotalFee(Integer.parseInt((notifyParams.get(TenpayParameterName.totalFee))));
		payInfo.setTransactionId(notifyParams.get(TenpayParameterName.transactionId));

		// payInfo.setTradeState(Integer.parseInt((notifyParams
		// .get(TenpayParameterName.tradeState))));
		try {
			payInfo.setCreateTime(new Date());
			payInfo.setLastUpTime(new Date());
			payInfo.setStatus("1");
		} catch (Throwable ex) {
			ex.printStackTrace();

		}
		return payInfo;

	}

	
	public ParkingService getParkingService() {
		return parkingService;
	}
	
	public PayInfoWxService getPayInfoWxService() {
		return payInfoWxService;
	}

	public TenpayNotify getTenpayNotify() {
		return tenpayNotify;
	}

	/**
	 * 对微信支付返回的参数集进行格式化预处理
	 * 
	 * @param notifyParams
	 *            支付结果参数集
	 * @return 支付结果参数集
	 */
	private Map<String, String> notifyParamProcess(Map notifyParams) {

		Map<String, String> reParams = new HashMap<String, String>();

		for (Iterator iter = notifyParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) notifyParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			reParams.put(name, valueStr);
		}

		return reParams;

	}

	/**
	 * 付款成功后处理业务逻辑
	 * 
	 * @param notifyParam
	 *            付款成功返回的参数集
	 */
	public String paymentSuccess(Map<String, String> notifyParam) {

		System.out.println("paymentSuccess方法");

		String outTradeNo = notifyParam.get(TenpayParameterName.outTradeNO);

		System.out.println("payparkingInfo数据" + outTradeNo);

		// 判断是否已经处理过该条订单(停车场微信）
		PayInfoWx payInfo = getPayInfoWxByOutTradeNo(outTradeNo);

		System.out.println("payInfo" + payInfo);

		logger.info("payInfo - " + payInfo);

		System.out.println("payInfoWxService" + payInfoWxService);

		// 将微信支付返回的数据集中的数据设置到payInfo中去
		payInfo = getPayInfoByDirectPay(notifyParam, payInfo);

		payInfo.setRefundStatus("unRefund");

		payInfoWxService.save(payInfo);

		logger.info("payInfo - save = success");

		System.out.println("payInfo success+" + payInfo);

		//更新停车场订单
		orderInfoService.updateOrderStatus(outTradeNo, "payed");

		OrderInfo orderInfo = orderInfoService.getOnlyOrderInfoByOrderCodes(outTradeNo);

		logger.info("paymentSuccess -- updateOrderStatus -  success");
		
		System.out.println("orderInfoStatus"+orderInfo.getOrderStatus());
		
		if (orderInfo!=null && orderInfo.getOrderStatus().equals("payed")) {
			
		//写回停车场出厂表
		System.out.println("写回停车场出厂表");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		
		java.util.Date date=new java.util.Date();  
		
		String payTime=sdf.format(date);  
		
		BigDecimal amount=orderInfo.getCarFee();
		
		 BigDecimal num = new BigDecimal("100");
		
		amount=amount.multiply(num);
		
		System.out.println("停车场费用"+amount);
		
		String params="parkCode="+orderInfo.getParkNo()+"&cardCode="+orderInfo.getCarNo()+"&inTime="+orderInfo.getInDateTime()+"&payTime="+payTime+"&amount="+amount;
		
		System.out.println("停车场params"+params);
		
		//CheckOutCarByPlate		 
		Map<String,Object> obj = parkingService.CheckOutCarByPlate(params);
		
		System.out.println("停车场结果1"+obj);
		
		if (obj.get("resultCode").toString().equals("0")){
			
			orderInfo.setOrderStatus("exchange");//已付款可以出场
			
			orderInfoService.update(orderInfo);
		}

		System.out.println("停车场订单状态"+orderInfo.getOrderStatus());
		
		System.out.println("停车场结果2"+obj);
		
		}
		return outTradeNo;

	}

	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	public void setPayInfoWxService(PayInfoWxService payInfoWxService) {
		this.payInfoWxService = payInfoWxService;
	}
	
	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}
	public void setTenpayNotify(TenpayNotify tenpayNotify) {
		this.tenpayNotify = tenpayNotify;
	}

	/**
	 * 验证消息是否是微信支付发出的合法消息
	 * 
	 * @param notifyParamsTemp
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	private boolean verifyNotifyByDirectPay(Map<String, String> notifyParam,String partnerKey) {

		// // 通过收款主账号确定支付宝的合作者ID和Key
		// Map<String, String> alipayPartnerAndKeyMap = scenicSpotService
		// .getAlipayPartnerAndKey(notifyParam
		// .get(TenpayParameterName.sellerEmail));
		//
		// // 设置合作者信息
		// tenpayNotify.partner = alipayPartnerAndKeyMap
		// .get(ScenicSpotService.ALIPAY_PARTNER_DB_NAME);
		// alipayNotify.key = alipayPartnerAndKeyMap
		// .get(ScenicSpotService.ALIPAY_KEY_DB_NAME);
		System.out.println("验证结果" + tenpayNotify);
		if (tenpayNotify.verify(notifyParam,partnerKey)) {
			return true;
		} else {
			return false;
		}

	}
	
	
	
	public PayInfoWx getPayInfoWxByOutTradeNo(String outTradeNo) {

		if (StringUtils.isBlank(outTradeNo)) {
			return null;
		}
		
		
		PayInfoWx  payInfoWx=null;
		
		String sql="select * from pay_info_wx where out_trade_no='"+outTradeNo+"'";
		
		Map<String,Object> map=dongrenJdbcTemplate.queryForMap(sql);
		
		if(map!=null && map.get("pid")!=null){
			
			payInfoWx=getPayInfoParkingWx(map.get("pid").toString());
		}

		return payInfoWx;


	}
	
	
	public PayInfoWx getPayInfoParkingWx(String pid){
		
		if (StringUtils.isBlank(pid)) {
			return null;
		}

		return hibernateTemplate.get(PayInfoWx.class, pid);
		
	}

	
	
	public Map<String,Object> verifyPhoneNum(String openId,String phoneNum)
	{
		String sql="select * from tenpay_info where open_id='"+openId+"'";
		
		return dongrenJdbcTemplate.queryForMap(sql);
	}
	

	
	public List<Map<String,Object>> getPhoneNumList(String openId,String phoneNum)
	{
		String sql="select open_id,phone_num from tenpay_info where open_id='"+openId+"'";
		
		return dongrenJdbcTemplate.queryForList(sql);
	}
	
	private Map<String, Object> getScenicInfo() {

		String sql = " select * from park.SCENIC_SPOT t";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	
	private Map<String, Object> getPaymentInfo(String pid) {

		String sql = " select order_code,cost_fee,plate_text,park_id from park.order_info where pid = '"
				+ pid + "' ";

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
	
	//不用
	public List<Map<String, Object>> getWxPayHelperOld(String pid,
			String remoteAddr, String openid, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("停车场进下单-----");

		Map<String, Object> paymentInfo = getPaymentInfo(pid);

		Map<String, Object> secnicInfo = getScenicInfo();
		
		String out_trade_no = (String) paymentInfo.get("order_code");
		
		String subInfo=" ";
		String parkingInfo=" ";
		if(paymentInfo.get("park_id")!=null){
			
			if("001".equalsIgnoreCase(paymentInfo.get("park_id").toString())){
				subInfo="PLC";
			}if("002".equalsIgnoreCase(paymentInfo.get("park_id").toString())){
				subInfo="PLLT";
			}if("003".equalsIgnoreCase(paymentInfo.get("park_id").toString())){
				subInfo="PJNM";
			}if("004".equalsIgnoreCase(paymentInfo.get("park_id").toString())){
				subInfo="PXG";
			}
		}
		
		if(paymentInfo.get("plate_text")!=null){
			
			parkingInfo=paymentInfo.get("plate_text").toString();
		}
		
		String body = "订单" +subInfo+parkingInfo;
//		String body = "订单" +subInfo+paymentInfo.get("plate_text");
//		String body = "订单"+out_trade_no;
		System.out.println("openid" + openid);
		BigDecimal totalPrices = (BigDecimal) paymentInfo.get("cost_fee");
		System.out.println(totalPrices);

		BigDecimal totalFees = new BigDecimal(100).multiply(totalPrices);

		System.out.println(totalFees);

		int totalFee = totalFees.intValue();

		System.out.println(totalFees);

		String totalPrice = String.valueOf(totalFee);

		Map<String, String> para = new HashMap<String, String>();
		para.put("appid", "wxac7afdc09e3db4d8");
		para.put("mch_id", "1442999902");
		para.put("nonce_str", genNonceStr());
		para.put("body", body);
		para.put("out_trade_no", out_trade_no);// 商户订单号要唯一
		para.put("total_fee", totalPrice);
		para.put("spbill_create_ip", remoteAddr);
		para.put("notify_url",notifyParkingUrl);// 支付成功后回调的action
		para.put("trade_type", "JSAPI");
		para.put("openid", openid);

		RequestHandler reqHandler = new RequestHandler(request, response);
		System.out.println("reqHandler-----" + reqHandler);
//		reqHandler.init(WxpayConfig.appId, WxpayConfig.appSerect,
//				WxpayConfig.partnerKey);

		String sign = reqHandler.createSign(para, false,null);

		System.out.println("sign-----" + sign);

		para.put("sign", sign);
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

//		String appid2 = WxpayConfig.appId;
		String timestamp = String.valueOf(genTimeStamp());
		String nonceStr2 = genNonceStr();
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
//		finalpackage.put("appId", appid2);
//		finalpackage.put("timeStamp", timestamp);
//		finalpackage.put("nonceStr", nonceStr2);
//		finalpackage.put("package", packages);
//		finalpackage.put("signType", "MD5");
//		finalpackage.put("key", WxpayConfig.partnerKey);

		String finalsign = reqHandler.createSign(finalpackage, false,null);

		System.out.println("finalsign" + finalsign);

//		map.put("appid", appid2);
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

		String sql = " SELECT * from park.order_info where pid='"
				+ pid + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

}
