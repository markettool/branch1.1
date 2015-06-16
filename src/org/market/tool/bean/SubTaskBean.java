package org.market.tool.bean;

import cn.bmob.v3.BmobObject;

public class SubTaskBean extends BmobObject {
	public static final int STATUS_ENROLL=0;
	public static final int STATUS_PERMIT=1;
	public static final int STATUS_COMPLETE=2;
	public static final int STATUS_CLOSED=3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String originTaskId;
	private String originTaskContent;
	private String username;
	private int status;
	public String getOriginTaskId() {
		return originTaskId;
	}
	public void setOriginTaskId(String originTaskId) {
		this.originTaskId = originTaskId;
	}
	public String getOriginTaskContent() {
		return originTaskContent;
	}
	public void setOriginTaskContent(String originTaskContent) {
		this.originTaskContent = originTaskContent;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
