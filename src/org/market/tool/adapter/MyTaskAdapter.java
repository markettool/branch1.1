package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;
import org.market.tool.util.BitmapHelp;
import org.market.tool.util.ProgressUtil;
import org.market.tool.view.CircleImageView;
import org.market.tool.view.DialogUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

public class MyTaskAdapter extends MyBaseAdapter{
	private List<TaskBean> beans;
	private TaskBean selectedBean;
	
	public MyTaskAdapter(Context context, List<TaskBean> beans) {
		super(context);
		this.beans = beans;
		
		bitmapUtils=BitmapHelp.getBitmapUtils(context);
	}

	@Override
	public int getCount() {
		return beans.size();
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.my_task_item, null);
			holder = new ViewHolder();
			holder.ivOwnerPic = (CircleImageView) convertView.findViewById(R.id.user_pic);
			holder.tvOwnername = (TextView) convertView.findViewById(R.id.user_name);
			holder.tvTaskContent = (TextView) convertView.findViewById(R.id.opera_content);

			holder.ivTaskPic = (ImageView) convertView.findViewById(R.id.opera_pic);
			holder.btCloseTask=(Button) convertView.findViewById(R.id.close_task);
			holder.tvExecuters=(TextView) convertView.findViewById(R.id.tv_executers);
			holder.tvCategory=(TextView) convertView.findViewById(R.id.category);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final int position = arg0;
		try {

			if (position < beans.size()) {
				holder.tvOwnername.setText(beans.get(position).getOwnerName());
				holder.tvTaskContent.setText(beans.get(position).getTaskContent());

				TaskBean bean=beans.get(position);
				BmobFile ownerPic=bean.getOwnerPic();
				if(ownerPic!=null){
					ownerPic.loadImageThumbnail(mContext, holder.ivOwnerPic, 60, 60);
				}else{
					holder.ivOwnerPic.setImageResource(R.drawable.wwj_748);
				}
				
				BmobFile operaPic=bean.getTaskPic();
				if(operaPic!=null){
					bitmapUtils.display(holder.ivTaskPic, operaPic.getFileUrl(mContext));
				}else{
					holder.ivTaskPic.setImageBitmap(null);
				}
				if(bean.getApplicants()!=null&&bean.getApplicants().size()!=0){
					holder.tvExecuters.setText(bean.getApplicants().get(0)+" ��"+bean.getApplicants().size()+"�� ����ִ������");
				}
				
			}
		} catch (Exception e) {

//			Log.e("majie", e.getMessage());
		}
		
		switch (beans.get(position).getStatus()) {
		case TaskBean.STATUS_NOT_BEGIN:
			holder.btCloseTask.setVisibility(View.VISIBLE);
//			holder.btCloseTask.setEnabled(true);
//			holder.btCloseTask.setText("�ر�����");
			break;

		case TaskBean.STATUS_CLOSED:
			holder.btCloseTask.setVisibility(View.GONE);
			break;
		}
		
		if(beans.get(position).getOwnerName().equals(user.getUsername())){
			holder.tvCategory.setText("�ҷ����");
			holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.blue_press));
		}else{
			holder.tvCategory.setText("��ִ�е�");
			holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.orange));
		    holder.btCloseTask.setVisibility(View.GONE);
		}
		
		
		holder.btCloseTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(beans.get(position).getExecutor()==null){
					updateClosedStatus(beans.get(position));
					return;
				}
				DialogUtil.show(mContext, "����ִ�����Ƿ��Ѿ��������", "����Ѿ�ִ����ɣ����ѵ������֧�����Է������δִ����ɣ����������˻�������", "�Է��Ѿ����", "�Է�δ���", new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ProgressUtil.showProgress(mContext, "");
						selectedBean=beans.get(position);
						updateClosedStatus(beans.get(position));
						queryUserByName(beans.get(position).getExecutor(), null);
					}
				}, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ProgressUtil.showProgress(mContext, "");
						selectedBean=beans.get(position);
						updateClosedStatus(beans.get(position));
						giveBack();
					}
				}, true);
				
			}
		});

		return convertView;
	}
	
	private void updateClosedStatus(TaskBean bean){
		updateTaskBean(bean);
		bean.setStatus(TaskBean.STATUS_CLOSED);
		notifyDataSetChanged();
	}
	
	class ViewHolder {
		CircleImageView ivOwnerPic;
		TextView tvOwnername;
		TextView tvCategory;
		TextView tvTaskContent;
		ImageView ivTaskPic;
		Button btCloseTask;
		TextView tvExecuters;
	}
	
	/**
	 * ���¶���
	 */
	private void updateTaskBean(TaskBean bean) {
		final TaskBean p2 = new TaskBean();
		p2.setStatus(TaskBean.STATUS_CLOSED);
		p2.update(mContext, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
//				Log.e("majie", "���³ɹ���" + p2.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
//				Log.e("majie", "����ʧ�ܣ�" + msg);
			}
		});

	}
	
	@Override
	public void action(User user,String msg) {
		super.action(user,msg);
		User u=new User();
		u.setFund(user.getFund()+selectedBean.getFund());
		u.update(mContext, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				ProgressUtil.closeProgress();				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ProgressUtil.closeProgress();
			}
		});
	}
	
	private void giveBack(){
		User u=new User();
		u.setFund(user.getFund()+selectedBean.getFund());
		u.update(mContext, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				ProgressUtil.closeProgress();				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ProgressUtil.closeProgress();
			}
		});
	}

}
