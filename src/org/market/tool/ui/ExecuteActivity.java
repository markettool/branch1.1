package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.TaskBean;

import android.os.Bundle;

public class ExecuteActivity extends ActivityBase {
	
	private TaskBean bean;
	
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
	}
	
	@Override
	protected void initData() {
		super.initData();
	}
	
	@Override
	protected void setListeners() {
		super.setListeners();
	}


}
