package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;
import org.market.tool.util.ProgressUtil;

import cn.bmob.v3.listener.UpdateListener;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RechargeActivity extends BaseActivity {
	private Button btSubmit;
	private EditText etRechargeFund;
	private double fund;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		
		initViews();
		setListeners();
		
		initTopBarForLeft("充值");
	}
	
	private void initViews(){
		btSubmit=(Button) findViewById(R.id.submit);
		etRechargeFund=(EditText) findViewById(R.id.et_recharge_fund);
	}
	
	private void setListeners(){
		btSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String etFund=etRechargeFund.getText().toString();
				if(TextUtils.isEmpty(etFund)){
					ShowToast("输入为空");
					return;
				}
				fund=new Double(etFund);
				new BmobPay(RechargeActivity.this).pay(fund, "购买金币", new PayListener() {
					
					@Override
					public void unknow() {
						
					}
					
					@Override
					public void succeed() {
						ShowToast("充值失败");
						updateFund();
						
					}
					
					@Override
					public void orderId(String arg0) {
						
					}
					
					@Override
					public void fail(int arg0, String arg1) {
						ShowToast("充值失败");
						
					}
				});
			}
		});
	}
	
	/**
	 * 更新对象
	 */
	private void updateFund() {
		ProgressUtil.showProgress(this, "");
		final User u = new User();
		u.setFund(fund);
		u.update(this, user.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowLog("success");
				ProgressUtil.closeProgress();
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowLog("更新失败：" + msg);
				ProgressUtil.closeProgress();
			}
		});

	}

}
