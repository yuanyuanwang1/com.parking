package com.wy.parking.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.wy.superClass.SuperModel;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

public class CarInfo extends SuperModel {

	private String CardNo = null;

	private String CardType = null;

	private String CardIndate = null;

	private String CardAmount = null;

	private String CarType = null;

	private String MasterName = null;

	private String MasterTel = null;

	private String MasterAddr = null;

	private String ParkPosition = null;

	private String PayAmount = null;

	private String Remark = null;

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getCardIndate() {
		return CardIndate;
	}

	public void setCardIndate(String cardIndate) {
		CardIndate = cardIndate;
	}

	public String getCardAmount() {
		return CardAmount;
	}

	public void setCardAmount(String cardAmount) {
		CardAmount = cardAmount;
	}

	public String getCarType() {
		return CarType;
	}

	public void setCarType(String carType) {
		CarType = carType;
	}

	public String getMasterName() {
		return MasterName;
	}

	public void setMasterName(String masterName) {
		MasterName = masterName;
	}

	public String getMasterTel() {
		return MasterTel;
	}

	public void setMasterTel(String masterTel) {
		MasterTel = masterTel;
	}

	public String getMasterAddr() {
		return MasterAddr;
	}

	public void setMasterAddr(String masterAddr) {
		MasterAddr = masterAddr;
	}

	public String getParkPosition() {
		return ParkPosition;
	}

	public void setParkPosition(String parkPosition) {
		ParkPosition = parkPosition;
	}

	public String getPayAmount() {
		return PayAmount;
	}

	public void setPayAmount(String payAmount) {
		PayAmount = payAmount;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
