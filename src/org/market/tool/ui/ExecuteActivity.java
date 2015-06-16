package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.OriginTaskBean;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ExecuteActivity extends ActivityBase {
	
	private OriginTaskBean bean;
	private TextView tvContent;
	
	private Button btComplete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_execute);
		
		initViews();
		setListeners();
		initData();
	}
	
	@Override
	protected void initViews() {
		super.initViews();
		tvContent=(TextView) findViewById(R.id.tv_task);
		btComplete=(Button) findViewById(R.id.btn_complete);
	}
	
	@Override
	protected void initData() {
		super.initData();
		
		initTopBarForLeft("Ö´ÐÐÈÎÎñ");
		
		bean=(OriginTaskBean) getIntent().getSerializableExtra("bean");
		if(bean!=null){
			tvContent.setText(bean.getTaskContent());
		}
	}
	
	@Override
	protected void setListeners() {
		super.setListeners();
		btComplete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
	}


}
