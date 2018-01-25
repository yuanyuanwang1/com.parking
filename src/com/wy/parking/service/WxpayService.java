/**
 * 
 */
package com.wy.parking.service;

import java.util.Date;
import java.util.Map;

import com.wy.parking.model.PayInfoWx;
import com.wy.superClass.SuperService;

public class WxpayService extends SuperService {

	/**
	 * 将即时到付返回的结果设置到PayInfo中去
	 * 
	 * @param notifyParams
	 *            支付宝返回的结果集
	 * @param payInfoWx
	 *            PayInfo对象
	 * @return PayInfo对象
	 */
	public PayInfoWx getPayInfoByDirectPay(Map map, PayInfoWx payInfoWx) {

		if (payInfoWx == null) {
			payInfoWx = new PayInfoWx();
			payInfoWx.setCreateTime(new Date());
			payInfoWx.setLastUpTime(new Date());
			payInfoWx.setStatus(SuperService.TABLE_STATUS_VALID);
		}

		// 商户订单号
		String out_trade_no = (String) map.get("out_trade_no");

		payInfoWx.setOutTradeNo(out_trade_no);

		Integer tradeState = (Integer) map.get("trade_state");
		String payInfo = (String) map.get("pay_info");
		String partner = (String) map.get("partner");
		String bankType = (String) map.get("bank_type");
		String bankBillno = (String) map.get("bank_billno");

		Integer totalFee = (Integer) map.get("total_fee");
		Integer feeType = (Integer) map.get("fee_type");
		String notifyId = (String) map.get("notify_id");
		String transactionId = (String) map.get("transaction_id");
		String attach = (String) map.get("attach");
		String timeEnd = (String) map.get("time_end");
		Integer transportFee = (Integer) map.get("transport_fee");

		Integer productFee = (Integer) map.get("product_fee");
		Integer discount = (Integer) map.get("discount");
		String buyerAlias = (String) map.get("buyer_alias");

		payInfoWx.setTradeState(tradeState);

		payInfoWx.setPayInfo(payInfo);

		payInfoWx.setPartner(partner);

		payInfoWx.setBankType(bankType);

		payInfoWx.setBankBillno(bankBillno);

		payInfoWx.setTotalFee(totalFee);

		payInfoWx.setFeeType(feeType);

		payInfoWx.setNotifyId(notifyId);

		payInfoWx.setTransactionId(transactionId);

		payInfoWx.setAttach(attach);

		payInfoWx.setTimeEnd(timeEnd);

		payInfoWx.setTransportFee(transportFee);

		payInfoWx.setProductFee(productFee);

		payInfoWx.setDiscount(discount);

		payInfoWx.setBuyerAlias(buyerAlias);

		return payInfoWx;

	}

}
