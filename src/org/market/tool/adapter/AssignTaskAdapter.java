package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.adapter.base.ViewHolder;
import org.market.tool.bean.Message;
import org.market.tool.bean.SubTaskBean;
import org.market.tool.bean.User;
import org.market.tool.config.SystemConfig;
import org.market.tool.util.MessageUtil;
import org.market.tool.util.ProgressUtil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;

public class AssignTaskAdapter extends MyBaseAdapter{
	
	private List<SubTaskBean> subTaskBeans;
	private String executor;
	
	public AssignTaskAdapter(Context context, List<SubTaskBean> subTaskBeans){
		super(context);
		this.subTaskBeans=subTaskBeans;
		this.mInflater=LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		return subTaskBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.assign_task_item, null);
		}
		
		TextView tvApplication=ViewHolder.get(convertView, R.id.tv_application);
		Button btAssign=ViewHolder.get(convertView, R.id.bt_assign);
		final int position=arg0;
		final SubTaskBean stb=subTaskBeans.get(position);
		tvApplication.setText(stb.getUsername()+" 报名执行任务");
		if(stb.getStatus()!=SubTaskBean.STATUS_ENROLL){
			btAssign.setVisibility(View.GONE);
		}
		btAssign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateSubStatus(stb);
			}
		});
		return convertView;
	}
	
	/**
	 * 更新对象
	 */
	private void updateSubStatus(final SubTaskBean stb) {
		ProgressUtil.showProgress(mContext, "");
		final SubTaskBean p = new SubTaskBean();
		p.setStatus(SubTaskBean.STATUS_PERMIT);
		p.update(mContext, stb.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowLog("更新成功");
				Intent intent=new Intent(SystemConfig.INTENT_ASSIGN_TASK_SUCCESS);
				intent.putExtra("executor", executor);
				mContext.sendBroadcast(intent);
				
				ProgressUtil.closeProgress();
				queryUserByName(stb.getUsername(), user.getUsername()+" 指定你执行任务");
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowLog("更新失败：" + msg);
				ProgressUtil.closeProgress();
			}
		});

	}
	
	@Override
	public void action(User user, String msg) {
		super.action(user, msg);
		String json=MessageUtil.toMessageJson(Message.ASSIGN, msg, user.getUsername(), user.getNick());
		push(user, json);
	}
	
}
