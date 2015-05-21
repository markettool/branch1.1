package org.market.tool.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.OperaAdapter;
import org.market.tool.bean.OperaBean;
import org.market.tool.ui.FragmentBase;
import org.market.tool.ui.PublishTaskActivity;
import org.market.tool.view.HeaderLayout.onRightImageButtonClickListener;
import org.market.tool.view.xlist.XListView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
public class TaskFragment extends FragmentBase {
	public static final String PUBLISHER_ID = "56OJxFyIuN0CmR98Ua";
	public static final String InlinePPID = "16TLettoApHowNUdHoefcMUi";
	public static final String InterstitialPPID = "16TLwebvAchksY6iOa7F4DXs";
	public static final String SplashPPID = "16TLwebvAchksY6iOGe3xcik";
	
	private RelativeLayout mAdContainer;
	
//	private ImageView btWrite;
	private XListView lv;
//	private AutoScrollTextView autoScrollTextView;
	
	private OperaAdapter adapter;
	
	public static final int FINISH_REFRESHING=0;
	public static final int FINISH_LOADING=1;
	
	private int focusSkip,nearSkip;
	private List<OperaBean> operaBeans=new ArrayList<OperaBean>();
	
	private int oldSize=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, null);
		mAdContainer = (RelativeLayout) view.findViewById(R.id.adcontainer);
//		btWrite=(ImageView) view.findViewById(R.id.btn_write);
		lv=(XListView) view.findViewById(R.id.lv);
//		lv.setOnScrollListener(new PauseOnScrollListener(BitmapHelp.getBitmapUtils(getActivity()), false, true));
//		autoScrollTextView=(AutoScrollTextView) view.findViewById(R.id.autoscroll_tv);
//		 autoScrollTextView.initScrollTextView(getActivity().getWindowManager(), 
//	                "��һ�汾�����칦�ܣ������ڴ���"); 
//	        autoScrollTextView.starScroll();  
	        
		setAdapter();
		setListeners();
		queryFocusOperas(FINISH_REFRESHING);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTopBarForRight("����", R.drawable.btn_chat_send_selector, new onRightImageButtonClickListener() {

			@Override
			public void onClick() {
			    startAnimActivity(PublishTaskActivity.class);
			}
		});
	}
	
	private void setListeners(){
//		btWrite.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				getActivity().startActivityForResult(new Intent(getActivity(), WriteOperaActivity.class),0x01);
//			}
//		});
		
//        mRefreshableView.setOnRefreshListener(new PullToRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				Log.e("majie", "refresh");
//				operaBeans.clear();
//				focusSkip=0;
//				nearSkip=0;
//				queryFocusOperas(FINISH_REFRESHING);
//			}
//		}, 1, false);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent intent=new Intent(getActivity(), CommentActivity.class);
//				intent.putExtra("operaBean", operaBeans.get(arg2));
//				getActivity().startActivity(intent);
			}
		});
	}
	
	private void queryFocusOperas(final int handle){
		synchronized (TaskFragment.this) {
			BmobQuery<OperaBean> focusQuery	 = new BmobQuery<OperaBean>();
			focusQuery.order("-commentNum,-likeNum");
			focusQuery.setLimit(10);
			focusQuery.setSkip(focusSkip);
			focusQuery.findObjects(getActivity(), new FindListener<OperaBean>() {

				@Override
				public void onSuccess(List<OperaBean> object) {
					Log.e("majie", "��ѯ�ɹ�����"+object.size()+"�����ݡ�");
					oldSize=operaBeans.size();
					focusSkip+=object.size();
					operaBeans.addAll(object);
//					queryNearOperas(handle);
					
					mHandler.sendEmptyMessage(handle);
				}

				@Override
				public void onError(int code, String msg) {
					Log.e("majie","��ѯʧ�ܣ�"+msg);
					mHandler.sendEmptyMessage(handle);
				}
			});
		}
		
	}
	
//	private void queryNearOperas(final int handle){
//		BmobQuery<OperaBean> nearQuery	 = new BmobQuery<OperaBean>();
//		nearQuery.setLimit(5);
//		nearQuery.order("-updatedAt");
//		nearQuery.setSkip(nearSkip);
//		nearQuery.findObjects(getActivity(), new FindListener<OperaBean>() {
//
//			@Override
//			public void onSuccess(List<OperaBean> object) {
//				Log.e("majie", "��ѯ�ɹ�����"+object.size()+"�����ݡ�");
//				oldSize=operaBeans.size();
//				nearSkip+=object.size();
//				operaBeans.addAll(object);
//				
//				mHandler.sendEmptyMessage(handle);
//			}
//
//			@Override
//			public void onError(int code, String msg) {
//				Log.e("majie","��ѯʧ�ܣ�"+msg);
//				mHandler.sendEmptyMessage(handle);
//			}
//		});
//	}
	
	private void setAdapter(){
		adapter=new OperaAdapter(getActivity(), operaBeans);
		lv.setAdapter(adapter);
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FINISH_REFRESHING:
//				mRefreshableView.finishRefreshing();
				break;

			case FINISH_LOADING:
//				mRefreshableView.finishLoading();
				if(oldSize+1<operaBeans.size()){
					lv.setSelection(oldSize+1);
				}
				break;
				
			}
			synchronized (TaskFragment.this) {
				adapter.notifyDataSetChanged();
			}
			
		};
	};
	
}
