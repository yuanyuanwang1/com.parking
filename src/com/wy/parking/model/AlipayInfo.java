package com.wy.parking.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wy.superClass.SuperModel;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

public class AlipayInfo extends SuperModel {

	private String payId = null;

	private String alipayPartner = null;

	private String alipayKey = null;

	private String commonPid = null;

	private String callBackUrl = null;

	private String notifyUrl = null;

	private String merchantUrl = null;

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getAlipayPartner() {
		return alipayPartner;
	}

	public void setAlipayPartner(String alipayPartner) {
		this.alipayPartner = alipayPartner;
	}

	public String getAlipayKey() {
		return alipayKey;
	}

	public void setAlipayKey(String alipayKey) {
		this.alipayKey = alipayKey;
	}

	public String getCommonPid() {
		return commonPid;
	}

	public void setCommonPid(String commonPid) {
		this.commonPid = commonPid;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}


}
