package org.market.tool.ui;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.MyLauncherTaskAdapter;
import org.market.tool.bean.OriginTaskBean;
import org.market.tool.view.xlist.XListView;
import org.market.tool.view.xlist.XListView.IXListViewListener;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
public class MyLauncherTaskActivity extends BaseActivity{
	
	private XListView xlv;
	
	private MyLauncherTaskAdapter adapter;
	
	public static final int FINISH_REFRESHING=0;
	public static final int FINISH_LOADING=1;
	
	private List<OriginTaskBean> taskBeans=new ArrayList<OriginTaskBean>();
	
	private int oldSize=0;
	
	private BroadcastReceiver receiver;
	
	private void setListeners(){
		xlv.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				update();
			}
			
			@Override
			public void onLoadMore() {
				taskBeans.size();
				queryOriginTasks(FINISH_LOADING);
			}
		});
		
		
		xlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				OriginTaskBean bean=taskBeans.get(arg2-1);
				Intent intent=null;
				if(bean.getOwnerName().equals(user.getUsername())){
					 intent=new Intent(MyLauncherTaskActivity.this, AssignTaskActiviity.class);
				}else{
					 intent=new Intent(MyLauncherTaskActivity.this, ExecuteActivity.class);
				}
				intent.putExtra("bean", taskBeans.get(arg2-1));
				startActivity(intent);
			}
		});
	}
	
	private void queryOriginTasks(final int handle){
		synchronized (MyLauncherTaskActivity.this) {
			BmobQuery<OriginTaskBean> lauchQuery	 = new BmobQuery<OriginTaskBean>();
			lauchQuery.addWhereEqualTo("ownerName", user.getUsername());
//			BmobQuery<OriginTaskBean> executeQuery=new BmobQuery<OriginTaskBean>();
//			executeQuery.addWhereEqualTo("executor", user.getUsername());
			List<BmobQuery<OriginTaskBean>> queries=new ArrayList<BmobQuery<OriginTaskBean>>();
//			queries.add(executeQuery);
			queries.add(lauchQuery);
			
			BmobQuery< OriginTaskBean> query=new BmobQuery<OriginTaskBean>();
			query.addWhereNotEqualTo("status", OriginTaskBean.STATUS_CLOSED);
			query.or(queries);
			
//			lauchQuery.setLimit(10);
//			lauchQuery.setSkip(focusSkip);
			query.findObjects(this, new FindListener<OriginTaskBean>() {

				@Override
				public void onSuccess(List<OriginTaskBean> object) {
//					Log.e("majie", "查询成功：共"+object.size()+"条数据。");
					oldSize=taskBeans.size();
					object.size();
					taskBeans.addAll(object);
					
					synchronized (MyLauncherTaskActivity.this) {
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
//					Log.e("majie","查询失败："+msg);
					synchronized (MyLauncherTaskActivity.this) {
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
		adapter=new MyLauncherTaskAdapter(this, taskBeans);
		xlv.setAdapter(adapter);
	}

	public void update() {
//		ShowToast("execute");
		taskBeans.clear();
		queryOriginTasks(FINISH_REFRESHING);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylauncher);
		
		initTopBarForLeft("我发起的任务");
		xlv=(XListView) findViewById(R.id.lv);
		
		setAdapter();
		setListeners();
		queryOriginTasks(FINISH_REFRESHING);
//		registerMyReceiver();
	}
	
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
////		unregisterMyReceiver();
//	}
	
//	private void registerMyReceiver(){
//		IntentFilter filter=new IntentFilter();
//		filter.addAction(SystemConfig.INTENT_ENROLL);
//		filter.addAction(SystemConfig.INTENT_PUBLISH_TASK_SUCCESS);
//		filter.addAction(SystemConfig.INTENT_ASSIGN_TASK_SUCCESS);
////		receiver=new MyBroadCastReceiver();
//		registerReceiver(receiver, filter);
//	}
	
//	private void unregisterMyReceiver(){
//		if(receiver!=null){
//			unregisterReceiver(receiver);
//		}
//	}
	
//	class MyBroadCastReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			update();
//		}
//		
//	}
	
}
