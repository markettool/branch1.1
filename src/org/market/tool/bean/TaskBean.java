package org.market.tool.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class TaskBean extends BmobObject {

	private static final long serialVersionUID = 1L;
	private BmobFile ownerPic;
	private String ownerName;
	private String taskContent;
	private int scanNum;
	private int commentNum;
	private BmobFile taskPic;
	private int status;
	private String receiverName;
	public BmobFile getOwnerPic() {
		return ownerPic;
	}
	public void setOwnerPic(BmobFile ownerPic) {
		this.ownerPic = ownerPic;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public int getScanNum() {
		return scanNum;
	}
	public void setScanNum(int scanNum) {
		this.scanNum = scanNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public BmobFile getTaskPic() {
		return taskPic;
	}
	public void setTaskPic(BmobFile taskPic) {
		this.taskPic = taskPic;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	

}
