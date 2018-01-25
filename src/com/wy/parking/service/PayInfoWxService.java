/**
 * 
 */
package com.wy.parking.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wy.parking.model.OrderInfo;
import com.wy.parking.model.PayInfoAlipay;
import com.wy.parking.model.PayInfoWx;
import com.wy.superClass.SuperService;


/**
 * @author jian198001
 * 
 */
public class PayInfoWxService extends SuperService {

	private Logger logger = Logger.getLogger(PayInfoWxService.class);

	public PayInfoWx getPayInfoWxByOutTradeNo(String outTradeNo) {

		if (StringUtils.isBlank(outTradeNo)) {
			return null;
		}
		
		
		PayInfoWx  payInfoWx=null;
		
		String sql="select * from pay_info_wx where out_trade_no='"+outTradeNo+"'";
		
		Map<String,Object> map=dongrenJdbcTemplate.queryForMap(sql);
		
		if(map!=null && map.get("pid")!=null){
			
			payInfoWx=getPayInfoWx(map.get("pid").toString());
		}

		return payInfoWx;

	}
	

	public String getOrderInfoPid(String outTradeNo) {
		
		String pid=null;

		if (StringUtils.isBlank(outTradeNo)) {
			return null;
		}
		
		String sql="select * from order_info where order_code='"+outTradeNo+"'";
		
		Map<String,Object> map=dongrenJdbcTemplate.queryForMap(sql);
		
		if(map!=null && map.get("pid")!=null){
			
			pid=map.get("pid").toString();
		}

		System.out.println("pid"+pid);
		
		return pid;

	}
	
	public PayInfoAlipay getPayInfoByOutTradeNo(String outTradeNo) {

		if (StringUtils.isBlank(outTradeNo)) {
			return null;
		}
		
		
		PayInfoAlipay  payInfoAlipay=null;
		
		String sql="select * from pay_info_zfb where out_trade_no='"+outTradeNo+"'";
		
		Map<String,Object> map=dongrenJdbcTemplate.queryForMap(sql);
		
		if(map!=null && map.get("pid")!=null){
			
			payInfoAlipay=getPayInfoAlipay(map.get("pid").toString());
		}

		return payInfoAlipay;

	}
	
	
	public PayInfoAlipay getPayInfoAlipay(String pid){
		
		if (StringUtils.isBlank(pid)) {
			return null;
		}

		return hibernateTemplate.get(PayInfoAlipay.class, pid);
		
	}
	
	
	public PayInfoWx getPayInfoWx(String pid){
		
		if (StringUtils.isBlank(pid)) {
			return null;
		}

		return hibernateTemplate.get(PayInfoWx.class, pid);
		
	}


	public Map<String, Object> getParkNo(String parkNo) {

		String sql = " SELECT * from common_user where pcode='" + parkNo + "' and status='1'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	
	public Map<String, Object> getWeixin(String pid) {

		String sql = " select * from tenpay_info t where t.common_pid='"+pid+"'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

}
