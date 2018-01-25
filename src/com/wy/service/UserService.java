package com.wy.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.lang.xwork.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.DigestUtils;

import com.wy.dao.PageInfo;
import com.wy.model.CommonUser;
import com.wy.model.CommonUserRoleMap;
import com.wy.superClass.SuperService;

/**
 * 用户服务类
 * 
 * @author Administrator
 * 
 */
public class UserService extends SuperService {

	private CommonService commonService = null;

	private Logger logger = Logger.getLogger(UserService.class);

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public static String getLoginTypeAdminName() {
		return LOGIN_TYPE_ADMIN_NAME;
	}

	public static String getPasswordCryptKey() {
		return PASSWORD_CRYPT_KEY;
	}

	public static String getDes() {
		return DES;
	}

	public static String passwordDigest(String password) {

		if (StringUtils.isBlank(password)) {

			return null;

		}

		return DigestUtils.md5DigestAsHex((password).getBytes());
	}

	public final static String encrypt(String password) {

		try {
			return byte2hex(encrypt(password.getBytes(),
					PASSWORD_CRYPT_KEY.getBytes()));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {

		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES);

		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(src);

	}

	public static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";
		for (int n = 0; n < b.length; n++) {

			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs = hs + "0" + stmp;

			else

				hs = hs + stmp;

		}
		return hs.toLowerCase();

	}

	public final static String LOGIN_TYPE_ADMIN_NAME = "admin";
	private final static String PASSWORD_CRYPT_KEY = "__jDlog_";
	private final static String DES = "DES";

	public String login(String username, String password, Integer ran) {

		String sql = " select * from common_user where username = '" + username
				+ "' and status = '" + super.TABLE_STATUS_VALID + "' ";

		Map<String, Object> map = dongrenJdbcTemplate.queryForMap(sql);

		if (map == null) {

			return null;

		}

		BigDecimal key1 = (BigDecimal) map.get("key1");

		BigDecimal key2 = (BigDecimal) map.get("key2");

		BigDecimal key3 = (BigDecimal) map.get("key3");

		BigDecimal key4 = (BigDecimal) map.get("key4");

		if (ran == null) {

			ran = 1;

		}

		if (key1 == null) {

			key1 = new BigDecimal(1);

		}

		if (key2 == null) {

			key2 = new BigDecimal(1);

		}

		if (key3 == null) {

			key3 = new BigDecimal(1);

		}

		if (key4 == null) {

			key4 = new BigDecimal(1);

		}

		Integer shiled = shiled(ran, key1.intValue(), key2.intValue(),
				key3.intValue(), key4.intValue());

		Long passwordInt = new Long(1);

		if (NumberUtils.isNumber(password)) {

			passwordInt = Long.parseLong(password);

		}

		if (!passwordInt.equals(shiled)) {

			return null;

		}

		return (String) map.get("pid");

	}

