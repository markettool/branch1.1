package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.TaskBean;
import org.market.tool.view.xlist.XListView;
import org.market.tool.view.xlist.XListView.IXListViewListener;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AssignTaskActiviity extends ActivityBase {
	
	private TextView tvTask;
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
			return;
		}
		tvTask.setText(bean.getTaskContent());
	}
	


}
