package org.market.tool.adapter;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.adapter.base.ViewHolder;
import org.market.tool.bean.Message;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;
import org.market.tool.config.Config;
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
	
	private TaskBean bean;
	private List<String> applictions;
	private String executor;
	
	public AssignTaskAdapter(Context context,TaskBean bean){
		super(context);
		this.bean=bean;
		this.applictions=bean.getApplicants();
		this.mInflater=LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		return applictions.size();
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
		tvApplication.setText(applictions.get(arg0)+" ����ִ������");
		if(bean.getExecutors()!=null&&bean.getExecutors().contains(applictions.get(arg0))){
			btAssign.setVisibility(View.GONE);
		}
		btAssign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				executor=applictions.get(position);
				updateExecutor(bean,applictions.get(position));
			}
		});
		return convertView;
	}
	
	/**
	 * ���¶���
	 */
	private void updateExecutor(TaskBean bean,final String username) {
		ProgressUtil.showProgress(mContext, "");
		final TaskBean p = new TaskBean();
		if(p.getExecutors()==null){
			p.setExecutors(new ArrayList<String>());
		}
		p.getExecutors().add(username);
		p.update(mContext, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowLog("���³ɹ�");
				Intent intent=new Intent(Config.INTENT_ASSIGN_TASK_SUCCESS);
				intent.putExtra("executor", executor);
				mContext.sendBroadcast(intent);
				
				ProgressUtil.closeProgress();
				queryUserByName(username, user.getUsername()+" ָ����ִ������");
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowLog("����ʧ�ܣ�" + msg);
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
