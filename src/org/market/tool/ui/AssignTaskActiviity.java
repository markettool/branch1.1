package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.adapter.AssignTaskAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.inter.Observer;
import org.market.tool.view.xlist.XListView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AssignTaskActiviity extends ActivityBase implements Observer{
	
	private TextView tvTask;
	private TextView tvAll;
	private XListView xlv;
	private TaskBean bean;
	private AssignTaskAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigntask);
		
		initView();
		setListeners();
		initData();
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
		AssignTaskAdapter.attach(this);
		
		bean=(TaskBean) getIntent().getSerializableExtra("bean");
		if(bean==null){
			finish();
			return;
		}
		tvTask.setText(bean.getTaskContent());
		initTopBarForLeft("�ɷ�����");
		setExecutorText();
		
		if(bean.getApplicants()!=null){
			adapter=new AssignTaskAdapter(this, bean);
			xlv.setAdapter(adapter);
		}
		
	}
	
	private void setExecutorText(){
		
		if(!TextUtils.isEmpty(bean.getExecutor())){
			tvAll.setText("���Ѿ��ɷ������� "+bean.getExecutor());
			if(adapter!=null){
				adapter.notifyDataSetInvalidated();
			}
			return;
		}
	}

	@Override
	public void update(String executor) {
		ShowLog("update");
		bean.setExecutor(executor);
		setExecutorText();
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AssignTaskAdapter.remove(this);;
	}

}
