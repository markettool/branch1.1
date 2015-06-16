package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.ui.fragment.LeftFragment;
import org.market.tool.ui.fragment.TaskFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description ������
 */
public class NewMainActivity extends SlidingFragmentActivity implements
		OnClickListener {
	
	/**
	 * SDK��ʼ�������������ҳ
	 */
	public static String APPID = "61cac84cd9fe5268b0ff9ff054236373";

	private ImageView topButton;
	private Fragment mContent;
	private TextView topTextView;
	
	private LeftFragment leftFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �ޱ���
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_main);
		
		Bmob.initialize(getApplicationContext(),APPID);

		topButton = (ImageView) findViewById(R.id.topButton);
		topButton.setOnClickListener(this);
		topTextView = (TextView) findViewById(R.id.topTv);
		
		initSlidingMenu(savedInstanceState);
		
		updateVersion();
	}
	
	private void updateVersion(){
//		BmobUpdateAgent.initAppVersion(this);
		BmobUpdateAgent.update(this);
		BmobUpdateAgent.setUpdateOnlyWifi(false);
		 BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

			 @Override
			 public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
			     // TODO Auto-generated method stub
			     //����updateStatus���жϸ����Ƿ�ɹ�
//				 Log.e("majie","updateStatus "+ updateStatus);
			 }
			});
	}

	/**
	 * ��ʼ�������
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// ��������״̬��Ϊ����õ�֮ǰ�����Fragment������ʵ����MyFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new TaskFragment();
			switchContent(mContent, "WEIKE");
		}

		// ������໬���˵�
		setBehindContentView(R.layout.menu_frame_left);
		leftFragment=new LeftFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, leftFragment).commit();

		// ʵ���������˵�����
		SlidingMenu sm = getSlidingMenu();
		// ���ÿ������һ����Ĳ˵�
		sm.setMode(SlidingMenu.LEFT);
		// ���û�����Ӱ�Ŀ��
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// ���û����˵���Ӱ��ͼ����Դ
		sm.setShadowDrawable(null);
		// ���û����˵���ͼ�Ŀ��
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ý��뽥��Ч����ֵ
		sm.setFadeDegree(0.35f);
		// ���ô�����Ļ��ģʽ,��������Ϊȫ��
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// �����·���ͼ���ڹ���ʱ�����ű���
		sm.setBehindScrollScale(0.0f);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	/**
	 * �л�Fragment
	 * 
	 * @param fragment
	 */
	public void switchContent(Fragment fragment, String title) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
		topTextView.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topButton:
			toggle();
			break;
		default:
			break;
		}
	}
}
