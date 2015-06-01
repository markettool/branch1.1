package org.market.tool.ui;

import java.util.ArrayList;

import org.market.tool.R;
import org.market.tool.adapter.AssignTaskAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.config.Config;
import org.market.tool.view.xlist.XListView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AssignTaskActiviity extends ActivityBase {
	
	private TextView tvTask;
	private TextView tvAll;
	private XListView xlv;
	private TaskBean bean;
	private AssignTaskAdapter adapter;
	
	private BroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigntask);
		
		initView();
		setListeners();
		initData();
		registerMyReceiver();
	}
	
	private void initView(){
		tvTask=(TextView) findViewById(R.id.tv_task);
		xlv=(XListView) findViewById(R.id.lv);
		xlv.setPullRefreshEnable(false);
		tvAll=(TextView) findViewById(R.id.tv_all_appliction);
	}
	
	private void setListeners(){
       xlv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
		}
	  });
	}
	
	private void initData(){
		
		bean=(TaskBean) getIntent().getSerializableExtra("bean");
		if(bean==null){
			finish();
			return;
		}
		tvTask.setText(bean.getTaskContent());
		initTopBarForLeft("派发任务");
		setExecutorText();
		
		if(bean.getApplicants()!=null){
			adapter=new AssignTaskAdapter(this, bean);
			xlv.setAdapter(adapter);
		}
		
	}
	
	private void setExecutorText(){
		
		if(!TextUtils.isEmpty(bean.getExecutor())){
			tvAll.setText("您已经派发任务于 "+bean.getExecutor());
			if(adapter!=null){
				adapter.notifyDataSetInvalidated();
			}
			return;
		}
	}

	public void update(String executor) {
		ShowLog("update");
		bean.setExecutor(executor);
		setExecutorText();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterMyReceiver();
	}
	
	private void registerMyReceiver(){
		IntentFilter filter=new IntentFilter();
		filter.addAction(Config.INTENT_ENROLL);
		filter.addAction(Config.INTENT_ASSIGN_TASK_SUCCESS);
		receiver=new MyBroadCastReceiver();
		registerReceiver(receiver, filter);
	}
	
	private void unregisterMyReceiver(){
		if(receiver!=null){
			unregisterReceiver(receiver);
		}
	}
	
	class MyBroadCastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			
			if(intent.getAction().equals(Config.INTENT_ENROLL)){
				if(bean.getApplicants()==null){
					bean.setApplicants(new ArrayList<String>());
				}
				bean.getApplicants().add(intent.getStringExtra("application"));
				adapter=new AssignTaskAdapter(AssignTaskActiviity.this, bean);
				xlv.setAdapter(adapter);
			}else{
				update(intent.getStringExtra("executor"));
			}
		}
		
	}

}
