package org.market.tool.util;

import org.market.tool.view.LoadingDialog;
import org.market.tool.view.LoadingDialog.OnDialogBackListener;

import android.content.Context;

/**
 * ͨ�ý�����
 * 
 * @author liurun {mobile:15010123578, email:liurun_225@sina.com, qq:305760407}
 * @date 2014-05-23
 */
public class ProgressUtil {
	public static void showProgress(Context context, CharSequence message) {
		LoadingDialog.showDialog(context);
	}
	
	/*
	 * ����dialog back ���ؼ�����
	 */
	public static void showProgressWithBackListener(Context context, OnDialogBackListener listener) {
		LoadingDialog.showDialogWithListener(context, listener);
	}
	
	/**
	 * ����dialog back���ؼ��̡�
	 * @param context
	 */
	public static void showProgressNoBack(Context context) {
		LoadingDialog.showDialogNoBack(context);
	}
	
	public static void closeProgress() {
		LoadingDialog.closeDialog();
	}
}
