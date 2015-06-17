package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.SubTaskBean;
import org.market.tool.util.ProgressUtil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;

public class ExecuteActivity extends ActivityBase {
	
	private SubTaskBean bean;
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
		
		bean=(SubTaskBean) getIntent().getSerializableExtra("bean");
		if(bean!=null){
			tvContent.setText(bean.getOriginTaskContent());
		}
	}
	
	@Override
	protected void setListeners() {
		super.setListeners();
		btComplete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateSubTaskBean();
			}
		});
	}
	
	/****
	 * close subtaskbean
	 */
	private void updateSubTaskBean() {
		ProgressUtil.showProgress(this, "");
		final SubTaskBean p = new SubTaskBean();
		p.setStatus(SubTaskBean.STATUS_COMPLETE);
		p.update(this, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ProgressUtil.closeProgress();
				ShowToast("close success");
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				ProgressUtil.closeProgress();
				ShowToast("close failure");
			}
		});
	}


}
