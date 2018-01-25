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
 * 上传图片服务类
 * 
 * @author Administrator
 * 
 */
public class ImageService extends SuperService {

	private Logger logger = Logger.getLogger(ImageService.class);

	public ImageInfo get(String pid) {

		return hibernateTemplate.get(ImageInfo.class, pid);

	}

	public List<ImageInfo> list(String mapEntity, String mapId) {

		// 查询条件
		DetachedCriteria criteria = DetachedCriteria.forClass(ImageInfo.class)
				.add(Restrictions.eq("status", TABLE_STATUS_VALID))
				.addOrder(Order.asc("orderNum"));

		if (StringUtils.isNotEmpty(mapId)) {

			criteria.add(Restrictions.eq("mapId", mapId));

		}

		if (StringUtils.isNotEmpty(mapEntity)) {

			criteria.add(Restrictions.eq("mapEntity", mapEntity));

		}

		List<ImageInfo> imageInfos = hibernateTemplate.findByCriteria(criteria);

		return imageInfos;

	}

	public String save(ImageInfo imageInfo) {

		if (imageInfo == null) {
			return null;
		}

		hibernateTemplate.save(imageInfo);

		return imageInfo.getPid();
	}

	public void saveList(List<ImageInfo> iamgeInfoList) {

		if (iamgeInfoList == null || iamgeInfoList.isEmpty()) {
			return;
		}

		for (ImageInfo imageInfo : iamgeInfoList) {
			hibernateTemplate.save(imageInfo);
		}

	}

	public List pageInfoToImageList(String srcUri, String imageAlt,
			String description, String mapEntity, String mapId) {

		if (srcUri == null || "".equals(srcUri.trim())) {
			return null;
		}

		if (mapEntity == null || "".equals(mapEntity.trim())) {
			return null;
		}

		if (mapId == null || "".equals(mapId.trim())) {
			return null;
		}

		String[] srcUriArr = srcUri.split(",");

		List list = new ArrayList();
		for (int i = 0; i < srcUriArr.length; i++) {
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.setSrcUri(srcUriArr[i]);
			// imageInfo.setImageAlt(imageAltArr[i]);
			// imageInfo.setDescriptions(descriptionArr[i]);
			imageInfo.setMapEntity(mapEntity);
			imageInfo.setMapId(mapId);
			imageInfo.setOrderNum(i);
			imageInfo.setCreateTime(new Date());
			imageInfo.setLastUpTime(new Date());
			imageInfo.setStatus(TABLE_STATUS_VALID);
			list.add(imageInfo);
		}

		return list;

	}

	public String addImageListStr(String imageListStr, String mapId,
			String mapEntity) {

		StringBuffer imageIdArr = new StringBuffer();

		String[] imageList = imageListStr.split(",");

		for (int i = 0; i < imageList.length; i++) {

			ImageInfo imageInfos = new ImageInfo();
			String[] imageIntroduce = imageList[i].split("\\|");
			imageInfos.setSrcUri(imageIntroduce[0]);
			if (imageIntroduce.length >= 2) {
				imageInfos.setImageAlt(imageIntroduce[1]);
			}
			if (imageIntroduce.length == 3) {
				imageInfos.setDescriptions(imageIntroduce[2]);
			}
			imageInfos.setMapEntity(mapEntity);
			imageInfos.setMapId(mapId);
			imageInfos.setOrderNum(i);
			imageInfos.setCreateTime(new Date());
			imageInfos.setLastUpTime(new Date());
			imageInfos.setStatus(TABLE_STATUS_VALID);

			hibernateTemplate.save(imageInfos);

			if (StringUtils.isBlank(imageIdArr.toString())) {
				imageIdArr.append(imageInfos.getPid());
			} else {
				imageIdArr.append("," + imageInfos.getPid());
			}

		}

		return imageIdArr.toString();

	}

	public void updateImageInfo(ImageInfo imageInfo, String routeId) {

		if (imageInfo == null || imageInfo.getSrcUri() == null
				|| "".equals(imageInfo.getSrcUri().trim())) {
			return;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("delete from image_info ");
		sql.append("where map_entity=:mapEntity and map_id=:mapId");

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("mapEntity", "travelling_route");
		paramMap.addValue("mapId", routeId);

		dongrenJdbcTemplate.update(sql.toString(), paramMap);

		List imageInfoList = null; // pageInfoToImageList(imageInfo.getSrcUri(),
		// imageInfo.getImageAlt(), imageInfo.getDescriptions(),
		// ImageType.ROUTE_INFO, routeId);

		saveList(imageInfoList);

	}

	public void update(ImageInfo imageInfo) {

		hibernateTemplate.update(imageInfo);

	}

}
