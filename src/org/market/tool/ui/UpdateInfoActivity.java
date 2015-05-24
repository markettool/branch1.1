package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.User;
import org.market.tool.view.HeaderLayout.onRightImageButtonClickListener;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;


/**
 * 设置昵称和性别
 * 
 * @ClassName: SetNickAndSexActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-7 下午4:03:40
 */
public class UpdateInfoActivity extends ActivityBase {
	public final static int NICK=0;
	public final static int MYSIGN=1;
	public final static int REALNAME=2;
	public final static int PHONENUM=3;
	
	private int TYPE=0;
	
	private String [] titles={"修改昵称","修改个性签名","修改真实姓名","修改手机号"};
	private String [] items={"昵称","个性签名","真实姓名","手机号"};
	EditText edit_nick;
	TextView tv_item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_updateinfo);
		
		TYPE=getIntent().getIntExtra("TYPE", 0);
		
		initView();
	}

	private void initView() {
		initTopBarForBoth(titles[TYPE], R.drawable.base_action_bar_true_bg_selector,
				new onRightImageButtonClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						String nick = edit_nick.getText().toString();
						if (nick.equals("")) {
							ShowToast("请填写"+items[TYPE]);
							return;
						}
						updateInfo(nick);
					}
				});
		edit_nick = (EditText) findViewById(R.id.edit_nick);
		edit_nick.setHint("请填写"+items[TYPE]);
		
		tv_item=(TextView) findViewById(R.id.tv_item);
		tv_item.setText(items[TYPE]);
	}

	/** 修改资料
	  * updateInfo
	  * @Title: updateInfo
	  * @return void
	  * @throws
	  */
	private void updateInfo(String msg) {
		final User user = userManager.getCurrentUser(User.class);
		User u = new User();
		switch (TYPE) {
		case NICK:
			u.setNick(msg);
			u.setHight(110);
			break;
			
		case MYSIGN:
			u.setMysign(msg);
			break;
			
		case REALNAME:
			break;
			
		case PHONENUM:
			u.setPhonenum(msg);
			break;

		default:
			break;
		}
		
		u.setObjectId(user.getObjectId());
		u.update(this, new UpdateListener() {

			@Override
			public void onSuccess() {
				final User c = userManager.getCurrentUser(User.class);
				ShowToast("修改成功:"+c.getNick()+",height = "+c.getHight());
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ShowToast("onFailure:" + arg1);
			}
		});
	}
}
