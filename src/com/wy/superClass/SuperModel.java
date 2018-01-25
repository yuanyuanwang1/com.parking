/**
 * 
 */
package com.wy.superClass;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class SuperModel implements Serializable {

	protected String pid = null;
	protected String status = null;
	protected Date createTime = null;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
