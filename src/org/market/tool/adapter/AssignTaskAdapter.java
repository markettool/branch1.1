package org.market.tool.adapter;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AssignTaskAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private TaskBean bean;
	private List<String> applictions;
	private Context context;
	private User user;
	
	public AssignTaskAdapter(Context context,TaskBean bean){
		this.context=context;
		this.bean=bean;
		this.applictions=bean.getApplicants();
		this.mInflater=LayoutInflater.from(context);
		
		user=BmobUser.getCurrentUser(context, User.class);
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
		ViewHolder holder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.assign_task_item, null);
			holder=new ViewHolder();
			holder.tvApplication=(TextView) convertView.findViewById(R.id.tv_application);
			holder.btAssign=(Button) convertView.findViewById(R.id.bt_assign);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvApplication.setText(applictions.get(arg0)+" ����ִ������");
		if(!TextUtils.isEmpty(bean.getExecutor())){
			holder.btAssign.setVisibility(View.GONE);
		}
		holder.btAssign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateExecutor(bean);
			}
		});
		return convertView;
	}
	
	class ViewHolder{
		TextView tvApplication;
		Button btAssign;
	}
	
	/**
	 * ���¶���
	 */
	private void updateExecutor(TaskBean bean) {
		final TaskBean p = new TaskBean();
		p.setExecutor(user.getUsername());
		p.update(context, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.e("majie", "���³ɹ���" + p.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.e("majie", "����ʧ�ܣ�" + msg);
			}
		});

	}

}
