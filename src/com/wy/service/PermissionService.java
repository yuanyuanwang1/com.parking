package com.wy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.wy.model.CommonPermission;
import com.wy.model.CommonRolePermissionMap;
import com.wy.superClass.SuperService;

public class PermissionService extends SuperService {

	private Logger logger = Logger.getLogger(PermissionService.class);

	/**
	 * @param pid
	 *            获取对象的PID
	 * @return 要获取的对象
	 */
	public CommonPermission get(String pid) {
		return hibernateTemplate.get(CommonPermission.class, pid);
	}

	public List<String> getPermissionsByRoleId(String roleId) {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(CommonRolePermissionMap.class);

		criteria.add(Restrictions.eq("roleId", roleId));

		List<CommonRolePermissionMap> commonRolePermissionMaps = hibernateTemplate
				.findByCriteria(criteria);

		if (commonRolePermissionMaps == null
				|| commonRolePermissionMaps.isEmpty()) {

			return null;

		}

		List<String> permissionIds = new ArrayList<String>();

		for (CommonRolePermissionMap commonRolePermissionMap : commonRolePermissionMaps) {

			permissionIds.add(commonRolePermissionMap.getPermissionId().trim());

		}

		return permissionIds;

	}

	public String createSearch(String hql, String searchValue) {
		if (StringUtils.isBlank(searchValue)) {

			return hql;

		}

		hql = hql + " and permissionName like '%" + searchValue + "%' ";

		return hql;

	}

}
