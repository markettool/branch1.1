package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.util.ProgressUtil;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class AssignTaskAdapter extends MyBaseAdapter {
	
	private TaskBean bean;
	private List<String> applictions;
	
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
		final int position=arg0;
		holder.tvApplication.setText(applictions.get(arg0)+" 申请执行任务");
		if(!TextUtils.isEmpty(bean.getExecutor())){
			holder.btAssign.setVisibility(View.GONE);
		}
		holder.btAssign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateExecutor(bean,applictions.get(position));
			}
		});
		return convertView;
	}
	
	class ViewHolder{
		TextView tvApplication;
		Button btAssign;
	}
	
	/**
	 * 更新对象
	 */
	private void updateExecutor(TaskBean bean,final String username) {
		ProgressUtil.showProgress(mContext, "");
		final TaskBean p = new TaskBean();
		p.setExecutor(username);
		p.update(mContext, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				ShowLog("更新成功");
				queryUserByName(username, user.getUsername()+" 指定你执行任务");
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowLog("更新失败：" + msg);
			}
		});

	}
	
}
