package org.market.tool.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.AssignTaskAdapter;
import org.market.tool.adapter.TaskAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.inter.Observer;
import org.market.tool.ui.FragmentBase;
import org.market.tool.ui.PublishTaskActivity;
import org.market.tool.ui.TaskDetailActivity;
import org.market.tool.view.xlist.XListView;
import org.market.tool.view.xlist.XListView.IXListViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
public class TaskFragment extends FragmentBase implements Observer{
	
	private RelativeLayout mAdContainer;
	
	private XListView xlv;
//	private AutoScrollTextView autoScrollTextView;
	
	private TaskAdapter adapter;
	
	public static final int FINISH_REFRESHING=0;
	public static final int FINISH_LOADING=1;
	
	private int focusSkip,nearSkip;
	private List<TaskBean> taskBeans=new ArrayList<TaskBean>();
	
	private int oldSize=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, null);
		mAdContainer = (RelativeLayout) view.findViewById(R.id.adcontainer);
		xlv=(XListView) view.findViewById(R.id.lv);
		
		PublishTaskActivity.attach(this);
		
//		lv.setOnScrollListener(new PauseOnScrollListener(BitmapHelp.getBitmapUtils(getActivity()), false, true));
//		autoScrollTextView=(AutoScrollTextView) view.findViewById(R.id.autoscroll_tv);
//		 autoScrollTextView.initScrollTextView(getActivity().getWindowManager(), 
//	                "下一版本加聊天功能，敬请期待！"); 
//	        autoScrollTextView.starScroll();  
	        
		setAdapter();
		setListeners();
		queryFocusOperas(FINISH_REFRESHING);
		return view;
	}
	
	private void setListeners(){
		xlv.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				Log.e("majie", "refresh");
				taskBeans.clear();
				focusSkip=0;
				queryFocusOperas(FINISH_REFRESHING);
			}
			
			@Override
			public void onLoadMore() {
				focusSkip=taskBeans.size();
				queryFocusOperas(FINISH_LOADING);
			}
		});
		
		
		xlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(getActivity(), TaskDetailActivity.class);
				intent.putExtra("taskBean", taskBeans.get(arg2-1));
				getActivity().startActivity(intent);
			}
		});
	}
	
	private void queryFocusOperas(final int handle){
		synchronized (TaskFragment.this) {
			BmobQuery<TaskBean> focusQuery	 = new BmobQuery<TaskBean>();
			focusQuery.order("-scanNum");
			focusQuery.setLimit(10);
			focusQuery.setSkip(focusSkip);
			focusQuery.addWhereEqualTo("status", 0);
			focusQuery.findObjects(getActivity(), new FindListener<TaskBean>() {

				@Override
				public void onSuccess(List<TaskBean> object) {
					Log.e("majie", "查询成功：共"+object.size()+"条数据。");
					oldSize=taskBeans.size();
					focusSkip+=object.size();
					taskBeans.addAll(object);
					
					synchronized (TaskFragment.this) {
						switch (handle) {
						case FINISH_LOADING:
							if(oldSize<taskBeans.size()){
								xlv.setSelection(oldSize);
							}
							xlv.stopLoadMore();
							break;

						default:
							xlv.stopRefresh();
							if(taskBeans.size()>8){
								xlv.setPullLoadEnable(true);
							}
							break;
						}
						adapter.notifyDataSetChanged();
					}
				}

				@Override
				public void onError(int code, String msg) {
					Log.e("majie","查询失败："+msg);
					synchronized (TaskFragment.this) {
						switch (handle) {
						case FINISH_LOADING:
							xlv.stopLoadMore();
							break;

						default:
							xlv.stopRefresh();
							break;
						}
						adapter.notifyDataSetChanged();
					}
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
//				Log.e("majie", "查询成功：共"+object.size()+"条数据。");
//				oldSize=operaBeans.size();
//				nearSkip+=object.size();
//				operaBeans.addAll(object);
//				
//				mHandler.sendEmptyMessage(handle);
//			}
//
//			@Override
//			public void onError(int code, String msg) {
//				Log.e("majie","查询失败："+msg);
//				mHandler.sendEmptyMessage(handle);
//			}
//		});
//	}
	
	private void setAdapter(){
		adapter=new TaskAdapter(getActivity(), taskBeans);
		xlv.setAdapter(adapter);
	}

	@Override
	public void update(String msg) {
		taskBeans.clear();
		focusSkip=0;
		queryFocusOperas(FINISH_REFRESHING);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		AssignTaskAdapter.remove(this);
		PublishTaskActivity.remove(this);
	}
	
}
