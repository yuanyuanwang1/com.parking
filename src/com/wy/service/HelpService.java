/**
 * 
 */
package com.wy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.wy.model.ImageInfo;
import com.wy.superClass.SuperService;

/**
 * 
 * 帮助信息服务类
 * 
 * @author jian198001
 * 
 */
public class HelpService extends SuperService {

	private Logger logger = Logger.getLogger(HelpService.class);

}
