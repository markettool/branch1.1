package org.market.tool.bean;

import cn.bmob.v3.BmobObject;

public class WithdrawCashBean extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String account;
	
	private double withdrawFund;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public double getWithdrawFund() {
		return withdrawFund;
	}

	public void setWithdrawFund(double withdrawFund) {
		this.withdrawFund = withdrawFund;
	}

}
