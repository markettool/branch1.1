package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.bean.TaskBean;
import org.market.tool.util.BitmapHelp;
import org.market.tool.view.CircleImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

import com.lidroid.xutils.BitmapUtils;

public class MyTaskAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private List<TaskBean> beans;
	private Context context;
	
	private BitmapUtils bitmapUtils;
	
	public MyTaskAdapter(Context context, List<TaskBean> beans) {
		this.context = context;
		this.beans = beans;
		this.mInflater = LayoutInflater.from(context);
		
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
					ownerPic.loadImageThumbnail(context, holder.ivOwnerPic, 60, 60);
				}else{
					holder.ivOwnerPic.setImageResource(R.drawable.wwj_748);
				}
				
				BmobFile operaPic=bean.getTaskPic();
				if(operaPic!=null){
					bitmapUtils.display(holder.ivTaskPic, operaPic.getFileUrl(context));
				}else{
					holder.ivTaskPic.setImageBitmap(null);
				}
				if(bean.getApplicants()!=null&&bean.getApplicants().size()!=0){
					holder.tvExecuters.setText(bean.getApplicants().get(0)+" 等"+bean.getApplicants().size()+"人 报名执行任务");
				}
				
			}
		} catch (Exception e) {

			Log.e("majie", e.getMessage());
		}
		
		switch (beans.get(position).getStatus()) {
		case 0:
			holder.btCloseTask.setEnabled(true);
			holder.btCloseTask.setText("关闭任务");
			break;

		case 1:
			holder.btCloseTask.setEnabled(false);
			holder.btCloseTask.setText("任务已关闭");
//			holder.btCloseTask.setBackgroundResource(R.drawable.btn_login_p);
//			holder.btCloseTask.setVisibility(View.GONE);
			break;
		}
		
		holder.btCloseTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				updateTaskBean(beans.get(position));
				beans.get(position).setStatus(1);
				notifyDataSetChanged();
			}
		});

		return convertView;
	}
	
	class ViewHolder {
		CircleImageView ivOwnerPic;
		TextView tvOwnername;
		TextView tvTaskContent;
		ImageView ivTaskPic;
		Button btCloseTask;
		TextView tvExecuters;
	}
	
	/**
	 * 更新对象
	 */
	private void updateTaskBean(TaskBean bean) {
		final TaskBean p2 = new TaskBean();
		p2.setStatus(1);
		p2.update(context, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.e("majie", "更新成功：" + p2.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.e("majie", "更新失败：" + msg);
			}
		});

	}

}
