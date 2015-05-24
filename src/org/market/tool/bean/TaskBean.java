package org.market.tool.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class TaskBean extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BmobFile userPic;
	private String username;
	private String taskContent;
	private int likeNum;
	private int commentNum;
//	private int statedLikeNum;
//	private int statedCommentNum;
	private BmobFile taskPic;
	/**任务发起人*/
//	private User taskUser;
	
	/*public int getStatLikeNum() {
		return statedLikeNum;
	}
	public void setStatLikeNum(int statLikeNum) {
		this.statedLikeNum = statLikeNum;
	}
	public int getStatCommentNum() {
		return statedCommentNum;
	}
	public void setStatCommentNum(int statCommentNum) {
		this.statedCommentNum = statCommentNum;
	}*/
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
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
	public void setTaskPic(BmobFile operaPic) {
		this.taskPic = operaPic;
	}
	public BmobFile getUserPic() {
		return userPic;
	}
	public void setUserPic(BmobFile userPic) {
		this.userPic = userPic;
	}
//	public User getTaskUser() {
//		return taskUser;
//	}
//	public void setTaskUser(User taskUser) {
//		this.taskUser = taskUser;
//	}

}
