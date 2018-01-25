package com.wy.parking.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.wy.parking.model.OrderInfo;
import com.wy.service.CommonService;
import com.wy.superClass.SuperService;

/**
 * 
 * @author lv App信息服务类
 */
public class OrderInfoService extends SuperService {

	public String saveParkingOrder(OrderInfo object) {
		String orderId = null;
		if (object != null) {
			object.setPid(UUID.randomUUID().toString());
			object.setOrderCode(CommonService.createSerials(object.getParkNo()));
			object.setOrderStatus("unpay");
			object.setStatus("1");
			object.setCreateTime(new Date());

			save(object);

			orderId = object.getPid();
		}
		return orderId;
	}

	public Map<String, Object> getOrderInfoId(String orderCode) {

		String sql = " select * from order_info where order_code = '"
				+ orderCode + "' ";

		System.out.println(sql);

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public Map<String, Object> getMapByOrderCode(String orderCode) {

		String sql = " SELECT T.* FROM ORDER_INFO T WHERE T.STATUS = '"
				+ super.TABLE_STATUS_VALID + "' AND T.ORDER_CODE = '"
				+ orderCode + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public OrderInfo getOrderInfo(String pid) {

		if (StringUtils.isBlank(pid)) {
			return null;
		}

		return hibernateTemplate.get(OrderInfo.class, pid);

	}

	public OrderInfo getOnlyOrderInfoByOrderCode(String orderCode) {

		if (StringUtils.isBlank(orderCode)) {
			return null;
		}

		Map<String, Object> map = getOrderInfoId(orderCode);

		System.out.println("map_order" + map);

		if (map == null && map.get("pid") == null) {

			return null;

		}

		System.out.println("pid" + map.get("pid"));

		return hibernateTemplate
				.get(OrderInfo.class, map.get("pid").toString());
	}

	public Boolean updateOrderStatus(String orderCode, String orderStatus) {

		if (StringUtils.isBlank(orderCode) || StringUtils.isBlank(orderStatus)) {
			return false;
		}

		OrderInfo orderInfo = getOnlyOrderInfoByOrderCode(orderCode);
		if (orderInfo == null) {
			return false;
		}

		orderInfo.setOrderStatus(orderStatus);

		update(orderInfo);

		return true;

	}

	public OrderInfo getOnlyOrderInfoByOrderCodes(String orderCode) {

		if (StringUtils.isBlank(orderCode)) {
			return null;
		}

		Map<String, Object> map = getOrderInfoId(orderCode);

		return getOrderParkingInfo(map.get("pid").toString());

	}

	public Map<String, Object> getOrderInfoIds(String orderCode) {

		String sql = " select pid,order_code,cost_fee from park.order_info where order_code = '"
				+ orderCode + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public OrderInfo getOrderParkingInfo(String pid) {

		if (StringUtils.isBlank(pid)) {
			return null;
		}

		return hibernateTemplate.get(OrderInfo.class, pid);

	}

}
