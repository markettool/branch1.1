package org.market.tool.ui;

import org.market.tool.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AccountActivity extends BaseActivity {
	
	private TextView tvTotalFund;
	private TextView tvRecharge;
	private TextView tvWithdrawCash;
	private double totalFund;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		initView();
		setListeners();
		initData();
	}

	protected void initView() {
		tvTotalFund=(TextView) findViewById(R.id.total_fund);
		tvRecharge=(TextView) findViewById(R.id.tv_recharge);
		tvWithdrawCash=(TextView) findViewById(R.id.tv_withdrawcash);
		
		initTopBarForLeft("�ҵ��˻�");
		
	}
	
	private void setListeners(){
		tvRecharge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startAnimActivity(RechargeActivity.class);
			}
		});
		tvWithdrawCash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startAnimActivity(WithdrawCashActivity.class);
			}
		});
		
	}

	protected void initData() {
		if(user==null){
			startAnimActivity(LoginActivity.class);
			finish();
			return;
		}
		totalFund=user.getFund();
		tvTotalFund.setText("�� "+totalFund+" Ԫ");
	}
	
//	private void updateUserAccount() {
//		if (user != null) {
//			User newUser = new User();
//			newUser.setFund(totalFund);
//			newUser.setObjectId(user.getObjectId());
//			newUser.update(this,new UpdateListener() {
//
//				@Override
//				public void onSuccess() {
////					toastMsg("�����û���Ϣ�ɹ�");
//				}
//
//				@Override
//				public void onFailure(int code, String msg) {
////					toastMsg("�����û���Ϣʧ��:" + msg);
//				}
//			});
//		} 
//	}


}
