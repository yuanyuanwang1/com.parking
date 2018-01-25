package com.wy.model;

import java.util.Date;

import com.wy.superClass.SuperModel;

/**
 * ImageInfo entity. @author MyEclipse Persistence Tools
 */

public class ImageInfo extends SuperModel {

	// Fields

	private String pid = null;

	// 图片说明
	private String imageAlt = null;

	// 详细注释
	private String descriptions = null;

	// 持久化存储文件路径
	private String srcUri = null;

	// 图片属于哪个MODEL
	private String mapEntity = null;

	// 所属MODEL的主键
	private String mapId = null;

	// 排序序号
	private Integer orderNum = null;

	private Date createTime = null;

	private Date lastUpTime = null;
	private String status = null;

	// Constructors

	/** default constructor */
	public ImageInfo() {
	}

	// Property accessors

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getImageAlt() {
		return this.imageAlt;
	}

	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}

	public String getDescriptions() {
		return this.descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getSrcUri() {
		return this.srcUri;
	}

	public void setSrcUri(String srcUri) {
		this.srcUri = srcUri;
	}

	public String getMapEntity() {
		return this.mapEntity;
	}

	public void setMapEntity(String mapEntity) {
		this.mapEntity = mapEntity;
	}

	public String getMapId() {
		return this.mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpTime() {
		return lastUpTime;
	}

	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
