/**
 * 
 */
package com.wy.parking.controller.m.frontPage.passport.moneyPayment;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.wy.parking.service.MoneyPaymentService;
import com.wy.superClass.SuperAction;


/**
 * @author wy
 * 
 */
public class WapAlipayNotifyDataProcessGetAction extends SuperAction {

	private Logger logger = Logger
			.getLogger(WapAlipayNotifyDataProcessGetAction.class);

	private MoneyPaymentService moneyPaymentService = null;

	private String resultInfo = null;

	public MoneyPaymentService getMoneyPaymentService() {
		return moneyPaymentService;
	}

	public void setMoneyPaymentService(MoneyPaymentService moneyPaymentService) {
		this.moneyPaymentService = moneyPaymentService;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	@Override
	public String execute() {

		logger.info("停车场WAP支付宝成功调用到服务！");

		Map notifyParams = request.getParameterMap();

		System.out.println("停车场WAP支付宝成功调用到服务！");
		
		resultInfo = moneyPaymentService.wapAlipayNotifyProcess(notifyParams);

		logger.info("resultInfo：" + resultInfo);

		return SUCCESS;

	}

	
}
