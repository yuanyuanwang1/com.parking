/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.moneyPayment;

import org.apache.log4j.Logger;

import com.wy.parking.service.MoneyPaymentService;
import com.wy.superClass.SuperAction;

/**
 * @author jian198001
 * 
 */
public class PaymentSubmitGetAction extends SuperAction {

	private Logger logger = Logger.getLogger(PaymentSubmitGetAction.class);

	// 依赖服务
	private MoneyPaymentService moneyPaymentService = null; // 订单支付服务类

	// 输入参数
	private String orderId = null; // 支付订单的订单号

	// 输出参数
	private String requestHtml = null; // 输出支付请求表单

	@Override
	public String execute() {

		requestHtml = moneyPaymentService.buildReuqest(orderId);

		return SUCCESS;

	}

	public MoneyPaymentService getMoneyPaymentService() {
		return moneyPaymentService;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getRequestHtml() {
		return requestHtml;
	}

	public void setMoneyPaymentService(MoneyPaymentService moneyPaymentService) {
		this.moneyPaymentService = moneyPaymentService;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setRequestHtml(String requestHtml) {
		this.requestHtml = requestHtml;
	}

}
