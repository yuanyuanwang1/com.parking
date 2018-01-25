/**
 * 
 */
package com.wy.parking.alipay;

import java.util.Map;

/**
 * @author Bin
 * 
 */
public class AlipayError {

	public static String system_error = "SYSTEM_ERROR";

	// 退款错误信息
	public static String batch_refund_status_error = "BATCH_REFUND_STATUS_ERROR";
	public static String batch_refund_data_error = "BATCH_REFUND_DATA_ERROR";
	public static String refund_trade_failed = "REFUND_TRADE_FAILED";
	public static String refund_fail = "REFUND_FAIL";

	public static Map<String, String> errorInfo = null;
	static {

		errorInfo.put(system_error, "支付宝系统错误");

		// 退款错误信息
		errorInfo.put(batch_refund_status_error, "退款记录状态错误");
		errorInfo.put(batch_refund_data_error, "批量退款后数据检查错误");
		errorInfo.put(refund_trade_failed, "不存在退交易，但是退收费和退分润失败");
		errorInfo.put(refund_fail, "退款失败");
	}

}
