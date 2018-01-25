/**
 * 
 */
package com.wy.parking.controller.web.userCenter.admin.Payment;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.model.CommonUser;
import com.wy.parking.model.AlipayInfo;
import com.wy.parking.model.TenpayInfo;
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class ModifyTenpayPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ModifyTenpayPostAction.class);

	private String pid = null;

	private ParkingService parkingService = null;

	private UserService userService = null;

	private String pcode = null;

	private String pname = null;

	private TenpayInfo tenpayInfo = null;


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
	
	private String CommonUserPid=null;
	
	

	public String getCommonUserPid() {
		return CommonUserPid;
	}

	public void setCommonUserPid(String commonUserPid) {
		CommonUserPid = commonUserPid;
	}


	public TenpayInfo getTenpayInfo() {
		return tenpayInfo;
	}

	public void setTenpayInfo(TenpayInfo tenpayInfo) {
		this.tenpayInfo = tenpayInfo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ParkingService getParkingService() {
		return parkingService;
	}

	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public String getCommonPid() {
		return commonPid;
	}

	public void setCommonPid(String commonPid) {
		this.commonPid = commonPid;
	}

	@Override
	public String execute() {

		if (StringUtils.isBlank(CommonUserPid)) {

			return ERROR;

		}

		// 查询是否存在支付宝信息，如果存在进行更新，不存在进行插入

		map = parkingService.getTenpayListById(CommonUserPid);

		if (map == null) {

			tenpayInfo.setCreateTime(new Date());

			tenpayInfo.setAppId(appId);
			
			tenpayInfo.setAppSerect(appSerect);
			
			tenpayInfo.setCommonPid(CommonUserPid);;
			
			tenpayInfo.setMchId(mchId);
			
			tenpayInfo.setNotifyUrl(notifyUrl);
			
			tenpayInfo.setPartner(partner);
			
			tenpayInfo.setPartnerKey(partnerKey);
			
			tenpayInfo.setRedirectUrl(redirectUrl);
			
			tenpayInfo.setStatus("1");
			
		     userService.save(tenpayInfo);

		}else
			
		{
			tenpayInfo=parkingService.getTenpayInfo(pid);
			
			if(tenpayInfo!=null)
			{
				tenpayInfo.setAppId(appId);
				
				tenpayInfo.setAppSerect(appSerect);
				
				tenpayInfo.setCommonPid(CommonUserPid);;
				
				tenpayInfo.setMchId(mchId);
				
				tenpayInfo.setNotifyUrl(notifyUrl);
				
				tenpayInfo.setPartner(partner);
				
				tenpayInfo.setPartnerKey(partnerKey);
				
				tenpayInfo.setRedirectUrl(redirectUrl);
				
			    userService.update(tenpayInfo);
				
			}
		}


		return SUCCESS;

	}

}
