/**
 * 
 */
package com.wy.parking.controller.tenpay;

/**
 * @author Wy
 * 
 * @category 微信支付参数名称类
 * 
 */
public class TenpayParameterName {

	/** 公众账号ID **/
	public static String appId = "appid";

	/** 通知类型 **/
	public static String bankType = "bank_type";

	/** 订单描述 **/
	public static String body = "body";

	/** 获取客户端IP **/
	public static String exterInvokeIP = "exter_invoke_ip";

	/** 货币种类 **/
	public static String feeType = "fee_type";

	/** 字符编码,取值： GBK、 UTF-8 **/
	public static String inputCharset = "input_charset";

	/** 是否关注公众账号 **/
	public static String isSubscribe = "is_subscribe";

	/** 接口调用成功状态 **/
	public static String isSuccess = "result_code";

	/** 合作身份者Key **/
	public static String key = "key";

	/** 随机字符串 **/
	public static String nonceStr = "nonce_str";

	/** 通知校验Id **/
	public static String notifyId = "notify_id";

	/** 通知类型 **/
	public static String notifyType = "trade_mode";

	/** 异步接收支付宝付款结果数据地址 **/
	public static String notifyUrl = "notify_url";

	/** 商户系统的订单号， 与请求一致 **/
	public static String outTradeNO = "out_trade_no";

	/** 商户号，也即之前步骤的partnerid, 由微信统一分配的10 位正整数 (120XXXXXXX) **/
	public static String partner = "mch_id";

	/** 支付类型 ,付款银行 **/
	public static String paymentType = "bank_type";

	/** 接口名称 **/
	public static String service = "service";

	/** 签名 **/
	public static String sign = "sign";

	/** 签名类型 **/
	public static String signType = "sign_type";

	public static String status_fail = "fail";

	// 微信返回 fail 失败，success 成功
	public static String status_success = "success";

	/** 商户号 **/
	public static String subject = "subject";

	/** 订单支付完时间 **/
	public static String timeEnd = "time_end";

	/** 总金额 **/
	public static String totalFee = "total_fee";

	/** 0—成功 其他保留支付结果信息 **/
	public static String tradeState = "trade_state";

	/** 交易类型 **/
	public static String tradeType = "trade_type";

	/** 微信支付订单号 **/
	public static String transactionId = "transaction_id";
}
