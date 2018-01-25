package com.wy.parking.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wy.superClass.SuperModel;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

public class TenpayInfo extends SuperModel {

	private String appId = null;

	private String partnerKey = null;

	private String signType = null;

	private String bankType = null;

	private String partner = null;

	private String feeType = null;

	private String notifyUrl = null;
	
	private String inputCharset = null;

	private String appSerect = null;

	private String getCodeRequest = null;

	private String mchId = null;

	private String redirectUrl = null;

	private String scope = null;
	
	private String commonPid=null;
	
	

	public String getCommonPid() {
		return commonPid;
	}

	public void setCommonPid(String commonPid) {
		this.commonPid = commonPid;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getAppSerect() {
		return appSerect;
	}

	public void setAppSerect(String appSerect) {
		this.appSerect = appSerect;
	}

	public String getGetCodeRequest() {
		return getCodeRequest;
	}

	public void setGetCodeRequest(String getCodeRequest) {
		this.getCodeRequest = getCodeRequest;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}


}
