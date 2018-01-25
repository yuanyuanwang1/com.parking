/**
 * 
 */
package com.wy.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wy.dao.PageInfo;
import com.wy.model.AreaInfo;
import com.wy.superClass.SuperService;
import com.wy.values.AreaType;

/**
 * 
 * 国家、省、市的相关服务
 * 
 * @author jian198001
 * 
 */
public class AreaService extends SuperService {

	private Logger logger = Logger.getLogger(AreaService.class);

	private List<AreaInfo> areaInfoList = null;

	private Element rootElement = null;

	public List<AreaInfo> getAreaInfoList() {

		if (areaInfoList == null) {

			Document rootDocument = load();

			rootElement = rootDocument.getRootElement();

			return list(rootElement, null);

		}

		return areaInfoList;

	}

	/**
	 * 根据传入的国家、省市编号获取相应的地区列表
	 * 
	 * @param countryCode
	 *            国家编码
	 * @param provinceCode
	 *            省份编码
	 * @return 地区列表
	 */
	public List<AreaInfo> searchAreaListByCode(String countryCode,
			String provinceCode) {

		List<AreaInfo> areaInfoList = getAreaInfoList();

		if (StringUtils.isBlank(countryCode)) {

			return areaInfoList;

		}

		if (StringUtils.isBlank(countryCode)
				&& StringUtils.isBlank(provinceCode)) {
			return areaInfoList;
		}

		for (AreaInfo countryInfo : areaInfoList) {

			if (!StringUtils.equals(countryInfo.getAreaCode(), countryCode)) {

				continue;

			}

			if (StringUtils.isBlank(provinceCode)) {

				return countryInfo.getChildArea();

			}

			for (AreaInfo provinceInfo : countryInfo.getChildArea()) {

				if (StringUtils
						.equals(provinceInfo.getAreaCode(), provinceCode)) {

					return provinceInfo.getChildArea();

				}

			}

		}

		return null;

	}

	
	/**
	 * 返回 以地区编码和地区名称 组合成的Json数据列表
	 * 
	 * @param countryCode
	 *            国家编码
	 * @param provinceCode
	 *            地区编码
	 * @return 地区列表 json数据格式
	 */
	public String getAreaListOfJson(String countryCode, String provinceCode) {

		List<AreaInfo> areaInfoList = searchAreaListByCode(countryCode,
				provinceCode);

		if (areaInfoList == null) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (AreaInfo areaInfo : areaInfoList) {

			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("areaCode", areaInfo.getAreaCode());
				jsonObject.put("areaName", areaInfo.getAreaName());
			} catch (JSONException e) {

				e.printStackTrace();
			}

			jsonArray.put(jsonObject);

		}

