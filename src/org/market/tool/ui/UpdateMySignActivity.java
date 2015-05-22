//package org.market.tool.ui;
//
//import org.market.tool.R;
//import org.market.tool.bean.User;
//import org.market.tool.view.HeaderLayout.onRightImageButtonClickListener;
//
//import cn.bmob.v3.listener.UpdateListener;
//
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class UpdateMySignActivity extends ActivityBase {
//
//	EditText edit_nick;
//	TextView tv_item;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_set_updateinfo);
//		initView();
//	}
//	
//	private void initView() {
//		initTopBarForBoth("修改个性签名", R.drawable.base_action_bar_true_bg_selector,
//				new onRightImageButtonClickListener() {
//
//					@Override
//					public void onClick() {
//						String nick = edit_nick.getText().toString();
//						if (nick.equals("")) {
//							ShowToast("请填写个性签名!");
//							return;
//						}
//						updateInfo(nick);
//					}
//				});
//		edit_nick = (EditText) findViewById(R.id.edit_nick);
////		edit_nick.setHint("请输入个性签名");
////		final User user = userManager.getCurrentUser(User.class);
////		edit_nick.setText(user.getMysign().toString());
//		
//		tv_item=(TextView) findViewById(R.id.tv_item);
//		tv_item.setText("个性签名");
//	}
//	
//	/** 修改资料
//	  * updateInfo
//	  * @Title: updateInfo
//	  * @return void
//	  * @throws
//	  */
//	private void updateInfo(String mysign) {
//		final User user = userManager.getCurrentUser(User.class);
//		User u = new User();
//		u.setMysign(mysign);
////		u.setHight(110);
//		u.setObjectId(user.getObjectId());
//		u.update(this, new UpdateListener() {
//
//			@Override
//			public void onSuccess() {
//				final User c = userManager.getCurrentUser(User.class);
//				ShowToast("修改成功:"+c.getNick()+",height = "+c.getHight());
//				finish();
//			}
//
//			@Override
//			public void onFailure(int arg0, String arg1) {
//				ShowToast("onFailure:" + arg1);
//			}
//		});
//	}
//}
