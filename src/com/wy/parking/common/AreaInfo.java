/**
 * 
 */
package com.wy.parking.common;

import org.apache.commons.lang.StringUtils;

import com.wy.service.AreaService;
import com.wy.superClass.SuperAction;

/**
 * @author Bin
 * 
 */
public class AreaInfo extends SuperAction {

	private AreaService areaService = null;

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public String getAreaList() {

		String countryCode = request.getParameter("countryCode");

		String provinceCode = request.getParameter("provinceCode");

		if (StringUtils.isBlank(countryCode)) {
			countryCode = null;
		}

		if (StringUtils.isBlank(provinceCode)) {
			provinceCode = null;
		}

		String areaResultStr = areaService.getAreaListOfJson(countryCode,
				provinceCode);

		this.printAjaxToPage(areaResultStr);

		return SUCCESS;

	}

}
