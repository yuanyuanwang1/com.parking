/**
 * 
 */
package com.wy.parking.alipay;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Bin
 * 
 * @category 参数配置类
 * 
 */
public final class AlipayConfig {

	// 支付宝服务接入网关URL
	public static String ALIPAY_GATEWAY = null;

	// 支付宝消息验证地址
	public static String HTTPS_VERIFY_URL = null;

	// // 支付宝服务接入网关URL
	// public static String ALIPAY_GATEWAY =
	// "http://bin-pc:8080/com.dongrensm.shgpw/test/alipay/AlipayCary.jsp";
	//
	// // 支付宝消息验证地址
	// public static String HTTPS_VERIFY_URL =
	// "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	// 接口名称
	public static String directPayService = "create_direct_pay_by_user";

	public static String refundFastPayService = "refund_fastpay_by_platform_pwd";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String inputCharset = "utf-8";

	// 支付方式 （1--商品购买）
	public static String paymentType = "1";

	// 接收返回数据的网页地址
	public static String returnUrl = null;


	// 支付宝发回支付数据接收地址
	public static String notifyUrl = null;
	
	// 支付宝发回支付数据退款接收地址
	public static String refundUrl = null;

	// 支付宝分润类型 （10 -- 卖家分给第三方）
	public static String royaltyType = "10";

	// 付款请求提交类型
	public static String requestMethod = "get";

	// 签名方式
	public static String signType = "MD5";

	static {
		Properties property = new Properties();
		try {
			property.load(AlipayConfig.class.getClassLoader()
					.getResourceAsStream("alipay.properties"));
			ALIPAY_GATEWAY = property.getProperty("alipay.alipayGateway");
			HTTPS_VERIFY_URL = property.getProperty("alipay.httpsVerifyUrl");
			returnUrl = property.getProperty("alipay.returnUrl");
			notifyUrl = property.getProperty("alipay.notifyUrl");
			refundUrl = property.getProperty("alipay.refundUrl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
