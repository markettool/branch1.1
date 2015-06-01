package org.market.tool.bean;

public class Message {
	public static final String ENROLL="enroll";
	public static final String ASSIGN="assign";
	
	private String tag;
	private String username;
	private String usernick;
	private String msg;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsernick() {
		return usernick;
	}
	public void setUsernick(String usernick) {
		this.usernick = usernick;
	}

}
