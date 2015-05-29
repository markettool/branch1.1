package org.market.tool.adapter.base;

import java.util.List;

import org.market.tool.bean.User;
import org.market.tool.util.BitmapHelp;
import org.market.tool.util.ProgressUtil;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;

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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

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
	 * ��Log ShowLog
	 */
	public void ShowLog(String msg) {
		BmobLog.i(msg);
	}
	
	protected void push(BmobChatUser targetUser ,String msg){
		// ��װBmobMessage����
		BmobMsg message = BmobMsg.createTextSendMsg(mContext, targetUser.getObjectId(), msg);
		message.setExtra("Bmob");
		// Ĭ�Ϸ�����ɣ������ݱ��浽������Ϣ�������Ự����
		manager.sendTextMessage(targetUser, message);			
	}
	
	protected void queryUserByName(String searchName,final String msg){
		userManager.queryUserByName(searchName, new FindListener<BmobChatUser>() {
	        @Override
	        public void onError(int arg0, String arg1) {
	            ShowToast("�����˴����쳣");
	            ProgressUtil.closeProgress();
	        }

	        @Override
	        public void onSuccess(List<BmobChatUser> arg0) {
	        	ProgressUtil.closeProgress();
	            push(arg0.get(0), msg);
	        }
	    });
	}
	

}
