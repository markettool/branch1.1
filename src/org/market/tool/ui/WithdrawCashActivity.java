package org.market.tool.ui;

import org.market.tool.R;
import org.market.tool.bean.User;
import org.market.tool.bean.WithdrawCashBean;
import org.market.tool.util.ProgressUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class WithdrawCashActivity extends BaseActivity {
	private Button btSubmit;
	private EditText etWithdrawAccount;
	private EditText etWithdrawFund;
	private double fund;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawcash);
		
		initViews();
		setListeners();
		
		initTopBarForLeft("����");
	}
	
	private void initViews(){
		btSubmit=(Button) findViewById(R.id.submit);
		etWithdrawFund=(EditText) findViewById(R.id.et_withdrawcash_fund);
		etWithdrawAccount=(EditText) findViewById(R.id.et_withdrawcash_account);
	}
	
	private void setListeners(){
		btSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(TextUtils.isEmpty(etWithdrawFund.getText().toString())){
					ShowToast("����Ϊ��");
					return;
				}
				if(TextUtils.isEmpty(etWithdrawAccount.getText().toString())){
					ShowToast("����Ϊ��");
					return;
				}
				fund=new Double(etWithdrawFund.getText().toString());
				if(fund>user.getFund()){
					ShowToast("�������˻�������ʧ��");
					return;
				}
				if(fund<1){
					ShowToast("С��1Ԫ��������");
					return;
				}
				insertWithDrawBean();
				
			}
		});
	}
	
	private void insertWithDrawBean(){
		ProgressUtil.showProgress(this, "");
		WithdrawCashBean bean=new WithdrawCashBean();
		bean.setAccount(etWithdrawAccount.getText().toString());
		bean.setWithdrawFund(fund);
		bean.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				updateFund();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ShowToast("����ʧ��");
				ShowLog(arg1);
				ProgressUtil.closeProgress();
			}
		});
	}
	
	/**
	 * ���¶���
	 */
	private void updateFund() {
		
		final User u = new User();
		u.setFund(user.getFund()-fund);
		u.update(this, user.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowLog("success");
				ShowToast("���ֳɹ�");
				ProgressUtil.closeProgress();
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowLog("����ʧ�ܣ�" + msg);
				ShowToast("����ʧ��");
				ProgressUtil.closeProgress();
			}
		});

	}


}
