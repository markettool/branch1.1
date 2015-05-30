package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.User;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class AccountActivity extends BaseActivity {
	
	private TextView tvTotalFund;
	private TextView tvRecharge;
	private User myUser;
	private float totalFund;
	
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
		
		initTopBarForLeft("�ҵ��˻�");
		
	}
	
	private void setListeners(){
		tvRecharge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new BmobPay(AccountActivity.this).pay(0.1d, "test", new PayListener() {
					
					@Override
					public void unknow() {
						
					}
					
					@Override
					public void succeed() {
						
					}
					
					@Override
					public void orderId(String arg0) {
						
					}
					
					@Override
					public void fail(int arg0, String arg1) {
						
					}
				});
			}
		});
		
//		mBtnTitleRight.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(totalFund<10){
//					toastMsg("����10Ԫ��������");
//					return;
//				}else{
////					startActivity(c);
//					Intent intent=new Intent(AccountActivity.this, GetCashActivity.class);
//					intent.putExtra("fund", totalFund);
//					startActivity(intent);
//				}
//			}
//		});
		
	}

	protected void initData() {
		myUser=BmobUser.getCurrentUser(this, User.class);
		if(myUser==null){
			startAnimActivity(LoginActivity.class);
			finish();
			return;
		}
		totalFund=myUser.getFund();
		tvTotalFund.setText("�� "+totalFund+" Ԫ");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		myUser=BmobUser.getCurrentUser(this, User.class);
	}
	
	private void updateUserAccount() {
		if (myUser != null) {
			User newUser = new User();
			newUser.setFund(totalFund);
			newUser.setObjectId(myUser.getObjectId());
			newUser.update(this,new UpdateListener() {

				@Override
				public void onSuccess() {
//					toastMsg("�����û���Ϣ�ɹ�");
				}

				@Override
				public void onFailure(int code, String msg) {
//					toastMsg("�����û���Ϣʧ��:" + msg);
				}
			});
		} 
	}


}
