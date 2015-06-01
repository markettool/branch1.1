package org.market.tool.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.AssignTaskAdapter;
import org.market.tool.adapter.MyTaskAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;
import org.market.tool.inter.Observer;
import org.market.tool.ui.AssignTaskActiviity;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
public class MyTaskFragment extends FragmentBase implements Observer{
	
	private RelativeLayout mAdContainer;
	
	private XListView xlv;
	
	private MyTaskAdapter adapter;
	
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
		
		AssignTaskAdapter.attach(this);
		PublishTaskActivity.attach(this);
		
		setAdapter();
		setListeners();
		queryFocusTasks(FINISH_REFRESHING);
		return view;
	}
	
	private void setListeners(){
		xlv.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				Log.e("majie", "refresh");
				taskBeans.clear();
				focusSkip=0;
				queryFocusTasks(FINISH_REFRESHING);
			}
			
			@Override
			public void onLoadMore() {
				focusSkip=taskBeans.size();
				queryFocusTasks(FINISH_LOADING);
			}
		});
		
		
		xlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(getActivity(), AssignTaskActiviity.class);
				intent.putExtra("bean", taskBeans.get(arg2-1));
				getActivity().startActivity(intent);
			}
		});
	}
	
	private void queryFocusTasks(final int handle){
		synchronized (MyTaskFragment.this) {
			BmobQuery<TaskBean> lauchQuery	 = new BmobQuery<TaskBean>();
			lauchQuery.addWhereEqualTo("ownerName", user.getUsername());
			BmobQuery<TaskBean> executeQuery=new BmobQuery<TaskBean>();
			executeQuery.addWhereEqualTo("executor", user.getUsername());
			List<BmobQuery<TaskBean>> queries=new ArrayList<BmobQuery<TaskBean>>();
			queries.add(executeQuery);
			queries.add(lauchQuery);
			
			BmobQuery< TaskBean> query=new BmobQuery<TaskBean>();
			query.addWhereNotEqualTo("status", TaskBean.STATUS_CLOSED);
			query.or(queries);
			
//			lauchQuery.setLimit(10);
//			lauchQuery.setSkip(focusSkip);
			query.findObjects(getActivity(), new FindListener<TaskBean>() {

				@Override
				public void onSuccess(List<TaskBean> object) {
//					Log.e("majie", "查询成功：共"+object.size()+"条数据。");
					oldSize=taskBeans.size();
					focusSkip+=object.size();
					taskBeans.addAll(object);
					
					synchronized (MyTaskFragment.this) {
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
					synchronized (MyTaskFragment.this) {
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
	
	
	private void setAdapter(){
		adapter=new MyTaskAdapter(getActivity(), taskBeans);
		xlv.setAdapter(adapter);
	}

	@Override
	public void update(String executor) {
//		ShowToast("execute");
		taskBeans.clear();
		focusSkip=0;
		queryFocusTasks(FINISH_REFRESHING);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		AssignTaskAdapter.remove(this);
		PublishTaskActivity.remove(this);
	}
	
}
