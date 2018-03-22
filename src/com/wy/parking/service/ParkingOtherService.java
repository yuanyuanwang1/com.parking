package com.wy.parking.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import wxpay.GetWxOrderno;
import wxpay.RequestHandler;
import wxpay.WxpayConfig;

import com.wy.dao.PageInfo;
import com.wy.model.CommonUser;
import com.wy.parking.model.AlipayInfo;
import com.wy.parking.model.OrderInfo;
import com.wy.parking.model.ParkingUser;
import com.wy.parking.model.TenpayInfo;
import com.wy.superClass.SuperModel;
import com.wy.superClass.SuperService;
import com.wy.util.HttpRequestUtil;
import com.wy.util.HttpUtil;

/**
 * 
 * @author lv App信息服务类
 */
public class ParkingOtherService extends SuperService {

	public List<Map<String, Object>> getSystemAccount(String username, String password) {

		String sql = "select * from parking_user t where t.pcode='"
				+ username + "' and password='" + password + "'";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		List<Map<String, Object>> list = null;

		list = dongrenJdbcTemplate.queryForList(sql, paramSource);

		return list;
	}

	public Map<String, Object> getParkByCode(String pcode,String pid) {

		String sql = " SELECT * from parking_user where pcode='" + pcode + "' and status='1'";
		
		if(StringUtils.isNotBlank(pid))
		{
			sql+=" and pid!='"+pid+"'";
		}

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public List<Map<String, Object>> getUserList(String searchValue) {
		// TODO Auto-generated method stub
		
		String sql = " SELECT * from parking_user where status='1' and role_id!='admin'";
		
		if(StringUtils.isNotBlank(searchValue))
		{
			sql+=" and pname like '%"+searchValue+"%'";
		}

		return dongrenJdbcTemplate.queryForList(sql);
	}

	public ParkingUser get(String pid) {
		// TODO Auto-generated method stub
		ParkingUser user = hibernateTemplate.get(ParkingUser.class, pid);

		return user;
	}
	
	public Map<String, Object> getMapOne(String pid) {

		String sql = " select t.* from parking_user t where t.pid = '"
				+ pid + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	
	public Map<String, Object> getMapOnePname(String pname) {

		String sql = " select t.* from parking_user t where t.pname = '"
				+ pname + "' and status='1'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	
	public Map<String, Object> getParkNo(String parkNo) {

		String sql = " SELECT * from common_user where pcode='" + parkNo + "' and status='1'";

		return dongrenJdbcTemplate.queryForMap(sql);

	}
	

	public Map<String, Object> deleteByCode(String parkcode) {

		String sql = " { call PRO_WHILTE_DEL(?, ?, ?) } ";

		Connection connection = null;

		CallableStatement callableStatement = null;

		try {

			connection = dataSource.getConnection();

			callableStatement = connection.prepareCall(sql);

			callableStatement.setString(1, parkcode);

			callableStatement.registerOutParameter(2, Types.INTEGER);

			callableStatement.registerOutParameter(3, Types.VARCHAR);

			callableStatement.execute();

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("appCode", callableStatement.getInt(2));

			map.put("errorText", callableStatement.getString(3));

			return map;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				callableStatement.close();

				connection.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;

	}
	
	public List<Map<String, Object>> whiteList(String searchValue,String startDate,String endDate,String parkCode) {

		String sql = " select t.*,CONVERT(varchar(100), CardIndate, 23) as CardIndate from white_list t where t.parkCode = '"
				+ parkCode + "' ";

		if(StringUtils.isNotBlank(searchValue))
		{
			sql+=" and (CarNo like '%"+searchValue+"%' or Remark like '%"+searchValue+"%' )";
		}
		
		sql+=" and CardIndate >='"+startDate+"' and CardIndate<='"+endDate+"' and status='1'";
		
		return dongrenJdbcTemplate.queryForList(sql);
	}
	
}
