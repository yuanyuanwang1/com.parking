package com.wy.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.wy.dao.DongrenJdbcTemplate;
import com.wy.model.SysParam;
import com.wy.superClass.SuperService;
import com.wy.util.DateUtil;

/**
 * 公共服务类，提供通用功能
 * 
 * @author Administrator
 * 
 */
public class CommonService extends SuperService{

	private Logger logger = Logger.getLogger(CommonService.class);

	public final static String VERIFY_CODE_SESSION_NAME = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

	public final static String UP_ROUTE_PHOTO_PATH = "fileLoad/upload";// 商品图片上传路径
	public final static String UP_FILE_PATH = "fileLoad\\upload\\";// 上传路径
	public final static String UP_CHART_PHOTO_PATH = "/fileLoad/upload/chart";

	public final static String TABLE_STATUS_VALID = "1";
	public final static String TABLE_STATUS_INVALID = "0";
	private static Set<String> unsafetyKeywordsSet = new HashSet<String>();
	static {
		unsafetyKeywordsSet.add("COMMON_USER");
	}

	public void checkUnsafetyKeywords(String tableName, String colName) {
		if ("COMMON_USER".equalsIgnoreCase(tableName)
				&& "PASSWORD".equalsIgnoreCase(colName)) {
			throw new RuntimeException("非法查询关键字：" + tableName + "：" + colName);
		}
	}

	public static String copyFile(String upPath, String fileName, File srcFile) {

		if (StringUtils.isBlank(fileName)) {

			return null;

		}

		String uri = null;

		uri = createFileName(fileName);

		uri = upPath + "/" + uri;

		copyFile(uri, srcFile);

		return uri;

	}

	public static String copyFile(String uri, File srcFile) {

		File destFile = new File(uri);

		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return uri;

	}

	public static String upFile(String upPath, String fileName, File file) {

		if (fileName == null || "".equals(fileName.trim())) {
			return null;
		}

		if (file == null) {
			return null;
		}

		String absoultePath = upPath;
		String savePath = CommonService.UP_ROUTE_PHOTO_PATH;

		File savefile = new File(new File(absoultePath), fileName);

		if (!savefile.getParentFile().exists()) {
			savefile.getParentFile().mkdirs();
			savePath = savePath + "/" + fileName;
		}

		try {
			FileUtils.copyFile(file, savefile);
			savePath = savePath + "/" + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return savePath;

	}

	public static String upFile(String upPath, String fileName, File file,
			StringBuffer filePath) {

		if (fileName == null || "".equals(fileName.trim())) {
			return null;
		}

		if (file == null) {
			return null;
		}

		String absoultePath = upPath;
		String savePath = CommonService.UP_ROUTE_PHOTO_PATH;

		File savefile = new File(new File(absoultePath), fileName);
		if (!savefile.getParentFile().exists()) {
			savefile.getParentFile().mkdirs();
			savePath = savePath + "/" + fileName;
		}
		try {
			FileUtils.copyFile(file, savefile);

			savePath = savePath + "/" + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return savePath;

	}

	/**
	 * Sql语句安全验证
	 * 
	 * @param sqlStr
	 *            Sql语句
	 * @return 若包含危险关键字返回空字符串，否则返回Sql语句
	 */
	public static String sqlStrFilter(String sqlStr) {

		if (StringUtils.isBlank(sqlStr)) {
			return "";
		}

		sqlStr = StringUtils.strip(sqlStr);

		String[] filterStrArr = new String[] { "select", "insert", "update",
				"alter", "truncate", "\"", "drop", ";" };

		for (String filterStr : filterStrArr) {

			if (sqlStr.contains(filterStr)) {
				return "";
			}

		}

		return sqlStr.trim();

	}

	public static Boolean verifyIdCode(String idCode) {

		if (StringUtils.isBlank(idCode)) {
			return null;
		}

		if (!idCode.matches("([0-9]{17}([0-9]|X))|([0-9]{15})")) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}

	}

	public static Boolean verifyPhoneNum(String phoneNum) {

		if (StringUtils.isBlank(phoneNum)) {
			return null;
		}

		if (!phoneNum
				.matches("^((13[0-9])|(15[^4,\\D])|(18[01236789]))\\d{8}$")) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}

	}

	public static String createFileName(String fileName) {

		if (StringUtils.isBlank(fileName)) {

			return null;

		}

		java.util.UUID uuid = java.util.UUID.randomUUID();

		String ext = org.apache.commons.io.FilenameUtils.getExtension(fileName);

		return uuid.toString() + "." + ext;

	}
	
	public boolean verifyUnique(String value, String propertyName,
			String className, String pid) {
		
		String sql="select * from '"+className+"' where status='1' and '"+propertyName+"'='"+value+"'";
		
		if (StringUtils.isNotBlank(pid)) {
			
			sql+=" and pid='"+pid+"'";
		}

		
		int count = (int) dongrenJdbcTemplate.findForCount(sql,new MapSqlParameterSource());

		if (count < 1) {

			return true;

		}

		return false;

	}

	/**
	 * 
	 * 上传附件lv
	 * 
	 */
	public static String upFile1(String upPath, String fileName, File file,
			StringBuffer filePath, String fileTimeName) {

		if (fileName == null || "".equals(fileName.trim())) {
			return null;
		}

		if (file == null) {
			return null;
		}

		String absoultePath = upPath;
		String savePath = CommonService.UP_ROUTE_PHOTO_PATH + fileTimeName;

		File savefile = new File(new File(absoultePath), fileName);
		if (!savefile.getParentFile().exists()) {
			savefile.getParentFile().mkdirs();
		}
		try {
			FileUtils.copyFile(file, savefile);

			savePath = savePath + "/" + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return savePath;

	}

	public static String createSerials(String salt) {

		String serials = null;

		Date date = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

		serials = salt + format.format(date) + createRandom(4);

		return serials;

	}
	public static String createRandom(int vcodeNum) {

		String vNum=null;
		try {
			String vchar = "0,1,2,3,4,5,6,7,8,9";
			String[] vcArray = vchar.split(",");
			vNum = "";
			int temp = -1; // 记录上次随机数值，尽量避免生产几个一样的随机数

			// 采用一个简单的算法以保证生成随机数的不同
			Random rand = new Random();
			for (int i = 1; i < vcodeNum + 1; i++) {
				if (temp != -1) {
					rand = new Random(i * temp * DateUtil.getLongTimeMillis());
				}

				int t = rand.nextInt(vcArray.length);
				if (temp != -1 && temp == t) {
					return createSMSVerify(vcodeNum);
				}
				temp = t;
				vNum += vcArray[t];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vNum;

	}
	public static String createSMSVerify(int vcodeNum) {

		return createRandom(vcodeNum);

	}
}
