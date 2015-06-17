package org.market.tool.ui;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.MyExecuteTaskAdapter;
import org.market.tool.R;
import org.market.tool.bean.SubTaskBean;
import org.market.tool.view.xlist.XListView;
import org.market.tool.view.xlist.XListView.IXListViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MyExecuteTaskActivity extends BaseActivity {
	
    private XListView xlv;
	
	private MyExecuteTaskAdapter adapter;
	
	public static final int FINISH_REFRESHING=0;
//	public static final int FINISH_LOADING=1;
	
	private List<SubTaskBean> stbs=new ArrayList<SubTaskBean>();
	
//	private int oldSize=0;
	
	private void setListeners(){
		xlv.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				update();
			}
			
			@Override
			public void onLoadMore() {
			}
		});
		
		
		xlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SubTaskBean stb=stbs.get(arg2-1);
				Intent intent=new Intent(MyExecuteTaskActivity.this, ExecuteActivity.class);
				intent.putExtra("bean", stb);
				startActivity(intent);
			}
		});
	}
	
	private void querySubTasks(final int handle){
		synchronized (MyExecuteTaskActivity.this) {
			BmobQuery<SubTaskBean> query	 = new BmobQuery<SubTaskBean>();
			query.addWhereEqualTo("username", user.getUsername());
			query.findObjects(this, new FindListener<SubTaskBean>() {

				@Override
				public void onSuccess(List<SubTaskBean> object) {
//					oldSize=stbs.size();
					stbs.addAll(object);
					
						switch (handle) {
//						case FINISH_LOADING:
//							if(oldSize<taskBeans.size()){
//								xlv.setSelection(oldSize);
//							}
//							xlv.stopLoadMore();
//							break;

						default:
							xlv.stopRefresh();
//							if(stbs.size()>8){
//								xlv.setPullLoadEnable(true);
//							}
							break;
						}
						adapter.notifyDataSetChanged();
					}

				@Override
				public void onError(int code, String msg) {
						switch (handle) {
//						case FINISH_LOADING:
//							xlv.stopLoadMore();
//							break;

						default:
							xlv.stopRefresh();
							break;
						}
						adapter.notifyDataSetChanged();
				}
			});
		}
		
	}
	
	
	private void setAdapter(){
		adapter=new MyExecuteTaskAdapter(this, stbs);
		xlv.setAdapter(adapter);
	}

	private void update() {
		stbs.clear();
		querySubTasks(FINISH_REFRESHING);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylauncher);
		
		initTopBarForLeft("我执行的任务");
		xlv=(XListView) findViewById(R.id.lv);
		xlv.setPullRefreshEnable(true);
		
		setAdapter();
		setListeners();
		querySubTasks(FINISH_REFRESHING);
	}

}
