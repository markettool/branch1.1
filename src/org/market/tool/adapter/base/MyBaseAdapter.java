package org.market.tool.adapter.base;

import java.util.List;

import org.market.tool.bean.User;
import org.market.tool.util.BitmapHelp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.Toast;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;

public abstract class MyBaseAdapter extends BaseAdapter {
	protected Context mContext;
	protected BmobChatManager manager;
	protected LayoutInflater mInflater;
	protected BmobUserManager userManager;
	protected BitmapUtils bitmapUtils;
	protected DbUtils dbUtils;
	protected User user;
	
	protected MyBaseAdapter(Context context) {
		this.mContext=context;
		manager=BmobChatManager.getInstance(context);
		userManager=BmobUserManager.getInstance(context);
		mInflater=LayoutInflater.from(context);
		user=BmobUser.getCurrentUser(context, User.class);
		bitmapUtils=BitmapHelp.getBitmapUtils(context);
		dbUtils=DbUtils.create(context);
	}
	
	Toast mToast;

	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			((Activity) mContext).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(mContext, text,
								Toast.LENGTH_SHORT);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});

		}
	}

	/**
	 * 打Log ShowLog
	 */
	public void ShowLog(String msg) {
		BmobLog.i(msg);
	}
	
	protected void push(BmobChatUser targetUser ,String msg){
		// 组装BmobMessage对象
		BmobMsg message = BmobMsg.createTextSendMsg(mContext, targetUser.getObjectId(), msg);
		message.setExtra("Bmob");
		// 默认发送完成，将数据保存到本地消息表和最近会话表中
		manager.sendTextMessage(targetUser, message);			
	}
	
	protected void queryUserByName(String searchName,final String msg){
		BmobQuery<User> query = new BmobQuery<User>();
		
		query.addWhereEqualTo("username", searchName);
		query.findObjects(mContext, new FindListener<User>() {

			@Override
			public void onSuccess(List<User> object) {

				if(object.size()!=0){
					action(object.get(0), msg);
				}
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}
	
	/**查询完用户的后续操作，写在这里*/
	public void action(User user,String msg){
		
	}
	

}
