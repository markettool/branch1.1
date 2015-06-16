package org.market.tool.ui;

import java.util.List;

import org.market.tool.bean.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/** 除登陆注册和欢迎页面外继承的基类-用于检测是否有其他设备登录了同一账号
  * @ClassName: ActivityBase
  * @Description: TODO
  * @author smile
  * @date 2014-6-13 下午5:18:24
  */
public class ActivityBase extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//自动登陆状态下检测是否在其他设备登陆
		checkLogin();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//锁屏状态下的检测
		checkLogin();
	}
	
	public void checkLogin() {
		BmobUserManager userManager = BmobUserManager.getInstance(this);
		if (userManager.getCurrentUser() == null) {
			ShowToast("您的账号已在其他设备上登录!");
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}
	}
	
	/** 隐藏软键盘
	  * hideSoftInputView
	  * @Title: hideSoftInputView
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	protected void push(User targetUser ,String json){
		String installationId = targetUser.getInstallId();
		BmobPushManager bmobPush = new BmobPushManager(this);
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("installationId", installationId);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(json);		
	}
	
	protected void queryUserByName(String searchName,final String msg){
		BmobQuery<User> query = new BmobQuery<User>();
		
		query.addWhereEqualTo("username", searchName);
		query.findObjects(this, new FindListener<User>() {

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
	
	protected void initViews() {
		
	}
	
	protected void initData() {
		
	}
	
	protected void  setListeners() {
		
	}
	
}