	public String login(String username, String password) {

		String sql = " select * from common_user where username = '" + username
				+ "' and password = '" + password + "' and status = '"
				+ super.TABLE_STATUS_VALID + "' ";
		
		Map<String, Object> map=null;
		
		try{

		 map = dongrenJdbcTemplate.queryForMap(sql);

		if (map == null) {

			return null;

		}


		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return (String) map.get("pid");

	}

	public AuthorizationInfo getAuthorizationInfo(String pid) {

		String sql = " select * from common_role where status = '"
				+ super.TABLE_STATUS_VALID
				+ "' and pid in ( select role_id from common_user_role_map where status = '"
				+ super.TABLE_STATUS_VALID + "' and user_id = '" + pid + "' ) ";

		List<Map<String, Object>> list = dongrenJdbcTemplate.queryForList(sql);

		if (list == null || list.isEmpty()) {

			return null;

		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		for (Map<String, Object> map : list) {

			simpleAuthorizationInfo.addRole((String) map.get("role_name"));

		}

		String sqlPermission = " select * from common_permission where status = '"
				+ super.TABLE_STATUS_VALID
				+ "' and pid in ( select permission_id from common_role_permission_map where status = '"
				+ super.TABLE_STATUS_VALID
				+ "' and role_id in ( select role_id from common_user_role_map where status = '"
				+ super.TABLE_STATUS_VALID
				+ "' and user_id = '"
				+ pid
				+ "' ) ) ";

		List<Map<String, Object>> listPermission = dongrenJdbcTemplate
				.queryForList(sqlPermission);

		if (listPermission == null || listPermission.isEmpty()) {

			return simpleAuthorizationInfo;

		}

		for (Map<String, Object> map : listPermission) {

			simpleAuthorizationInfo.addStringPermission((String) map
					.get("permission_name"));

			Permission permission = new WildcardPermission(
					(String) map.get("permission_name"));

			simpleAuthorizationInfo.addObjectPermission(permission);

		}

		return simpleAuthorizationInfo;

	}

	/**
	 * @param pid
	 *            删除对象的PID
	 * @return 更新行数
	 */
	public void delete(String pid) {

		CommonUser commonUser = get(pid);

		super.delete(commonUser);

	}

	/**
	 * @param pid
	 *            获取对象的PID
	 * @return 要获取的对象
	 */
	public CommonUser get(String pid) {

		CommonUser user = hibernateTemplate.get(CommonUser.class, pid);

		return user;

	}

	public CommonUserRoleMap getCommonUserRoleMap(String pid) {

		CommonUserRoleMap user = hibernateTemplate.get(CommonUserRoleMap.class,
				pid);

		return user;

	}

	public String addOrEdit(CommonUser obj, String[] roleIds) {

		if (obj.getPid() != null) {

			CommonUserRoleMap userRoleMap = new CommonUserRoleMap();

			userRoleMap.setUserId(obj.getPid());
			hibernateTemplate.deleteAll(hibernateTemplate
					.findByExample(userRoleMap));

		}

		hibernateTemplate.saveOrUpdate(obj);

		if (roleIds != null) {
			for (String roleId : roleIds) {
				CommonUserRoleMap userRoleMap = new CommonUserRoleMap();
				userRoleMap.setUserId(obj.getPid());
				userRoleMap.setRoleId(roleId);

				hibernateTemplate.saveOrUpdate(userRoleMap);

			}
		}

		return obj.getPid();
	}

	/**
	 * @param pid
	 *            当前对象的PID
	 * @param roleName
	 *            验证的属性
	 * @return 是否存在
	 */
	public Boolean validationPassword(String pid, String oldPassword) {

		oldPassword = passwordDigest(oldPassword);

		CommonUser user = get(pid);

		if (StringUtils.equals(oldPassword, user.getPassword())) {

			return true;

		}

		return false;

	}

	/**
	 * @param pid
	 *            修改密码的对象ID
	 * @param password
	 *            新密码
	 * @return 更新行数
	 */
	public void editPassword(String pid, String password) {

		password = passwordDigest(password);

		CommonUser commonUser = get(pid);

		commonUser.setPassword(password);

		super.update(commonUser);

	}

	private boolean verifySigned(String signed, String password) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		String date = dateFormat.format(new Date());

		String signedLocal = passwordDigest(password + date);

		if (StringUtils.equals(signed, signedLocal)) {

			return true;

		}

		return false;

	}

	public Map<String, Object> getMapOne(String pid) {

		String sql = " select common_user.*, ( select role_name from common_role where status = '"
				+ super.TABLE_STATUS_VALID
				+ "' and common_role.pid in ( select role_id from common_user_role_map where user_id = '"
				+ pid
				+ "' ) ) as role_name from common_user where pid = '"
				+ pid + "' and status = '" + super.TABLE_STATUS_VALID + "' ";

		return dongrenJdbcTemplate.queryForMap(sql);

	}

	public Boolean isUsernameUnique(String username) {

		Integer unique = unique("common_user", "username", username);

		if (unique < 1) {

			return true;

		}

		return false;

	}

	public Boolean isUsernameUnique(String username, String pid) {

		Integer unique = unique("common_user", "username", username, pid);

		if (unique < 1) {

			return true;

		}

		return false;

	}

	public String save(CommonUser commonUser, String[] roleIds) {

		super.save(commonUser);

		if (roleIds != null) {
			for (String roleId : roleIds) {
				CommonUserRoleMap userRoleMap = new CommonUserRoleMap();
				userRoleMap.setUserId(commonUser.getPid());
				userRoleMap.setRoleId(roleId);

				super.save(userRoleMap);

			}
		}

		return commonUser.getPid();
	}

	// rnd -2^31 ~ +2^31
	// key1 0~65535
	// key1 0~65535
	// key1 0~65535
	// key1 0~65535

	// 返回值 -2^31 ~ +2^31
	private int shiled(int rnd, int key1, int key2, int key3, int key4) {

		int x1 = rnd & 0xffff;

		int x2 = (rnd >> 16) & 0xffff;
		//
		int y1 = x1 ^ key2;
		int y11 = x2 ^ key1;

		y1 = y1 + y11;
		y1 = y1 % 65536;

		if (y1 > 32767) {
			y1 -= 32767;
		}

		y1 = y1 ^ 16;
		y1 = y1 % 65536;

		if (y1 > 32767) {
			y1 -= 32767;
		}

		y1 %= key4;
		y1 = y1 % 65536;

		int y;
		y = y1 ^ key3;
		if (y > 50000) {
			y = y - 50000 - 1;
		}
		y11 = x1 + key1;
		y11 = y11 % 65536;

		if (y11 > 32767) {
			y11 -= 32767;
		}

		int y22 = y11 % key3;
		y22 = y22 % 65536;

		y11 = key4 ^ x2;
		int y2;
		y2 = y22 ^ y11;

		if (y2 > 50000) {
			y2 = y2 - 50000 - 1;
		}
		y ^= y2;

		y2 = y2 % 65536;

		int ReTurnData;
		ReTurnData = y2;

		ReTurnData = ReTurnData << 16;
		ReTurnData = ReTurnData ^ y;
		return ReTurnData;
	}

	/**
	 * 用户列表根据人员创建时间排序lv
	 * 
	 * @param tableName
	 * @param pageNum
	 * @param searchValue
	 */
	public PageInfo getUserPage(String tableName, int pageNum,
			String searchValue) {

		String sql = " select * from " + tableName + " t where t.status = '"
				+ TABLE_STATUS_VALID + "' ";

		if (StringUtils.isNotBlank(searchValue)) {

			sql = sql + " and (t.username like '%" + searchValue
					+ "%' or t.pname like '%" + searchValue + "%') ";

		}

		sql += " order by t.username desc ";

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public boolean verifyUsernameUniqueAll(String username) {

		boolean verify = verifyUniqueAll(username, "username",
				"Common_User", null);

		return verify;

	}

	public boolean verifyUniqueAll(String value, String propertyName,
			String className, String pid) {
		
		String sql = " select * from " + className + " t where t.status = '"
				+ TABLE_STATUS_VALID + "' and  username='"+value+"'";

		
		int count = dongrenJdbcTemplate.queryForInt(sql, new MapSqlParameterSource());

		if (count < 1) {

			return true;

		}

		return false;

	}

}
