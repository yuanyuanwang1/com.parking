package com.wy.parking.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wy.superClass.SuperModel;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

public class OrderInfo extends SuperModel {

	private String ParkNo = null;//
	private String CarNo = null;//
	private String CardType = null;//
	private Date InDateTime = null;//
	private String payType = null;
	private String orderStatus = null;
	private BigDecimal CarFee=null;
	private String orderCode=null;
	private Date payTime=null;
	
	

	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getParkNo() {
		return ParkNo;
	}
	public void setParkNo(String parkNo) {
		ParkNo = parkNo;
	}
	public String getCarNo() {
		return CarNo;
	}
	public void setCarNo(String carNo) {
		CarNo = carNo;
	}
	public String getCardType() {
		return CardType;
	}
	public void setCardType(String cardType) {
		CardType = cardType;
	}
	public Date getInDateTime() {
		return InDateTime;
	}
	public void setInDateTime(Date inDateTime) {
		InDateTime = inDateTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getCarFee() {
		return CarFee;
	}
	public void setCarFee(BigDecimal carFee) {
		CarFee = carFee;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	

}
