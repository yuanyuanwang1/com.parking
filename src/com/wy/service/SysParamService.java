/**
 * 
 */
package com.wy.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wy.dao.PageInfo;
import com.wy.model.SysParam;
import com.wy.superClass.SuperService;


/**
 * 
 * 旅游路线商品信息的服务类
 * 
 * @author Administrator
 * 
 */
public class SysParamService extends SuperService {

	private CommonService commonService = null;

	/**
	 * @return the commonService
	 */
	public CommonService getCommonService() {
		return commonService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	private Logger logger = Logger.getLogger(SysParamService.class);

	public PageInfo list(String searchValue, int pageNum) {

		if (pageNum < 1) {

			pageNum = 1;

		}
		
		String sql="select * from Sys_Param where status='1'";
		
		if (StringUtils.isNotBlank(searchValue)) {
			
			sql+=" and (param_name like '%'"+searchValue+"%' or param_code like '%'"+searchValue+"'%'";
		}
				
		sql+=" order by create_time desc";

		return dongrenJdbcTemplate.getPage(sql, pageNum);

	}

	public SysParam get(String pid) {

		if (StringUtils.isBlank(pid)) {

			return null;

		}

		SysParam sysParam = hibernateTemplate.get(SysParam.class, pid);

		return sysParam;

	}

	public void save(SysParam sysParam) {
		addOrEdit(sysParam);
	}

	private void addOrEdit(SysParam obj) {

		hibernateTemplate.saveOrUpdate(obj);

	}

	public boolean verifyParamName(String paramName) {

		boolean verify = commonService.verifyUnique(paramName, "paramName",
				"SYS_PARAM", null);

		return verify;

	}

	public boolean verifyParamCode(String paramCode) {

		boolean verify = commonService.verifyUnique(paramCode, "paramCode","SYS_PARAM", null);

		return verify;

	}

}
