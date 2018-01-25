/**
 * 
 */
package com.wy.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wy.dao.PageInfo;
import com.wy.superClass.SuperService;

/**
 * 
 * 货币的相关服务
 * 
 * @author jian198001
 * 
 */
public class CurrencyService extends SuperService {

	private Logger logger = Logger.getLogger(CurrencyService.class);

	public List<Map<String, Object>> getList() {

		// 获取解析器
		SAXReader reader = new SAXReader();
		// 解析xml获取代表整个文档的document对象
		Document document = null;

		try {
			document = reader.read(new File(this.getClass().getClassLoader()
					.getResource("").getPath()
					+ "/config/currencyCode.xml"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取根节点
		Element root = document.getRootElement();

		List<Element> childElements = root.elements();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Element child : childElements) {

			Element entityNameElement = child.element("entityName");

			String entityName = entityNameElement.getText();

			Element entityNameEnElement = child.element("entityNameEn");

			String entityNameEn = entityNameEnElement.getText();

			Element currencyNameElement = child.element("currencyName");

			String currencyName = currencyNameElement.getText();

			Element currencyNameEnElement = child.element("currencyNameEn");

			String currencyNameEn = currencyNameEnElement.getText();

			Element alphaCodeElement = child.element("alphaCode");

			String alphaCode = alphaCodeElement.getText();

			Element numericCodeElement = child.element("numericCode");

			String numericCode = numericCodeElement.getText();

			Element minorUnitElement = child.element("minorUnit");

			String minorUnit = minorUnitElement.getText();

			Element aliasNameElement = child.element("aliasName");

			String aliasName = aliasNameElement.getText();

			Attribute statusAttribute = child.attribute("status");

			String status = null;

			if (statusAttribute != null) {

				status = statusAttribute.getText();

			}

			if (StringUtils.isBlank(aliasName)) {

				aliasName = currencyName;

			}

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("entityName", StringUtils.trim(entityName));

			map.put("entityNameEn", StringUtils.trim(entityNameEn));

			map.put("currencyName", StringUtils.trim(currencyName));

			map.put("currencyNameEn", StringUtils.trim(currencyNameEn));

			map.put("alphaCode", StringUtils.trim(alphaCode));

			map.put("numericCode", StringUtils.trim(numericCode));

			map.put("minorUnit", StringUtils.trim(minorUnit));

			map.put("aliasName", StringUtils.trim(aliasName));

			map.put("status", StringUtils.trim(status));

			list.add(map);

		}

		return list;

	}

	public Map<String, Object> getMapOne(String alphaCode) {

		List<Map<String, Object>> list = getList();

		for (Map<String, Object> map : list) {

			if (StringUtils.equals((String) map.get("alphaCode"),
					StringUtils.trim(alphaCode))) {

				return map;

			}

		}

		return null;

	}

	public PageInfo getPage(String searchValue, int iPageNum) {

		List<Map<String, Object>> list = getList();

		return PageInfo.getPage(list, iPageNum, null);

	}

	public String update(String alphaCode, String aliasName, String status) {

		return null;

	}

	public List<Map<String, Object>> getListStatus() {

		List<Map<String, Object>> list = getList();

		List<Map<String, Object>> listReturn = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map : list) {

			if (StringUtils.equals((String) map.get("status"),
					super.TABLE_STATUS_VALID)) {

				listReturn.add(map);

			}

		}

		return listReturn;

	}

}