		return jsonArray.toString();

	}
	
	/**
	 * 根据输入的地区编码得到相应的地区对象
	 * 
	 * @param countryCode
	 *            国家编码
	 * @param provinceCode
	 *            省份编码
	 * @param cityCode
	 *            市级编码
	 * @return 地区对象
	 */
	public AreaInfo searchAreaInfoByCode(String countryCode,
			String provinceCode, String cityCode) {

		// 如果国家编码为空，则查询不到任何数据
		if (StringUtils.isBlank(countryCode)) {
			return null;
		}

		// 取得地区列表对象
		List<AreaInfo> areaInfoList = getAreaInfoList();

		// 遍历地区列表
		for (AreaInfo countryInfo : areaInfoList) {

			// 处理国家对象，只有此国家对象的编码与要查找的国家编码相同，才进行处理
			if (!StringUtils.equals(countryInfo.getAreaCode(), countryCode)) {

				continue;

			}

			// 如果只查找国家代码，不查找省代码，则直接把国家对象及对应的下面所有省市对象返回
			// 如果要查找的省代码不为空
			if (StringUtils.isBlank(provinceCode)) {

				return countryInfo;

			}

			// 处理此国家对象下级的省对象
			// 遍历省对象
			for (AreaInfo provinceInfo : countryInfo.getChildArea()) {

				// 只有此省对象的编码与要查找的省编码相同，才进行处理
				if (!StringUtils.equals(provinceInfo.getAreaCode(),
						provinceCode)) {

					continue;

				}

				// 如果只查找省代码，不查找市代码，则直接把省对象及对应的下面所有市对象返回
				if (StringUtils.isBlank(cityCode)) {
					return provinceInfo;
				}

				// 遍历市对象
				for (AreaInfo cityInfo : provinceInfo.getChildArea()) {

					// 如果此市对象的编码与要查找的市编码相同，则返回此市对象
					if (StringUtils.equals(cityInfo.getAreaCode(), cityCode)) {
						return cityInfo;
					}

				}

			}

		}

		// 如果国家、省、市代码都查找不到，则返回空对象
		return null;
	}

	/**
	 * 加载Xml数据到AreaList中去；
	 * 
	 * @param element
	 *            节点
	 * @param parentAreaInfo
	 *            父级对象
	 * @return 地区信息列表
	 */
	private List<AreaInfo> list(Element element, AreaInfo parentAreaInfo) {

		if (element == null) {

			return null;

		}

		// 新建地区列表对象，里面无数据
		List<AreaInfo> areaInfoList = new ArrayList<AreaInfo>();

		// 为XML节点构造迭代器
		Iterator iterator = element.elementIterator();

		// 遍历顶层节点
		while (iterator.hasNext()) {

			// 取出子节点
			Element sonElement = (Element) iterator.next();

			// 构造地区对象
			AreaInfo areaInfo = new AreaInfo();

			// 从XML取出数据值放入地区对象中
			areaInfo.setAreaCode(sonElement.attribute("areaCode").getText());
			areaInfo.setAreaName(sonElement.attribute("areaName").getText());

			if (sonElement.attribute("status") != null) {

				areaInfo.setStatus(sonElement.attribute("status").getText());

			}

			// 如果父地区对象为空，则此对象是国家对象
			if (parentAreaInfo == null) {
				// 设置此节点为国家节点
				areaInfo.setAreaType(AreaType.COUNTRY);
				// 迭代处理下级的省对象
				areaInfo.setChildArea(list(sonElement, areaInfo));
			}

			// 如果此对象的父对象类型是国家对象，则此对象是省对象
			if (parentAreaInfo != null
					&& AreaType.COUNTRY.equals(parentAreaInfo.getAreaType())) {

				// 设置此节点为省节点
				areaInfo.setAreaType(AreaType.PROVINCE);
				// 将传入的国家对象作为父对象
				areaInfo.setParentArea(parentAreaInfo);
				// 迭代处理下级的市对象
				areaInfo.setChildArea(list(sonElement, areaInfo));
			}

			// 如果此对象的父对象类型是省对象，则此对象是市对象
			if (parentAreaInfo != null
					&& AreaType.PROVINCE.equals(parentAreaInfo.getAreaType())) {

				// 设置此节点为市节点
				areaInfo.setAreaType(AreaType.CITY);

				// 将传入的省对象作为父对象
				areaInfo.setParentArea(parentAreaInfo);
			}

			// 将此对象加入地区列表中
			areaInfoList.add(areaInfo);

		}

		// 返回地区列表
		return areaInfoList;

	}

	/**
	 * 加载Xml文件
	 * 
	 * @return Xml文件对象
	 */
	private Document load() {

		Document document = null;

		try {

			SAXReader saxReader = new SAXReader();

			// 读取地区XML配置文件内容
			document = saxReader.read(new File(this.getClass().getClassLoader()
					.getResource("").getPath()
					+ "/config/areaInfo.xml"));

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return document;

	}

	public PageInfo getPage(String searchValue, int iPageNum) {

		List<AreaInfo> list = getAreaInfoList();

		return PageInfo.getPage(list, iPageNum, null);

	}

}
