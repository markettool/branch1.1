package org.market.tool.ui;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.AssignTaskAdapter;
import org.market.tool.bean.OriginTaskBean;
import org.market.tool.bean.SubTaskBean;
import org.market.tool.bean.User;
import org.market.tool.config.SystemConfig;
import org.market.tool.util.ProgressUtil;
import org.market.tool.view.xlist.XListView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class AssignTaskActiviity extends ActivityBase {
	
	private TextView tvTask;
	private TextView tvAll;
	private XListView xlv;
	private OriginTaskBean bean;
	private AssignTaskAdapter adapter;
	private Button btCloseTask;
	
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
		btCloseTask=(Button) findViewById(R.id.btn_close);
	}
	
	protected void setListeners(){
       xlv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
		}
	  });
       
       btCloseTask.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
//			if(bean.getExecutor()==null){
//				updateClosedStatus(bean);
//				return;
//			}
//			DialogUtil.show(AssignTaskActiviity.this, "任务执行者是否已经完成任务？", "如果已经执行完成，将把担保金额支付给对方；如果未执行完成，担保金额会退还给您？", "对方已经完成", "对方未完成", new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					ProgressUtil.showProgress(AssignTaskActiviity.this, "");
//					updateClosedStatus(bean);
//					queryUserByName(bean.getExecutor(), null);
//				}
//			}, new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					ProgressUtil.showProgress(AssignTaskActiviity.this, "");
//					updateClosedStatus(bean);
//					giveBack();
//				}
//			}, true);
		}
	  });
	}
	
	protected void initData(){
		
		bean=(OriginTaskBean) getIntent().getSerializableExtra("bean");
		if(bean==null){
			finish();
			return;
		}
		tvTask.setText(bean.getTaskContent());
		initTopBarForLeft("派发任务");
		queryApplicants();
		
//		if(bean.getApplicants()!=null){
//			adapter=new AssignTaskAdapter(this, bean);
//			xlv.setAdapter(adapter);
//		}
		
	}
	
	private void setExecutorText(){
		
//		if(!TextUtils.isEmpty(bean.getExecutor())){
//			tvAll.setText("您已经派发任务于 "+bean.getExecutor());
//			if(adapter!=null){
//				adapter.notifyDataSetInvalidated();
//			}
//			return;
//		}
	}

	public void update(String executor) {
		ShowLog("update");
//		bean.setExecutor(executor);
		setExecutorText();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterMyReceiver();
	}
	
	private void registerMyReceiver(){
		IntentFilter filter=new IntentFilter();
		filter.addAction(SystemConfig.INTENT_ENROLL);
		filter.addAction(SystemConfig.INTENT_ASSIGN_TASK_SUCCESS);
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
			
//			if(intent.getAction().equals(Config.INTENT_ENROLL)){
//				if(bean.getApplicants()==null){
//					bean.setApplicants(new ArrayList<String>());
//				}
//				bean.getApplicants().add(intent.getStringExtra("application"));
//				adapter=new AssignTaskAdapter(AssignTaskActiviity.this, bean);
//				xlv.setAdapter(adapter);
//			}else{
//				update(intent.getStringExtra("executor"));
//			}
		}
		
	}
	
//	private void updateClosedStatus(OriginTaskBean bean){
//		updateTaskBean(bean);
//		bean.setStatus(OriginTaskBean.STATUS_CLOSED);
//	}
	
	/**
	 * 更新对象
	 */
	private void updateTaskBean(OriginTaskBean bean) {
		final OriginTaskBean p2 = new OriginTaskBean();
		p2.setStatus(OriginTaskBean.STATUS_CLOSED);
		p2.update(AssignTaskActiviity.this, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
			}

			@Override
			public void onFailure(int code, String msg) {
			}
		});
	}
	
	private void giveBack(){
		User u=new User();
		u.setFund(user.getFund()+bean.getFund());
		u.update(AssignTaskActiviity.this, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				ProgressUtil.closeProgress();				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ProgressUtil.closeProgress();
			}
		});
	}
	
	@Override
	public void action(User user,String msg) {
		super.action(user,msg);
		User u=new User();
		u.setFund(user.getFund()+bean.getFund());
		u.update(this, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				ProgressUtil.closeProgress();				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ProgressUtil.closeProgress();
			}
		});
	}
	
	private void queryApplicants(){
		ProgressUtil.showProgress(this, "");
		BmobQuery<SubTaskBean> bmobQuery=new BmobQuery<SubTaskBean>();
		bmobQuery.addWhereEqualTo("originTaskId", bean.getObjectId());
		bmobQuery.findObjects(this, new FindListener<SubTaskBean>() {
			
			@Override
			public void onSuccess(List<SubTaskBean> stbs) {
				setLvData(stbs);
				ProgressUtil.closeProgress();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				ShowToast("net error");
				ProgressUtil.closeProgress();
			}
		});
	}
	
	private void setLvData(List<SubTaskBean> stbs){
		adapter=new AssignTaskAdapter(this, stbs);
		xlv.setAdapter(adapter);
	}

}
