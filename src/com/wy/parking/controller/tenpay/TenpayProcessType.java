/**
 * 
 */
package com.wy.parking.controller.tenpay;

/**
 * @author jian198001
 * 
 */
public class TenpayProcessType {

	/** 即时支付 **/
	public static String DirectPay = "1";
	/** 即时批量有密退款 **/
	public static String RefundFastPay = "batch_refund_notify";

	/** 即时支付success **/
	public final static String TradeFinished = "0";

}
