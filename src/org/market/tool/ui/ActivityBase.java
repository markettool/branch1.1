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

/** ����½ע��ͻ�ӭҳ����̳еĻ���-���ڼ���Ƿ��������豸��¼��ͬһ�˺�
  * @ClassName: ActivityBase
  * @Description: TODO
  * @author smile
  * @date 2014-6-13 ����5:18:24
  */
public class ActivityBase extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�Զ���½״̬�¼���Ƿ��������豸��½
		checkLogin();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//����״̬�µļ��
		checkLogin();
	}
	
	public void checkLogin() {
		BmobUserManager userManager = BmobUserManager.getInstance(this);
		if (userManager.getCurrentUser() == null) {
			ShowToast("�����˺����������豸�ϵ�¼!");
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}
	}
	
	/** ���������
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
	
	/**��ѯ���û��ĺ���������д������*/
	public void action(User user,String msg){
		
	}
	
	protected void initViews() {
		
	}
	
	protected void initData() {
		
	}
	
	protected void  setListeners() {
		
	}
	
}
