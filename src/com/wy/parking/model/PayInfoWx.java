package com.wy.parking.model;

import java.util.Date;

import com.wy.superClass.SuperModel;

/**
 * PayInfo entity. @author MyEclipse Persistence Tools
 */

public class PayInfoWx extends SuperModel {

	private String attach = null;
	private String bankBillno = null;
	private String bankType = null;
	private String buyerAlias = null;

	protected String createUserId = null;
	protected String descriptions = null;

	private Integer discount = null;
	private Integer feeType = null;
	private Date lastUpTime = null;
	private String notifyId = null;
	private String outTradeNo = null;
	private String partner = null;
	private String payInfo = null;
	private String paymentType = null;

	protected String pcode = null;
	// Fields

	protected String pname = null;

	private Integer productFee = null;
	private String refundStatus = null;

	private String subject = null;

	private String timeEnd = null;

	private Integer totalFee = null;

	private Integer tradeMode = null;
	private Integer tradeState = null;
	private String transactionId = null;
	private Integer transportFee = null;
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getBankBillno() {
		return bankBillno;
	}
	public void setBankBillno(String bankBillno) {
		this.bankBillno = bankBillno;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBuyerAlias() {
		return buyerAlias;
	}
	public void setBuyerAlias(String buyerAlias) {
		this.buyerAlias = buyerAlias;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getFeeType() {
		return feeType;
	}
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	public Date getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getProductFee() {
		return productFee;
	}
	public void setProductFee(Integer productFee) {
		this.productFee = productFee;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}
	public Integer getTradeState() {
		return tradeState;
	}
	public void setTradeState(Integer tradeState) {
		this.tradeState = tradeState;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(Integer transportFee) {
		this.transportFee = transportFee;
	}

}
