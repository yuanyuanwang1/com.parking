/**
 * 
 */
package com.alipay;

/**
 * @author Bin
 * 
 * @category 支付宝参数名称类
 * 
 */
public class AlipayParameterName {

	/** 接口名称 **/
	public static String service = "service";

	/** 合作身份者ID **/
	public static String partner = "partner";

	/** 合作身份者Key **/
	public static String key = "key";

	/** 参数编码类型 **/
	public static String inputCharset = "_input_charset";

	/** 支付类型 **/
	public static String paymentType = "payment_type";

	/** 异步接收支付宝付款结果数据地址 **/
	public static String notifyUrl = "notify_url";

	/** 支付宝付款成功后跳转地址 **/
	public static String returnUrl = "return_url";

	/** 付款主账号 **/
	public static String sellerEmail = "seller_email";

	/** 订单编号 **/
	public static String outTradeNO = "out_trade_no";

	/** 订单标题 **/
	public static String subject = "subject";

	/** 订单描述 **/
	public static String body = "body";

	/** 订单交易价格 **/
	public static String totalFee = "total_fee";

	/** 订单分润类型 **/
	public static String royaltyType = "royalty_type";

	/** 订单分润数据（格式：收款人账号^金额^备注 备注不能为空！） **/
	public static String royaltyParameters = "royalty_parameters";

	/** 防钓鱼时间戳 **/
	public static String antiPhishingKey = "anti_phishing_key";

	/** 获取客户端IP **/
	public static String exterInvokeIP = "exter_invoke_ip";

	/** 签名 **/
	public static String sign = "sign";

	/** 签名类型 **/
	public static String signType = "sign_type";

	/** 接口调用成功状态 **/
	public static String isSuccess = "is_success";

	/** 通知校验Id **/
	public static String notifyId = "notify_id";

	/** 通知类型 **/
	public static String notifyType = "notify_type";

	/** 订单支付处理状态 **/
	public static String tradeStatus = "trade_status";

	/** 支付宝支付单号 **/
	public static String tradeNo = "trade_no";

	/** 订单创建时间 **/
	public static String gmtCreate = "gmt_create";

	/** 订单支付时间 **/
	public static String gmtPayment = "gmt_payment";

	/** 订单关闭时间 **/
	public static String gmtClose = "gmt_close";

	/** 退款状态 **/
	public static String refundStatus = "refund_status";

	/** 退款时间 **/
	public static String gmtRefund = "gmt_refund";

	/** 买家支付宝账号 **/
	public static String buyerEmail = "buyer_email";

	/** 卖家支付宝账号 **/
	public static String sellerId = "seller_id";

	/** 买家支付宝账号 **/
	public static String buyerId = "buyer_id";

	/** 信用支付购票员代理人 **/
	public static String agentUserId = "agent_user_id";

	/** 请求退款时间（格式：yyyy-MM-dd HH:mm:ss） **/
	public static String refundDate = "refund_date";

	/** 退款批次号（必须与退款时间一致，后加3位流水号） **/
	public static String batchNo = "batch_no";

	/** 退款总笔数 **/
	public static String batchNum = "batch_num";

	/** 单笔数据集（格式：16位支付宝处理单号^退款金额^说明） **/
	public static String detailData = "detail_data";

	/** 退款成功总数 **/
	public static String successNum = "success_num";

	/** 退款结果明细 **/
	public static String resultDetails = "result_details";
}
