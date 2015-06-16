package org.market.tool.ui.fragment;

import org.market.tool.R;
import org.market.tool.bean.User;
import org.market.tool.ui.AccountActivity;
import org.market.tool.ui.LoginActivity;
import org.market.tool.ui.MyLauncherTaskActivity;
import org.market.tool.ui.NewMainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
/**
 * @date 2014/11/14
 * @author wuwenjie
 * @description ²à±ßÀ¸²Ëµ¥
 */
public class LeftFragment extends Fragment implements OnClickListener{
	private View todayView;
	private View lastListView;
	private View settingsView;
	private RelativeLayout myData;
	private TextView username;
	private ImageView userimg;
	
	private User myUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, null);
		findViews(view);
		return view;
	}
	
	
	public void findViews(View view) {
		todayView = view.findViewById(R.id.tvAccount);
		lastListView = view.findViewById(R.id.tvLastlist);
		settingsView = view.findViewById(R.id.tvMySettings);
		myData=(RelativeLayout) view.findViewById(R.id.my_data);
		username=(TextView) view.findViewById(R.id.user_name);
		userimg=(ImageView) view.findViewById(R.id.avatar_pic);
		userimg.setOnClickListener(this);
		todayView.setOnClickListener(this);
		lastListView.setOnClickListener(this);
		settingsView.setOnClickListener(this);
		myData.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		myUser = BmobUser.getCurrentUser(getActivity(),User.class);
		refresh();
	};
	
	private void refresh(){
		if(myUser!=null){
			if(myUser.getNick()!=null){
				username.setText(myUser.getNick());
			}else{
				username.setText("no nick");
			}
			 
		}
		else{
			username.setText("Î´µÇÂ¼");
		}
	}
	
	@Override
	public void onClick(View v) {
		Fragment newContent = null;
		String title = null;
		switch (v.getId()) {
		case R.id.tvAccount: // account
			getActivity().startActivity(new Intent(getActivity(), AccountActivity.class));
			break;
		case R.id.tvLastlist:// share
			getActivity().startActivity(new Intent(getActivity(), MyLauncherTaskActivity.class));
			break;
		case R.id.tvMySettings: // ÉèÖÃ
//			getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
			break;
		case R.id.my_data:
			if(myUser==null){
				getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
			}else{
//				getActivity().startActivity(new Intent(getActivity(), MyDataActivity.class));
			}
			break;
		case R.id.avatar_pic:
			if(myUser==null){
				getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
			}else{
//				getActivity().startActivity(new Intent(getActivity(), MyDataActivity.class));
			}
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}
	
	/**
	 * ÇÐ»»fragment
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof NewMainActivity) {
			NewMainActivity fca = (NewMainActivity) getActivity();
			fca.switchContent(fragment, title);
		}
	}
	
//	private void downloadUserPic(){
//		String dir=FileUtils.getSDCardRoot()+getActivity().getPackageName()+File.separator;
//		File dirFile=new File(dir);
//		if(!dirFile.exists()){
//			dirFile.mkdirs();
//		}
//		MyUser myUser=BmobUser.getCurrentUser(getActivity(), MyUser.class);
//		if(myUser==null){
//			return;
//		}
//	}
	
//	private void onClickShare() {  
//		  
//        Intent intent=new Intent(Intent.ACTION_SEND);   
//        intent.setType("text/plain");   
//        intent.putExtra(Intent.EXTRA_SUBJECT, "ÂÒµ¯");   
//        intent.putExtra(Intent.EXTRA_TEXT, "·¢±íÂÒµ¯¼È¿É×¬Ç®£¬ËÙËÙÏÂÔØ!\n" +
//        		"http://markettool-app.stor.sinaapp.com/opera.apk\n"+"Çë½«Á´½Ó¸´ÖÆµ½ä¯ÀÀÆ÷½øÐÐÏÂÔØ¡£");    
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
//        startActivity(Intent.createChooser(intent, getActivity().getTitle()));   
//  
//    } 
	
}
