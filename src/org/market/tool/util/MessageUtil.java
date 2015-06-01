package org.market.tool.util;

import org.market.tool.bean.Message;

import com.google.gson.Gson;

public class MessageUtil {
	
	public static String toMessageJson(String tag,String msg,String username,String usernick){
		Message message=new Message();
		message.setTag(tag);
		message.setMsg(msg);
		message.setUsername(username);
		message.setUsernick(usernick);
		Gson gson=new Gson();
		return gson.toJson(message);
	}
	
	public static Message getMessageFromJson(String json){
		Gson gson=new Gson();
		return gson.fromJson(json, Message.class);
	}


}
