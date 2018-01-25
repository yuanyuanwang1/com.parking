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
import com.wy.parking.service.ParkingService;
import com.wy.service.UserService;
import com.wy.superClass.SuperAction;

/**
 * @author wy
 * 
 */
public class ModifyPostAction extends SuperAction {

	private Logger logger = Logger.getLogger(ModifyPostAction.class);

	private String pid = null;

	private ParkingService parkingService = null;

	private UserService userService = null;

	private String pcode = null;

	private String pname = null;

	private String payId = null;

	private String alipayPartner = null;

	private String alipayKey = null;

	private String callBackUrl = null;

	protected Map<String, Object> maps = null;

	private CommonUser commonUser = null;

	private String notifyUrl = null;

	private String merchantUrl = null;

	private AlipayInfo alipayInfo = null;
	
	private String CommonUserPid=null;
	
	

	public String getCommonUserPid() {
		return CommonUserPid;
	}

	public void setCommonUserPid(String commonUserPid) {
		CommonUserPid = commonUserPid;
	}

	public AlipayInfo getAlipayInfo() {
		return alipayInfo;
	}

	public void setAlipayInfo(AlipayInfo alipayInfo) {
		this.alipayInfo = alipayInfo;
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

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public Map<String, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}

	public CommonUser getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(CommonUser commonUser) {
		this.commonUser = commonUser;
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

	@Override
	public String execute() {

		if (StringUtils.isBlank(CommonUserPid)) {

			return ERROR;

		}

		// 查询是否存在支付宝信息，如果存在进行更新，不存在进行插入

		map = parkingService.getAlipayListById(CommonUserPid);

		if (map == null) {

			alipayInfo.setCreateTime(new Date());

			alipayInfo.setAlipayKey(alipayKey);
			
			alipayInfo.setAlipayPartner(alipayPartner);
			
			alipayInfo.setCallBackUrl(callBackUrl);
			
			alipayInfo.setCommonPid(CommonUserPid);
			
			alipayInfo.setMerchantUrl(merchantUrl);
			
			alipayInfo.setNotifyUrl(notifyUrl);
			
			alipayInfo.setPayId(payId);
			
			alipayInfo.setStatus("1");

		     userService.save(alipayInfo);

		}else
			
		{
			alipayInfo=parkingService.getAlipayInfo(pid);
			
			if(alipayInfo!=null)
			{
				alipayInfo.setAlipayKey(alipayKey);
				
				alipayInfo.setAlipayPartner(alipayPartner);
				
				alipayInfo.setCallBackUrl(callBackUrl);
				
				alipayInfo.setCommonPid(CommonUserPid);
				
				alipayInfo.setMerchantUrl(merchantUrl);
				
				alipayInfo.setNotifyUrl(notifyUrl);
				
				alipayInfo.setPayId(payId);
				
			    userService.update(alipayInfo);
				
			}
		}


		return SUCCESS;

	}

}
