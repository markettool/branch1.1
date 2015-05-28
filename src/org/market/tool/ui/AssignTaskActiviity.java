package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.adapter.AssignTaskAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.view.xlist.XListView;

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
		TaskBean bean=(TaskBean) getIntent().getSerializableExtra("bean");
		if(bean==null){
			finish();
			return;
		}
		initTopBarForLeft("派发任务");
		tvTask.setText(bean.getTaskContent());
		if(!TextUtils.isEmpty(bean.getExecutor())){
			tvAll.setText("您已经派发任务于 "+bean.getExecutor());
			return;
		}
		
		AssignTaskAdapter adapter=new AssignTaskAdapter(this, bean);
		xlv.setAdapter(adapter);
	}
	


}
